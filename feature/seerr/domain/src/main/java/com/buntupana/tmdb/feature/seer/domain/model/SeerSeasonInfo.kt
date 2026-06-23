package com.buntupana.tmdb.feature.seer.domain.model

import com.panabuntu.tmdb.core.common.model.SeerrStatus

data class SeerSeasonInfo(
    val seasonNumber: Int,
    val seasonName: String?,
    val status: SeerrStatus?
)