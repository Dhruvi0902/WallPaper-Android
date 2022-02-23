package com.example.wallpaperandroid.data.database

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.wallpaperandroid.data.model.favourites.Favourites

@Dao
interface FavouritesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addData(favourites: Favourites)

    @Query("Select * from Favourites")
    fun getAllData(): DataSource.Factory<Int, Favourites>

    @Query("Delete from Favourites where id=:id")
    fun deleteData(id: String)

    @Query("Delete from Favourites where fullUrl=:url")
    fun deleteDataByUrl(url: String)

    @Query("Select * from Favourites where id=:id")
    fun getId(id: String?):Favourites?

    @Query("Select * from Favourites where fullUrl=:fullUrl")
    fun getByFullUrl(fullUrl: String?): Favourites?

}