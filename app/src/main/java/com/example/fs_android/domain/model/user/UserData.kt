package com.example.fs_android.domain.model.user

import java.io.Serializable
import java.util.Date

data class UserData(
    val userId: Long,
    val username: String,
): Serializable
