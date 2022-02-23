package com.example.wallpaperandroid.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.wallpaperandroid.data.model.SplashResponse
import com.example.wallpaperandroid.data.model.collection.CollectionResponse
import com.example.wallpaperandroid.data.model.favourites.Favourites

@Database(entities = [SplashResponse::class,CollectionResponse::class,Favourites::class], version = 1, exportSchema = false)
@TypeConverters(Converter::class)
abstract class ImageDatabase:RoomDatabase() {

    abstract fun getImageDao():ImageDao
    abstract fun getCollectionDao():CollectionDao
    abstract fun getFavouritesDao():FavouritesDao
}