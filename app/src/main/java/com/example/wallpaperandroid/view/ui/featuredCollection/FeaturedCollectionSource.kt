package com.example.wallpaperandroid.view.ui.featuredCollection

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.wallpaperandroid.data.model.collection.CollectionResponse
import com.example.wallpaperandroid.view.collection.CollectionPagedListDataSource
import kotlinx.coroutines.CoroutineScope

class FeaturedCollectionSource(private val scope: CoroutineScope): DataSource.Factory<Int,CollectionResponse>() {

    private var responseStatus=MutableLiveData<FeaturedCollectionDataSource>()
    override fun create(): DataSource<Int, CollectionResponse> {
       val dataSource= FeaturedCollectionDataSource(scope)
        responseStatus.postValue(dataSource)
        return dataSource
    }
}