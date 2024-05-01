package com.example.fs_android.domain

import com.example.fs_android.data.NetworkService
import com.example.fs_android.data.response.BaseError
import com.example.fs_android.domain.model.batch.Batch
import com.example.fs_android.utils.NetworkState
import com.example.fs_android.utils.parseError

class ChartRepository(private val service: NetworkService) {
    suspend fun getDetails(
        id: String
    ): NetworkState<Batch> {
        return try {
            val response = service.getBatch(id)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    body.data?.mapToBatch()?.let { batch ->
                        NetworkState.Success(batch)
                    } ?: run { NetworkState.Error(error = BaseError(error = "Null Response")) }
                } else {
                    parseError(response)
                }
            } else {
                println("Response : ${response.message()}")
                parseError(response)
            }
        } catch (e: Exception) {
            println("Response2 : ${e.message}")
            NetworkState.Error(error = e)
        }
    }
}