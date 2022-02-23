package com.example.wallpaperandroid.data.model.search

import com.google.gson.annotations.SerializedName

data class SearchResponse(

	@field:SerializedName("total")
	val total: Int? = null,

	@field:SerializedName("total_pages")
	val totalPages: Int? = null,

	@field:SerializedName("results")
	val results: List<ResultsItem?>? = null
)