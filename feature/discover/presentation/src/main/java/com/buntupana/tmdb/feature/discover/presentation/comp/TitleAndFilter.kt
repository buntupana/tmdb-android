package com.buntupana.tmdb.feature.discover.presentation.comp

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.buntupana.tmdb.core.ui.composables.widget.menu_selector.ExpandableMenuSelector
import com.buntupana.tmdb.core.ui.composables.widget.menu_selector.ExpandableMenuSelectorAlign
import com.buntupana.tmdb.core.ui.composables.widget.menu_selector.ExpandableMenuSelectorItem
import com.buntupana.tmdb.core.ui.theme.Dimens

@Composable
fun <T : ExpandableMenuSelectorItem> TitleAndFilter(
    modifier: Modifier = Modifier,
    title: String = "",
    filterSet: Set<T> = emptySet(),
    indexSelected: Int = 0,
    filterClicked: ((item: T, index: Int) -> Unit)? = null
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Text(
            modifier = Modifier
                .padding(start = Dimens.padding.medium)
                .align(Alignment.CenterStart),
            text = title,
            fontWeight = FontWeight.SemiBold,
            fontSize = 22.sp,
        )
        ExpandableMenuSelector(
            menuItemSet = filterSet,
            defaultIndexSelected = indexSelected,
            onItemClick = { item, index ->
                filterClicked?.invoke(item, index)
            },
            menuAlign = ExpandableMenuSelectorAlign.END,
            horizontalPadding = Dimens.padding.medium
        )
    }
}