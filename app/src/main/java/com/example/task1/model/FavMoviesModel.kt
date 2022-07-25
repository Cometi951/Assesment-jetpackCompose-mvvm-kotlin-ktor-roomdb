package com.example.task1.model

import com.example.task1.model.fav_movie
import kotlinx.serialization.Serializable

@Serializable
data class FavMoviesModel(
    val created_by: String? = null,
    val description: String? = null,
    val favorite_count: Int? = null,
    val id: String? = null,
    val iso_639_1: String? = null,
    val item_count: Int? = null,
    val items: List<fav_movie?>? = null,
    val name: String? = null,
)

