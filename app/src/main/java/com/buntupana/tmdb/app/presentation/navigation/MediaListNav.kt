package com.buntupana.tmdb.app.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.toArgb
import com.buntupana.tmdb.app.presentation.getViewModel
import com.buntupana.tmdb.app.presentation.nav3.Navigator
import com.buntupana.tmdb.app.presentation.nav3.ResultStore
import com.buntupana.tmdb.app.presentation.nav3.RouteNav3
import com.buntupana.tmdb.feature.discover.presentation.media_filter.MediaFilterResult
import com.buntupana.tmdb.feature.discover.presentation.media_list.MediaListNavArgs
import com.buntupana.tmdb.feature.discover.presentation.media_list.MediaListResult
import com.buntupana.tmdb.feature.discover.presentation.media_list.MediaListScreen
import com.panabuntu.tmdb.core.common.entity.MediaType

@Composable
fun MediaListNav(
    navigator: Navigator,
    resultStore: ResultStore,
    mediaType: MediaType
) {

    val mediaListResult = resultStore.getResult<MediaFilterResult>(MediaFilterResult::class)?.let { result ->
        MediaListResult.ApplyFilter(result.mediaListFilter)
    }

    MediaListScreen(
        viewModel = getViewModel(MediaListNavArgs(mediaType)),
        mediaListResult = mediaListResult,
        onMediaItemClicked = { mediaType, mediaItemId, posterDominantColor ->
            navigator.navigate(
                RouteNav3.MediaDetail(
                    mediaId = mediaItemId,
                    mediaType = mediaType,
                    backgroundColor = posterDominantColor.toArgb()
                )
            )
        },
        onFilterClick = { mediaListFilter ->
            navigator.navigate(RouteNav3.MediaFilter(mediaListFilter))
        }
    )
}