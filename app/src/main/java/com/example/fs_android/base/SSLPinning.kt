package com.example.fs_android.base

import okhttp3.CertificatePinner

object SSLPinning {
    fun getPinnedCertificate(): CertificatePinner {
        return CertificatePinner.Builder()
            .add("example.com", "sha256/AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA=")
            // Add more certificates or public keys here if needed
            .build()
    }
}