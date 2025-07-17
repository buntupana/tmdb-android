package com.buntupana.tmdb.feature.discover.presentation.movies

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalBottomSheetProperties
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.buntupana.tmdb.core.ui.theme.Dimens
import com.buntupana.tmdb.feature.discover.domain.entity.MediaFilter
import com.buntupana.tmdb.feature.discover.presentation.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieFilterDialog(
    showDialog: Boolean,
    sheetState: SheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
    onDismiss: () -> Unit,
    onApplyFilterClick: (mediaFilter: MediaFilter) -> Unit,
) {

    if (showDialog.not()) return

    MovieFilterContent(
        sheetState = sheetState,
        onDismiss = onDismiss,
        onApplyFilterClick = onApplyFilterClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MovieFilterContent(
    sheetState: SheetState,
    onDismiss: () -> Unit,
    onApplyFilterClick: (mediaFilter: MediaFilter) -> Unit,
) {

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = MaterialTheme.colorScheme.background,
        dragHandle = {},
        properties = ModalBottomSheetProperties(shouldDismissOnBackPress = false)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    vertical = Dimens.padding.big
                )
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(R.string.text_select_filter),
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(Dimens.padding.big))

            LazyColumn {
                items(count = MovieFilter.entries.size) { index ->

                    if (MovieFilter.entries[index] == MovieFilter.CUSTOM) return@items

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                val mediaFilter = when (MovieFilter.entries[index]) {
                                    MovieFilter.POPULAR -> MediaFilterMovieDefault.popular
                                    MovieFilter.NOW_PLAYING -> MediaFilterMovieDefault.nowPlaying
                                    MovieFilter.UPCOMING -> MediaFilterMovieDefault.upcoming
                                    MovieFilter.TOP_RATED -> MediaFilterMovieDefault.topRated
                                    MovieFilter.CUSTOM -> MediaFilterMovieDefault.popular
                                }
                                onApplyFilterClick(mediaFilter)
                                onDismiss()
                            }
                            .padding(
                                vertical = Dimens.padding.small,
                                horizontal = Dimens.padding.horizontal
                            )
                    ) {
                        Text(
                            text = stringResource(MovieFilter.entries[index].filterNameResId),
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
private fun MovieFilterDialogPreview() {
    MovieFilterDialog(
        sheetState = SheetState(
            skipPartiallyExpanded = true,
            LocalDensity.current,
            initialValue = SheetValue.Expanded
        ),
        showDialog = true,
        onDismiss = {},
        onApplyFilterClick = {}
    )
}