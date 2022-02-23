package com.example.wallpaperandroid.data.model.collection

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Type(

	@field:SerializedName("pretty_slug")
	val prettySlug: String? = null,

	@field:SerializedName("slug")
	val slug: String? = null
): Serializable