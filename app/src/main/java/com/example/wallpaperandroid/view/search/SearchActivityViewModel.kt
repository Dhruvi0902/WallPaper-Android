package com.example.wallpaperandroid.view.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.wallpaperandroid.data.model.search.ResultsItem
import com.example.wallpaperandroid.data.network.ResponseStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class SearchActivityViewModel:ViewModel() {

    private val pageSize = 15
    private val parentJob = Job()
    private val scope = CoroutineScope(Dispatchers.IO + parentJob)

    var responseStatusLiveData: LiveData<ResponseStatus<List<ResultsItem>>>? =null
    var collectionResponseStatus: LiveData<ResponseStatus<List<com.example.wallpaperandroid.data.model.searchCollection.ResultsItem>>>? =null


    fun getSearchedImages(searchString:String): LiveData<PagedList<ResultsItem>> {
        val imageDataSource = SearchImageDataSource(scope,searchString)
        // Get the paged list
        val defaultConfig = PagedList.Config.Builder()
            .setEnablePlaceholders(true)
            .setPrefetchDistance(5)
            .setInitialLoadSizeHint(10)
            .setPageSize(pageSize)
            .build()


        // Get the paged list
        return LivePagedListBuilder(imageDataSource, defaultConfig)
            .build()
    }

    fun getSearchedCollection(searchString:String): LiveData<PagedList<com.example.wallpaperandroid.data.model.searchCollection.ResultsItem>> {
        val imageDataSource = SearchCollectionDataSource(scope,searchString)
        // Get the paged list
        val defaultConfig = PagedList.Config.Builder()
            .setEnablePlaceholders(true)
            .setPrefetchDistance(5)
            .setInitialLoadSizeHint(10)
            .setPageSize(pageSize)
            .build()


        // Get the paged list
        return LivePagedListBuilder(imageDataSource, defaultConfig)
            .build()
    }

}