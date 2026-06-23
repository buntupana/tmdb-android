package com.buntupana.tmdb.feature.seerr.domain.model

import com.panabuntu.tmdb.core.common.model.SeerrStatus

data class SeerrMediaInfo(
    val mediaStatus: SeerrStatus?,
    val seasons: List<SeerrSeasonInfo>? = null
)
