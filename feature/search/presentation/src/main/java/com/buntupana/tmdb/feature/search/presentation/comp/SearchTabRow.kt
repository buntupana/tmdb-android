package com.buntupana.tmdb.feature.search.presentation.comp

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SecondaryScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.buntupana.tmdb.core.ui.R
import com.buntupana.tmdb.core.ui.composables.TabItemCount
import com.buntupana.tmdb.core.ui.theme.AppTheme
import com.buntupana.tmdb.core.ui.util.getOnBackgroundColor
import com.buntupana.tmdb.feature.search.presentation.MediaResultCount
import com.buntupana.tmdb.feature.search.presentation.SearchType
import kotlinx.coroutines.launch

@Composable
fun SearchTabRow(
    modifier: Modifier = Modifier,
    pagerState: PagerState?,
    resultCountList: List<MediaResultCount>
) {

    if (resultCountList.isEmpty() || pagerState == null) return

    val scope = rememberCoroutineScope()

    SecondaryScrollableTabRow(
        modifier = modifier
            .background(MaterialTheme.colorScheme.primaryContainer),
        selectedTabIndex = pagerState.currentPage,
        contentColor = MaterialTheme.colorScheme.primaryContainer.getOnBackgroundColor(),
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        edgePadding = 0.dp,
        divider = {},
        indicator = {
            SecondaryIndicator(
                modifier = Modifier.tabIndicatorOffset(pagerState.currentPage),
                color = MaterialTheme.colorScheme.secondaryContainer,
            )
        }
    ) {
        // Add tabs for all of our pages
        resultCountList.forEachIndexed { index, resultCount ->

            val titleResId = when (resultCount.searchType) {
                SearchType.MOVIE -> R.string.common_movies
                SearchType.TV_SHOW -> R.string.common_tv_shows
                SearchType.PERSON -> com.buntupana.tmdb.feature.search.presentation.R.string.search_people
            }

            Tab(
                text = {
                    TabItemCount(
                        titleResId = titleResId,
                        resultCount = resultCount.resultCount,
                        isSelected = pagerState.currentPage == index
                    )
                },
                selected = pagerState.currentPage == index,
                onClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                }
            )
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
private fun SearchTabRowPreview() {
    AppTheme {

        val mediaResultCount = listOf(
            MediaResultCount(SearchType.MOVIE, 10),
            MediaResultCount(SearchType.TV_SHOW, 20),
            MediaResultCount(SearchType.PERSON, 30)
        )

        SearchTabRow(
            modifier = Modifier.fillMaxWidth(),
            pagerState = PagerState(0) { mediaResultCount.size },
            resultCountList = mediaResultCount
        )
    }
}