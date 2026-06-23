package com.buntupana.tmdb.feature.seer.domain.model

import com.panabuntu.tmdb.core.common.model.SeerrStatus

data class SeerMediaInfo(
    val mediaStatus: SeerrStatus?,
    val seasons: List<SeerSeasonInfo>? = null
)
