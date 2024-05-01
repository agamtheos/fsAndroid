package com.example.fs_android.data.response.pagination

import com.example.fs_android.data.response.batch.BatchResponse
import com.google.gson.annotations.SerializedName

data class PaginationResponse
    (
    @SerializedName("totalItems")
    val totalItems: Int,

    @SerializedName("numberOfElements")
    val numberOfElements: Int,

    @SerializedName("totalPages")
    val totalPages: Int,

    @SerializedName("filter-by")
    val filterBy: String,

    @SerializedName("sort")
    val sort: String,

    @SerializedName("component-filter")
    val componentFilter: List<ComponentFilter>,

    @SerializedName("value")
    val value: String,

    @SerializedName("content")
    val content: List<BatchResponse> = emptyList()
//    val content: BatchResponse
) {
    data class ComponentFilter(
        @SerializedName("key")
        val key: String,
        @SerializedName("label")
        val label: String
    )
}