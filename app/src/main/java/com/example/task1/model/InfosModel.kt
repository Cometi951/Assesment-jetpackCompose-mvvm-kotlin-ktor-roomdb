package com.example.task1.model

import kotlinx.serialization.Serializable

@Serializable
data class InfosModel(
    val adult: Boolean? = null,
    val backdrop_path: String? = null,
    val budget: Int? = null,
    val genres: List<Genre?>? = null,
    val homepage: String? = null,
    val id: Int? = null,
    val imdb_id: String? = null,
    val original_language: String? = null,
    val original_title: String? = null,
    val overview: String? = null,
    val popularity: Double? = null,
    val poster_path: String? = null,
    val production_companies: List<ProductionCompany?>? = null,
    val production_countries: List<ProductionCountry?>? = null,
    val release_date: String? = null,
    val revenue: Int? = null,
    val runtime: Int? = null,
    val spoken_languages: List<SpokenLanguage?>? = null,
    val status: String? = null,
    val tagline: String? = null,
    val title: String? = null,
    val video: Boolean? = null,
    val vote_average: Double? = null,
    val vote_count: Int? = null
) {
    @Serializable
    data class Genre(
        val id: Int? = null,
        val name: String? = null
    )

    @Serializable
    data class ProductionCompany(
        val id: Int? = null,
        val logo_path: String? = null,
        val name: String? = null,
        val origin_country: String? = null
    )

    @Serializable
    data class ProductionCountry(
        val iso_3166_1: String? = null,
        val name: String? = null
    )

    @Serializable
    data class SpokenLanguage(
        val iso_639_1: String? = null,
        val name: String? = null
    )
}