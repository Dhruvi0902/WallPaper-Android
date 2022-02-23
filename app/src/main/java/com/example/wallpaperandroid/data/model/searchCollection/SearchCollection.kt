package com.example.wallpaperandroid.data.model.searchCollection

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class SearchCollection(

	@field:SerializedName("total")
	val total: Int? = null,

	@field:SerializedName("total_pages")
	val totalPages: Int? = null,

	@field:SerializedName("results")
	val results: List<ResultsItem?>? = null
):Serializable