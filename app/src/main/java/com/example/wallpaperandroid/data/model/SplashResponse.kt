package com.example.wallpaperandroid.data.model

import androidx.annotation.NonNull
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity
data class SplashResponse(

	@field:SerializedName("current_user_collections")
	val currentUserCollections: List<Any?>? = null,

	@field:SerializedName("color")
	val color: String? = null,

	@Embedded(prefix = "sponsorship_")
	@field:SerializedName("sponsorship")
	val sponsorship: Sponsorship? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("liked_by_user")
	val likedByUser: Boolean? = null,

	@Embedded(prefix = "url_")
	@field:SerializedName("urls")
	val urls: Urls? = null,

	@field:SerializedName("alt_description")
	val altDescription: String? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("width")
	val width: Int? = null,

	@Embedded(prefix = "links_")
	@field:SerializedName("links")
	val links: Links? = null,

	@NonNull
	@PrimaryKey
	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("categories")
	val categories: List<String?>? = null,

	@field:SerializedName("promoted_at")
	val promotedAt: String? = null,

	@Embedded(prefix = "user_")
	@field:SerializedName("user")
	val user: User? = null,

	@field:SerializedName("height")
	val height: Int? = null,

	@field:SerializedName("likes")
	val likes: Int? = null
): Serializable