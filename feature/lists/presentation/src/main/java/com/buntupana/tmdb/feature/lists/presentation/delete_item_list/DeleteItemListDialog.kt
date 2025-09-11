package com.buntupana.tmdb.feature.lists.presentation.delete_item_list

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
import com.buntupana.tmdb.feature.presentation.R
import com.panabuntu.tmdb.core.common.entity.MediaType
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel
import timber.log.Timber

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeleteItemListDialog(
    viewModel: DeleteItemListViewModel = koinViewModel(),
    deleteItemListNav: DeleteItemListNav?,
    showDialog: Boolean,
    sheetState: SheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
    onDismiss: () -> Unit,
    onCancelClick: (itemId: String) -> Unit = {},
    onDeleteSuccess: () -> Unit
) {
    if (showDialog.not() || deleteItemListNav == null) return

    LaunchedEffect(deleteItemListNav) {
        viewModel.onEvent(DeleteItemListEvent.Init(deleteItemListNav))
    }

    val scope = rememberCoroutineScope()
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(lifecycleOwner.lifecycle) {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            launch {
                viewModel.sideEffect.collect { sideEffect ->
                    Timber.d("SignOutDialog: sideEffect = $sideEffect")
                    when (sideEffect) {
                        DeleteItemListSideEffect.DeleteSuccess -> {
                            sheetState.hide()
                            onDismiss()
                            onDeleteSuccess()
                        }
                    }
                }
            }
        }
    }

    ConfirmationDialog(
        sheetState = sheetState,
        title = stringResource(R.string.text_delete_from_list),
        description = annotatedStringResource(
            R.string.message_delete_item_list_confirmation,
            viewModel.state.mediaName
        ),
        isLoading = viewModel.state.isLoading,
        confirmButtonColor = MaterialTheme.colorScheme.error,
        onCancelClick = {
            onCancelClick(deleteItemListNav.itemId)
        },
        onConfirmClick = {
            viewModel.onEvent(DeleteItemListEvent.ConfirmDelete)
        },
        onDismiss = {
            scope.launch {
                sheetState.hide()
                onDismiss()
            }
        }
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    name = "DefaultPreviewLight",
    showBackground = true,
)
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "DefaultPreviewDark",
    showBackground = true,
)
@Composable
fun DeleteItemListScreenPreview() {
    AppTheme {
        DeleteItemListDialog(
            deleteItemListNav = DeleteItemListNav(
                itemId = "",
                listId = 0,
                mediaId = 0,
                mediaName = "The incredible Hulk",
                mediaType = MediaType.MOVIE
            ),
            showDialog = true,
            sheetState = SheetState(
                skipPartiallyExpanded = true,
                positionalThreshold = { 0f },
                initialValue = SheetValue.Expanded,
                velocityThreshold = { 0f }
            ),
            onDismiss = {},
            onDeleteSuccess = {}
        )
    }
}