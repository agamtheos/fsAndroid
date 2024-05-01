package com.example.fs_android.domain

import com.example.fs_android.data.NetworkService
import com.example.fs_android.data.response.BaseError
import com.example.fs_android.domain.model.batch.Batch
import com.example.fs_android.utils.NetworkState
import com.example.fs_android.utils.parseError

class BatchRepository(private val service: NetworkService) {
    suspend fun getBatches(
        page: Int,
        sort: String,
        sortBy: String,
        size: Int = 5,
        filterBy: String? = null,
        filterValue: String? = null
    ): NetworkState<List<Batch>> {
        return try {
            val response = service.getListBatch(page, sort, sortBy, size, filterBy, filterValue)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    body.data?.content?.map { it.mapToBatch() }?.let {
                        NetworkState.Success(it)
                    } ?: run {
                        NetworkState.Error(error = BaseError(error = "Null Response"))
                    }
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