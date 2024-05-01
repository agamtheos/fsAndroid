package com.example.fs_android.presentation

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.fs_android.MainActivity
import com.example.fs_android.base.AppModule
import com.example.fs_android.databinding.ActivityLoginBinding
import com.example.fs_android.domain.model.user.User
import com.example.fs_android.presentation.viewmodel.LoginViewModel
import com.example.fs_android.utils.DataStoreUtils
import com.example.fs_android.utils.Error
import com.example.fs_android.utils.Initiate
import com.example.fs_android.utils.Loading
import com.example.fs_android.utils.Success
import com.example.fs_android.utils.observeIn
import kotlinx.coroutines.launch


class LoginActivity : AppCompatActivity() {

    private var loginViewModel: LoginViewModel? = null

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initiate()
        observer()

        binding.btnLogin.setOnClickListener {
            val username = binding.edtUsername.text.toString().trim()
            val password = binding.edtPassword.text.toString().trim()
            loginViewModel?.postLogin(username, password)
        }
    }

    private fun initiate() {
        loginViewModel =
            ViewModelProvider(this, AppModule.loginViewModelFactory)[LoginViewModel::class.java]
    }

    private fun observer() {
        loginViewModel?.login?.observeIn(this) {
            when (it) {
                is Success -> storeToken(it.data)
                is Error -> Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                is Loading -> showLoader(it.isLoading)
                is Initiate -> {}
            }
        }
    }

    private fun storeToken(data: User) {
        lifecycleScope.launch {
            DataStoreUtils.get().updateData {
                data
            }
            moveToMain()
        }
    }

    private fun moveToMain() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun showLoader(isLoading: Boolean) {
        with(binding) {
            btnLogin.isVisible = !isLoading
            pbLogin.isVisible = isLoading
        }
    }


}