package com.buntupana.tmdb.feature.account.presentation.list_detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.buntupana.tmdb.core.ui.R
import com.buntupana.tmdb.core.ui.composables.CircularProgressIndicatorDelayed
import com.buntupana.tmdb.core.ui.composables.ErrorAndRetry
import com.buntupana.tmdb.core.ui.composables.LazyColumnGeneric
import com.buntupana.tmdb.core.ui.composables.TopBarLogo
import com.buntupana.tmdb.core.ui.composables.item.MediaItemHorizontal
import com.buntupana.tmdb.core.ui.theme.Dimens
import com.buntupana.tmdb.core.ui.theme.PrimaryColor
import com.buntupana.tmdb.core.ui.util.getOnBackgroundColor
import com.buntupana.tmdb.core.ui.util.setStatusBarLightStatusFromBackground
import com.buntupana.tmdb.feature.account.presentation.create_update_list.CreateUpdateListDialog
import com.buntupana.tmdb.feature.account.presentation.delete_list.DeleteListDialog
import com.buntupana.tmdb.feature.account.presentation.delete_list.DeleteListNav
import com.buntupana.tmdb.feature.account.presentation.list_detail.comp.ListDetailHeader
import com.panabuntu.tmdb.core.common.entity.MediaType
import com.panabuntu.tmdb.core.common.model.MediaItem
import kotlinx.coroutines.launch
import timber.log.Timber

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListDetailScreen(
    viewModel: ListDetailViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
    onLogoClick: () -> Unit,
    onSearchClick: () -> Unit,
    onMediaClick: (mediaItemId: Long, mediaType: MediaType, mainPosterColor: Color?) -> Unit,
) {

    val lifecycleOwner = LocalLifecycleOwner.current

    var showEditListDialog by remember { mutableStateOf(false) }
    var showRemoveListConfirmationDialog by remember { mutableStateOf(false) }

    var mediaItemList by remember { mutableStateOf<LazyPagingItems<MediaItem>?>(null) }

    mediaItemList = viewModel.state.mediaItemList?.collectAsLazyPagingItems()

    LaunchedEffect(lifecycleOwner.lifecycle) {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {

            viewModel.onEvent(ListDetailEvent.GetDetails)

            launch {
                viewModel.sideEffect.collect { sideEffect ->
                    Timber.d("WatchlistScreen: sideEffect = $sideEffect")
                    when (sideEffect) {
                        is ListDetailSideEffect.RefreshMediaItemList -> {
                            mediaItemList?.refresh()
                        }
                    }
                }
            }
        }
    }

    ListDetailContent(
        state = viewModel.state,
        onBackClick = onBackClick,
        onLogoClick = onLogoClick,
        onSearchClick = onSearchClick,
        onMediaClick = { mediaItem, mainPosterColor ->
            val mediaType = when (mediaItem) {
                is MediaItem.Movie -> MediaType.MOVIE
                is MediaItem.TvShow -> MediaType.TV_SHOW
            }
            onMediaClick(mediaItem.id, mediaType, mainPosterColor)
        },
        onRetryClick = { viewModel.onEvent(ListDetailEvent.GetDetails) },
        onEditClick = {
            showEditListDialog = true
        },
        onDeleteClick = {
            showRemoveListConfirmationDialog = true
        }
    )

    CreateUpdateListDialog(
        listId = viewModel.state.listId,
        listName = viewModel.state.listName,
        listDescription = viewModel.state.description,
        isPublic = viewModel.state.isPublic,
        showDialog = showEditListDialog,
        onDismiss = { showEditListDialog = false },
        onCreateUpdateListSuccess = {
            showEditListDialog = false
            viewModel.onEvent(ListDetailEvent.GetDetails)
        }
    )

    DeleteListDialog(
        deleteListNav = DeleteListNav(
            listId = viewModel.state.listId,
            listName = viewModel.state.listName
        ),
        showDialog = showRemoveListConfirmationDialog,
        onDismiss = { showRemoveListConfirmationDialog = false },
        onDeleteSuccess = onBackClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListDetailContent(
    state: ListDetailState,
    onBackClick: () -> Unit,
    onLogoClick: () -> Unit,
    onSearchClick: () -> Unit,
    onMediaClick: (mediaItem: MediaItem, mainPosterColor: Color?) -> Unit,
    onRetryClick: () -> Unit,
    onEditClick: () -> Unit = {},
    onDeleteClick: () -> Unit = {}
) {

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    setStatusBarLightStatusFromBackground(
        LocalView.current,
        PrimaryColor
    )

    Scaffold(
        modifier = Modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopBarLogo(
                backgroundColor = PrimaryColor,
                onLogoClick = onLogoClick,
                onBackClick = onBackClick,
                onSearchClick = onSearchClick,
                scrollBehavior = scrollBehavior
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier.padding(top = paddingValues.calculateTopPadding())
        ) {

            ListDetailHeader(
                modifier = Modifier.fillMaxWidth(),
                listName = state.listName,
                description = state.description,
                backdropUrl = state.backdropUrl,
                isPublic = state.isPublic,
                itemsTotalCount = state.itemTotalCount,
                onEditClick = onEditClick,
                onDeleteClick = onDeleteClick
            )

            if (state.isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicatorDelayed()
                }
            }

            if (state.isError) {
                Box(modifier = Modifier.fillMaxSize()) {
                    ErrorAndRetry(
                        modifier = Modifier.align(Alignment.Center),
                        textColor = MaterialTheme.colorScheme.background.getOnBackgroundColor(),
                        errorMessage = stringResource(id = R.string.message_loading_content_error),
                        onRetryClick = onRetryClick
                    )
                }
            }

            LazyColumnGeneric(
                modifier = Modifier.fillMaxSize(),
                topPadding = Dimens.padding.small,
                bottomPadding = Dimens.padding.small + paddingValues.calculateBottomPadding(),
                itemList = state.mediaItemList?.collectAsLazyPagingItems(),
                animateItem = true,
                noResultContent = {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = stringResource(R.string.message_no_results_found)
                        )
                    }
                }
            ) { mediaItem ->

                MediaItemHorizontal(
                    modifier = Modifier
                        .fillMaxWidth(),
                    mediaId = mediaItem.id,
                    title = mediaItem.name,
                    posterUrl = mediaItem.posterUrl,
                    overview = mediaItem.overview,
                    releaseDate = mediaItem.releaseDate,
                    onMediaClick = { _, mainPosterColor ->
                        onMediaClick(mediaItem, mainPosterColor)
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ListDetailScreenPreview() {
    ListDetailContent(
        state = ListDetailState(
            listId = 0,
            listName = "The 97th Academy Award nominees for Best Motion Picture of the Year Oscars"
        ),
        onBackClick = {},
        onLogoClick = {},
        onSearchClick = {},
        onMediaClick = { _, _ -> },
        onRetryClick = {}
    )
}