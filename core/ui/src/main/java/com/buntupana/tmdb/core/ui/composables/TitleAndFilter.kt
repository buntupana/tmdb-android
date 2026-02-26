package com.buntupana.tmdb.core.ui.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.buntupana.tmdb.core.ui.R
import com.buntupana.tmdb.core.ui.composables.widget.AppTextButton
import com.buntupana.tmdb.core.ui.composables.widget.menu_selector.ExpandableMenuSelector
import com.buntupana.tmdb.core.ui.composables.widget.menu_selector.ExpandableMenuSelectorAlign
import com.buntupana.tmdb.core.ui.composables.widget.menu_selector.ExpandableMenuSelectorItem
import com.buntupana.tmdb.core.ui.filter_type.MediaFilter
import com.buntupana.tmdb.core.ui.theme.AppTheme
import com.buntupana.tmdb.core.ui.theme.Dimens

@Composable
fun <T : ExpandableMenuSelectorItem> TitleAndFilter(
    modifier: Modifier = Modifier,
    title: String = "",
    filterSet: Set<T> = emptySet(),
    indexSelected: Int = 0,
    titleClicked: (() -> Unit)? = null,
    filterClicked: ((item: T, index: Int) -> Unit)? = null
) {
    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        AppTextButton(
            modifier = Modifier
                .padding(start = Dimens.padding.tiny)
                .align(Alignment.CenterStart),
            onClick = { titleClicked?.invoke() },
            enabled = titleClicked != null,
            rippleColor = MaterialTheme.colorScheme.onBackground
        ) {
            Text(
                text = title,
                fontWeight = FontWeight.SemiBold,
                fontSize = 22.sp,
                color = MaterialTheme.colorScheme.onBackground
            )
            titleClicked ?: return@AppTextButton
            Icon(
                modifier = Modifier.size(22.dp),
                painter = painterResource(R.drawable.ic_arrow_right),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onBackground
            )
        }
        ExpandableMenuSelector(
            modifier = Modifier.align(Alignment.CenterEnd),
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

@Preview(showBackground = true)
@Composable
fun TitleAndFilterPreview() {
    AppTheme {
        TitleAndFilter(
            title = "Title",
            filterSet = MediaFilter.entries.toSet(),
            titleClicked = {}
        )
    }
}