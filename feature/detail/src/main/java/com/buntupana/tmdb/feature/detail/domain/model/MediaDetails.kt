package com.buntupana.tmdb.feature.detail.domain.model

import android.os.Parcelable
import com.buntupana.tmdb.core.domain.model.MediaItem
import kotlinx.parcelize.Parcelize
import java.time.LocalDate

@Parcelize
sealed class MediaDetails(
    open val id: Long,
    open val title: String,
    open val posterUrl: String?,
    open val backdropUrl: String?,
    open val trailerUrl: String,
    open val overview: String,
    open val tagLine: String,
    open val releaseDate: LocalDate?,
    open val userScore: Int,
    open val runTime: Long,
    open val genreList: List<String>,
    open val ageCertification: String,
    open val creatorList: List<Person.Crew>,
    open val castList: List<Person.Cast>,
    open val crewList: List<Person.Crew>,
    open val recommendationList: List<MediaItem>
) : Parcelable {

    data class Movie(
        override val id: Long,
        override val title: String,
        override val posterUrl: String?,
        override val backdropUrl: String?,
        override val trailerUrl: String,
        override val overview: String,
        override val tagLine: String,
        override val releaseDate: LocalDate?,
        val localReleaseDate: String?,
        override val userScore: Int,
        override val runTime: Long,
        override val genreList: List<String>,
        override val ageCertification: String,
        override val creatorList: List<Person.Crew.Movie>,
        override val castList: List<Person.Cast.Movie>,
        override val crewList: List<Person.Crew.Movie>,
        override val recommendationList: List<MediaItem>,
        val localCountryCodeRelease: String
    ) : MediaDetails(
        id = id,
        title = title,
        posterUrl = posterUrl,
        backdropUrl = backdropUrl,
        trailerUrl = trailerUrl,
        overview = overview,
        tagLine = tagLine,
        releaseDate = releaseDate,
        userScore = userScore,
        runTime = runTime,
        genreList = genreList,
        ageCertification = ageCertification,
        creatorList = creatorList,
        castList = castList,
        crewList = crewList,
        recommendationList = recommendationList
    )

    data class TvShow(
        override val id: Long,
        override val title: String,
        override val posterUrl: String?,
        override val backdropUrl: String?,
        override val trailerUrl: String,
        override val overview: String,
        override val tagLine: String,
        override val releaseDate: LocalDate?,
        override val userScore: Int,
        override val runTime: Long,
        override val genreList: List<String>,
        override val ageCertification: String,
        override val creatorList: List<Person.Crew.TvShow>,
        override val castList: List<Person.Cast.TvShow>,
        override val crewList: List<Person.Crew.TvShow>,
        override val recommendationList: List<MediaItem>,
        val seasonList: List<Season>,
        val lastEpisode: Episode?,
        val nextEpisode: Episode?,
        val isInAir: Boolean
    ) : MediaDetails(
        id = id,
        title = title,
        posterUrl = posterUrl,
        backdropUrl = backdropUrl,
        trailerUrl = trailerUrl,
        overview = overview,
        tagLine = tagLine,
        releaseDate = releaseDate,
        userScore = userScore,
        runTime = runTime,
        genreList = genreList,
        ageCertification = ageCertification,
        creatorList = creatorList,
        castList = castList,
        crewList = crewList,
        recommendationList = recommendationList
    )
}
