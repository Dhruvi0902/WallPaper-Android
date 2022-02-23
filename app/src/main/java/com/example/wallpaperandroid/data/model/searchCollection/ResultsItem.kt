package com.example.wallpaperandroid.data.model.searchCollection

import com.example.wallpaperandroid.data.model.User
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ResultsItem(

	@field:SerializedName("featured")
	val featured: Boolean? = null,

	@field:SerializedName("private")
	val jsonMemberPrivate: Boolean? = null,

	@field:SerializedName("cover_photo")
	val coverPhoto: CoverPhoto? = null,

	@field:SerializedName("total_photos")
	val totalPhotos: Int? = null,

	@field:SerializedName("share_key")
	val shareKey: String? = null,

	@field:SerializedName("description")
	val description: Any? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("last_collected_at")
	val lastCollectedAt: String? = null,

	@field:SerializedName("links")
	val links: Links? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("published_at")
	val publishedAt: String? = null,

	@field:SerializedName("user")
	val user: User? = null
):Serializable