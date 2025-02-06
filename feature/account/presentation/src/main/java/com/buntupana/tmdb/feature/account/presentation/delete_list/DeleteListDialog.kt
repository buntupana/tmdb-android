package com.buntupana.tmdb.feature.account.presentation.delete_list

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.ModalBottomSheetProperties
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import com.buntupana.tmdb.core.ui.theme.Dimens
import com.buntupana.tmdb.core.ui.util.annotatedStringResource
import com.buntupana.tmdb.core.ui.util.isInvisible
import com.buntupana.tmdb.feature.account.presentation.R
import kotlinx.coroutines.launch
import timber.log.Timber

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeleteListDialog(
    viewModel: DeleteListViewModel = hiltViewModel(),
    deleteListNav: DeleteListNav,
    showDialog: Boolean,
    sheetState: SheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
    onDismiss: () -> Unit,
    onDeleteSuccess: () -> Unit
) {

    if (showDialog.not()) return

    LaunchedEffect(deleteListNav) {
        viewModel.onEvent(DeleteListEvent.Init(deleteListNav))
    }

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
        state = viewModel.state,
        sheetState = sheetState,
        onDeleteListClick = {
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
fun DeleteListContent(
    state: DeleteListState,
    sheetState: SheetState,
    onDeleteListClick: () -> Unit,
    onDismiss: () -> Unit,
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = MaterialTheme.colorScheme.background,
        dragHandle = {},
        properties = ModalBottomSheetProperties(
            shouldDismissOnBackPress = false
        )
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
                text = stringResource(R.string.text_delete_list),
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(Dimens.padding.big))


            Box {

                if (state.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                Column(
                    modifier = Modifier.isInvisible(state.isLoading),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(
                        text =  annotatedStringResource(
                            R.string.message_delete_list_confirmation,
                            state.listName
                        ),
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(Dimens.padding.huge))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        Button(
                            onClick = onDismiss
                        ) {
                            Text(
                                text = stringResource(com.buntupana.tmdb.core.ui.R.string.text_cancel),
                                style = MaterialTheme.typography.titleMedium,
                            )
                        }
                        Button(
                            onClick = onDeleteListClick,
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                        ) {
                            Text(
                                text = stringResource(com.buntupana.tmdb.core.ui.R.string.text_confirm),
                                style = MaterialTheme.typography.titleMedium
                            )
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun DeleteListScreenPreview() {
    DeleteListContent(
        state = DeleteListState(),
        sheetState = SheetState(
            skipPartiallyExpanded = true,
            LocalDensity.current,
            initialValue = SheetValue.Expanded
        ),
        onDeleteListClick = {},
        onDismiss = {}
    )
}