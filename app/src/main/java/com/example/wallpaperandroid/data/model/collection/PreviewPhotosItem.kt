package com.example.wallpaperandroid.data.model.collection

import androidx.room.Embedded
import com.example.wallpaperandroid.data.model.Urls
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class PreviewPhotosItem(

	@Embedded(prefix = "PreviewPhotosItem_Urls_")
	@field:SerializedName("urls")
	val urls: Urls? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: String? = null
): Serializable