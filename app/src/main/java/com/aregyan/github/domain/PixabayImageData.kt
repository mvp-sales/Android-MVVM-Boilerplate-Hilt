package com.aregyan.github.domain

data class PixabayImageData(
    val id: Int,
    val imageUrl: String,
    val username: String,
    val imageSize: Int,
    val views: Int,
    val downloads: Int,
    val likes: Int,
    val comments: Int,
    val favorites: Int,
    val tags: String,
    val type: String,
    val largeImageUrl: String
)
