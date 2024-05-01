package com.example.fs_android.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fs_android.data.response.BaseError
import com.example.fs_android.domain.BatchRepository
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

class BatchViewModel(private val repository: BatchRepository) : ViewModel() {
    private val _batch = MutableStateFlow<UIState<List<Batch>>>(Initiate())
    val batch: StateFlow<UIState<List<Batch>>> = _batch

    fun getBatch(
        page: Int = 0,
        sort: String = "createdAt",
        sortBy: String = "desc",
        size: Int = 5,
        filterBy: String? = null,
        filterValue: String? = null
    ) {
        viewModelScope.launch(Dispatchers.Main) {
            _batch.value = Loading(true)
            val process = async(Dispatchers.IO) {
                repository.getBatches(page, sort, sortBy, size, filterBy, filterValue)
            }
            when (val state = process.await()) {
                is NetworkState.Success -> {
                    _batch.value = Loading(false)
                    _batch.value = Success(data = state.data)
                }

                is NetworkState.Error -> {
                    _batch.value = Loading(false)
                    _batch.value = Error((state.error as BaseError).error)
                    // print error to logcat
                    println((state.error as BaseError).error)
                }
            }
        }
    }
}