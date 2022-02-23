package com.example.wallpaperandroid.data.model.favourites

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Favourites(

    @PrimaryKey
    @NonNull
    val id: String,

    val fullUrl: String? = null,

    val thumbnailUrl: String? = null

)