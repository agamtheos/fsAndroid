package com.example.fs_android.domain.model.user

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val userName: String? = null,
    val token: String? = null
) {

}