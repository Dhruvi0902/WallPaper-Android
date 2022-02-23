package com.example.wallpaperandroid.data.model

import androidx.room.Embedded
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Sponsorship(

	@Embedded(prefix = "sponsor_")
	@field:SerializedName("sponsor")
	val sponsor: Sponsor? = null,

	@field:SerializedName("tagline")
	val tagLine: String? = null,

	@field:SerializedName("impression_urls")
	val impressionUrls: List<String?>? = null
): Serializable