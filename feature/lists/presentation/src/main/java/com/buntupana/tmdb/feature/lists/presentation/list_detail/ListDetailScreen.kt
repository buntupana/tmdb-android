package com.buntupana.tmdb.feature.lists.presentation.list_detail

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.AnimationConstants
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
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
import com.buntupana.tmdb.core.ui.composables.TopBarLogo
import com.buntupana.tmdb.core.ui.composables.item.ActionsAlign
import com.buntupana.tmdb.core.ui.composables.item.MediaItemHorizontal
import com.buntupana.tmdb.core.ui.composables.item.MediaItemHorizontalPlaceHolder
import com.buntupana.tmdb.core.ui.composables.item.SwipeableItem
import com.buntupana.tmdb.core.ui.composables.list.LazyColumnGeneric
import com.buntupana.tmdb.core.ui.theme.Dimens
import com.buntupana.tmdb.core.ui.theme.PrimaryColor
import com.buntupana.tmdb.core.ui.util.getOnBackgroundColor
import com.buntupana.tmdb.core.ui.util.setStatusBarLightStatusFromBackground
import com.buntupana.tmdb.feature.lists.presentation.delete_item_list.DeleteItemListDialog
import com.buntupana.tmdb.feature.lists.presentation.delete_item_list.DeleteItemListNav
import com.buntupana.tmdb.feature.lists.presentation.list_detail.comp.ListDetailHeader
import com.panabuntu.tmdb.core.common.entity.MediaType
import com.panabuntu.tmdb.core.common.model.MediaItem
import kotlinx.coroutines.delay
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
    onUpdateListClick: (listId: Long, listName: String, listDescription: String, isPublic: Boolean) -> Unit,
    onDeleteListClick: (listId: Long, listName: String) -> Unit
) {

    val lifecycleOwner = LocalLifecycleOwner.current

    var showRemoveItemListConfirmDialog by remember { mutableStateOf(false) }

    var deleteItemListNav by remember { mutableStateOf<DeleteItemListNav?>(null) }

    var mediaItemList by remember { mutableStateOf<LazyPagingItems<ItemListViewEntity>?>(null) }

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

                        is ListDetailSideEffect.UpdateItemRevealed -> {
                            mediaItemList?.itemSnapshotList?.find {
                                it?.id == sideEffect.itemId
                            }?.let { itemListViewEntity ->
                                itemListViewEntity.isDeleteRevealed.value = sideEffect.isRevealed
                            }
                        }

                        ListDetailSideEffect.NavigateBack -> {
                            // add delay to wait for bottomsheet to disappear
                            delay(AnimationConstants.DefaultDurationMillis.toLong())
                            onBackClick()
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
            onUpdateListClick(
                viewModel.state.listId,
                viewModel.state.listName,
                viewModel.state.description.orEmpty(),
                viewModel.state.isPublic
            )
        },
        onDeleteClick = {
            onDeleteListClick(viewModel.state.listId, viewModel.state.listName)
        },
        onItemDeleteClick = { itemId, mediaItem ->
            deleteItemListNav = DeleteItemListNav(
                itemId = itemId,
                listId = viewModel.state.listId,
                mediaId = mediaItem.id,
                mediaName = mediaItem.name,
                mediaType = mediaItem.mediaType
            )
            showRemoveItemListConfirmDialog = true
        }
    )

    DeleteItemListDialog(
        deleteItemListNav = deleteItemListNav,
        showDialog = showRemoveItemListConfirmDialog,
        onDismiss = {
            showRemoveItemListConfirmDialog = false
        },
        onCancelClick = { itemId ->
            mediaItemList?.itemSnapshotList?.find {
                it?.id == itemId
            }?.let { itemListViewEntity ->
                itemListViewEntity.isDeleteRevealed.value = false
            }
        },
        onDeleteSuccess = {
            showRemoveItemListConfirmDialog = false
            viewModel.onEvent(
                ListDetailEvent.SuccessDeleteItemList(
                    itemId = deleteItemListNav?.itemId.orEmpty(),
                    mediaType = deleteItemListNav?.mediaType ?: MediaType.MOVIE
                )
            )
        }
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
    onDeleteClick: () -> Unit = {},
    onItemDeleteClick: (itemId: String, mediaItem: MediaItem) -> Unit = { _, _ -> }
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
                modifier = Modifier
                    .fillMaxSize(),
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
                },
                placeHolder = {
                    MediaItemHorizontalPlaceHolder(
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            ) { _, itemListViewEntity ->

                val isRevealed = itemListViewEntity.isDeleteRevealed

                // key is need it to recomposite when isRevealed
                key(isRevealed) {
                    SwipeableItem(
                        modifier = Modifier
                            .fillMaxWidth()
                            .animateItem(),
                        actionsAlign = ActionsAlign.END,
                        onExpanded = {
                            onItemDeleteClick(itemListViewEntity.id, itemListViewEntity.mediaItem)
                            itemListViewEntity.isDeleteRevealed.value = true
                        },
                        isRevealed = isRevealed.value,
                        actions = { progressProvider ->

                            val backgroundColor = animateColorAsState(
                                targetValue = lerp(
                                    start = MaterialTheme.colorScheme.background,
                                    stop = MaterialTheme.colorScheme.error,
                                    fraction = progressProvider() * 2
                                ),
                                animationSpec = tween(0)
                            )
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(
                                        horizontal = Dimens.padding.horizontal,
                                        vertical = Dimens.padding.verticalItem
                                    )
                                    .clip(RoundedCornerShape(Dimens.posterRound))
                                    .background(backgroundColor.value),
                            ) {
                                Icon(
                                    modifier = Modifier
                                        .align(Alignment.CenterEnd)
                                        .padding(horizontal = Dimens.padding.huge),
                                    imageVector = Icons.Rounded.Delete,
                                    contentDescription = null,
                                    tint = Color.White,
                                )
                            }
                        },
                        content = {
                            MediaItemHorizontal(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .animateItem(),
                                mediaId = itemListViewEntity.mediaItem.id,
                                title = itemListViewEntity.mediaItem.name,
                                posterUrl = itemListViewEntity.mediaItem.posterUrl,
                                overview = itemListViewEntity.mediaItem.overview,
                                releaseDate = itemListViewEntity.mediaItem.releaseDate,
                                onMediaClick = { _, mainPosterColor ->
                                    onMediaClick(itemListViewEntity.mediaItem, mainPosterColor)
                                }
                            )
                        }
                    )
                }
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