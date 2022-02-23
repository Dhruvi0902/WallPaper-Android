package com.example.wallpaperandroid.data.model.collection

import androidx.room.Embedded
import com.example.wallpaperandroid.data.model.Urls
import com.example.wallpaperandroid.data.model.User
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class CoverPhoto(

	@field:SerializedName("current_user_collections")
	val currentUserCollections: List<String?>? = null,

	@field:SerializedName("color")
	val color: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("liked_by_user")
	val likedByUser: Boolean? = null,

	@Embedded(prefix = "urls_")
	@field:SerializedName("urls")
	val urls: Urls? = null,

	@field:SerializedName("alt_description")
	val altDescription: String? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("width")
	val width: Int? = null,

	@Embedded(prefix = "coverPhoto_links_")
	@field:SerializedName("links")
	val links: Links? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("categories")
	val categories: List<String?>? = null,

	@field:SerializedName("promoted_at")
	val promotedAt: String? = null,

	@Embedded(prefix = "coverPhoto_user")
	@field:SerializedName("user")
	val user: User? = null,

	@field:SerializedName("height")
	val height: Int? = null,

	@field:SerializedName("likes")
	val likes: Int? = null
): Serializable