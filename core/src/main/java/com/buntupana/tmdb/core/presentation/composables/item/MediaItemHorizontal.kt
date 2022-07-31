package com.buntupana.tmdb.core.presentation.composables.item

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.buntupana.tmdb.core.domain.model.MediaItem

@Composable
fun MediaItemHorizontal(
    modifier: Modifier,
    clickable: (() -> Unit)? = null,
    mediaItem: MediaItem
) {
    when (mediaItem) {
        is MediaItem.Movie, is MediaItem.TvShow -> {

            val overview: String
            val releaseDate: String
            when (mediaItem) {
                is MediaItem.Movie -> {
                    overview = mediaItem.overview
                    releaseDate = mediaItem.releaseDate
                }
                is MediaItem.TvShow -> {
                    overview = mediaItem.overview
                    releaseDate = mediaItem.releaseDate
                }
                else -> {
                    overview = ""
                    releaseDate = ""
                }
            }
            MovieOrTvShowItemHorizontal(
                modifier = modifier,
                clickable = clickable,
                title = mediaItem.name,
                posterUrl = mediaItem.posterUrl,
                overview = overview,
                releaseDate = releaseDate
            )
        }
        is MediaItem.Person -> {
            PersonItemHorizontal(
                modifier = modifier,
                name = mediaItem.name,
                posterUrl = mediaItem.posterUrl,
                knownForDepartment = mediaItem.knownForDepartment,
                knownFor = mediaItem.knowFor.joinToString(", ")
            )
        }
        MediaItem.Unknown -> {}
    }
}