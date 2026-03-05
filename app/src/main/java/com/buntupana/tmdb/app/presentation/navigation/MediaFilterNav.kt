package com.buntupana.tmdb.app.presentation.navigation

import androidx.compose.runtime.Composable
import com.buntupana.tmdb.app.presentation.getViewModel
import com.buntupana.tmdb.app.presentation.nav3.Navigator
import com.buntupana.tmdb.app.presentation.nav3.ResultStore
import com.buntupana.tmdb.app.presentation.nav3.RouteNav3
import com.buntupana.tmdb.feature.discover.presentation.media_filter.MediaFilterNavArgs
import com.buntupana.tmdb.feature.discover.presentation.media_filter.MediaFilterResult
import com.buntupana.tmdb.feature.discover.presentation.media_filter.MediaFilterScreen

@Composable
fun MediaFilterNav(
    navigator: Navigator,
    resultStore: ResultStore,
    route: RouteNav3.MediaFilter
) {

    val navArgs = MediaFilterNavArgs(route.mediaListFilter)

    MediaFilterScreen(
        viewModel = getViewModel(navArgs),
        onBackClick = {
            navigator.goBack()
        },
        onApplyFilterClick = { mediaListFilter ->
            resultStore.setResult(MediaFilterResult::class, MediaFilterResult(mediaListFilter))
            navigator.goBack()
        }
    )
}