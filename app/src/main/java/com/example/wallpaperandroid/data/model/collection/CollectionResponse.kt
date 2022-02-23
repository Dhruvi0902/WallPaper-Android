package com.example.wallpaperandroid.data.model.collection

import androidx.annotation.NonNull
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.wallpaperandroid.data.model.User
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity
data class CollectionResponse(

	@PrimaryKey
	@NonNull
	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("featured")
	val featured: Boolean? = null,

	@field:SerializedName("private")
	val jsonMemberPrivate: Boolean? = null,

	@Embedded(prefix = "cover_photo_")
	@field:SerializedName("cover_photo")
	val coverPhoto: CoverPhoto? = null,

	@field:SerializedName("total_photos")
	val totalPhotos: Int? = null,

	@field:SerializedName("share_key")
	val shareKey: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("tags")
	val tags: List<TagsItem?>? = null,

	@field:SerializedName("preview_photos")
	val previewPhotos: List<PreviewPhotosItem?>? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("curated")
	val curated: Boolean? = null,

	@Embedded(prefix = "links_")
	@field:SerializedName("links")
	val links: Links? = null,

	@field:SerializedName("published_at")
	val publishedAt: String? = null,

	@Embedded(prefix = "user")
	@field:SerializedName("user")
	val user: User? = null
):Serializable