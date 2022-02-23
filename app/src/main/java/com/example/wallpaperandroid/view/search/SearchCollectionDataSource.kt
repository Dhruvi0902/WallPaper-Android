package com.example.wallpaperandroid.view.search

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.wallpaperandroid.data.model.searchCollection.ResultsItem
import kotlinx.coroutines.CoroutineScope

class SearchCollectionDataSource(private val scope: CoroutineScope, private val query:String): DataSource.Factory<Int, ResultsItem>() {

    private var responseStatus=MutableLiveData<SearchCollectionPagedListDataSource>()
    override fun create(): DataSource<Int, ResultsItem> {
       val dataSource=SearchCollectionPagedListDataSource(scope,query)
        responseStatus.postValue(dataSource)
        return dataSource
    }
}