package com.example.wallpaperandroid.data.model.searchCollection

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Links(

	@field:SerializedName("self")
	val self: String? = null,

	@field:SerializedName("html")
	val html: String? = null,

	@field:SerializedName("photos")
	val photos: String? = null,

	@field:SerializedName("likes")
	val likes: String? = null,

	@field:SerializedName("related")
	val related: String? = null,

	@field:SerializedName("download")
	val download: String? = null
):Serializable