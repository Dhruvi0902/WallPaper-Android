package com.example.wallpaperandroid.view.favourites

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.wallpaperandroid.data.database.ImageDatabase
import com.example.wallpaperandroid.data.model.favourites.Favourites
import org.koin.core.KoinComponent
import org.koin.core.inject

class FavouritesViewModel : ViewModel(),KoinComponent {
    private val pageSize = 15

    private val imageDatabase: ImageDatabase by inject()
    var imagesLiveData: LiveData<PagedList<Favourites>> = getImages()


    private fun getImages(): LiveData<PagedList<Favourites>> {
        val dataSource = imageDatabase.getFavouritesDao().getAllData()
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

}
