package com.example.fs_android.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fs_android.data.response.BaseError
import com.example.fs_android.domain.ChartRepository
import com.example.fs_android.domain.model.batch.Batch
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

class ChartViewModel(private val repository: ChartRepository) : ViewModel() {
    private val _detail = MutableStateFlow<UIState<Batch>>(Initiate())
    val batch: StateFlow<UIState<Batch>> = _detail

    fun getBatch(
        id: String
    ) {
        viewModelScope.launch(Dispatchers.Main) {
            _detail.value = Loading(true)
            val process = async(Dispatchers.IO) {
                repository.getDetails(id)
            }
            when (val state = process.await()) {
                is NetworkState.Success -> {
                    _detail.value = Loading(false)
                    _detail.value = Success(data = state.data)
                }

                is NetworkState.Error -> {
                    _detail.value = Loading(false)
                    _detail.value = Error((state.error as BaseError).error)
                    // print error to logcat
                    println((state.error as BaseError).error)
                }
            }
        }
    }
}