package com.aregyan.github.views.home

import com.aregyan.github.domain.PixabayImageData

data class ImageViewData(
    val id: Int,
    val imageUrl: String,
    val username: String
)

fun PixabayImageData.toViewData() = ImageViewData(
    id, imageUrl, username
)
