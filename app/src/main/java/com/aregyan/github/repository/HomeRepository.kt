package com.aregyan.github.repository

import com.aregyan.github.domain.PixabayImageData
import com.aregyan.github.network.PixabayService
import com.aregyan.github.network.model.toDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class HomeRepository @Inject constructor(
    private val pixabayService: PixabayService
) {

    fun fetchImages(page: Int): Flow<List<PixabayImageData>> = flow {
        emit(
            pixabayService.fetchImages(page).hits.map {
                it.toDomain()
            }
        )
    }
}