package com.buntupana.tmdb.feature.detail.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDate

@Parcelize
sealed class MediaDetails(
    open val id: Long,
    open val title: String,
    open val posterUrl: String,
    open val backdropUrl: String,
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
    open val crewList: List<Person.Crew>
) : Parcelable {

    data class Movie(
        override val id: Long,
        override val title: String,
        override val posterUrl: String,
        override val backdropUrl: String,
        override val trailerUrl: String,
        override val overview: String,
        override val tagLine: String,
        override val releaseDate: LocalDate?,
        val localReleaseDate: String?,
        override val userScore: Int,
        override val runTime: Long,
        override val genreList: List<String>,
        override val ageCertification: String,
        override val creatorList: List<Person.Crew>,
        override val castList: List<Person.Cast>,
        override val crewList: List<Person.Crew>,
        val localCountryCodeRelease: String
    ) : MediaDetails(
        id,
        title,
        posterUrl,
        backdropUrl,
        trailerUrl,
        overview,
        tagLine,
        releaseDate,
        userScore,
        runTime,
        genreList,
        ageCertification,
        creatorList,
        castList,
        crewList
    )

    data class TvShow(
        override val id: Long,
        override val title: String,
        override val posterUrl: String,
        override val backdropUrl: String,
        override val trailerUrl: String,
        override val overview: String,
        override val tagLine: String,
        override val releaseDate: LocalDate?,
        override val userScore: Int,
        override val runTime: Long,
        override val genreList: List<String>,
        override val ageCertification: String,
        override val creatorList: List<Person.Crew>,
        override val castList: List<Person.Cast>,
        override val crewList: List<Person.Crew>
    ) : MediaDetails(
        id,
        title,
        posterUrl,
        backdropUrl,
        trailerUrl,
        overview,
        tagLine,
        releaseDate,
        userScore,
        runTime,
        genreList,
        ageCertification,
        creatorList,
        castList,
        crewList
    )
}
