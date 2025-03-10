package com.buntupana.tmdb.feature.lists.presentation.manage_lists

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalBottomSheetProperties
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import com.buntupana.tmdb.core.ui.composables.CircularProgressIndicatorDelayed
import com.buntupana.tmdb.core.ui.theme.Dimens
import com.buntupana.tmdb.feature.lists.domain.model.MediaList
import com.buntupana.tmdb.feature.lists.presentation.manage_lists.comp.ManageListsHeader
import com.buntupana.tmdb.feature.lists.presentation.manage_lists.comp.ManageListsLists
import com.panabuntu.tmdb.core.common.entity.MediaType
import kotlinx.coroutines.launch
import timber.log.Timber

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManageListsDialog(
    viewModel: ManageListsViewModel = hiltViewModel(),
    sheetState: SheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
        confirmValueChange = { newState ->
            newState != SheetValue.Hidden
        }
    ),
    onDismiss: () -> Unit,
) {

    val scope = rememberCoroutineScope()
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(lifecycleOwner.lifecycle) {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            launch {
                viewModel.sideEffect.collect { sideEffect ->
                    Timber.d("SignOutDialog: sideEffect = $sideEffect")
                    when (sideEffect) {

                        is ManageListsSideEffect.SetListsSuccess -> {
                            sheetState.hide()
                            onDismiss()
                        }

                        ManageListsSideEffect.Dismiss -> {
                            sheetState.hide()
                            onDismiss()
                        }
                    }
                }
            }
        }
    }

    ManageListsContent(
        state = viewModel.state,
        sheetState = sheetState,
        onDismiss = {
            scope.launch {
                sheetState.hide()
                onDismiss()
            }
        },
        onAddToListClick = { listItem ->
            viewModel.onEvent(ManageListsEvent.AddToList(listItem))
        },
        onDeleteFromListClick = { listItem ->
            viewModel.onEvent(ManageListsEvent.DeleteFromList(listItem))
        },
        onConfirmClick = {
            viewModel.onEvent(ManageListsEvent.Confirm)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManageListsContent(
    state: ManageListsState,
    sheetState: SheetState,
    onDismiss: () -> Unit,
    onAddToListClick: (mediaList: MediaList) -> Unit = {},
    onDeleteFromListClick: (mediaList: MediaList) -> Unit = {},
    onConfirmClick: () -> Unit = {}
) {

    ModalBottomSheet(
        modifier = Modifier.statusBarsPadding(),
        onDismissRequest = {
            if (state.isLoading.not()) {
                onDismiss()
            }
        },
        sheetState = sheetState,
        containerColor = MaterialTheme.colorScheme.background,
        dragHandle = {},
        properties = ModalBottomSheetProperties(
            shouldDismissOnBackPress = false
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    horizontal = Dimens.padding.horizontal,
                    vertical = Dimens.padding.big
                )
                .animateContentSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            ManageListsHeader(
                modifier = Modifier.fillMaxWidth(),
                isLoading = state.isLoading,
                areListsNull = state.listMediaLists == null,
                onCancelClick = onDismiss,
                onConfirmClick = onConfirmClick
            )

            Box {
                androidx.compose.animation.AnimatedVisibility(
                    visible = state.listMediaLists != null,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    ManageListsLists(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = Dimens.padding.small),
                        state = state,
                        onAddToListClick = onAddToListClick,
                        onDeleteFromListClick = onDeleteFromListClick
                    )
                }

                androidx.compose.animation.AnimatedVisibility(
                    visible = state.isLoading,
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
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
private fun ManageListsPreview() {
    ManageListsContent(
        ManageListsState(
            isLoading = false,
            mediaType = MediaType.MOVIE,
            searchKey = "",
            listMediaLists = listOf(
                MediaList(
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
                    updatedAt = null
                )
            ),
            listAllLists = listOf(
                MediaList(
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
                    updatedAt = null
                )
            )
        ),
        sheetState = SheetState(
            skipPartiallyExpanded = true,
            LocalDensity.current,
            initialValue = SheetValue.Expanded
        ),
        onDismiss = {},
    )
}