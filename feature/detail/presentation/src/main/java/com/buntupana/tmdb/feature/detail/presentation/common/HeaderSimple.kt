package com.buntupana.tmdb.feature.detail.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.buntupana.tmdb.core.ui.composables.ImageFromUrl
import com.buntupana.tmdb.core.ui.theme.DetailBackgroundColor
import com.buntupana.tmdb.core.ui.theme.Dimens
import com.buntupana.tmdb.core.ui.theme.Typography
import com.buntupana.tmdb.core.ui.util.getOnBackgroundColor
import com.panabuntu.tmdb.core.common.isNotNullOrBlank

@Composable
fun HeaderSimple(
    modifier: Modifier = Modifier,
    backgroundColor: Color,
    posterUrl: String?,
    mediaName: String,
    releaseYear: String?,
    subtitle: String? = null,
    setDominantColor: (color: Color) -> Unit = {}
) {
    Row(
        modifier = modifier
            .height(120.dp)
            .fillMaxWidth()
            .background(backgroundColor)
            .padding(horizontal = Dimens.padding.medium),
        verticalAlignment = Alignment.CenterVertically,
    ) {

        if (posterUrl.isNotNullOrBlank()) {
            ImageFromUrl(
                modifier = Modifier
                    .fillMaxHeight(0.8f)
                    .clip(RoundedCornerShape(Dimens.posterRound))
                    .aspectRatio(Dimens.aspectRatioMediaPoster),
                imageUrl = posterUrl,
                setDominantColor = { setDominantColor(it) }
            )
            Spacer(modifier = Modifier.padding(Dimens.padding.small))
            Arrangement.Start
        } else {
            Arrangement.Center
        }

        Column {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = buildAnnotatedString {
                    withStyle(SpanStyle(fontWeight = Typography.titleLarge.fontWeight)) {
                        append(mediaName)
                    }
                    append(" ")
                    append("($releaseYear)")
                },
                color = backgroundColor.getOnBackgroundColor(),
                fontSize = Typography.titleLarge.fontSize
            )

            if (subtitle.isNullOrBlank()) return@Column

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = subtitle,
                color = backgroundColor.getOnBackgroundColor(),
                fontSize = Typography.titleMedium.fontSize
            )
        }
    }
}

@Preview
@Composable
private fun CastHeaderPreview() {
    HeaderSimple(
        backgroundColor = DetailBackgroundColor,
        posterUrl = null,
        mediaName = "Pain Hustlers",
        subtitle = "asdf",
        releaseYear = "2023",
        setDominantColor = {}
    )
}

