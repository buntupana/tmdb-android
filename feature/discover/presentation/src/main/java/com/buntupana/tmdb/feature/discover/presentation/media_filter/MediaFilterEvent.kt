package com.buntupana.tmdb.feature.discover.presentation.media_filter

import com.buntupana.tmdb.feature.discover.domain.entity.MediaFilter
import com.buntupana.tmdb.feature.discover.domain.entity.MonetizationType
import com.buntupana.tmdb.feature.discover.domain.entity.ReleaseType
import java.time.LocalDate

sealed class MediaFilterEvent {
    data class Init(val mediaFilter: MediaFilter) : MediaFilterEvent()

    data class ChangeSortBy(
        val sortBySimple: SortBySimple,
        val sortByOrder: SortByOrder
    ) : MediaFilterEvent()

    data class SelectMonetizationType(val monetizationType: MonetizationType) : MediaFilterEvent()

    data class SelectReleaseType(val releaseType: ReleaseType) : MediaFilterEvent()

    data class SelectReleaseDateRange(val releaseDateFrom: LocalDate?, val releaseDateTo: LocalDate?) : MediaFilterEvent()
}