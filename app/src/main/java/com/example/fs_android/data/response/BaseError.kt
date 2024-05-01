package com.example.fs_android.data.response

import com.google.gson.annotations.SerializedName

data class BaseError(
    @SerializedName("message", alternate = ["status_message"])
    val error: String = "Error"
): Exception(error)