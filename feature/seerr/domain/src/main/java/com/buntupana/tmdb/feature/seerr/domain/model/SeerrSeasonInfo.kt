package com.buntupana.tmdb.feature.seerr.domain.model

import com.panabuntu.tmdb.core.common.model.SeerrStatus

data class SeerrSeasonInfo(
    val seasonNumber: Int,
    val seasonName: String?,
    val status: SeerrStatus?
)