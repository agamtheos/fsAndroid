package com.example.fs_android.base.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody

class MockInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalResponse = chain.proceed(chain.request())
        val originalRequest = chain.request()
        val isMock = originalRequest.headers["is_mock"] == "true"

        try {
            if (!isMock) return originalResponse
            return originalResponse
                .newBuilder()

//                .code(400)
//                .body(failedLogin().toResponseBody(originalResponse.body?.contentType()))

                .code(200)
                .body(getSuccessLogin().toResponseBody(originalResponse.body?.contentType()))

                .build()
        } catch (e: Exception) {
            return originalResponse
        }
    }

    private fun getSuccessLogin(): String {
        return "{\"data\":{\"role\":{\"roleName\":\"superuser\"},\"menu\":[{\"menuName\":\"Funder Statement\"},{\"menuName\":\"Blacklist Funder\"}],\"token\":\"Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJiOTk4ZjcyM2UxMWZlNTA5ZGZjNGJjYzJmZDE2ZGE2MSIsInN1YiI6IjU4ZDA5Zjk1YzNhMzY4MzhhZjAwMWY5NyIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.DUcrVTi-Ml70qXhfL5odSMJlSRA1m_qs7OcdvaOIQN4\"},\"success\": true,\"message\":\"Success!\",\"status\": 200,\"timestamp\": 1712297756047}"
    }

    private fun failedLogin(): String {
        return "{\"path\":\"/api/auth/v1/login\",\"data\": {},\"success\": false,\"errorCode\": \"FV01007\",\"message\": \"User tidak ditemukan!\",\"status\": 404,\"timestamp\": 1712297814803}"
    }
}