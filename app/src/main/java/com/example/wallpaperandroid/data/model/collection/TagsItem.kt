package com.example.wallpaperandroid.data.model.collection

import androidx.room.Embedded
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class TagsItem(

	@field:SerializedName("type")
	val type: String? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@Embedded(prefix = "TagsItem_Source_")
	@field:SerializedName("source")
	val source: Source? = null
): Serializable