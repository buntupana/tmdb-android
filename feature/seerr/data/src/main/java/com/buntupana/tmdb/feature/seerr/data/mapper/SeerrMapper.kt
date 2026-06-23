package com.buntupana.tmdb.feature.seerr.data.mapper

import com.buntupana.tmdb.feature.seerr.data.remote_data_source.raw.SeerrMediaRaw
import com.buntupana.tmdb.feature.seerr.data.remote_data_source.raw.SeerrUserRaw
import com.buntupana.tmdb.feature.seerr.domain.model.SeerrMediaInfo
import com.buntupana.tmdb.feature.seerr.domain.model.SeerrSeasonInfo
import com.buntupana.tmdb.feature.seerr.domain.model.SeerrUser
import com.panabuntu.tmdb.core.common.model.SeerrStatus
import com.panabuntu.tmdb.core.common.util.isNotNullOrEmpty

fun SeerrUserRaw.toDomain(): SeerrUser = SeerrUser(
    id = id,
    email = email,
    displayName = displayName ?: username ?: plexUsername ?: jellyfinUsername,
    avatar = avatar
)

fun SeerrMediaRaw.toDomain(): SeerrMediaInfo {

    val seasonInfoList = if (mediaInfo == null && seasons.isNotNullOrEmpty()) {
        seasons.map { season ->
            SeerrSeasonInfo(
                seasonNumber = season.seasonNumber,
                seasonName = season.name,
                status = SeerrStatus.UNKNOWN
            )
        }
    } else {

        // Convert requests to a Map for O(1) lookup during iteration
        val requestSeasonsByNumber = mediaInfo?.requests
            .orEmpty()
            .flatMap { it.seasons }
            .associateBy { it.seasonNumber }

        mediaInfo?.seasons?.map { season ->
            val requestSeason = requestSeasonsByNumber[season.seasonNumber]

            // Status priority: Use the request status if available, fallback to the media status
            val status = when (requestSeason?.status) {
                1 -> SeerrStatus.PENDING
                2 -> SeerrStatus.REQUESTED
                3, 4 -> SeerrStatus.UNKNOWN
                5 -> SeerrStatus.AVAILABLE
                else -> SeerrStatus.fromCode(season.status)
            }

            SeerrSeasonInfo(
                seasonNumber = season.seasonNumber,
                seasonName = seasons?.firstOrNull { it.seasonNumber == season.seasonNumber }?.name,
                status = status
            )
        }
    }

    return SeerrMediaInfo(
        mediaStatus = SeerrStatus.fromCode(mediaInfo?.status),
        seasons = seasonInfoList
    )
}
