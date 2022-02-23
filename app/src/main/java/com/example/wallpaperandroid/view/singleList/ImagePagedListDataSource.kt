package com.example.wallpaperandroid.view.singleList

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.example.wallpaperandroid.Utils
import com.example.wallpaperandroid.WallPaperApplication
import com.example.wallpaperandroid.data.database.ImageDatabase
import com.example.wallpaperandroid.data.model.SplashResponse
import com.example.wallpaperandroid.data.network.ApiUrls
import com.example.wallpaperandroid.data.network.ResponseStatus
import com.example.wallpaperandroid.data.network.safeApiCall
import com.example.wallpaperandroid.framework.ApiInterface
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject

class ImagePagedListDataSource(private val scope: CoroutineScope):PageKeyedDataSource<Int,SplashResponse>(), KoinComponent {

    private val apiInterface: ApiInterface by inject()
    private val imageDatabase: ImageDatabase by inject()
    private var dataStatus = MutableLiveData<ResponseStatus<List<SplashResponse>>>()

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, SplashResponse>) {
        scope.launch(Dispatchers.IO) {
            if (Utils.isConnected(WallPaperApplication.applicationContext())) {
                dataStatus.postValue(ResponseStatus.loading(null))
                val responseStatus = safeApiCall(call = {
                    apiInterface.getListData(ApiUrls.apiKey, 1, params.requestedLoadSize) })
                if (responseStatus.status == ResponseStatus.Status.SUCCESS) {
                    imageDatabase.getImageDao().addData(responseStatus.data!!)
                    dataStatus.postValue(ResponseStatus.success(responseStatus.data))
                    callback.onResult(responseStatus.data,null,2)
                } else if (responseStatus.status == ResponseStatus.Status.ERROR) {
                    dataStatus.postValue(ResponseStatus.error(responseStatus.message!!))
                }
            } else {
                dataStatus.postValue(ResponseStatus.error("No Network Available, Please put internet on"))
            }
        }

    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, SplashResponse>) {
        scope.launch(Dispatchers.IO) {
            if (Utils.isConnected(WallPaperApplication.applicationContext())) {
                dataStatus.postValue(ResponseStatus.loading(null))
                val responseStatus = safeApiCall(call = {
                    apiInterface.getListData(ApiUrls.apiKey, params.key, params.requestedLoadSize)
                })
                if (responseStatus.status == ResponseStatus.Status.SUCCESS) {
                    imageDatabase.getImageDao().addData(responseStatus.data!!)
                    dataStatus.postValue(ResponseStatus.success(responseStatus.data))
                    callback.onResult(responseStatus.data,params.key+1)
                } else if (responseStatus.status == ResponseStatus.Status.ERROR) {
                    dataStatus.postValue(ResponseStatus.error(responseStatus.message!!))
                }
            } else {
                dataStatus.postValue(ResponseStatus.error("No Network Available, Please put internet on"))
            }
        }

    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, SplashResponse>) {
//        no need
    }


}