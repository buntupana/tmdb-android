package com.buntupana.tmdb.feature.lists.presentation.create_update_list

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import com.buntupana.tmdb.core.ui.theme.Dimens
import com.buntupana.tmdb.core.ui.util.isInvisible
import com.buntupana.tmdb.feature.lists.presentation.create_update_list.comp.CreateListForm
import com.buntupana.tmdb.feature.presentation.R
import kotlinx.coroutines.launch
import timber.log.Timber

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateUpdateListDialog(
    viewModel: CreateUpdateListViewModel = hiltViewModel(),
    sheetState: SheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
        confirmValueChange = { viewModel.state.isLoading.not() }
    ),
    onDismiss: () -> Unit,
    onCreateUpdateListSuccess: () -> Unit = {}
) {

    val lifecycleOwner = LocalLifecycleOwner.current
    val scope = rememberCoroutineScope()

    LaunchedEffect(lifecycleOwner.lifecycle) {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            launch {
                viewModel.sideEffect.collect { sideEffect ->
                    Timber.d("CreateListDialog: sideEffect = $sideEffect")
                    when (sideEffect) {
                        CreateUpdateListSideEffect.CreateUpdateListSuccess -> {
                            onCreateUpdateListSuccess()
                            sheetState.hide()
                            onDismiss()
                        }
                    }
                }
            }
        }
    }

    CreateListContent(
        state = viewModel.state,
        sheetState = sheetState,
        onDismiss = {
            scope.launch {
                sheetState.hide()
                onDismiss()
            }
        },
        onCreateListClick = { viewModel.onEvent(CreateUpdateListEvent.CreateUpdateList) },
        updateForm = { listName, listDescription, isPublic ->

            viewModel.onEvent(
                CreateUpdateListEvent.UpdateForm(
                    listName = listName,
                    description = listDescription,
                    isPublic = isPublic
                )
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateListContent(
    state: CreateUpdateListState,
    sheetState: SheetState,
    onDismiss: () -> Unit,
    onCreateListClick: () -> Unit,
    updateForm: (listName: String, listDescription: String, isPublic: Boolean) -> Unit
) {
    ModalBottomSheet(
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
                .fillMaxWidth()
                .padding(
                    horizontal = Dimens.padding.horizontal,
                    vertical = Dimens.padding.big
                )
                .animateContentSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            val titleStrResId = if (state.isNewList) {
                R.string.text_create_list
            } else {
                R.string.text_edit_list
            }

            Text(
                text = stringResource(titleStrResId),
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(Dimens.padding.huge))

            Box {

                if (state.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                    )
                }

                CreateListForm(
                    modifier = Modifier
                        .fillMaxWidth()
                        .isInvisible(state.isLoading),
                    listName = state.listName,
                    description = state.description,
                    isPublic = state.isPublic,
                    updateForm = updateForm,
                    onCreateListClick = onCreateListClick
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun CreateListScreenPreview() {
    CreateListContent(
        state = CreateUpdateListState(
            isNewList = false,
            listName = "The 97th Academy Award nominees for Best Motion Picture of the Year Oscars",
            description = "",
            isPublic = true
        ),
        sheetState = SheetState(
            skipPartiallyExpanded = true,
            LocalDensity.current,
            initialValue = SheetValue.Expanded
        ),
        onDismiss = {},
        onCreateListClick = {},
        updateForm = { _, _, _ -> }
    )
}