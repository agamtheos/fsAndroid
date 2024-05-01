//package com.example.fs_android.presentation.factory
//
//import HomeViewModel
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.ViewModelProvider
//import com.example.fs_android.domain.MovieRepository
//
//
//class HomeViewModelFactory (
//    private val repository: MovieRepository
//): ViewModelProvider.Factory {
//
//    @Suppress("UNCHECKED_CAST")
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        return if(modelClass.isAssignableFrom(HomeViewModel::class.java)) {
//            HomeViewModel(this.repository) as T
//        } else {
//            throw IllegalAccessException("ViewModel not Found")
//        }
//    }
//
//}