package com.buntupana.tmdb.feature.discover.presentation.media_list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalBottomSheetProperties
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.buntupana.tmdb.core.ui.theme.Dimens
import com.buntupana.tmdb.feature.discover.presentation.R
import com.buntupana.tmdb.feature.discover.presentation.media_list.filters.MediaFilterListDefault
import com.buntupana.tmdb.feature.discover.presentation.media_list.filters.MovieDefaultFilter
import com.buntupana.tmdb.feature.discover.presentation.media_list.filters.TvShowDefaultFilter
import com.buntupana.tmdb.feature.discover.presentation.model.MediaListFilter
import com.panabuntu.tmdb.core.common.entity.MediaType
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MediaDefaultFilterDialog(
    mediaType: MediaType,
    showDialog: Boolean,
    sheetState: SheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
    onDismiss: () -> Unit,
    onApplyFilterClick: (movieListFilter: MediaListFilter) -> Unit,
) {

    if (showDialog.not()) return

    val coroutineScope = rememberCoroutineScope()

    MovieFilterContent(
        mediaType = mediaType,
        sheetState = sheetState,
        onDismiss = {
            coroutineScope.launch {
                sheetState.hide()
                onDismiss()
            }
        },
        onApplyFilterClick = onApplyFilterClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MovieFilterContent(
    mediaType: MediaType,
    sheetState: SheetState,
    onDismiss: () -> Unit,
    onApplyFilterClick: (movieListFilter: MediaListFilter) -> Unit,
) {

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = MaterialTheme.colorScheme.background,
        dragHandle = {},
        properties = ModalBottomSheetProperties(shouldDismissOnBackPress = false)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    vertical = Dimens.padding.big
                )
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(R.string.text_select_filter),
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(Dimens.padding.big))

            LazyColumn {

                val listSize = when (mediaType) {
                    MediaType.MOVIE -> MovieDefaultFilter.entries.size
                    MediaType.TV_SHOW -> TvShowDefaultFilter.entries.size
                }

                items(count = listSize) { index ->

                    if (isCustomFilter(mediaType, index)) return@items

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                onApplyFilterClick(getMediaDefaultFilter(mediaType, index))
                                onDismiss()
                            }
                            .padding(
                                vertical = Dimens.padding.medium,
                                horizontal = Dimens.padding.horizontal
                            )
                    ) {

                        val titleResid = when (mediaType) {
                            MediaType.MOVIE -> MovieDefaultFilter.entries[index].filterNameResId
                            MediaType.TV_SHOW -> TvShowDefaultFilter.entries[index].filterNameResId
                        }

                        Text(
                            text = stringResource(titleResid),
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

private fun getMediaDefaultFilter(mediaType: MediaType, index: Int): MediaListFilter {
    return when (mediaType) {
        MediaType.MOVIE -> {
            when (MovieDefaultFilter.entries[index]) {
                MovieDefaultFilter.POPULAR -> MediaFilterListDefault.popularMovie
                MovieDefaultFilter.NOW_PLAYING -> MediaFilterListDefault.nowPlayingMovie
                MovieDefaultFilter.UPCOMING -> MediaFilterListDefault.upcomingMovie
                MovieDefaultFilter.TOP_RATED -> MediaFilterListDefault.topRatedMovie
                MovieDefaultFilter.CUSTOM -> MediaFilterListDefault.popularMovie
            }
        }

        MediaType.TV_SHOW -> {
            when (TvShowDefaultFilter.entries[index]) {
                TvShowDefaultFilter.POPULAR -> MediaFilterListDefault.popularTvShow
                TvShowDefaultFilter.AIRING_TODAY -> MediaFilterListDefault.airingTodayTvShow
                TvShowDefaultFilter.ON_TV -> MediaFilterListDefault.onTvTvShow
                TvShowDefaultFilter.TOP_RATED -> MediaFilterListDefault.topRatedTvShow
                TvShowDefaultFilter.CUSTOM -> MediaFilterListDefault.popularTvShow
            }
        }
    }
}

private fun isCustomFilter(mediaType: MediaType, index: Int): Boolean {
    return when (mediaType) {
        MediaType.MOVIE -> MovieDefaultFilter.entries[index] == MovieDefaultFilter.CUSTOM
        MediaType.TV_SHOW -> TvShowDefaultFilter.entries[index] == TvShowDefaultFilter.CUSTOM
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
private fun MovieFilterDialogPreview() {
    MediaDefaultFilterDialog(
        mediaType = MediaType.TV_SHOW,
        sheetState = SheetState(
            skipPartiallyExpanded = true,
            positionalThreshold = { 0f },
            initialValue = SheetValue.Expanded,
            velocityThreshold = { 0f }
        ),
        showDialog = true,
        onDismiss = {},
        onApplyFilterClick = {}
    )
}