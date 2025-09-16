package com.buntupana.tmdb.feature.lists.presentation.delete_item_watchlist_favorites

import android.content.res.Configuration
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import com.buntupana.tmdb.core.ui.composables.dialog.ConfirmationDialog
import com.buntupana.tmdb.core.ui.theme.AppTheme
import com.buntupana.tmdb.core.ui.util.annotatedStringResource
import com.buntupana.tmdb.feature.lists.presentation.watchlist_favorites.ScreenType
import com.buntupana.tmdb.feature.presentation.R
import com.panabuntu.tmdb.core.common.entity.MediaType
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel
import timber.log.Timber

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeleteItemWatchlistFavoritesDialog(
    viewModel: DeleteItemWatchlistFavoritesViewModel = koinViewModel(),
    deleteItemWatchlistFavoritesNav: DeleteItemWatchlistFavoritesNav?,
    showDialog: Boolean,
    sheetState: SheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
    onDismiss: () -> Unit,
    onCancelClick: (mediaId: Long, mediaType: MediaType) -> Unit,
    onDeleteSuccess: () -> Unit
) {
    if (showDialog.not() || deleteItemWatchlistFavoritesNav == null) return

    LaunchedEffect(deleteItemWatchlistFavoritesNav) {
        viewModel.onEvent(DeleteItemWatchlistFavoritesEvent.Init(deleteItemWatchlistFavoritesNav))
    }

    val scope = rememberCoroutineScope()
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(lifecycleOwner.lifecycle) {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            launch {
                viewModel.sideEffect.collect { sideEffect ->
                    Timber.d("SignOutDialog: sideEffect = $sideEffect")
                    when (sideEffect) {
                        DeleteItemWatchlistFavoritesSideEffect.DeleteSuccess -> {
                            sheetState.hide()
                            onDismiss()
                            onDeleteSuccess()
                        }
                    }
                }
            }
        }
    }

    DeleteItemWatchlistFavoritesContent(
        state = viewModel.state,
        sheetState = sheetState,
        onDismiss = {
            scope.launch {
                sheetState.hide()
                onDismiss()
            }
        },
        onConfirmClick = {
            viewModel.onEvent(DeleteItemWatchlistFavoritesEvent.ConfirmDelete)
        },
        onCancelClick = {
            onCancelClick(
                deleteItemWatchlistFavoritesNav.mediaId,
                deleteItemWatchlistFavoritesNav.mediaType
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DeleteItemWatchlistFavoritesContent(
    state: DeleteItemWatchlistFavoritesState,
    sheetState: SheetState,
    onDismiss: () -> Unit,
    onConfirmClick: () -> Unit,
    onCancelClick: () -> Unit
) {

    val (titleStrResId, descriptionStrResId) = when(state.screenType) {
        ScreenType.WATCHLIST -> {
            R.string.text_delete_from_watchlist to R.string.message_delete_item_watchlist_confirmation
        }
        ScreenType.FAVORITES -> {
            R.string.text_delete_from_favorites to R.string.message_delete_item_favorites_confirmation
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
        onCancelClick = onCancelClick,
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
fun DeleteItemWatchlistFavoritesPreview() {
    AppTheme {
        DeleteItemWatchlistFavoritesContent(
            state = DeleteItemWatchlistFavoritesState(
                screenType = ScreenType.WATCHLIST,
            ),
            sheetState = SheetState(
                skipPartiallyExpanded = true,
                positionalThreshold = { 0f },
                initialValue = SheetValue.Expanded,
                velocityThreshold = { 0f }
            ),
            onDismiss = {},
            onConfirmClick = {},
            onCancelClick = {}
        )
    }
}