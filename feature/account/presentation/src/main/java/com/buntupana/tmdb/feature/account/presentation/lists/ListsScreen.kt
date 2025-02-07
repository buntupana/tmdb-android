package com.buntupana.tmdb.feature.account.presentation.lists

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
import com.buntupana.tmdb.core.ui.composables.CircularProgressIndicatorDelayed
import com.buntupana.tmdb.core.ui.composables.ErrorAndRetry
import com.buntupana.tmdb.core.ui.composables.LazyColumnGeneric
import com.buntupana.tmdb.core.ui.composables.TopBarTitle
import com.buntupana.tmdb.core.ui.theme.Dimens
import com.buntupana.tmdb.core.ui.theme.PrimaryColor
import com.buntupana.tmdb.core.ui.util.getOnBackgroundColor
import com.buntupana.tmdb.core.ui.util.isVisible
import com.buntupana.tmdb.core.ui.util.setStatusBarLightStatusFromBackground
import com.buntupana.tmdb.feature.account.domain.model.ListItem
import com.buntupana.tmdb.feature.account.presentation.create_update_list.CreateUpdateListDialog
import com.buntupana.tmdb.feature.account.presentation.lists.comp.ListItemVertical
import com.buntupana.tmdb.feature.account.presentation.lists.comp.ListSubBar
import kotlinx.coroutines.launch
import timber.log.Timber
import com.buntupana.tmdb.core.ui.R as RCore

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListsScreen(
    viewModel: ListsViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
    onSearchClick: () -> Unit,
    onListDetailClick: (listId: Long, listName: String, description: String?, backdropUrl: String?) -> Unit,
) {
    val lifecycleOwner = LocalLifecycleOwner.current

    var showCreateListBottomSheet by remember { mutableStateOf(false) }

    var listItems by remember {  mutableStateOf<LazyPagingItems<ListItem>?>(null)}

    listItems = viewModel.state.listItems?.collectAsLazyPagingItems()

    LaunchedEffect(lifecycleOwner.lifecycle) {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {

            viewModel.onEvent(ListsEvent.GetLists)

            launch {
                viewModel.sideEffect.collect { sideEffect ->
                    Timber.d("WatchlistScreen: sideEffect = $sideEffect")
                    when (sideEffect) {
                        is ListsSideEffect.RefreshListItemList -> {
                            listItems?.refresh()
                        }
                    }
                }
            }
        }
    }

    ListsContent(
        state = viewModel.state,
        onBackClick = onBackClick,
        onSearchClick = onSearchClick,
        onListDetailClick = onListDetailClick,
        onRetryClick = {
            viewModel.onEvent(ListsEvent.GetLists)
        },
        onCreateListClick = {
            showCreateListBottomSheet = true
        }
    )

    CreateUpdateListDialog(
        showDialog = showCreateListBottomSheet,
        onDismiss = { showCreateListBottomSheet = false },
        onCreateUpdateListSuccess = {
            viewModel.onEvent(ListsEvent.GetLists)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListsContent(
    state: ListsState,
    onBackClick: () -> Unit,
    onSearchClick: () -> Unit,
    onListDetailClick: (listId: Long, listName: String, description: String?, backdropUrl: String?) -> Unit,
    onRetryClick: () -> Unit,
    onCreateListClick: () -> Unit
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
            TopBarTitle(
                title = stringResource(com.buntupana.tmdb.feature.account.presentation.R.string.text_lists),
                backgroundColor = PrimaryColor,
                onBackClick = onBackClick,
                onSearchClick = onSearchClick,
                scrollBehavior = scrollBehavior
            )
        }
    ) { paddingValues ->


        val listItems = state.listItems?.collectAsLazyPagingItems()

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
                    errorMessage = stringResource(id = com.buntupana.tmdb.core.ui.R.string.message_loading_content_error),
                    onRetryClick = onRetryClick
                )
            }
        }

        Column(
            modifier = Modifier
                .padding(
                    top = paddingValues.calculateTopPadding()
                )
        ) {

            ListSubBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .isVisible(
                        isVisible = state.isError.not() && state.isLoading.not(),
                        animateSize = true
                    ),
                listItemTotalCount = state.listItemTotalCount,
                onCreateListClick = onCreateListClick
            )

            listItems ?: return@Scaffold

            LazyColumnGeneric(
                modifier = Modifier
                    .padding(horizontal = Dimens.padding.small)
                    .fillMaxSize(),
                topPadding = Dimens.padding.small,
                bottomPadding = Dimens.padding.small + paddingValues.calculateBottomPadding(),
                animateItem = true,
                itemList = listItems,
                noResultContent = {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = stringResource(RCore.string.message_no_results_found)
                        )
                    }
                }
            ) { item ->
                ListItemVertical(
                    modifier = Modifier.animateItem(),
                    listItem = item,
                    onItemClick = onListDetailClick
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ListsScreenPreview() {
    ListsContent(
        ListsState(
            isLoading = false,
            isError = false,
        ),
        onBackClick = {},
        onSearchClick = {},
        onListDetailClick = { _, _, _, _ -> },
        onRetryClick = {},
        onCreateListClick = {}
    )
}