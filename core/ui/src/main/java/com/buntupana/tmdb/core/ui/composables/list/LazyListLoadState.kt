package com.buntupana.tmdb.core.ui.composables.list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import com.buntupana.tmdb.core.ui.R
import com.buntupana.tmdb.core.ui.composables.CircularProgressIndicatorDelayed
import com.buntupana.tmdb.core.ui.composables.ErrorAndRetry
import com.buntupana.tmdb.core.ui.snackbar.SnackbarController
import com.buntupana.tmdb.core.ui.snackbar.SnackbarEvent
import com.buntupana.tmdb.core.ui.theme.Dimens
import com.buntupana.tmdb.core.ui.util.UiText
import com.buntupana.tmdb.core.ui.util.getOnBackgroundColor
import kotlinx.coroutines.delay

private const val REFRESH_PLACEHOLDERS_COUNT = 10
private const val APPEND_PREPEND_PLACEHOLDERS_COUNT = 1

fun LazyListScope.lazyListLoadStateGeneric(
    loadState: LoadState,
    itemCount: Int,
    isRefresh: Boolean = false,
    retry: () -> Unit,
    placeHolder: (@Composable () -> Unit)? = null
) {

    item {

        when (loadState) {

            is LoadState.Error -> {

                if (isRefresh && itemCount > 0) {
                    LaunchedEffect(true) {
                        SnackbarController.sendEvent(
                            SnackbarEvent(
                                UiText.StringResource(R.string.message_refresh_content_error)
                            )
                        )
                    }
                    return@item
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .then(
                            if (isRefresh) Modifier.padding(top = 200.dp) else Modifier
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    ErrorAndRetry(
                        contentColor = MaterialTheme.colorScheme.background.getOnBackgroundColor(),
                        errorMessage = stringResource(id = R.string.message_loading_content_error),
                        onRetryClick = retry
                    )
                }
            }

            LoadState.Loading -> {


                if (isRefresh && itemCount > 0) return@item

                if (placeHolder != null) {

                    var showPlaceholder by remember { mutableStateOf(false) }

                    LaunchedEffect(showPlaceholder.not()) {
                        delay(500)
                        showPlaceholder = true
                    }

                    if (showPlaceholder) {
                        repeat(if (isRefresh) REFRESH_PLACEHOLDERS_COUNT else APPEND_PREPEND_PLACEHOLDERS_COUNT) {
                            placeHolder()
                        }
                    }
                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = Dimens.padding.vertical)
                            .then(
                                if (isRefresh) Modifier.padding(top = 200.dp) else Modifier
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicatorDelayed()
                    }
                }

            }

            is LoadState.NotLoading -> {}
        }
    }
}

