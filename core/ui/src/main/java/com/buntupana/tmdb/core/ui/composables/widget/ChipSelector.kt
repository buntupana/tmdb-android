package com.buntupana.tmdb.core.ui.composables.widget

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.buntupana.tmdb.core.ui.R
import com.buntupana.tmdb.core.ui.theme.AppTheme
import com.buntupana.tmdb.core.ui.theme.Dimens
import com.buntupana.tmdb.core.ui.util.SelectableItem
import com.buntupana.tmdb.core.ui.util.UiText
import com.buntupana.tmdb.core.ui.util.getOnBackgroundColor

@Composable
fun ChipSelector(
    modifier: Modifier = Modifier,
    title: String? = null,
    showAllChip: Boolean = false,
    chipItemList: List<SelectableItem>,
    onSelectionChanged: (chipItemList: List<SelectableItem>) -> Unit
) {

    Column(modifier = modifier) {

        if (title != null) {
            Text(
                modifier = Modifier
                    .padding(
                        bottom = Dimens.padding.small
                    ),
                text = title,
                style = MaterialTheme.typography.titleMedium
            )
        }

        val isAllSelected = chipItemList.none { it.isSelected }

        FlowRow(
            modifier = Modifier
                .padding(
                    top = Dimens.padding.small
                ),
            horizontalArrangement = Arrangement.spacedBy(Dimens.padding.small)
        ) {
            if (showAllChip) {
                FilterChip(
                    selected = isAllSelected,
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = MaterialTheme.colorScheme.tertiary
                    ),
                    onClick = {
                        chipItemList.map { chipItem ->
                            chipItem.copy(isSelected = false)
                        }.let { chipItems ->
                            onSelectionChanged(chipItems)
                        }
                    },
                    label = {
                        val textColor = if (isAllSelected) {
                            MaterialTheme.colorScheme.tertiary.getOnBackgroundColor()
                        } else {
                            MaterialTheme.colorScheme.onBackground
                        }
                        Text(
                            text = stringResource(R.string.common_all),
                            color = textColor
                        )
                    }
                )
            }

            chipItemList.forEach {
                FilterChip(
                    selected = it.isSelected,
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = MaterialTheme.colorScheme.tertiary
                    ),
                    onClick = {
                        chipItemList.map { chipItem ->
                            chipItem.copy(
                                isSelected = if (chipItem.id == it.id) chipItem.isSelected.not() else chipItem.isSelected
                            )
                        }.let { chipItems ->
                            onSelectionChanged(chipItems)
                        }
                    },
                    label = {
                        val textColor = if (it.isSelected) {
                            MaterialTheme.colorScheme.tertiary.getOnBackgroundColor()
                        } else {
                            MaterialTheme.colorScheme.onBackground
                        }
                        Text(
                            text = it.name.asString(),
                            color = textColor
                        )
                    }
                )
            }
        }
    }
}

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    name = "DefaultPreviewLight",
    showBackground = true
)
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "DefaultPreviewDark",
    showBackground = true
)
@Composable
private fun ChipSelectorPreview() {

    var chipItemList by remember {
        mutableStateOf(
            listOf(
                SelectableItem(1, UiText.DynamicString("Action"), false),
                SelectableItem(2, UiText.DynamicString("Adventure"), false),
                SelectableItem(3, UiText.DynamicString("Animation"), false),
            )
        )
    }

    AppTheme {
        ChipSelector(
            modifier = Modifier,
            title = "Genres",
            showAllChip = true,
            chipItemList = chipItemList,
            onSelectionChanged = {
                chipItemList = it
            }
        )
    }
}