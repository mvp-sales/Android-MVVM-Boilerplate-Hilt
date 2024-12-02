package com.aregyan.github.network.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PixabayResponse(
    @Json(name = "hits")
    val hits: List<PixabayItemResponse>
)
