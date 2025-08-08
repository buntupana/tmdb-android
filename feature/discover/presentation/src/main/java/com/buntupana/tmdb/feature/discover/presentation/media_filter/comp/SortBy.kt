package com.buntupana.tmdb.feature.discover.presentation.media_filter.comp

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.buntupana.tmdb.core.ui.composables.DropdownMenuText
import com.buntupana.tmdb.core.ui.theme.Dimens
import com.buntupana.tmdb.feature.discover.presentation.R
import com.buntupana.tmdb.feature.discover.presentation.media_filter.SortByOrder
import com.buntupana.tmdb.feature.discover.presentation.media_filter.SortBySimple

@Composable
fun SortBy(
    modifier: Modifier = Modifier,
    sortBySelected: SortBySimple,
    sortByOrderSelected: SortByOrder,
    onApplySortBy: (sortBySimple: SortBySimple, sortByOrder: SortByOrder) -> Unit,
) {
    Column(
        modifier = modifier
    ) {

        Text(
            modifier = Modifier.padding(
                bottom = Dimens.padding.small
            ),
            text = stringResource(R.string.text_sort_by),
            style = MaterialTheme.typography.titleMedium
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = Dimens.padding.small
                )
        ) {

            DropdownMenuText(
                modifier = Modifier.animateContentSize(),
                text = stringResource(sortBySelected.stringResId),
                optionMap = SortBySimple.entries.mapIndexed { index, sortBy ->
                    index to stringResource(sortBy.stringResId)
                }.toMap(),
                onOptionClicked = { id, value ->
                    onApplySortBy(
                        SortBySimple.entries[id],
                        sortByOrderSelected
                    )
                }
            )

            DropdownMenuText(
                modifier = Modifier.animateContentSize(),
                text = stringResource(sortByOrderSelected.stringResId),
                optionMap = SortByOrder.entries.mapIndexed { index, sortBy ->
                    index to stringResource(sortBy.stringResId)
                }.toMap(),
                onOptionClicked = { id, value ->
                    onApplySortBy(
                        sortBySelected,
                        SortByOrder.entries[id]
                    )
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SortByPreview() {
    SortBy(
        modifier = Modifier,
        sortBySelected = SortBySimple.POPULARITY,
        sortByOrderSelected = SortByOrder.DESCENDING,
        onApplySortBy = { _, _ -> }
    )
}