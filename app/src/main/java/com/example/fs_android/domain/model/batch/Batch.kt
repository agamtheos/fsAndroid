package com.example.fs_android.domain.model.batch

import java.io.Serializable

data class Batch(
    var funderStatementBatchId: String,
    var batchName: String,
    var totalFunderFile: Long,
    var totalSent: Long,
    var totalRejected: Long,
    var totalBlacklisted: Long,
    var approveBy: String?,
    var approveAt: String?,
    var rejectedBy: String?,
    var rejectedAt: String?,
    var status: String,
    var createdAt: String,
    var createdBy: String,
    var updatedAt: String?,
    var updatedBy: String?,
) : Serializable
