package com.buntupana.tmdb.feature.discover.presentation.media_list

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.FilterList
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.buntupana.tmdb.core.ui.composables.item.MediaItemHorizontal
import com.buntupana.tmdb.core.ui.composables.list.LazyColumnGeneric
import com.buntupana.tmdb.core.ui.theme.Dimens
import com.buntupana.tmdb.core.ui.theme.SecondaryColor
import com.buntupana.tmdb.core.ui.util.IconButton
import com.buntupana.tmdb.core.ui.util.getOnBackgroundColor
import com.buntupana.tmdb.feature.discover.presentation.media_list.filters.MediaFilterListDefault
import com.buntupana.tmdb.feature.discover.presentation.model.MediaListFilter
import com.panabuntu.tmdb.core.common.entity.MediaType

@Composable
fun MediaListScreen(
    viewModel: MediaListViewModel = hiltViewModel(),
    mediaListFilterResult: MediaListFilter?,
    onMediaItemClicked: (mediaType: MediaType, mediaItemId: Long, posterDominantColor: Color) -> Unit,
    onFilterClick: (movieListFilter: MediaListFilter) -> Unit
) {

    LaunchedEffect(mediaListFilterResult) {
        if (mediaListFilterResult != null) {
            viewModel.onEvent(MediaListEvent.FilterMediaList(mediaListFilterResult))
        }
    }

    MediaListContent(
        state = viewModel.state,
        onMediaClick = onMediaItemClicked,
        onApplyFilterClick = {
            viewModel.onEvent(MediaListEvent.FilterMediaList(mediaListFilter = it))
        },
        onFilterClick = onFilterClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MediaListContent(
    state: MediaListState,
    onMediaClick: (mediaType: MediaType, mediaItemId: Long, posterDominantColor: Color) -> Unit,
    onApplyFilterClick: (movieListFilter: MediaListFilter) -> Unit,
    onFilterClick: (movieListFilter: MediaListFilter) -> Unit
) {

    var showDefaultFiltersDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Dimens.padding.medium, vertical = Dimens.padding.vertical),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Button(
                colors = ButtonDefaults.buttonColors(containerColor = SecondaryColor),
                onClick = { showDefaultFiltersDialog = true },
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    val filterNameResId = when(state.mediaType) {
                        MediaType.MOVIE -> state.movieDefaultFilter.filterNameResId
                        MediaType.TV_SHOW -> state.tvShowDefaultFilter.filterNameResId
                    }

                    Text(
                        modifier = Modifier.animateContentSize(),
                        text = stringResource(filterNameResId)
                    )
                }
            }

            IconButton(
                onClick = {
                    onFilterClick(state.mediaListFilter)
                },
                rippleColor = MaterialTheme.colorScheme.background.getOnBackgroundColor()
            ) {
                Icon(
                    imageVector = Icons.Rounded.FilterList,
                    contentDescription = "Filter"
                )
            }
        }

        val mediaItems = when (state.mediaType) {
            MediaType.MOVIE -> state.movieItems?.collectAsLazyPagingItems()
            MediaType.TV_SHOW -> state.tvShowItems?.collectAsLazyPagingItems()
        }

        if (mediaItems != null) {
            LazyColumnGeneric(
                modifier = Modifier.fillMaxSize(),
                topPadding = Dimens.padding.medium,
                animateItem = false,
                itemList = mediaItems,
                noResultContent = {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No results"
                        )
                    }
                }
            ) { _, item ->
                MediaItemHorizontal(
                    modifier = Modifier
                        .animateItem()
                        .height(Dimens.imageSize.posterHeight),
                    onMediaClick = { _, mainPosterColor ->
                        onMediaClick(state.mediaType, item.id, mainPosterColor)
                    },
                    mediaId = item.id,
                    title = item.name,
                    posterUrl = item.posterUrl,
                    overview = item.overview,
                    releaseDate = item.releaseDate
                )
            }
        }
    }

    MediaDefaultFilterDialog(
        mediaType = state.mediaType,
        showDialog = showDefaultFiltersDialog,
        onDismiss = {
            showDefaultFiltersDialog = false
        },
        onApplyFilterClick = onApplyFilterClick,
    )
}

@Preview(showBackground = true)
@Composable
fun MoviesScreenPreview() {
    MediaListContent(
        state = MediaListState(
            mediaType = MediaType.MOVIE,
            mediaListFilter = MediaFilterListDefault.popularMovie
        ),
        onMediaClick = { _, _, _ -> },
        onFilterClick = {},
        onApplyFilterClick = {}
    )
}