package com.buntupana.tmdb.feature.account.presentation.account.account

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Login
import androidx.compose.material.icons.automirrored.filled.Logout
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
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import com.buntupana.tmdb.core.ui.composables.CarouselMediaItem
import com.buntupana.tmdb.core.ui.composables.TitleAndFilter
import com.buntupana.tmdb.core.ui.filter_type.MediaFilter
import com.buntupana.tmdb.core.ui.theme.Dimens
import com.buntupana.tmdb.feature.account.presentation.R
import com.buntupana.tmdb.feature.account.presentation.account.composables.AccountInfoTop
import com.buntupana.tmdb.feature.account.presentation.sign_out.SignOutDialog
import com.panabuntu.tmdb.core.common.entity.MediaType
import com.buntupana.tmdb.core.ui.R as RCore

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountScreen(
    viewModel: AccountViewModel = hiltViewModel(),
    onSignInClick: () -> Unit,
    onWatchListClick: () -> Unit,
    onMediaItemClicked: (mediaItemId: Long, mediaItemType: MediaType, posterDominantColor: Color) -> Unit
) {

    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(lifecycleOwner.lifecycle) {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.onEvent(AccountEvent.GetWatchlist(viewModel.state.watchlistFilterSelected))
        }
    }

    var showBottomSheet by remember { mutableStateOf(false) }

    AccountContent(
        state = viewModel.state,
        onSignInClick = onSignInClick,
        onSignOutClick = {
            showBottomSheet = true
        },
        onWatchListClick = onWatchListClick,
        changeWatchlistType = { mediaFilter ->
            viewModel.onEvent(AccountEvent.GetWatchlist(mediaFilter))
        },
        navigateToDetail = { mediaItem, posterDominantColor ->
            when (mediaItem) {
                is com.panabuntu.tmdb.core.common.model.MediaItem.Movie -> {
                    onMediaItemClicked(
                        mediaItem.id,
                        MediaType.MOVIE,
                        posterDominantColor
                    )
                }

                is com.panabuntu.tmdb.core.common.model.MediaItem.TvShow -> {
                    onMediaItemClicked(
                        mediaItem.id,
                        MediaType.TV_SHOW,
                        posterDominantColor
                    )
                }
            }
        }
    )

    SignOutDialog(
        showDialog = showBottomSheet,
        onDismiss = { showBottomSheet = false }
    )
}

@Composable
fun AccountContent(
    state: AccountState,
    onSignInClick: () -> Unit,
    onSignOutClick: () -> Unit,
    onWatchListClick: () -> Unit,
    changeWatchlistType: (mediaFilter: MediaFilter) -> Unit,
    navigateToDetail: (mediaItem: com.panabuntu.tmdb.core.common.model.MediaItem, posterDominantColor: Color) -> Unit
) {

    if (state.isUserLogged) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(state = rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AccountInfoTop(
                modifier = Modifier.fillMaxWidth(),
                avatarUrl = state.avatarUrl,
                username = state.username
            )

            val lazyListStateWatchlist: LazyListState = rememberLazyListState()
            val lazyListStateFavorites: LazyListState = rememberLazyListState()

            TitleAndFilter(
                modifier = Modifier.padding(vertical = Dimens.padding.medium),
                title = stringResource(id = RCore.string.text_watchlist),
                filterSet = state.watchlistFilterSet,
                indexSelected = state.watchlistFilterSet.indexOf(state.watchlistFilterSelected),
                titleClicked = onWatchListClick,
                filterClicked = { item, _ ->
                    changeWatchlistType(item)
                    lazyListStateWatchlist.requestScrollToItem(index = 0)
                }
            )

            CarouselMediaItem(
                modifier = Modifier
                    .fillMaxWidth(),
                mediaItemList = state.watchlistMediaItemList,
                isLoadingError = state.isWatchlistLoadingError,
                lazyListState = lazyListStateWatchlist,
                onItemClicked = { mediaItem, mainPosterColor ->
                    navigateToDetail(mediaItem, mainPosterColor)
                },
                onRetryClicked = {
                    changeWatchlistType(state.watchlistFilterSelected)
                }
            )

            Spacer(Modifier.padding(vertical = Dimens.padding.small))

//            TitleAndFilter(
//                modifier = Modifier.padding(vertical = Dimens.padding.medium),
//                title = stringResource(id = RCore.string.text_favorites),
//                filterSet = state.watchlistFilterSet,
//                indexSelected = state.watchlistFilterSet.indexOf(state.watchlistFilterSelected),
//                titleClicked = onWatchListClick,
//                filterClicked = { item, _ ->
//                    changeWatchlistType(item)
//                    lazyListStateFavorites.requestScrollToItem(index = 0)
//                }
//            )
//
//            CarouselMediaItem(
//                modifier = Modifier.fillMaxWidth(),
//                mediaItemList = state.watchlistMediaItemList,
//                isLoadingError = state.isWatchlistLoadingError,
//                lazyListState = lazyListStateFavorites,
//                onItemClicked = { mediaItem, mainPosterColor ->
//                    navigateToDetail(mediaItem, mainPosterColor)
//                },
//                onRetryClicked = {
//                    changeWatchlistType(state.watchlistFilterSelected)
//                }
//            )

            Spacer(Modifier.padding(vertical = Dimens.padding.vertical))

            Button(
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                onClick = onSignOutClick
            ) {
                Icon(Icons.AutoMirrored.Filled.Login, contentDescription = null)
                Spacer(modifier = Modifier.padding(horizontal = Dimens.padding.tiny))
                Text(text = stringResource(R.string.text_sign_out))
            }

            Spacer(Modifier.padding(vertical = Dimens.padding.vertical))
        }
    } else {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = onSignInClick,
            ) {
                Icon(Icons.AutoMirrored.Filled.Logout, contentDescription = null)
                Spacer(modifier = Modifier.padding(horizontal = Dimens.padding.tiny))
                Text(text = stringResource(R.string.text_sign_in))
            }
        }
    }
}

@Preview
@Composable
fun AccountScreenPreview() {
    AccountContent(
        AccountState(
            isUserLogged = true,
            username = "Alvaro"
        ),
        onSignInClick = {},
        onSignOutClick = {},
        onWatchListClick = {},
        changeWatchlistType = {},
        navigateToDetail = { _, _ -> }
    )
}