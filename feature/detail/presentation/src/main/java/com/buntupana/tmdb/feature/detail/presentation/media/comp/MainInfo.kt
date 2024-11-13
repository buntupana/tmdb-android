package com.buntupana.tmdb.feature.detail.presentation.media.comp

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.buntupana.tmdb.core.ui.composables.DivisorCircle
import com.buntupana.tmdb.core.ui.composables.HoursMinutesText
import com.buntupana.tmdb.core.ui.composables.NestedVerticalLazyGrid
import com.buntupana.tmdb.core.ui.composables.OutlinedText
import com.buntupana.tmdb.core.ui.composables.widget.UserScore
import com.buntupana.tmdb.core.ui.theme.DetailBackgroundColor
import com.buntupana.tmdb.core.ui.theme.Dimens
import com.buntupana.tmdb.core.ui.util.getOnBackgroundColor
import com.buntupana.tmdb.feature.detail.domain.model.MediaDetails
import com.buntupana.tmdb.feature.detail.domain.model.Person
import com.buntupana.tmdb.feature.detail.presentation.R
import com.buntupana.tmdb.feature.detail.presentation.mediaDetailsMovieSample
import com.buntupana.tmdb.core.ui.R as RCore

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MainInfo(
    modifier: Modifier = Modifier,
    mediaDetails: MediaDetails,
    textColor: Color,
    onItemClick: (personId: Long) -> Unit
) {

    val uriHandler = LocalUriHandler.current

    Column(
        modifier = modifier
    ) {

        // Title and release year
        FlowRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = Dimens.padding.medium,
                    bottom = Dimens.padding.small,
                    start = Dimens.padding.medium,
                    end = Dimens.padding.medium
                ),
            horizontalArrangement = Arrangement.Center,
            verticalArrangement = Arrangement.Center,

            ) {
            Text(
                text = mediaDetails.title,
                color = textColor,
                fontWeight = FontWeight(600),
                fontSize = 24.sp,
                textAlign = TextAlign.Center
            )

            mediaDetails.releaseDate?.let { releaseDate ->

                Spacer(modifier = Modifier.width(Dimens.padding.small))

                Text(
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .alpha(0.7f),
                    text = "(${releaseDate.year})",
                    color = textColor,
                    fontWeight = FontWeight(400)
                )
                Spacer(modifier = Modifier.width(4.dp))
            }
        }

        // User score and trailer
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = Dimens.padding.small),
            verticalAlignment = Alignment.CenterVertically,
        ) {

            Row(
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = Dimens.padding.small, horizontal = Dimens.padding.medium),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                UserScore(
                    modifier = Modifier.size(50.dp),
                    score = mediaDetails.userScore
                )
                Spacer(modifier = Modifier.width(Dimens.padding.small))
                Text(
                    text = stringResource(id = RCore.string.text_user_score),
                    color = textColor,
                    fontWeight = FontWeight(700)
                )
            }
            if (mediaDetails.trailerUrl.isNotBlank()) {
                Image(
                    modifier = Modifier
                        .size(height = 34.dp, width = 1.dp)
                        .alpha(0.5f),
                    painter = ColorPainter(textColor),
                    contentDescription = null
                )
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .padding(Dimens.padding.small)
                        .clickable {
                            uriHandler.openUri(mediaDetails.trailerUrl)
                        },
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Image(
                        modifier = Modifier.size(24.dp),
                        painter = painterResource(id = R.drawable.ic_play),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(textColor)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        modifier = Modifier.align(Alignment.CenterVertically),
                        text = stringResource(R.string.text_play_trailer),
                        color = textColor,
                        fontWeight = FontWeight(400),
                    )
                }
            }
        }

        // Certification, duration and genres
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0x1A000000))
                .border(BorderStroke(1.dp, Color(0x40000000)))
                .padding(vertical = Dimens.padding.small, horizontal = Dimens.padding.medium),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {

                OutlinedText(
                    modifier = Modifier.padding(horizontal = Dimens.padding.tiny),
                    text = mediaDetails.ageCertification,
                    color = textColor
                )

                var isReleaseDateAndCountryCodeInfo = false
                if (mediaDetails is MediaDetails.Movie) {

                    Row {
                        if (mediaDetails.localReleaseDate.orEmpty().isNotBlank()) {
                            Text(
                                modifier = Modifier.padding(horizontal = Dimens.padding.tiny),
                                text = mediaDetails.localReleaseDate.orEmpty(),
                                color = textColor
                            )
                            isReleaseDateAndCountryCodeInfo = true
                        }
                        if (mediaDetails.localCountryCodeRelease.isNotBlank()) {
                            Text(
                                modifier = Modifier.padding(horizontal = Dimens.padding.tiny),
                                text = "(${mediaDetails.localCountryCodeRelease})",
                                color = textColor
                            )
                            isReleaseDateAndCountryCodeInfo = true
                        }
                    }
                }

                if (
                    (mediaDetails.ageCertification.isNotBlank() || isReleaseDateAndCountryCodeInfo) &&
                    mediaDetails.runTime != 0L
                ) {
                    DivisorCircle(
                        color = textColor
                    )
                }

                if (mediaDetails.runTime != 0L) {
                    HoursMinutesText(
                        modifier = Modifier.padding(horizontal = Dimens.padding.tiny),
                        time = mediaDetails.runTime,
                        color = textColor
                    )
                }
            }
            Text(
                text = mediaDetails.genreList.joinToString(", "),
                color = textColor
            )
        }
    }

    // Tagline and overview
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Dimens.padding.medium, vertical = Dimens.padding.medium)
    ) {

        if (mediaDetails.tagLine.isNotBlank()) {
            Text(
                modifier = Modifier.alpha(0.7f),
                text = mediaDetails.tagLine,
                color = textColor,
                fontStyle = FontStyle.Italic,
                fontSize = Dimens.textSize.title
            )
            Spacer(modifier = Modifier.height(Dimens.padding.small))
        }
        if (mediaDetails.overview.isNotBlank()) {
            Text(
                text = stringResource(id = R.string.text_overview),
                color = textColor,
                fontWeight = FontWeight(600),
                fontSize = 18.sp
            )
            Spacer(modifier = Modifier.height(Dimens.padding.small))
            Text(
                text = mediaDetails.overview,
                color = textColor
            )
        }

        // Creators
        if (mediaDetails.creatorList.isNotEmpty()) {

            Spacer(modifier = Modifier.height(Dimens.padding.medium))

            NestedVerticalLazyGrid(
                modifier = Modifier
                    .fillMaxWidth(),
                columns = 2,
                columnSeparation = 8.dp,
                itemList = mediaDetails.creatorList
            ) { item ->
                Column(
                    modifier = Modifier
                        .padding(vertical = Dimens.padding.small)
                        .clickable {
                            onItemClick(item.id)
                        }
                ) {
                    Text(
                        text = item.name,
                        color = textColor,
                        fontWeight = FontWeight.Bold
                    )

                    val job = when (item) {
                        is Person.Crew.Movie -> item.job
                        is Person.Crew.TvShow -> item.jobList.joinToString("/") { it.job }
                    }

                    Text(
                        text = job.ifBlank { stringResource(R.string.text_creator) },
                        color = textColor
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun MainInfoPreview() {

    MainInfo(
        modifier = Modifier
            .fillMaxHeight()
            .background(DetailBackgroundColor),
        mediaDetails = mediaDetailsMovieSample,
        onItemClick = {},
        textColor = DetailBackgroundColor.getOnBackgroundColor()
    )
}