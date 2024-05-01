package com.example.fs_android.domain.model

data class Movie(
    val id: Int,
    val image: String,
    val title: String,
    val vote: Int,
    val voteAverage: Double,
    val desc: String,
    val releaseDate:String
)