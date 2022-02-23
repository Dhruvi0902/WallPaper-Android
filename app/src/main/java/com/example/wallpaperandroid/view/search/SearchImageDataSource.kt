package com.example.wallpaperandroid.view.search

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.wallpaperandroid.data.model.SplashResponse
import com.example.wallpaperandroid.data.model.search.ResultsItem
import kotlinx.coroutines.CoroutineScope

class SearchImageDataSource(private val scope: CoroutineScope,private val query:String): DataSource.Factory<Int, ResultsItem>() {

    private var responseStatus=MutableLiveData<SearchImagePagedListDataSource>()
    override fun create(): DataSource<Int, ResultsItem> {
       val dataSource=SearchImagePagedListDataSource(scope,query)
        responseStatus.postValue(dataSource)
        return dataSource
    }
}