package com.buntupana.tmdb.feature.lists.presentation.delete_item_list

import android.content.res.Configuration
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import com.buntupana.tmdb.core.ui.composables.dialog.ConfirmationDialog
import com.buntupana.tmdb.core.ui.theme.AppTheme
import com.buntupana.tmdb.core.ui.util.annotatedStringResource
import com.buntupana.tmdb.feature.presentation.R
import com.panabuntu.tmdb.core.common.entity.MediaType
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel
import timber.log.Timber

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeleteItemListDialog(
    viewModel: DeleteItemListViewModel = koinViewModel(),
    sheetState: SheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
    onDismiss: (mediaId: Long, mediaType: MediaType) -> Unit,
    onDeleteSuccess: () -> Unit
) {
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(lifecycleOwner.lifecycle) {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            launch {
                viewModel.sideEffect.collect { sideEffect ->
                    Timber.d("SignOutDialog: sideEffect = $sideEffect")
                    when (sideEffect) {
                        DeleteItemListSideEffect.DeleteSuccess -> {
                            onDeleteSuccess()
                        }
                    }
                }
            }
        }
    }

    DeleteItemListContent(
        state = viewModel.state,
        sheetState = sheetState,
        onDismiss = {
            onDismiss(viewModel.state.mediaId, viewModel.state.mediaType)
        },
        onConfirmClick = {
            viewModel.onEvent(DeleteItemListEvent.ConfirmDelete)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DeleteItemListContent(
    state: DeleteItemListState,
    sheetState: SheetState,
    onDismiss: () -> Unit,
    onConfirmClick: () -> Unit
) {

    val (titleStrResId, descriptionStrResId) =when(state.listType) {
        is ListType.CustomList -> {
            R.string.lists_delete_from_list to R.string.lists_delete_item_list_confirmation
        }
        ListType.Favorites -> {
            R.string.lists_delete_from_favorites to R.string.lists_delete_item_favorites_confirmation
        }
        ListType.Watchlist -> {
            R.string.lists_delete_from_watchlist to R.string.lists_delete_item_watchlist_confirmation
        }
    }

    ConfirmationDialog(
        sheetState = sheetState,
        title = stringResource(titleStrResId),
        description = annotatedStringResource(
            descriptionStrResId,
            state.mediaName
        ),
        isLoading = state.isLoading,
        confirmButtonColor = MaterialTheme.colorScheme.error,
        onConfirmClick = onConfirmClick,
        onDismiss = onDismiss
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    name = "DefaultPreviewLight",
    showBackground = true, heightDp = 300
)
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "DefaultPreviewDark",
    showBackground = true, heightDp = 300
)
@Composable
fun DeleteItemListScreenPreview() {
    AppTheme {
        DeleteItemListContent(
            state = DeleteItemListState(
                isLoading = false,
                mediaName = "The Flash",
                mediaType = MediaType.MOVIE,
                mediaId = 1,
                listType = ListType.Favorites
            ),
            sheetState = SheetState(
                skipPartiallyExpanded = true,
                positionalThreshold = { 0f },
                initialValue = SheetValue.Expanded,
                velocityThreshold = { 0f }
            ),
            onDismiss = {},
            onConfirmClick = {}
        )
    }
}