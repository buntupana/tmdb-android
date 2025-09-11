package com.buntupana.tmdb.feature.lists.presentation.lists

import android.content.res.Configuration
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.paging.compose.collectAsLazyPagingItems
import com.buntupana.tmdb.core.ui.composables.CircularProgressIndicatorDelayed
import com.buntupana.tmdb.core.ui.composables.ErrorAndRetry
import com.buntupana.tmdb.core.ui.composables.list.LazyColumnGeneric
import com.buntupana.tmdb.core.ui.composables.top_bar.TopBarTitle
import com.buntupana.tmdb.core.ui.theme.AppTheme
import com.buntupana.tmdb.core.ui.theme.Dimens
import com.buntupana.tmdb.core.ui.util.SetSystemBarsColors
import com.buntupana.tmdb.core.ui.util.getOnBackgroundColor
import com.buntupana.tmdb.core.ui.util.isVisible
import com.buntupana.tmdb.core.ui.util.paddingValues
import com.buntupana.tmdb.feature.lists.presentation.lists.comp.ListItemVertical
import com.buntupana.tmdb.feature.lists.presentation.lists.comp.ListSubBar
import com.buntupana.tmdb.feature.presentation.R
import org.koin.compose.viewmodel.koinViewModel
import com.buntupana.tmdb.core.ui.R as RCore

@Composable
fun ListsScreen(
    viewModel: ListsViewModel = koinViewModel(),
    onBackClick: () -> Unit,
    onSearchClick: () -> Unit,
    onListDetailClick: (listId: Long, listName: String, description: String?, backdropUrl: String?) -> Unit,
    onCreateListDialogClick: () -> Unit
) {

    ListsContent(
        state = viewModel.state,
        onBackClick = onBackClick,
        onSearchClick = onSearchClick,
        onListDetailClick = onListDetailClick,
        onRetryClick = {
            viewModel.onEvent(ListsEvent.GetLists)
        },
        onCreateListClick = onCreateListDialogClick
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

    SetSystemBarsColors(
        statusBarColor = MaterialTheme.colorScheme.primaryContainer,
        navigationBarColor = MaterialTheme.colorScheme.primaryContainer,
        translucentNavigationBar = true
    )

    Scaffold(
        modifier = Modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            Column {
                TopBarTitle(
                    title = stringResource(R.string.text_lists),
                    backgroundColor = MaterialTheme.colorScheme.primaryContainer,
                    onBackClick = onBackClick,
                    onSearchClick = onSearchClick,
                    scrollBehavior = scrollBehavior
                )
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
            }
        }
    ) { paddingValues ->

        val userListDetailsList = state.userListDetailsList?.collectAsLazyPagingItems()

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
                    errorMessage = stringResource(id = RCore.string.message_loading_content_error),
                    onRetryClick = onRetryClick
                )
            }
        }

        Column(
            modifier = Modifier
                .paddingValues(top = { paddingValues.calculateTopPadding() })
        ) {

            userListDetailsList ?: return@Scaffold

            LazyColumnGeneric(
                modifier = Modifier
                    .padding(horizontal = Dimens.padding.small)
                    .fillMaxSize(),
                topPadding = Dimens.padding.small,
                bottomPadding = { Dimens.padding.small + paddingValues.calculateBottomPadding() },
                animateItem = true,
                itemList = userListDetailsList,
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
            ) { _, item ->
                ListItemVertical(
                    modifier = Modifier.animateItem(),
                    userListDetails = item,
                    onItemClick = onListDetailClick
                )
            }
        }
    }
}

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    name = "DefaultPreviewLight",
    showBackground = true
)
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "DefaultPreviewDark",
    showBackground = true
)
@Composable
fun ListsScreenPreview() {
    AppTheme {
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
}