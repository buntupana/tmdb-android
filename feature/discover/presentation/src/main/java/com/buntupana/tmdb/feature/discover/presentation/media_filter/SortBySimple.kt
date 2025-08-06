package com.buntupana.tmdb.feature.discover.presentation.media_filter

import androidx.annotation.StringRes
import com.buntupana.tmdb.feature.discover.domain.entity.MonetizationType
import com.buntupana.tmdb.feature.discover.domain.entity.ReleaseType
import com.buntupana.tmdb.feature.discover.presentation.R

enum class SortBySimple(
    @StringRes val stringResId: Int,
) {
    POPULARITY(R.string.text_sort_by_popularity),
    RATING(R.string.text_sort_by_rating),
    RELEASE_DATE(R.string.text_sort_by_release_date),
    TITLE(R.string.text_sort_by_title),
}

enum class SortByOrder(
    @StringRes val stringResId: Int
) {
    ASCENDING(R.string.text_ascending),
    DESCENDING(R.string.text_descending)
}

fun getAvailabilityResId(monetizationType: MonetizationType): Int {
    return when (monetizationType) {
        MonetizationType.FREE -> R.string.text_availability_free
        MonetizationType.FLAT_RATE -> R.string.text_availability_stream
        MonetizationType.RENT -> R.string.text_availability_rent
        MonetizationType.ADS -> R.string.text_availability_ads
        MonetizationType.BUY -> R.string.text_availability_buy
    }
}

fun getReleaseTypeResId(releaseType: ReleaseType): Int {
    return when (releaseType) {
        ReleaseType.THEATRICAL_LIMITED -> R.string.text_release_type_theatrical_limited
        ReleaseType.THEATRICAL -> R.string.text_release_type_theatrical
        ReleaseType.PREMIER -> R.string.text_release_type_premier
        ReleaseType.DIGITAL -> R.string.text_release_type_digital
        ReleaseType.PHYSICAL -> R.string.text_release_type_physical
        ReleaseType.TV -> R.string.text_release_type_tv
    }
}