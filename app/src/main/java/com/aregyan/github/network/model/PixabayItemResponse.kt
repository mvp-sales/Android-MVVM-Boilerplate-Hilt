package com.aregyan.github.network.model

import com.aregyan.github.domain.PixabayImageData
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PixabayItemResponse(
    @Json(name = "id")
    val id: Int,
    @Json(name = "previewURL")
    val imageUrl: String,
    @Json(name = "user")
    val user: String,
    @Json(name = "imageSize")
    val imageSize: Int,
    @Json(name = "views")
    val views: Int,
    @Json(name = "downloads")
    val downloads: Int,
    @Json(name = "likes")
    val likes: Int,
    @Json(name = "comments")
    val comments: Int,
    @Json(name = "tags")
    val tags: String,
    @Json(name = "type")
    val type: String,
)

fun PixabayItemResponse.toDomain() = PixabayImageData(
    id, imageUrl, user, imageSize, views, downloads, likes, comments, tags, type
)