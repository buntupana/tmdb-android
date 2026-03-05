package com.buntupana.tmdb.app.presentation.navigation

import androidx.compose.runtime.Composable
import com.buntupana.tmdb.app.presentation.nav3.Navigator
import com.buntupana.tmdb.app.presentation.nav3.ResultStore
import com.buntupana.tmdb.feature.discover.presentation.media_filter.MediaFilterResult
import com.buntupana.tmdb.feature.discover.presentation.media_filter.MediaFilterScreen

@Composable
fun MediaFilterNav(
    navigator: Navigator,
    resultStore: ResultStore
) {
    MediaFilterScreen(
        onBackClick = {
            navigator.goBack()
        },
        onApplyFilterClick = { mediaListFilter ->
            resultStore.setResult(MediaFilterResult::class, MediaFilterResult(mediaListFilter))
            navigator.goBack()
        }
    )
}