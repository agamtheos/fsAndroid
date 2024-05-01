package com.example.fs_android.data.response


import com.google.gson.annotations.SerializedName

data class new(
    @SerializedName("menu")
    val menu: List<Menu?>?,
    @SerializedName("role")
    val role: Role?,
    @SerializedName("token")
    val token: String?
) {
    data class Menu(
        @SerializedName("menuName")
        val menuName: String?
    )

    data class Role(
        @SerializedName("roleName")
        val roleName: String?
    )
}