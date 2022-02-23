package com.example.wallpaperandroid.data.database

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.wallpaperandroid.data.model.SplashResponse
import kotlinx.coroutines.flow.Flow

@Dao
interface ImageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addData(dataList: List<SplashResponse>)

    @Query("Select * from SplashResponse")
    fun getAllData(): DataSource.Factory<Int, SplashResponse>
}