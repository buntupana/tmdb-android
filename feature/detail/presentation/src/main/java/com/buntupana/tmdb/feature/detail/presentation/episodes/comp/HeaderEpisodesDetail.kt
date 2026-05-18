package com.buntupana.tmdb.feature.detail.presentation.episodes.comp

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.tooling.preview.Preview
import com.buntupana.tmdb.core.ui.composables.HeaderSimple
import com.buntupana.tmdb.core.ui.theme.AppTheme
import com.buntupana.tmdb.core.ui.theme.Dimens
import com.buntupana.tmdb.core.ui.theme.Typography
import com.buntupana.tmdb.core.ui.util.getOnBackgroundColor
import com.buntupana.tmdb.feature.detail.presentation.R

@Composable
fun HeaderEpisodesDetail(
    modifier: Modifier = Modifier,
    backgroundColor: Color,
    posterUrl: String?,
    tvShowName: String,
    releaseYear: String?,
    seasonName: String,
    episodesCount: Int,
    setDominantColor: (color: Color) -> Unit = {}
) {

    val episodes = if (episodesCount > 0) {
        pluralStringResource(
            id = R.plurals.detail_episodes_count,
            count = episodesCount,
            episodesCount
        )
    } else {
        null
    }

    HeaderSimple(
        modifier = modifier,
        backgroundColor = backgroundColor,
        posterUrl = posterUrl,
        mediaName = tvShowName,
        releaseYear = releaseYear,
        setDominantColor = setDominantColor,
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = Dimens.padding.tiny)
            ,
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier,
                text = seasonName,
                color = backgroundColor.getOnBackgroundColor(),
                style = Typography.titleMedium
            )

            AnimatedVisibility(
                visible = episodes != null,
                enter = fadeIn()
            ) {
                Text(
                    modifier = Modifier,
                    text = episodes.orEmpty(),
                    color = backgroundColor.getOnBackgroundColor(),
                    fontSize = Typography.titleMedium.fontSize
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
private fun CastHeaderPreview() {
    AppTheme {
        HeaderEpisodesDetail(
            backgroundColor = MaterialTheme.colorScheme.surfaceDim,
            posterUrl = null,
            tvShowName = "Pain Hustlers",
            seasonName = "Season 2",
            episodesCount = 7,
            releaseYear = "2023",
            setDominantColor = {}
        )
    }
}

