package com.panabuntu.tmdb.core.common.entity

enum class MediaType(
    val value: String
) {
    MOVIE("movie"),
    TV_SHOW("tv");

    companion object {
        fun fromValue(value: String): MediaType = when (value) {
            "movie" -> MOVIE
            "tv" -> TV_SHOW
            else -> throw IllegalArgumentException()
        }
    }
}