package com.buntupana.tmdb.feature.discover.presentation.movies

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.FilterList
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.buntupana.tmdb.feature.discover.domain.entity.MediaFilter
import com.buntupana.tmdb.feature.discover.presentation.media_filter.MediaFilterDialog

@Composable
fun MoviesScreen(
    viewModel: MoviesViewModel = hiltViewModel(),
    onMovieItemClicked: (mediaItemId: Long, posterDominantColor: Color) -> Unit
) {
    MoviesContent(
        state = viewModel.state,
        onMediaClick = { mediaId, mainPosterColor ->
            onMovieItemClicked(mediaId, mainPosterColor)
        },
        onApplyFilterClick = {
            viewModel.onEvent(MoviesEvent.FilterMovies(mediaFilter = it))
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoviesContent(
    state: MoviesState,
    onMediaClick: (mediaId: Long, mainPosterColor: Color) -> Unit,
    onApplyFilterClick: (mediaFilter: MediaFilter) -> Unit
) {

    var showDefaultFiltersDialog by remember { mutableStateOf(false) }
    var showMovieFiltersDialog by remember { mutableStateOf(false) }

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
                    Text(
                        modifier = Modifier.animateContentSize(),
                        text = stringResource(state.movieFilter.filterNameResId)
                    )
                }
            }

            IconButton(
                onClick = {
                    showMovieFiltersDialog = true
                },
                rippleColor = MaterialTheme.colorScheme.background.getOnBackgroundColor()
            ) {
                Icon(
                    imageVector = Icons.Rounded.FilterList,
                    contentDescription = "Filter"
                )
            }
        }

        val lazyListState: LazyListState = rememberLazyListState()
        val movieItems = state.movieItems?.collectAsLazyPagingItems()

        if (movieItems != null) {
            LazyColumnGeneric(
                modifier = Modifier.fillMaxSize(),
                state = lazyListState,
                topPadding = Dimens.padding.medium,
                animateItem = false,
                itemList = movieItems,
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
                        onMediaClick(item.id, mainPosterColor)
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

    MovieFilterDialog(
        showDialog = showDefaultFiltersDialog,
        onDismiss = {
            showDefaultFiltersDialog = false
        },
        onApplyFilterClick = onApplyFilterClick,
    )

    MediaFilterDialog(
        showDialog = showMovieFiltersDialog,
        mediaFilter = state.mediaFilter,
        onDismiss = {
            showMovieFiltersDialog = false
        },
        onApplyFilterClick = onApplyFilterClick
    )
}

@Preview(showBackground = true)
@Composable
fun MoviesScreenPreview() {
    MoviesContent(
        state = MoviesState(),
        onMediaClick = { _, _ -> },
        onApplyFilterClick = {}
    )
}