package com.buntupana.tmdb.feature.discover.presentation.comp

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.buntupana.tmdb.core.presentation.composables.widget.menu_selector.MenuSelector
import com.buntupana.tmdb.core.presentation.composables.widget.menu_selector.MenuSelectorItem
import com.buntupana.tmdb.core.presentation.theme.Dimens

@Composable
fun <T : MenuSelectorItem> TitleAndFilter(
    title: String = "",
    filterSet: Set<T> = emptySet(),
    indexSelected: Int = 0,
    filterClicked: ((item: T, index: Int) -> Unit)? = null
) {
    Box(
        modifier = Modifier.padding(Dimens.padding.medium),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = title,
            fontWeight = FontWeight.SemiBold,
            fontSize = 22.sp,
            modifier = Modifier.align(Alignment.CenterStart)
        )
        MenuSelector(
            menuItemSet = filterSet,
            indexSelected = indexSelected,
            onItemClick = { item, index ->
                filterClicked?.invoke(item, index)
            }
        )
    }
}