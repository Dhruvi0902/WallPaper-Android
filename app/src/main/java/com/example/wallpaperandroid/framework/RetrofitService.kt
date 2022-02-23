package com.example.wallpaperandroid.framework

import com.example.wallpaperandroid.Utils
import com.example.wallpaperandroid.WallPaperApplication
import com.example.wallpaperandroid.data.model.SplashResponse
import com.example.wallpaperandroid.data.model.collection.CollectionResponse
import com.example.wallpaperandroid.data.model.search.ResultsItem
import com.example.wallpaperandroid.data.model.search.SearchResponse
import com.example.wallpaperandroid.data.model.searchCollection.SearchCollection
import com.example.wallpaperandroid.data.network.ApiUrls
import com.facebook.stetho.okhttp3.StethoInterceptor
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*


object RetrofitService {



    val networkModuleDi = module {

        single {
            val interceptor = Interceptor { chain ->
                val originalResponse = chain.proceed(chain.request())
                if (Utils.isConnected(WallPaperApplication.applicationContext())) {
                    val maxAge = 60
                    return@Interceptor originalResponse.newBuilder()
                        .header("Cache-Control", "public, max-age=$maxAge").build()
                } else {
                    val maxStale = 60 * 60 * 24 * 28
                    return@Interceptor originalResponse.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=$maxStale")
                        .build()
                }
            }


            val okHttpClient: OkHttpClient =
                OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .addNetworkInterceptor(StethoInterceptor())
                    .build()



            Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(ApiUrls.baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        factory {
            (get<Retrofit>()).create<ApiInterface>(
                ApiInterface::class.java
            )
        }
    }


}

interface ApiInterface {
    @GET("photos/")
    suspend fun getListData(@Query("client_id") clientId: String, @Query("page") page: Int,
                            @Query("per_page") pageSize: Int) : Response<List<SplashResponse>>

    @GET("search/photos/")
    suspend fun getSearchedWallpaper(@Query("client_id") clientId: String,@Query("query") query: String, @Query("page") page: Int,
                            @Query("per_page") pageSize: Int) : Response<SearchResponse>

    @GET("search/collections/")
    suspend fun getSearchedCollections(@Query("client_id") clientId: String,@Query("query") query: String, @Query("page") page: Int,
                            @Query("per_page") pageSize: Int) : Response<SearchCollection>

    @GET("collections/")
    suspend fun getCollectionData(@Query("client_id") clientId: String, @Query("page") page: Int,
                        @Query("per_page") pageSize: Int) : Response<List<CollectionResponse>>

    @GET("collections/featured/")
    suspend fun getFeaturedCollectionData(@Query("client_id") clientId: String, @Query("page") page: Int,
                        @Query("per_page") pageSize: Int) : Response<List<CollectionResponse>>
}