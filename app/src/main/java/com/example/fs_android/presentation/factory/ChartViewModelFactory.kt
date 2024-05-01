package com.example.fs_android.presentation.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.fs_android.domain.ChartRepository
import com.example.fs_android.presentation.viewmodel.ChartViewModel

class ChartViewModelFactory(
    private val repository: ChartRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(ChartViewModel::class.java)) {
            ChartViewModel(this.repository) as T
        } else {
            throw IllegalAccessException("ViewModel not Found")
        }
    }
}