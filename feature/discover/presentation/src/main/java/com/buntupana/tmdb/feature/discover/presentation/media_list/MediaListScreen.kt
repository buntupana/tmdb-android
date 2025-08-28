package com.buntupana.tmdb.feature.discover.presentation.media_list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
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
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.buntupana.tmdb.core.ui.composables.CircularProgressIndicatorDelayed
import com.buntupana.tmdb.core.ui.composables.item.MediaItemHorizontal
import com.buntupana.tmdb.core.ui.composables.list.LazyColumnGeneric
import com.buntupana.tmdb.core.ui.theme.Dimens
import com.buntupana.tmdb.core.ui.theme.PrimaryColor
import com.buntupana.tmdb.core.ui.util.SetSystemBarsColors
import com.buntupana.tmdb.feature.discover.presentation.media_list.comp.MediaListTopBar
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

    SetSystemBarsColors(
        statusBarColor = PrimaryColor
    )

    var showDefaultFiltersDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {

            val filterNameResId = when (state.mediaType) {
                MediaType.MOVIE -> state.movieDefaultFilter.filterNameResId
                MediaType.TV_SHOW -> state.tvShowDefaultFilter.filterNameResId
            }

            MediaListTopBar(
                filterName = stringResource(filterNameResId),
                onDefaultFiltersClick = {
                    showDefaultFiltersDialog = true
                },
                onFilterClick = {
                    onFilterClick(state.mediaListFilter)
                }
            )
        }
    ) { paddingValues ->

        val mediaItems = when (state.mediaType) {
            MediaType.MOVIE -> state.movieItems?.collectAsLazyPagingItems()
            MediaType.TV_SHOW -> state.tvShowItems?.collectAsLazyPagingItems()
        }

        if (
            state.isLoading ||
            mediaItems?.loadState?.refresh is LoadState.Loading
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicatorDelayed(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }

        if (mediaItems != null) {
            LazyColumnGeneric(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = paddingValues.calculateTopPadding()),
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

@Preview(showBackground = true, showSystemUi = true)
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