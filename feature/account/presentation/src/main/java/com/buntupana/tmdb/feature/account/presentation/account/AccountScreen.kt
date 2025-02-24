package com.buntupana.tmdb.feature.account.presentation.account

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import com.buntupana.tmdb.core.ui.composables.TitleAndFilter
import com.buntupana.tmdb.core.ui.composables.item.CarouselMediaItem
import com.buntupana.tmdb.core.ui.filter_type.MediaFilter
import com.buntupana.tmdb.core.ui.theme.Dimens
import com.buntupana.tmdb.feature.account.presentation.R
import com.buntupana.tmdb.feature.account.presentation.account.comp.AccountInfoTop
import com.buntupana.tmdb.feature.account.presentation.account.comp.ListItemsSection
import com.buntupana.tmdb.feature.account.presentation.sign_out.SignOutDialog
import com.panabuntu.tmdb.core.common.entity.MediaType
import com.panabuntu.tmdb.core.common.model.MediaItem
import kotlinx.coroutines.launch
import com.buntupana.tmdb.core.ui.R as RCore

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountScreen(
    viewModel: AccountViewModel = hiltViewModel(),
    onSignInClick: () -> Unit,
    onWatchListClick: (mediaType: MediaType) -> Unit,
    onFavoritesClick: (mediaType: MediaType) -> Unit,
    onListsClick: () -> Unit,
    onMediaItemClicked: (mediaItemId: Long, mediaItemType: MediaType, posterDominantColor: Color) -> Unit,
    onListDetailClick: (listId: Long, listName: String, description: String?, backdropUrl: String?) -> Unit,
) {

    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(lifecycleOwner.lifecycle) {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            launch {
                viewModel.onEvent(AccountEvent.GetWatchlist(viewModel.state.watchlistFilterSelected))
                viewModel.onEvent(AccountEvent.GetFavorites(viewModel.state.favoritesFilterSelected))
                viewModel.onEvent(AccountEvent.GetLists)
            }
        }
    }

    var showBottomSheet by remember { mutableStateOf(false) }

    AccountContent(
        state = viewModel.state,
        onSignInClick = onSignInClick,
        onSignOutClick = {
            showBottomSheet = true
        },
        onWatchListClick = {
            val mediaType = when (viewModel.state.watchlistFilterSelected) {
                MediaFilter.MOVIES -> MediaType.MOVIE
                MediaFilter.TV_SHOWS -> MediaType.TV_SHOW
            }
            onWatchListClick(mediaType)
        },
        onFavoritesClick = {
                val mediaType = when (viewModel.state.favoritesFilterSelected) {
                    MediaFilter.MOVIES -> MediaType.MOVIE
                    MediaFilter.TV_SHOWS -> MediaType.TV_SHOW
                }
            onFavoritesClick(mediaType)
        },
        onListsClick = onListsClick,
        changeWatchlistType = { mediaFilter ->
            viewModel.onEvent(AccountEvent.GetWatchlist(mediaFilter))
        },
        changeFavoritesType = { mediaFilter ->
            viewModel.onEvent(AccountEvent.GetFavorites(mediaFilter))
        },
        navigateToMediaDetail = { mediaItem, posterDominantColor ->
            when (mediaItem) {
                is MediaItem.Movie -> {
                    onMediaItemClicked(
                        mediaItem.id,
                        MediaType.MOVIE,
                        posterDominantColor
                    )
                }

                is MediaItem.TvShow -> {
                    onMediaItemClicked(
                        mediaItem.id,
                        MediaType.TV_SHOW,
                        posterDominantColor
                    )
                }
            }
        },
        navigateToListDetail = { listItemId, listName, description, backdropUrl ->
            onListDetailClick(listItemId, listName, description, backdropUrl)
        },
        listLoadItems = {
            viewModel.onEvent(AccountEvent.GetLists)
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
    onFavoritesClick: () -> Unit,
    onListsClick: () -> Unit,
    changeWatchlistType: (mediaFilter: MediaFilter) -> Unit,
    changeFavoritesType: (mediaFilter: MediaFilter) -> Unit,
    listLoadItems: () -> Unit,
    navigateToMediaDetail: (mediaItem: MediaItem, posterDominantColor: Color) -> Unit,
    navigateToListDetail: (listId: Long, listName: String, description: String?, backdropUrl: String?) -> Unit,
) {

    if (state.isUserLogged) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(state = rememberScrollState()),
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
                filterSet = MediaFilter.entries.toSet(),
                indexSelected = MediaFilter.entries.toSet().indexOf(state.watchlistFilterSelected),
                titleClicked = onWatchListClick,
                filterClicked = { item, _ ->
                    changeWatchlistType(item)
                    lazyListStateWatchlist.requestScrollToItem(index = 0)
                }
            )

            CarouselMediaItem(
                modifier = Modifier.fillMaxWidth(),
                animationEnabled = true,
                mediaItemList = state.watchlistMediaItemList,
                isLoadingError = state.isWatchlistLoadingError,
                lazyListState = lazyListStateWatchlist,
                onItemClicked = { mediaItem, mainPosterColor ->
                    navigateToMediaDetail(mediaItem, mainPosterColor)
                },
                onRetryClicked = {
                    changeWatchlistType(state.watchlistFilterSelected)
                },
                onShowMoreClick =  onWatchListClick
            )

            ListItemsSection(
                modifier = Modifier.fillMaxWidth(),
                listItemList = state.listsMediaItemList,
                isLoadingError = state.isListsLoadingError,
                titleClicked = onListsClick,
                onItemClicked = navigateToListDetail,
                onRetryClicked = listLoadItems,
                onShowMoreClick = onListsClick
            )

            TitleAndFilter(
                modifier = Modifier.padding(vertical = Dimens.padding.medium),
                title = stringResource(id = RCore.string.text_favorites),
                filterSet = MediaFilter.entries.toSet(),
                indexSelected = MediaFilter.entries.toSet().indexOf(state.favoritesFilterSelected),
                titleClicked = onFavoritesClick,
                filterClicked = { item, _ ->
                    changeFavoritesType(item)
                    lazyListStateFavorites.requestScrollToItem(index = 0)
                }
            )

            CarouselMediaItem(
                modifier = Modifier.fillMaxWidth(),
                animationEnabled = true,
                mediaItemList = state.favoritesMediaItemList,
                isLoadingError = state.isFavoritesLoadingError,
                lazyListState = lazyListStateFavorites,
                onItemClicked = { mediaItem, mainPosterColor ->
                    navigateToMediaDetail(mediaItem, mainPosterColor)
                },
                onRetryClicked = {
                    changeFavoritesType(state.favoritesFilterSelected)
                },
                onShowMoreClick = onFavoritesClick
            )

            Spacer(Modifier.padding(vertical = Dimens.padding.vertical))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                    onClick = onSignOutClick
                ) {
                    Icon(Icons.AutoMirrored.Filled.Login, contentDescription = null)
                    Spacer(modifier = Modifier.padding(horizontal = Dimens.padding.tiny))
                    Text(text = stringResource(R.string.text_sign_out))
                }
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

@Preview(showBackground = true)
@Composable
fun AccountScreenPreview() {
    AccountContent(
        AccountState(
            isUserLogged = true,
            username = "Alvaro",
            listsMediaItemList = null
        ),
        onSignInClick = {},
        onSignOutClick = {},
        onWatchListClick = {},
        onFavoritesClick = {},
        onListsClick = {},
        changeWatchlistType = {},
        changeFavoritesType = {},
        navigateToMediaDetail = { _, _ -> },
        navigateToListDetail = { _, _, _, _ -> },
        listLoadItems = {}
    )
}