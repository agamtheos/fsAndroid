package com.example.fs_android.presentation

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.fs_android.MainActivity
import com.example.fs_android.R
import com.example.fs_android.utils.DataStoreUtils
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch


@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        lifecycleScope.launch {
            delay(2000)
            decideNextPage()
//            val i = Intent(this@SplashActivity, LoginActivity::class.java)
//            startActivity(i)
            finish()
        }
    }

    private suspend fun decideNextPage() {
        val token = DataStoreUtils.get().data.firstOrNull()?.token.orEmpty()
        if(token.isEmpty()) {
            val i = Intent(this@SplashActivity, LoginActivity::class.java)
            startActivity(i)
        }else{
            val i = Intent(this@SplashActivity, MainActivity::class.java)
            startActivity(i)
        }
    }
}