package com.buntupana.tmdb.core.ui.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.paging.LoadState
import com.buntupana.tmdb.core.ui.theme.Dimens
import com.buntupana.tmdb.core.ui.util.getOnBackgroundColor


fun LazyListScope.lazyListLoadStateGeneric(
    loadState: LoadState,
    retry: () -> Unit
) {

    item {
        when (loadState) {

            is LoadState.Error -> {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    ErrorAndRetry(
                        textColor = MaterialTheme.colorScheme.background.getOnBackgroundColor(),
                        errorMessage = stringResource(id = com.buntupana.tmdb.core.ui.R.string.message_loading_content_error),
                        onRetryClick = retry
                    )
                }
            }

            LoadState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = Dimens.padding.vertical),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicatorDelayed()
                }
            }

            is LoadState.NotLoading -> {}
        }
    }
}

