package com.example.wallpaperandroid.view.search

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.example.wallpaperandroid.Utils
import com.example.wallpaperandroid.WallPaperApplication
import com.example.wallpaperandroid.data.model.search.ResultsItem
import com.example.wallpaperandroid.data.network.ApiUrls
import com.example.wallpaperandroid.data.network.ResponseStatus
import com.example.wallpaperandroid.data.network.safeApiCall
import com.example.wallpaperandroid.framework.ApiInterface
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject

class SearchImagePagedListDataSource(private val scope: CoroutineScope,private val string: String):PageKeyedDataSource<Int,ResultsItem>(), KoinComponent {

    private val apiInterface: ApiInterface by inject()
    private var dataStatus = MutableLiveData<ResponseStatus<List<ResultsItem>>>()

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, ResultsItem>) {
        scope.launch(Dispatchers.IO) {
            if (Utils.isConnected(WallPaperApplication.applicationContext())) {
                dataStatus.postValue(ResponseStatus.loading(null))
                val responseStatus = safeApiCall(call = {
                    apiInterface.getSearchedWallpaper(ApiUrls.apiKey,string, 1, params.requestedLoadSize) })
                if (responseStatus.status == ResponseStatus.Status.SUCCESS) {
                    dataStatus.postValue(ResponseStatus.success(responseStatus.data?.results) as ResponseStatus<List<ResultsItem>>?)
                    callback.onResult(responseStatus.data?.results as MutableList<ResultsItem>,null,2)
                } else if (responseStatus.status == ResponseStatus.Status.ERROR) {
                    dataStatus.postValue(ResponseStatus.error(responseStatus.message!!))
                }
            } else {
                dataStatus.postValue(ResponseStatus.error("No Network Available, Please put internet on"))
            }
        }

    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, ResultsItem>) {
        scope.launch(Dispatchers.IO) {
            if (Utils.isConnected(WallPaperApplication.applicationContext())) {
                dataStatus.postValue(ResponseStatus.loading(null))
                val responseStatus = safeApiCall(call = {
                    apiInterface.getSearchedWallpaper(ApiUrls.apiKey,string, params.key, params.requestedLoadSize)
                })
                if (responseStatus.status == ResponseStatus.Status.SUCCESS) {
                    dataStatus.postValue(ResponseStatus.success(responseStatus.data?.results) as ResponseStatus<List<ResultsItem>>?)
                    callback.onResult(responseStatus.data?.results as MutableList<ResultsItem>,params.key+1)
                } else if (responseStatus.status == ResponseStatus.Status.ERROR) {
                    dataStatus.postValue(ResponseStatus.error(responseStatus.message!!))
                }
            } else {
                dataStatus.postValue(ResponseStatus.error("No Network Available, Please put internet on"))
            }
        }

    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, ResultsItem>) {
//        no need
    }


}