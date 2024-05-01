package com.example.fs_android.base

import android.app.Application
import android.content.Context
import androidx.datastore.dataStore
import com.example.fs_android.helper.CryptoManagerImpl
import com.example.fs_android.utils.ApplicationProviderUtils
import com.example.fs_android.utils.DataStoreUtils
import com.example.fs_android.utils.UserSettingsSerializer

class BaseApplication: Application() {

    private val Context.dataStore by dataStore(
        fileName = "user-settings.json",
        serializer = UserSettingsSerializer()
    )

    override fun onCreate() {
        super.onCreate()
        ApplicationProviderUtils.initialize(this)
        DataStoreUtils.initialize(dataStore)
    }
}