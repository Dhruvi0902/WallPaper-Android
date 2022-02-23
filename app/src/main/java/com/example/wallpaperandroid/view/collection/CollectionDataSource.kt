package com.example.wallpaperandroid.view.collection

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.wallpaperandroid.data.model.collection.CollectionResponse
import kotlinx.coroutines.CoroutineScope

class CollectionDataSource(private val scope: CoroutineScope): DataSource.Factory<Int,CollectionResponse>() {

    private var responseStatus=MutableLiveData<CollectionPagedListDataSource>()
    override fun create(): DataSource<Int, CollectionResponse> {
       val dataSource=
           CollectionPagedListDataSource(
               scope
           )
        responseStatus.postValue(dataSource)
        return dataSource
    }
}