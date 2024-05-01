package com.example.fs_android.base.interceptor

import androidx.datastore.core.DataStore
import com.example.fs_android.domain.model.user.User
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException


class HeaderInterceptor(
    private val dataStore: DataStore<User>
) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = mapHeaders(chain)
        return chain.proceed(request)
    }

    private fun mapHeaders(chain: Interceptor.Chain): Request {
        val original = chain.request()
        val isGuest = original.headers["is_guest"] == "true"
        val getToken = runBlocking {
            dataStore.data.firstOrNull()?.token // ini untuk nyimpan kaya localstorage
        }
        val requestBuilder = original.newBuilder()

        if (!getToken.isNullOrEmpty() && !isGuest) setTokenHeader(requestBuilder, getToken)

        return requestBuilder.build()
    }

    private fun setTokenHeader(requestBuilder: Request.Builder, token: String) {
        requestBuilder.header("Authorization", "Bearer " + token)
    }


}