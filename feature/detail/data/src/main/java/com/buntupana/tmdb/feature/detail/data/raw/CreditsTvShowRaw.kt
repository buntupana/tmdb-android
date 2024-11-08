package com.buntupana.tmdb.feature.detail.data.raw

data class CreditsTvShowRaw(
    val cast: List<MediaCastTvShowRaw>,
    val crew: List<MediaCrewTvShowRaw>
)