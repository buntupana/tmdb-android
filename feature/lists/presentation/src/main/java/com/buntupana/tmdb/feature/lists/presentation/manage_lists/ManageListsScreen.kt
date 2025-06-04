package com.buntupana.tmdb.feature.lists.presentation.manage_lists

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideIn
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import com.buntupana.tmdb.core.ui.composables.CircularProgressIndicatorDelayed
import com.buntupana.tmdb.core.ui.composables.ErrorAndRetry
import com.buntupana.tmdb.core.ui.composables.top_bar.TopBarLogo
import com.buntupana.tmdb.core.ui.theme.DetailBackgroundColor
import com.buntupana.tmdb.core.ui.util.setStatusBarLightStatusFromBackground
import com.buntupana.tmdb.feature.lists.domain.model.UserListDetails
import com.buntupana.tmdb.feature.lists.presentation.manage_lists.comp.HeaderManageLists
import com.buntupana.tmdb.feature.lists.presentation.manage_lists.comp.ManageListsBottomBar
import com.buntupana.tmdb.feature.lists.presentation.manage_lists.comp.ManageListsLists
import com.panabuntu.tmdb.core.common.entity.MediaType
import com.panabuntu.tmdb.core.common.util.Const
import kotlinx.coroutines.launch
import timber.log.Timber
import com.buntupana.tmdb.core.ui.R as RCore

@Composable
fun ManageListsDialog(
    viewModel: ManageListsViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
    onLogoClick: () -> Unit,
    onCreateListClick: () -> Unit
) {
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(lifecycleOwner.lifecycle) {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            launch {
                viewModel.sideEffect.collect { sideEffect ->
                    Timber.d("SignOutDialog: sideEffect = $sideEffect")
                    when (sideEffect) {

                        is ManageListsSideEffect.SetListsSuccess -> {
                            onBackClick()
                        }
                    }
                }
            }
        }
    }

    ManageListsContent(
        state = viewModel.state,
        onBackClick = onBackClick,
        onLogoClick = onLogoClick,
        onRetryClick = {
            viewModel.onEvent(ManageListsEvent.GetLists)
        },
        onAddToListClick = { listItem ->
            viewModel.onEvent(ManageListsEvent.AddToList(listItem))
        },
        onDeleteFromListClick = { listItem ->
            viewModel.onEvent(ManageListsEvent.DeleteFromList(listItem))
        },
        onConfirmClick = {
            viewModel.onEvent(ManageListsEvent.Confirm)
        },
        onCreateListClick = onCreateListClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManageListsContent(
    state: ManageListsState,
    onBackClick: () -> Unit,
    onRetryClick: () -> Unit,
    onLogoClick: () -> Unit,
    onAddToListClick: (userListDetails: UserListDetails) -> Unit = {},
    onDeleteFromListClick: (userListDetails: UserListDetails) -> Unit = {},
    onConfirmClick: () -> Unit = {},
    onCreateListClick: () -> Unit = {}
) {

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    setStatusBarLightStatusFromBackground(
        LocalView.current,
        state.backgroundColor
    )

    Scaffold(
        modifier = Modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            Column {

                TopBarLogo(
                    backgroundColor = state.backgroundColor,
                    onBackClick = { onBackClick() },
                    onLogoClick = { onLogoClick() },
                    scrollBehavior = scrollBehavior
                )

                HeaderManageLists(
                    backgroundColor = state.backgroundColor,
                    posterUrl = state.posterUrl,
                    mediaName = state.mediaName,
                    listsCount = state.userListDetails?.size,
                    releaseYear = state.releaseYear,
                )
            }
        }
    ) { paddingValues ->

        Timber.d("ManageListsContent: state = $state")

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = paddingValues.calculateTopPadding())
                .animateContentSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Box(
                modifier = Modifier.weight(1f)
            ) {
                androidx.compose.animation.AnimatedVisibility(
                    visible = state.userListDetails != null,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    ManageListsLists(
                        modifier = Modifier.fillMaxSize(),
                        state = state,
                        onAddToListClick = onAddToListClick,
                        onDeleteFromListClick = onDeleteFromListClick
                    )
                }

                if (state.isError) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(MaterialTheme.colorScheme.background.copy(alpha = 0.8f))
                            .clickable(enabled = false) {},
                        contentAlignment = Alignment.Center
                    ) {
                        ErrorAndRetry(
                            modifier = Modifier,
                            errorMessage = stringResource(RCore.string.message_loading_content_error),
                            onRetryClick = onRetryClick
                        )
                    }
                }

                androidx.compose.animation.AnimatedVisibility(
                    visible = state.isContentLoading || state.isConfirmLoading,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(MaterialTheme.colorScheme.background.copy(alpha = 0.8f))
                            .clickable(enabled = false) {},
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicatorDelayed()
                    }
                }
            }

            androidx.compose.animation.AnimatedVisibility(
                visible = state.isContentLoading.not() && state.isConfirmLoading.not() && state.isError.not(),
                enter = slideIn(
                    tween(
                        Const.ANIM_DURATION,
                        easing = LinearOutSlowInEasing
                    )
                ) { fullSize ->
                    IntOffset(x = 0, y = fullSize.height)
                },
                exit = fadeOut()
            ) {
                ManageListsBottomBar(
                    modifier = Modifier.fillMaxWidth(),
                    bottomPadding = paddingValues.calculateBottomPadding(),
                    backgroundColor = state.backgroundColor,
                    onCreateListClick = onCreateListClick,
                    onConfirmClick = onConfirmClick
                )

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ManageListsPreview() {
    ManageListsContent(
        ManageListsState(
            isContentLoading = false,
            mediaType = MediaType.MOVIE,
            searchKey = "",
            mediaName = "Blue Velvet",
            backgroundColor = DetailBackgroundColor,
            posterUrl = "asdf",
            releaseYear = "1998",
            userListDetails = listOf(
                UserListDetails(
                    id = 1,
                    name = "List 1",
                    description = "Description 1",
                    itemCount = 1,
                    isPublic = true,
                    backdropUrl = null,
                    revenue = null,
                    runtime = null,
                    posterUrl = null,
                    averageRating = null,
                    updatedAt = null,
                    shareLink = "test"
                )
            ),
            listAllLists = listOf(
                UserListDetails(
                    id = 2,
                    name = "List 2",
                    description = "Description 1",
                    itemCount = 1,
                    isPublic = true,
                    backdropUrl = null,
                    revenue = null,
                    runtime = null,
                    posterUrl = null,
                    averageRating = null,
                    updatedAt = null,
                    shareLink = "test"
                )
            )
        ),
        onRetryClick = {},
        onBackClick = {},
        onLogoClick = {}
    )
}