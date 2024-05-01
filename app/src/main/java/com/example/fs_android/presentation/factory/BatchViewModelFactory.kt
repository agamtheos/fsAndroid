package com.example.fs_android.presentation.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.fs_android.domain.BatchRepository
import com.example.fs_android.presentation.viewmodel.BatchViewModel

class BatchViewModelFactory(
    private val repository: BatchRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(BatchViewModel::class.java)) {
            BatchViewModel(this.repository) as T
        } else {
            throw IllegalAccessException("ViewModel not Found")
        }
    }
}