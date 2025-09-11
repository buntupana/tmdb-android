package com.buntupana.tmdb.feature.lists.presentation.delete_list

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
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel
import timber.log.Timber

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeleteListDialog(
    viewModel: DeleteListViewModel = koinViewModel(),
    sheetState: SheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
    onDismiss: () -> Unit,
    onDeleteSuccess: () -> Unit = {}
) {

    val scope = rememberCoroutineScope()
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(lifecycleOwner.lifecycle) {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            launch {
                viewModel.sideEffect.collect { sideEffect ->
                    Timber.d("SignOutDialog: sideEffect = $sideEffect")
                    when (sideEffect) {
                        DeleteListSideEffect.DeleteListSuccess -> {
                            sheetState.hide()
                            onDismiss()
                            onDeleteSuccess()
                        }
                    }
                }
            }
        }
    }

    DeleteListContent(
        sheetState = sheetState,
        state = viewModel.state,
        onConfirmClick = {
            viewModel.onEvent(DeleteListEvent.ConfirmDeleteList)
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
@Composable
private fun DeleteListContent(
    state: DeleteListState,
    sheetState: SheetState,
    onDismiss: () -> Unit,
    onConfirmClick: () -> Unit
) {
    ConfirmationDialog(
        sheetState = sheetState,
        title = stringResource(R.string.text_delete_list),
        description = annotatedStringResource(
            R.string.message_delete_list_confirmation,
            state.listName
        ),
        isLoading = state.isLoading,
        confirmButtonColor = MaterialTheme.colorScheme.error,
        confirmationButtonTextColor = MaterialTheme.colorScheme.onError,
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
private fun DeleteListScreenPreview() {
    AppTheme {
        DeleteListContent(
            state = DeleteListState(listName = "List Name"),
            sheetState = SheetState(
                skipPartiallyExpanded = true,
                positionalThreshold = { 0f },
                initialValue = SheetValue.Expanded,
                velocityThreshold = { 0f }
            ),
            onConfirmClick = {},
            onDismiss = {}
        )
    }
}
