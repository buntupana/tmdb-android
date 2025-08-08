package com.buntupana.tmdb.feature.discover.presentation.mapper

import com.buntupana.tmdb.core.ui.util.SelectableItem
import com.buntupana.tmdb.core.ui.util.UiText
import com.buntupana.tmdb.feature.discover.domain.entity.Genre
import com.buntupana.tmdb.feature.discover.domain.entity.MonetizationType
import com.buntupana.tmdb.feature.discover.domain.entity.ReleaseType
import com.buntupana.tmdb.feature.discover.presentation.R

fun Genre.toStringResourceId(): Int {
    return when (this) {
        Genre.ACTION -> R.string.text_genre_action
        Genre.ADVENTURE -> R.string.text_genre_adventure
        Genre.ANIMATION -> R.string.text_genre_animation
        Genre.COMEDY -> R.string.text_genre_comedy
        Genre.CRIME -> R.string.text_genre_crime
        Genre.DOCUMENTARY -> R.string.text_genre_documentary
        Genre.DRAMA -> R.string.text_genre_drama
        Genre.FAMILY -> R.string.text_genre_family
        Genre.FANTASY -> R.string.text_genre_fantasy
        Genre.HISTORY -> R.string.text_genre_history
        Genre.HORROR -> R.string.text_genre_horror
        Genre.MUSIC -> R.string.text_genre_music
        Genre.MYSTERY -> R.string.text_genre_mystery
        Genre.ROMANCE -> R.string.text_genre_romance
        Genre.SCI_FI -> R.string.text_genre_science_fiction
        Genre.THRILLER -> R.string.text_genre_thriller
        Genre.WAR -> R.string.text_genre_war
        Genre.WESTERN -> R.string.text_genre_western
    }
}

fun Genre.toSelectableItem(id: Int, isSelected: Boolean = false): SelectableItem {
    return SelectableItem(
        id = id,
        name = UiText.StringResource(this.toStringResourceId()),
        isSelected = isSelected
    )
}

fun MonetizationType.toStringResourceId(): Int {
    return when (this) {
        MonetizationType.FREE -> R.string.text_availability_free
        MonetizationType.FLAT_RATE -> R.string.text_availability_stream
        MonetizationType.RENT -> R.string.text_availability_rent
        MonetizationType.ADS -> R.string.text_availability_ads
        MonetizationType.BUY -> R.string.text_availability_buy
    }
}

fun MonetizationType.toSelectableItem(id: Int, isSelected: Boolean = false): SelectableItem {
    return SelectableItem(
        id = id,
        name = UiText.StringResource(this.toStringResourceId()),
        isSelected = isSelected
    )
}

fun ReleaseType.toStringResourceId(): Int {
    return when (this) {
        ReleaseType.THEATRICAL_LIMITED -> R.string.text_release_type_theatrical_limited
        ReleaseType.THEATRICAL -> R.string.text_release_type_theatrical
        ReleaseType.PREMIER -> R.string.text_release_type_premier
        ReleaseType.DIGITAL -> R.string.text_release_type_digital
        ReleaseType.PHYSICAL -> R.string.text_release_type_physical
        ReleaseType.TV -> R.string.text_release_type_tv
    }
}

fun ReleaseType.toSelectableItem(id: Int, isSelected: Boolean = false): SelectableItem {
    return SelectableItem(
        id = id,
        name = UiText.StringResource(this.toStringResourceId()),
        isSelected = isSelected
    )
}