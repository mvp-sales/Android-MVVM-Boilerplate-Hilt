package com.aregyan.github.network

import com.aregyan.github.network.model.PixabayResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface PixabayService {
    @GET("?key=47397596-f05d1b910895f5f93d0c2a7a2")
    suspend fun fetchImages(@Query("page") page: Int) : PixabayResponse

    @GET("?key=47397596-f05d1b910895f5f93d0c2a7a2")
    suspend fun fetchImage(@Query("id") id: Int) : PixabayResponse
}