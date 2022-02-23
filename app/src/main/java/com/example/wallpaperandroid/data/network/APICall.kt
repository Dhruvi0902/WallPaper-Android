package com.example.wallpaperandroid.data.network

import retrofit2.Response

suspend fun <T : Any> safeApiCall(call: suspend () -> Response<T>): ResponseStatus<T> {
    return try {
        val myResp = call.invoke()
        if (myResp.isSuccessful) {
            ResponseStatus.success(myResp.body()!!)
        } else {
            ResponseStatus.error(myResp.errorBody()?.toString() ?: "Something went wrong")
        }

    } catch (e: Exception) {
        ResponseStatus.error(e.message ?: "Internet error runs")
    }
}