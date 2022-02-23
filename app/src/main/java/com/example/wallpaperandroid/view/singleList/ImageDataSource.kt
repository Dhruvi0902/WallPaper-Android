package com.example.wallpaperandroid.view.singleList

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.wallpaperandroid.data.model.SplashResponse
import kotlinx.coroutines.CoroutineScope

class ImageDataSource(private val scope: CoroutineScope): DataSource.Factory<Int,SplashResponse>() {

    private var responseStatus=MutableLiveData<ImagePagedListDataSource>()
    override fun create(): DataSource<Int, SplashResponse> {
       val dataSource=ImagePagedListDataSource(scope)
        responseStatus.postValue(dataSource)
        return dataSource
    }
}