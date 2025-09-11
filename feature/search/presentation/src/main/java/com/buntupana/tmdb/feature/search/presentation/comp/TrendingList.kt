package com.buntupana.tmdb.feature.search.presentation.comp

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.buntupana.tmdb.core.ui.theme.AppTheme
import com.buntupana.tmdb.core.ui.theme.Dimens
import com.buntupana.tmdb.feature.search.domain.model.SearchItem
import com.buntupana.tmdb.feature.search.presentation.R
import com.buntupana.tmdb.core.ui.R as RCore

@Composable
fun TrendingList(
    modifier: Modifier = Modifier,
    mediaItemList: List<SearchItem>,
    clickable: (mediaItem: SearchItem) -> Unit
) {

    if (mediaItemList.isEmpty()) {
        return
    }

    val listState = rememberLazyListState()

    LazyColumn(
        modifier = modifier.fillMaxWidth(),
        state = listState
    ) {
        item {
            Column(
                Modifier
                    .fillMaxWidth()
                    .background(Color.Gray.copy(alpha = 0.2f))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = Dimens.padding.medium,
                            vertical = Dimens.padding.small
                        ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        modifier = Modifier.size(32.dp),
                        painter = painterResource(id = R.drawable.ic_trending_icon),
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.width(Dimens.padding.small))
                    Text(
                        text = stringResource(id = RCore.string.text_trending),
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                HorizontalDivider()
            }
        }

        items(mediaItemList) { mediaItem ->
            SuggestionItem(
                mediaItem = mediaItem,
                clickable = clickable
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
fun TrendingListPreview() {
    val mediaItemList = listOf(
        SearchItem.Movie(
            id = 1,
            name = "Movie Title 1",
            imageUrl = null,
            originalLanguage = "en",
            popularity = 8.0f,
            voteAverage = 8,
            originalName = "",
            voteCount = 100
        ),
        SearchItem.TvShow(
            id = 2,
            name = "TV Show Name 1",
            imageUrl = null,
            popularity = 8.0f,
            voteAverage = 8,
            originalName = "",
            voteCount = 100
        ),
        SearchItem.Person(
            id = 3,
            name = "Person Name 1",
            imageUrl = null,
            popularity = 100f,
            originalName = ""
        ),
        SearchItem.Movie(
            id = 4,
            name = "Movie Title 2",
            imageUrl = null,
            originalLanguage = "en",
            popularity = 8.0f,
            voteAverage = 8,
            originalName = "",
            voteCount = 100
        )
    )
    AppTheme {
        TrendingList(
            mediaItemList = mediaItemList,
            clickable = {}
        )
    }
}