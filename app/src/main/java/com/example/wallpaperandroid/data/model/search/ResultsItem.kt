package com.example.wallpaperandroid.data.model.search

import com.example.wallpaperandroid.data.model.Links
import com.example.wallpaperandroid.data.model.Urls
import com.example.wallpaperandroid.data.model.User
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ResultsItem(

	@field:SerializedName("urls")
	val urls: Urls? = null,

	@field:SerializedName("current_user_collections")
	val currentUserCollections: List<Any?>? = null,

	@field:SerializedName("color")
	val color: String? = null,

	@field:SerializedName("width")
	val width: Int? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("links")
	val links: Links? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("liked_by_user")
	val likedByUser: Boolean? = null,

	@field:SerializedName("user")
	val user: User? = null,

	@field:SerializedName("height")
	val height: Int? = null,

	@field:SerializedName("likes")
	val likes: Int? = null
):Serializable