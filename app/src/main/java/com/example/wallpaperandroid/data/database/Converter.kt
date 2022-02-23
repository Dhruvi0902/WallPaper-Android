package com.example.wallpaperandroid.data.database

import androidx.room.TypeConverter
import com.example.wallpaperandroid.data.model.collection.PreviewPhotosItem
import com.example.wallpaperandroid.data.model.collection.TagsItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


class Converter {

    @TypeConverter
    fun fromString(value: String?): List<String?>? {
        val listType: Type = object : TypeToken<List<String?>?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromList(list: List<String?>?): String? {
        val gson = Gson()
        return gson.toJson(list)
    }

    @TypeConverter
    fun stringToPreviewPhotoItem(json: String?): List<PreviewPhotosItem>? {
        val gson = Gson()
        val type = object :
            TypeToken<List<PreviewPhotosItem?>?>() {}.type
        return gson.fromJson(json, type)
    }

    @TypeConverter
    fun previewPhotoItemToString(list: List<PreviewPhotosItem?>?): String? {
        val gson = Gson()
        val type = object :
            TypeToken<List<PreviewPhotosItem?>?>() {}.type
        return gson.toJson(list, type)
    }

    @TypeConverter
    fun stringToTagsItem(json: String?): List<TagsItem>? {
        val gson = Gson()
        val type = object :
            TypeToken<List<TagsItem?>?>() {}.type
        return gson.fromJson(json, type)
    }

    @TypeConverter
    fun tagsItemToString(list: List<TagsItem?>?): String? {
        val gson = Gson()
        val type = object :
            TypeToken<List<TagsItem?>?>() {}.type
        return gson.toJson(list, type)
    }

    @TypeConverter
    fun fromAny(value: String?):List<Any?>?{
        val listType: Type = object : TypeToken<List<Any?>?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromAnyList(list: List<Any?>?): String? {
        val gson = Gson()
        return gson.toJson(list)
    }

}