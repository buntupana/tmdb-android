package com.buntupana.tmdb.data

data class TvShowRaw(
    val id: Int,
    val name: String,
    val original_name: String,
    val overview: String,
    val poster_path: String,
    val backdrop_path: String,
    val original_language: String,
    val first_air_date: String,
    val genre_ids: List<Int>,
    val origin_country: List<String>,
    val popularity: Double,
    val vote_average: Double,
    val vote_count: Int,
    val media_type: String
)