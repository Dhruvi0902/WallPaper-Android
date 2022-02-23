package com.example.wallpaperandroid.data.database

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.wallpaperandroid.data.model.SplashResponse
import com.example.wallpaperandroid.data.model.collection.CollectionResponse

@Dao
interface CollectionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addData(dataList: List<CollectionResponse>)

    @Query("Select * from CollectionResponse")
    fun getAllData(): DataSource.Factory<Int, CollectionResponse>
}