package com.example.fs_android.data.response.batch

import com.example.fs_android.domain.model.batch.Batch
import com.google.gson.annotations.SerializedName

data class BatchResponse(
    @SerializedName("funderStatementBatchId")
    val funderStatementBatchId: String,

    @SerializedName("batchName")
    val funderStatementBatchName: String,

    @SerializedName("totalFunderFile")
    val totalFunderFile: Long,

    @SerializedName("totalSent")
    val totalSent: Long,

    @SerializedName("totalRejected")
    val totalRejected: Long,

    @SerializedName("totalBlacklisted")
    val totalBlacklisted: Long,

    @SerializedName("approveBy")
    val approveBy: String,

    @SerializedName("approveAt")
    val approveAt: String,

    @SerializedName("rejectedBy")
    val rejectedBy: String,

    @SerializedName("rejectedAt")
    val rejectedAt: String,

    @SerializedName("status")
    val status: String,

    @SerializedName("createdAt")
    val createdAt: String,

    @SerializedName("createdBy")
    val createdBy: String,

    @SerializedName("updatedAt")
    val updatedAt: String,

    @SerializedName("updatedBy")
    val updatedBy: String
) {
    data class UserData(
        @SerializedName("userId")
        val userId: Long,

        @SerializedName("username")
        val username: String,
    )

    fun mapToBatch() = Batch(
        funderStatementBatchId = funderStatementBatchId,
        batchName = funderStatementBatchName,
        totalFunderFile = totalFunderFile,
        totalSent = totalSent,
        totalRejected = totalRejected,
        totalBlacklisted = totalBlacklisted,
        approveBy = approveBy,
        approveAt = approveAt,
        rejectedBy = rejectedBy,
        rejectedAt = rejectedAt,
        status = status,
        // make createdAt format to "dd-MM-yyyy HH:mm"
        createdAt = createdAt,
        createdBy = createdBy,
        updatedAt = updatedAt,
        updatedBy = updatedBy,
    )
}