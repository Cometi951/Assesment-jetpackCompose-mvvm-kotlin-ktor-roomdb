package com.example.task1.model

import kotlinx.serialization.Serializable

@Serializable
data class MoviesModel(
    val page: Int? = null,
    var results: List<Item>? = null,
    val total_pages: Int? = null,
    val total_results: Int? = null,
    var failed: Boolean? = null
)