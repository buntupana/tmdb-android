package com.buntupana.tmdb.feature.account.presentation.sign_out

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
import com.buntupana.tmdb.feature.account.presentation.R
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel
import timber.log.Timber

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignOutDialog(
    viewModel: SignOutViewModel = koinViewModel(),
    showDialog: Boolean,
    sheetState: SheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
    onDismiss: () -> Unit
) {

    if (showDialog.not()) return

    val coroutineScope = rememberCoroutineScope()
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(lifecycleOwner.lifecycle) {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            launch {
                viewModel.sideEffect.collect { sideEffect ->
                    Timber.d("SignOutDialog: sideEffect = $sideEffect")
                    when (sideEffect) {
                        SignOutSideEffect.SignOutSuccess -> onDismiss()
                    }
                }
            }
        }
    }
    SignOutContent(
        state = viewModel.state,
        sheetState = sheetState,
        onDismiss = {
            coroutineScope.launch {
                sheetState.hide()
                onDismiss()
            }
        },
        onSignOutClick = { viewModel.onEvent(SignOutEvent.SignOut) }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SignOutContent(
    state: SignOutState,
    sheetState: SheetState,
    onDismiss: () -> Unit,
    onSignOutClick: () -> Unit
) {
    ConfirmationDialog(
        sheetState = sheetState,
        title = stringResource(R.string.account_sign_out),
        description = stringResource(R.string.account_sign_out_confirmation),
        isLoading = state.isLoading,
        confirmButtonColor = MaterialTheme.colorScheme.error,
        confirmationButtonTextColor = MaterialTheme.colorScheme.onError,
        onConfirmClick = onSignOutClick,
        onDismiss = onDismiss
    )
}

@ExperimentalMaterial3Api
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
private fun SignOutDialogPreview() {
    AppTheme {
        SignOutContent(
            SignOutState(isLoading = false),
            sheetState = SheetState(
                skipPartiallyExpanded = true,
                positionalThreshold = { 0f },
                initialValue = SheetValue.Expanded,
                velocityThreshold = { 0f }
            ),
            onDismiss = {},
            onSignOutClick = {}
        )
    }
}