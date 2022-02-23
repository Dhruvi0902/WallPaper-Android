package com.example.wallpaperandroid.view.singleList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.wallpaperandroid.data.database.ImageDatabase
import com.example.wallpaperandroid.data.model.SplashResponse
import com.example.wallpaperandroid.data.model.favourites.Favourites
import com.example.wallpaperandroid.data.network.ResponseStatus
import kotlinx.coroutines.*
import org.koin.core.KoinComponent
import org.koin.core.inject

class ListViewModel : ViewModel() , KoinComponent {

    private val favData: MutableLiveData<Favourites> = MutableLiveData()
    private val pageSize = 15
    private val parentJob = Job()
    private val scope = CoroutineScope(Dispatchers.IO + parentJob)

    private val imageDatabase: ImageDatabase by inject()
    var responseStatusLiveData: LiveData<ResponseStatus<List<SplashResponse>>>? =null
    private val imageDataSource:ImageDataSource = ImageDataSource(scope)


     fun getImages(): LiveData<PagedList<SplashResponse>> {
        val dataSource = imageDatabase.getImageDao().getAllData()


        // Get the paged list
        val defaultConfig = PagedList.Config.Builder()
            .setEnablePlaceholders(true)
            .setPrefetchDistance(5)
            .setInitialLoadSizeHint(10)
            .setPageSize(pageSize)
            .build()


        // Get the paged list
        return LivePagedListBuilder(dataSource, defaultConfig)
            .build()

    }

     fun getOnlineImages():LiveData<PagedList<SplashResponse>>{
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


    override fun onCleared() {
        super.onCleared()
        scope.cancel()
    }


}

