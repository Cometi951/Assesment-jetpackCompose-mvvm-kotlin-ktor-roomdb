package com.example.task1.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity(tableName = "movies")
@Serializable
data class Item(
    @PrimaryKey var key : Int? = null,
    val adult: Boolean? = null,
    val backdrop_path: String? = null,
    val id: Int? = null,
    val media_type: String? = null,
    val original_language: String? = null,
    val original_title: String? = null,
    val overview: String? = null,
    val popularity: Double? = null,
    val poster_path: String? = null,
    val release_date: String? = null,
    val title: String? = null,
    val video: Boolean? = null,
    val vote_average: Double? = null,
    val vote_count: Int? = null,
    var favourite : Boolean? = null
)

@Entity(tableName = "fav_movies")
@Serializable
data class fav_movie(
    @PrimaryKey var key : Int? = null,
    val adult: Boolean? = null,
    val backdrop_path: String? = null,
    val id: Int? = null,
    val media_type: String? = null,
    val original_language: String? = null,
    val original_title: String? = null,
    val overview: String? = null,
    val popularity: Double? = null,
    val poster_path: String? = null,
    val release_date: String? = null,
    val title: String? = null,
    val video: Boolean? = null,
    val vote_average: Double? = null,
    val vote_count: Int? = null,
    var favourite : Boolean? = null
)