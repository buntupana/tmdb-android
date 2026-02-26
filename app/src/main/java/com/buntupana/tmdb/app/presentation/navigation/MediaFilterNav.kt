package com.buntupana.tmdb.app.presentation.navigation

import androidx.compose.runtime.Composable
import com.buntupana.tmdb.core.ui.navigation.NavRoutesMain
import com.buntupana.tmdb.feature.discover.presentation.media_filter.MediaFilterResult
import com.buntupana.tmdb.feature.discover.presentation.media_filter.MediaFilterScreen

@Composable
fun MediaFilterNav(
    navRoutesMain: NavRoutesMain
) {
    MediaFilterScreen(
        onBackClick = {
            navRoutesMain.popBackStack()
        },
        onApplyFilterClick = { mediaListFilter ->
            navRoutesMain.saveResult(
                MediaFilterResult(mediaListFilter)
            )
            navRoutesMain.popBackStack()
        }
    )
}