package com.buntupana.tmdb.feature.discover.presentation.mapper

import com.buntupana.tmdb.core.ui.util.SelectableItem
import com.buntupana.tmdb.core.ui.util.UiText
import com.buntupana.tmdb.feature.discover.domain.entity.MonetizationType
import com.buntupana.tmdb.feature.discover.domain.entity.MovieGenre
import com.buntupana.tmdb.feature.discover.domain.entity.ReleaseType
import com.buntupana.tmdb.feature.discover.domain.entity.TvShowGenre
import com.buntupana.tmdb.feature.discover.presentation.R

fun MovieGenre.toStringResourceId(): Int {
    return when (this) {
        MovieGenre.ACTION -> R.string.text_genre_action
        MovieGenre.ADVENTURE -> R.string.text_genre_adventure
        MovieGenre.ANIMATION -> R.string.text_genre_animation
        MovieGenre.COMEDY -> R.string.text_genre_comedy
        MovieGenre.CRIME -> R.string.text_genre_crime
        MovieGenre.DOCUMENTARY -> R.string.text_genre_documentary
        MovieGenre.DRAMA -> R.string.text_genre_drama
        MovieGenre.FAMILY -> R.string.text_genre_family
        MovieGenre.FANTASY -> R.string.text_genre_fantasy
        MovieGenre.HISTORY -> R.string.text_genre_history
        MovieGenre.HORROR -> R.string.text_genre_horror
        MovieGenre.MUSIC -> R.string.text_genre_music
        MovieGenre.MYSTERY -> R.string.text_genre_mystery
        MovieGenre.ROMANCE -> R.string.text_genre_romance
        MovieGenre.SCI_FI -> R.string.text_genre_science_fiction
        MovieGenre.THRILLER -> R.string.text_genre_thriller
        MovieGenre.WAR -> R.string.text_genre_war
        MovieGenre.WESTERN -> R.string.text_genre_western
    }
}

fun MovieGenre.toSelectableItem(id: Int, isSelected: Boolean = false): SelectableItem {
    return SelectableItem(
        id = id,
        name = UiText.StringResource(this.toStringResourceId()),
        isSelected = isSelected
    )
}

fun TvShowGenre.toStringResourceId(): Int {
    return when(this) {
        TvShowGenre.ACTION_AND_ADVENTURE -> R.string.text_genre_action_and_adventure
        TvShowGenre.ANIMATION -> R.string.text_genre_animation
        TvShowGenre.COMEDY -> R.string.text_genre_comedy
        TvShowGenre.CRIME -> R.string.text_genre_crime
        TvShowGenre.DOCUMENTARY -> R.string.text_genre_documentary
        TvShowGenre.DRAMA -> R.string.text_genre_drama
        TvShowGenre.FAMILY -> R.string.text_genre_family
        TvShowGenre.KIDS -> R.string.text_genre_kids
        TvShowGenre.MYSTERY -> R.string.text_genre_mystery
        TvShowGenre.NEWS -> R.string.text_genre_news
        TvShowGenre.REALITY -> R.string.text_genre_reality
        TvShowGenre.SCI_FI_AND_FANTASY -> R.string.text_genre_sci_fi_and_fantasy
        TvShowGenre.SOAP -> R.string.text_genre_soap
        TvShowGenre.TALK -> R.string.text_genre_talk
        TvShowGenre.WAR_AND_POLITICS -> R.string.text_genre_war_and_politics
        TvShowGenre.WESTERN -> R.string.text_genre_western
    }
}

fun TvShowGenre.toSelectableItem(id: Int, isSelected: Boolean = false): SelectableItem {
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