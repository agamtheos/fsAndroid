package com.example.fs_android.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fs_android.data.request.LoginRequest
import com.example.fs_android.data.response.BaseError
import com.example.fs_android.domain.LoginRepository
import com.example.fs_android.domain.model.user.User
import com.example.fs_android.utils.Error
import com.example.fs_android.utils.Initiate
import com.example.fs_android.utils.Loading
import com.example.fs_android.utils.NetworkState
import com.example.fs_android.utils.Success
import com.example.fs_android.utils.UIState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val repository: LoginRepository
): ViewModel(){
    private val _login = MutableStateFlow<UIState<User>>(Initiate())
    val login: StateFlow<UIState<User>> = _login

    fun postLogin(username: String, password: String) {
        viewModelScope.launch(Dispatchers.Main) {
            _login.value = Loading(true)
            val process = async(Dispatchers.IO) {
                repository.postLogin(LoginRequest(username,password))
            }
            when(val state = process.await()) {
                is NetworkState.Success -> {
                    _login.value = Loading(false)
                    _login.value = Success(data = state.data)
                }
                is NetworkState.Error -> {
                    _login.value = Loading(false)
                    _login.value = Error((state.error as BaseError).error)
                }
            }
        }
    }


}