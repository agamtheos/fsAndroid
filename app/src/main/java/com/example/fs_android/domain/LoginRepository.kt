package com.example.fs_android.domain

import com.example.fs_android.data.NetworkService
import com.example.fs_android.data.request.LoginRequest
import com.example.fs_android.data.response.BaseError
import com.example.fs_android.data.response.login.mapToUser
import com.example.fs_android.domain.model.user.User
import com.example.fs_android.utils.NetworkState
import com.example.fs_android.utils.parseError

class LoginRepository (private val service: NetworkService) {

    suspend fun postLogin(request: LoginRequest): NetworkState<User> {
        return try {
            val response = service.postLogin(loginRequest = request)
            if(response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    body.data?.mapToUser()?.let { user ->
                        NetworkState.Success(user)
                    } ?: run { NetworkState.Error(error = BaseError(error = "Null Response")) }
                } else {
                    parseError(response)
                }
            } else {
                parseError(response)
            }
        }catch (e: Exception) {
            NetworkState.Error(error = e)
        }
    }

}