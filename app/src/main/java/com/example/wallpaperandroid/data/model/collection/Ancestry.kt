package com.example.wallpaperandroid.data.model.collection

import androidx.room.Embedded
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Ancestry(

	@field:SerializedName("type")
	val type: Type? = null,

	@Embedded(prefix = "category_")
	@field:SerializedName("category")
	val category: Category? = null,

	@Embedded(prefix = "subcategory_")
	@field:SerializedName("subcategory")
	val subcategory: Subcategory? = null
): Serializable