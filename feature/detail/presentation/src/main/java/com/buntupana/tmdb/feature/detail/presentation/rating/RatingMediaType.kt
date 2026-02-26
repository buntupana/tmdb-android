package com.buntupana.tmdb.feature.detail.presentation.rating

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
sealed class RatingMediaType(
    val title: String,
    val rating: Int?
) : Parcelable {

    @Serializable
    data class Movie(
        val movieId: Long,
        private val _title: String,
        private val _rating: Int?
    ) : RatingMediaType(title = _title, rating = _rating)

    @Serializable
    data class TvShow(
        val tvShowId: Long,
        private val _title: String,
        private val _rating: Int?
    ) : RatingMediaType(title = _title, rating = _rating)

    @Serializable
    data class Episode(
        val tvShowId: Long,
        private val _title: String,
        private val _rating: Int?,
        val seasonNumber: Int,
        val episodeNumber: Int,
    ) : RatingMediaType(title = _title, rating = _rating)
}