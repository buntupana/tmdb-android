package com.buntupana.tmdb.feature.lists.presentation.manage_lists.comp

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.buntupana.tmdb.core.ui.composables.HeaderSimple
import com.buntupana.tmdb.core.ui.composables.VerticalTextRoulette
import com.buntupana.tmdb.core.ui.theme.Typography
import com.buntupana.tmdb.core.ui.util.getOnBackgroundColor
import com.buntupana.tmdb.feature.presentation.R

@Composable
fun HeaderManageLists(
    modifier: Modifier = Modifier,
    backgroundColor: Color,
    posterUrl: String?,
    mediaName: String,
    releaseYear: String?,
    listsCount: Int? = null,
    setDominantColor: (color: Color) -> Unit = {}
) {

    HeaderSimple(
        modifier,
        backgroundColor,
        posterUrl,
        mediaName,
        releaseYear,
        setDominantColor
    ) {

        listsCount ?: return@HeaderSimple

        Row {
            Text(
                text = stringResource(R.string.text_belongs_to),
                color = backgroundColor.getOnBackgroundColor(),
                fontSize = Typography.titleMedium.fontSize
            )
            VerticalTextRoulette(
                text = " $listsCount ",
                color = backgroundColor.getOnBackgroundColor(),
                fontSize = Typography.titleMedium.fontSize
            )
            Text(
                text = stringResource(R.string.text_lists).lowercase(),
                color = backgroundColor.getOnBackgroundColor(),
                fontSize = Typography.titleMedium.fontSize
            )
        }
    }
}

@Preview
@Composable
private fun CastHeaderPreview() {
    HeaderManageLists(
        backgroundColor = MaterialTheme.colorScheme.surfaceDim,
        posterUrl = null,
        mediaName = "Pain Hustlers",
        listsCount = 6,
        releaseYear = "2023",
        setDominantColor = {}
    )
}

