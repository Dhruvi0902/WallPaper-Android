package com.example.wallpaperandroid.framework

import com.example.wallpaperandroid.view.collection.CollectionViewModel
import com.example.wallpaperandroid.view.favourites.FavouritesViewModel
import com.example.wallpaperandroid.view.imagePreview.ImageViewModel
import com.example.wallpaperandroid.view.search.SearchActivityViewModel
import com.example.wallpaperandroid.view.singleList.ListViewModel
import com.example.wallpaperandroid.view.ui.featuredCollection.FeaturedCollectionViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModuleDI = module {
    viewModel { ListViewModel() }
    viewModel { CollectionViewModel() }
    viewModel { FavouritesViewModel() }
    viewModel { ImageViewModel() }
    viewModel { SearchActivityViewModel() }
    viewModel { FeaturedCollectionViewModel() }
}