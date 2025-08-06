package com.buntupana.tmdb.feature.account.presentation.sign_out

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import com.buntupana.tmdb.core.ui.theme.Dimens
import com.buntupana.tmdb.core.ui.theme.SecondaryColor
import com.buntupana.tmdb.feature.account.presentation.R
import kotlinx.coroutines.launch
import timber.log.Timber
import com.buntupana.tmdb.core.ui.R as RCore

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignOutDialog(
    viewModel: SignOutViewModel = hiltViewModel(),
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
fun SignOutContent(
    state: SignOutState,
    sheetState: SheetState,
    onDismiss: () -> Unit,
    onSignOutClick: () -> Unit
) {

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = MaterialTheme.colorScheme.background,
        dragHandle = {}
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = Dimens.padding.horizontal,
                    vertical = Dimens.padding.big
                )
                .animateContentSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.text_sign_out),
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(Dimens.padding.small))
            Text(
                text = stringResource(R.string.message_sign_out_confirmation),
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(Dimens.padding.big))
            if (state.isLoading) {
                CircularProgressIndicator()
            } else {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Button(
                        onClick = onDismiss
                    ) {
                        Text(
                            text = stringResource(RCore.string.text_cancel),
                            style = MaterialTheme.typography.titleMedium,
                        )
                    }
                    Button(
                        onClick = onSignOutClick,
                        colors = ButtonDefaults.buttonColors(containerColor = SecondaryColor)
                    ) {
                        Text(
                            text = stringResource(RCore.string.text_confirm),
                            style = MaterialTheme.typography.titleMedium,
                        )
                    }
                }
            }
        }
    }
}

@ExperimentalMaterial3Api
@Preview
@Composable
fun SignOutScreenPreview() {
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