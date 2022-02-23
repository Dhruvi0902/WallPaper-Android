package com.example.wallpaperandroid.view.ui.featuredCollection

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.wallpaperandroid.data.database.ImageDatabase
import com.example.wallpaperandroid.data.model.collection.CollectionResponse
import com.example.wallpaperandroid.data.network.ResponseStatus
import com.example.wallpaperandroid.view.collection.CollectionDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import org.koin.core.inject

class FeaturedCollectionViewModel : ViewModel() {
    private val pageSize = 15
    private val parentJob = Job()
    private val scope = CoroutineScope(Dispatchers.IO + parentJob)

    var responseStatusLiveData: LiveData<ResponseStatus<List<CollectionResponse>>>? =null
    private val collectionDataSource: FeaturedCollectionSource = FeaturedCollectionSource(scope)


    fun getOnlineCollection(): LiveData<PagedList<CollectionResponse>> {
        // Get the paged list
        val defaultConfig = PagedList.Config.Builder()
            .setEnablePlaceholders(true)
            .setPrefetchDistance(5)
            .setInitialLoadSizeHint(10)
            .setPageSize(pageSize)
            .build()


        // Get the paged list
        return LivePagedListBuilder(collectionDataSource, defaultConfig)
            .build()
    }

    override fun onCleared() {
        super.onCleared()
        scope.cancel()
    }
}