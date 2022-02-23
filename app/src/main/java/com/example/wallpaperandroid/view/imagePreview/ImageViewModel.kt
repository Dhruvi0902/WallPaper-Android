package com.example.wallpaperandroid.view.imagePreview

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wallpaperandroid.data.database.ImageDatabase
import com.example.wallpaperandroid.data.model.SplashResponse
import com.example.wallpaperandroid.data.model.collection.CollectionResponse
import com.example.wallpaperandroid.data.model.favourites.Favourites
import com.example.wallpaperandroid.data.model.search.ResultsItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject

class ImageViewModel : ViewModel(),KoinComponent {


    private val imageDatabase: ImageDatabase by inject()
    val favData:MutableLiveData<Favourites> = MutableLiveData()

    fun updateFavSearch(isFav: Boolean,search: ResultsItem?){
        viewModelScope.launch(Dispatchers.IO) {
            if (isFav) {
                imageDatabase.getFavouritesDao()
                    .addData(Favourites(search?.id!!, search.urls?.full, search.urls?.thumb))
            } else {
                imageDatabase.getFavouritesDao().deleteData(search?.id!!)
            }
        }
    }

    fun updateFavExplore(isFav: Boolean,data: SplashResponse?){
        viewModelScope.launch(Dispatchers.IO) {
            if (isFav) {
                imageDatabase.getFavouritesDao()
                    .addData(Favourites(data!!.id, data.urls?.full, data.urls?.thumb))
            } else {
                imageDatabase.getFavouritesDao().deleteData(data!!.id)
            }
        }
    }

    fun updateFavCollect(isFav: Boolean, id: String,thumb:String,full:String){
        viewModelScope.launch(Dispatchers.IO) {
            if (isFav) {
                imageDatabase.getFavouritesDao()
                    .addData(Favourites(id, full, thumb))
            } else {
                imageDatabase.getFavouritesDao().deleteData(id)
            }
        }
    }

    fun updateFavSearchCollect(isFav: Boolean, id: String?,data:com.example.wallpaperandroid.data.model.searchCollection.ResultsItem){
        viewModelScope.launch(Dispatchers.IO) {
            if (isFav) {
                imageDatabase.getFavouritesDao()
                    .addData(
                        Favourites(
                            id ?: "1",
                            data.coverPhoto?.urls?.full,
                            data.coverPhoto?.urls?.thumb
                        )
                    )
            } else {
                imageDatabase.getFavouritesDao().deleteDataByUrl(data.coverPhoto?.urls?.full!!)
            }
        }
    }



    fun getFavourite(id:String?){
        viewModelScope.launch(Dispatchers.IO) {
           favData.postValue(imageDatabase.getFavouritesDao().getId(id))
        }
    }

    fun getFavouriteByImage(fullUrl: String?) {
        viewModelScope.launch(Dispatchers.IO) {
            favData.postValue(imageDatabase.getFavouritesDao().getByFullUrl(fullUrl))
        }
    }

    fun updateFavFeatCollect(fav: Boolean, data: CollectionResponse) {
        viewModelScope.launch(Dispatchers.IO) {
            if (fav) {
                imageDatabase.getFavouritesDao()
                    .addData(Favourites(data.id.toString(), data.coverPhoto?.urls?.full, data.coverPhoto?.urls?.thumb))
            } else {
                imageDatabase.getFavouritesDao().deleteData(data.id.toString())
            }
        }
    }

    fun deleteFav(id: String?) {
        viewModelScope.launch(Dispatchers.IO){
            imageDatabase.getFavouritesDao().deleteData(id!!)
        }
    }
}