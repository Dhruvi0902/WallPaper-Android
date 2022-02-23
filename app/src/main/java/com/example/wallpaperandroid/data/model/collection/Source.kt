package com.example.wallpaperandroid.data.model.collection

import androidx.room.Embedded
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Source(

	@field:SerializedName("meta_description")
	val metaDescription: String? = null,

	@Embedded(prefix = "ancestry_")
	@field:SerializedName("ancestry")
	val ancestry: Ancestry? = null,

	@Embedded(prefix = "Source_CoverPhoto_")
	@field:SerializedName("cover_photo")
	val coverPhoto: CoverPhoto? = null,

	@field:SerializedName("meta_title")
	val metaTitle: String? = null,

	@field:SerializedName("subtitle")
	val subtitle: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("title")
	val title: String? = null
): Serializable