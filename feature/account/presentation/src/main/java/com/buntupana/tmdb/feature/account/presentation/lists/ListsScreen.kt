package com.buntupana.tmdb.feature.account.presentation.lists

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.buntupana.tmdb.core.ui.composables.ErrorAndRetry
import com.buntupana.tmdb.core.ui.composables.LazyColumnGeneric
import com.buntupana.tmdb.core.ui.composables.TopBarTitle
import com.buntupana.tmdb.core.ui.theme.Dimens
import com.buntupana.tmdb.core.ui.theme.PrimaryColor
import com.buntupana.tmdb.core.ui.util.getOnBackgroundColor
import com.buntupana.tmdb.core.ui.util.isVisible
import com.buntupana.tmdb.core.ui.util.setStatusBarLightStatusFromBackground
import com.buntupana.tmdb.feature.account.presentation.lists.comp.ListItemVertical
import com.buntupana.tmdb.feature.account.presentation.lists.comp.ListSubBar
import timber.log.Timber

@Composable
fun ListsScreen(
    viewModel: ListsViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
    onSearchClick: () -> Unit,
    onListDetailClick: (listItemId: Long, mainPosterColor: Color?) -> Unit,
) {
    ListsContent(
        state = viewModel.state,
        onBackClick = onBackClick,
        onSearchClick = onSearchClick,
        onListDetailClick = onListDetailClick,
        onRetryClick = {
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
    onListDetailClick: (listItemId: Long, mainPosterColor: Color?) -> Unit,
    onRetryClick: () -> Unit
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
                title = "Lists",
                backgroundColor = PrimaryColor,
                onBackClick = onBackClick,
                onSearchClick = onSearchClick,
                scrollBehavior = scrollBehavior
            )
        }
    ) { paddingValues ->


        val listItems = state.listItems?.collectAsLazyPagingItems()

        if (state.isLoading || listItems?.loadState?.refresh == LoadState.Loading) {
            Timber.d("ListsContent: loading")
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
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
                onCreateListClick = {}
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
                            text = "hola"
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
            isError = true,
        ),
        onBackClick = {},
        onSearchClick = {},
        onListDetailClick = { _, _ -> },
        onRetryClick = {}
    )
}