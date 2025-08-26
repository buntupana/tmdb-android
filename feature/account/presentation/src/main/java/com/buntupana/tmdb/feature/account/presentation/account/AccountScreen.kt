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
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.buntupana.tmdb.core.ui.composables.TitleAndFilter
import com.buntupana.tmdb.core.ui.composables.item.CarouselMediaItem
import com.buntupana.tmdb.core.ui.filter_type.MediaFilter
import com.buntupana.tmdb.core.ui.theme.Dimens
import com.buntupana.tmdb.core.ui.theme.PrimaryColor
import com.buntupana.tmdb.core.ui.util.SetSystemBarsColors
import com.buntupana.tmdb.feature.account.presentation.R
import com.buntupana.tmdb.feature.account.presentation.account.comp.AccountTopBar
import com.buntupana.tmdb.feature.account.presentation.account.comp.ListItemsSection
import com.buntupana.tmdb.feature.account.presentation.account.comp.SignUp
import com.buntupana.tmdb.feature.account.presentation.sign_out.SignOutDialog
import com.panabuntu.tmdb.core.common.entity.MediaType
import com.panabuntu.tmdb.core.common.model.MediaItem
import com.buntupana.tmdb.core.ui.R as RCore

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountScreen(
    viewModel: AccountViewModel = hiltViewModel(),
    onSignInClick: () -> Unit,
    onWatchListClick: (mediaType: MediaType) -> Unit,
    onFavoritesClick: (mediaType: MediaType) -> Unit,
    onListsClick: () -> Unit,
    onMediaItemClicked: (mediaItemType: MediaType, mediaItemId: Long, posterDominantColor: Color) -> Unit,
    onListDetailClick: (listId: Long, listName: String, description: String?, backdropUrl: String?) -> Unit,
) {

    var showBottomSheet by remember { mutableStateOf(false) }

    AccountContent(
        state = viewModel.state,
        onSignUpClick = onSignInClick,
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
                        MediaType.MOVIE,
                        mediaItem.id,
                        posterDominantColor
                    )
                }

                is MediaItem.TvShow -> {
                    onMediaItemClicked(
                        MediaType.TV_SHOW,
                        mediaItem.id,
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
    onSignUpClick: () -> Unit,
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

    SetSystemBarsColors(
        statusBarColor = PrimaryColor,
        navigationBarColor = PrimaryColor
    )

    Scaffold(
        topBar = {
            AccountTopBar(
                modifier = Modifier
                    .fillMaxWidth(),
                avatarUrl = state.avatarUrl,
                username = state.username
            )
        }
    ) { paddingValues ->

        if (state.isUserLogged) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(state = rememberScrollState())
                    .padding(paddingValues),
            ) {

                val lazyListStateWatchlist: LazyListState = rememberLazyListState()
                val lazyListStateFavorites: LazyListState = rememberLazyListState()

                TitleAndFilter(
                    modifier = Modifier.padding(vertical = Dimens.padding.medium),
                    title = stringResource(id = RCore.string.text_watchlist),
                    filterSet = MediaFilter.entries.toSet(),
                    indexSelected = MediaFilter.entries.toSet()
                        .indexOf(state.watchlistFilterSelected),
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
                    onShowMoreClick = onWatchListClick
                )

                ListItemsSection(
                    modifier = Modifier.fillMaxWidth(),
                    userListDetailsList = state.userListDetailsList,
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
                    indexSelected = MediaFilter.entries.toSet()
                        .indexOf(state.favoritesFilterSelected),
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
                        Icon(Icons.AutoMirrored.Filled.Logout, contentDescription = null)
                        Spacer(modifier = Modifier.padding(horizontal = Dimens.padding.tiny))
                        Text(text = stringResource(R.string.text_sign_out))
                    }
                }

                Spacer(Modifier.padding(vertical = Dimens.padding.vertical))
            }
        } else {
            SignUp(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(paddingValues),
                onSignUpClick = onSignUpClick
            )
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
            userListDetailsList = null
        ),
        onSignUpClick = {},
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