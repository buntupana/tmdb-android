package com.buntupana.tmdb.feature.discover.presentation.model

import android.os.Parcelable
import com.buntupana.tmdb.core.ui.util.LocalDateSerializer
import com.buntupana.tmdb.feature.discover.domain.entity.MonetizationType
import com.buntupana.tmdb.feature.discover.domain.entity.MovieGenre
import com.buntupana.tmdb.feature.discover.domain.entity.ReleaseType
import com.buntupana.tmdb.feature.discover.domain.entity.TvShowGenre
import com.buntupana.tmdb.feature.discover.presentation.media_filter.SortByOrder
import com.buntupana.tmdb.feature.discover.presentation.media_filter.SortBySimple
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import java.time.LocalDate

@Serializable
@Parcelize
sealed class MediaListFilter(
    val sortBy: SortBySimple = SortBySimple.POPULARITY,
    val sortByOrder: SortByOrder = SortByOrder.DESCENDING,
    val monetizationTypeList: List<MonetizationType> = emptyList(),
    @Serializable(with = LocalDateSerializer::class)
    val releaseDateFrom: LocalDate? = null,
    @Serializable(with = LocalDateSerializer::class)
    val releaseDateTo: LocalDate? = null,
    val language: String? = null,
    val userScoreMin: Int = 0,
    val userScoreMax: Int = 100,
    val includeNotRated: Boolean = true,
    val minUserVotes: Int = 0,
    val runtimeMin: Int = 0,
    val runtimeMax: Int = 390
) : Parcelable {

    @Serializable
    data class Movie(
        private val _sortBy: SortBySimple = SortBySimple.POPULARITY,
        private val _sortByOrder: SortByOrder = SortByOrder.DESCENDING,
        val releaseTypeList: List<ReleaseType> = emptyList(),
        private val _monetizationTypeList: List<MonetizationType> = emptyList(),
        @Serializable(with = LocalDateSerializer::class)
        private val _releaseDateFrom: LocalDate? = null,
        @Serializable(with = LocalDateSerializer::class)
        private val _releaseDateTo: LocalDate? = null,
        val genreList: List<MovieGenre> = emptyList(),
        private val _language: String? = null,
        private val _userScoreMin: Int = 0,
        private val _userScoreMax: Int = 100,
        private val _includeNotRated: Boolean = true,
        private val _minUserVotes: Int = 0,
        private val _runtimeMin: Int = 0,
        private val _runtimeMax: Int = 390
    ) : MediaListFilter(
        sortBy = _sortBy,
        sortByOrder = _sortByOrder,
        monetizationTypeList = _monetizationTypeList,
        releaseDateFrom = _releaseDateFrom,
        releaseDateTo = _releaseDateTo,
        language = _language,
        userScoreMin = _userScoreMin,
        userScoreMax = _userScoreMax,
        includeNotRated = _includeNotRated,
        minUserVotes = _minUserVotes,
        runtimeMin = _runtimeMin,
        runtimeMax = _runtimeMax
    )

    @Serializable
    data class TvShow(
        private val _sortBy: SortBySimple = SortBySimple.POPULARITY,
        private val _sortByOrder: SortByOrder = SortByOrder.DESCENDING,
        private val _monetizationTypeList: List<MonetizationType> = emptyList(),
        @Serializable(with = LocalDateSerializer::class)
        private val _releaseDateFrom: LocalDate? = null,
        @Serializable(with = LocalDateSerializer::class)
        private val _releaseDateTo: LocalDate? = null,
        val genreList: List<TvShowGenre> = emptyList(),
        private val _language: String? = null,
        private val _userScoreMin: Int = 0,
        private val _userScoreMax: Int = 100,
        private val _includeNotRated: Boolean = true,
        private val _minUserVotes: Int = 0,
        private val _runtimeMin: Int = 0,
        private val _runtimeMax: Int = 390
    ) : MediaListFilter(
        sortBy = _sortBy,
        sortByOrder = _sortByOrder,
        monetizationTypeList = _monetizationTypeList,
        releaseDateFrom = _releaseDateFrom,
        releaseDateTo = _releaseDateTo,
        language = _language,
        userScoreMin = _userScoreMin,
        userScoreMax = _userScoreMax,
        includeNotRated = _includeNotRated,
        minUserVotes = _minUserVotes,
        runtimeMin = _runtimeMin,
        runtimeMax = _runtimeMax
    )
}