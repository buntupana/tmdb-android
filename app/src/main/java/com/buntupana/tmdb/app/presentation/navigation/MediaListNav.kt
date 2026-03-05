package com.buntupana.tmdb.app.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import com.buntupana.tmdb.app.presentation.nav3.Navigator
import com.buntupana.tmdb.app.presentation.nav3.ResultStore
import com.buntupana.tmdb.feature.discover.presentation.media_list.MediaListNavArgs
import com.buntupana.tmdb.feature.discover.presentation.media_list.MediaListResult
import com.buntupana.tmdb.feature.discover.presentation.media_list.MediaListScreen
import com.buntupana.tmdb.feature.discover.presentation.model.MediaListFilter
import com.panabuntu.tmdb.core.common.entity.MediaType
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun MediaListNav(
    navigator: Navigator,
    resultStore: ResultStore,
    mediaType: MediaType
) {

    val mediaListResult = resultStore.getResult<MediaListResult>(MediaListResult::class)

    MediaListScreen(
        viewModel = getViewModelWithNavArgs(
            MediaListNavArgs(mediaType)
        ),
        mediaListResult = mediaListResult,
        onMediaItemClicked = { mediaType, mediaItemId, posterDominantColor ->

        },
        onFilterClick = { mediaListFilter ->
            when (mediaListFilter) {
                is MediaListFilter.Movie -> {}
                is MediaListFilter.TvShow -> {}
            }
        },
    )
}

@Composable
inline fun <reified V : ViewModel> getViewModelWithNavArgs(navArgs: Any? = null): V {
    return koinViewModel<V>(
        parameters = {
            parametersOf(navArgs)
        }
    )
}