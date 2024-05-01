package com.example.fs_android.data.response

import com.google.gson.annotations.SerializedName

sealed class DataResponse<out T> {
    data class SingleResponse<T>(val item: T) : DataResponse<T>()
    data class ListResponse<T>(val items: List<T>) : DataResponse<T>()
}

data class BaseResponse<Response>(
    @SerializedName("results", alternate = ["data"])
    val data: Response?,
//    val data: DataResponse<Response>?,
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Int,
    @SerializedName("timestamp")
    val timestamp: Long
)