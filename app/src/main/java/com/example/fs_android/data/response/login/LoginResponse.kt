package com.example.fs_android.data.response.login

import com.example.fs_android.domain.model.user.User
import com.google.gson.annotations.SerializedName


data class LoginResponse
    (
    @SerializedName("menu")
    val menu: List<MenuResponse?>?,

    @SerializedName("role")
    val role: RoleResponse?,

    @SerializedName("token")
    val token: String?
) {
    data class MenuResponse(
        @SerializedName("menuName")
        val menuName: String?
    )

    data class RoleResponse(
        @SerializedName("roleName")
        val roleName: String?
    )
}

fun LoginResponse?.mapToUser(): User {
    return User (
        userName = this?.role?.roleName.orEmpty(),
        token = this?.token.orEmpty()
    )
}