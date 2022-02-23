package com.example.wallpaperandroid.framework

import androidx.room.Room
import com.example.wallpaperandroid.WallPaperApplication
import com.example.wallpaperandroid.data.database.ImageDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module


val databaseModule = module {

    single {
        Room.databaseBuilder(
            (androidApplication() as WallPaperApplication),
            ImageDatabase::class.java,
            "WallPaperDatabase.db"
        ).build()
    }

    single { (get() as ImageDatabase).getImageDao() }
    single { (get() as ImageDatabase).getCollectionDao() }

}
