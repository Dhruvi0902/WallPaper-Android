package com.example.wallpaperandroid.data.model.searchCollection

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ProfileImage(

	@field:SerializedName("small")
	val small: String? = null,

	@field:SerializedName("large")
	val large: String? = null,

	@field:SerializedName("medium")
	val medium: String? = null
):Serializable