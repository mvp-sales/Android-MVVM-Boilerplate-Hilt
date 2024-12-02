package com.aregyan.github.repository

import com.aregyan.github.network.PixabayService
import com.aregyan.github.network.model.toDomain
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ImageDetailsRepository @Inject constructor(
    private val pixabayService: PixabayService
) {

    fun fetchImage(id: Int) = flow {
        emit(pixabayService.fetchImage(id).hits.first().toDomain())
    }
}