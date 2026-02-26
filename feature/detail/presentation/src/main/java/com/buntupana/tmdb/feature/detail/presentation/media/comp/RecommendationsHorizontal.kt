package com.buntupana.tmdb.feature.detail.presentation.media.comp

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.buntupana.tmdb.core.ui.theme.AppTheme
import com.buntupana.tmdb.core.ui.theme.Dimens
import com.buntupana.tmdb.feature.detail.presentation.R
import com.buntupana.tmdb.feature.detail.presentation.mediaDetailsMovieSample
import com.panabuntu.tmdb.core.common.entity.MediaType
import com.panabuntu.tmdb.core.common.model.MediaItem

@Composable
fun RecommendationsHorizontal(
    modifier: Modifier = Modifier,
    mediaItemList: List<MediaItem>,
    onItemClick: (mediaId: Long, mediaType: MediaType) -> Unit
) {

    if (mediaItemList.isEmpty()) {
        return
    }

    Column(
        modifier = modifier
    ) {
        HorizontalDivider()

        Text(
            modifier = Modifier.padding(
                horizontal = Dimens.padding.horizontal,
                vertical = Dimens.padding.vertical
            ),
            text = stringResource(id = R.string.detail_recommendations),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )

        LazyRow(
            modifier = Modifier.fillMaxWidth()
        ) {
            item {
                Spacer(modifier = Modifier.padding(horizontal = Dimens.padding.tiny))
            }
            items(mediaItemList) { item ->
                RecommendationItem(
                    itemWidth = Dimens.recommendationItemWidth,
                    mediaItem = item,
                    onItemClick = onItemClick
                )
            }
            item {
                Spacer(modifier = Modifier.padding(horizontal = Dimens.padding.tiny))
            }
        }
    }
}

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    name = "DefaultPreviewLight",
    showBackground = true,
)
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "DefaultPreviewDark",
    showBackground = true,
)
@Composable
private fun RecommendationsHorizontalPreview() {
    AppTheme {
        RecommendationsHorizontal(
            modifier = Modifier.fillMaxWidth(),
            mediaItemList = mediaDetailsMovieSample.recommendationList,
            onItemClick = {_, _ ->}
        )
    }
}