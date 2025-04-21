package com.buntupana.tmdb.feature.detail.presentation.media.comp

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.buntupana.tmdb.core.ui.composables.ShowMoreButton
import com.buntupana.tmdb.core.ui.theme.Dimens
import com.buntupana.tmdb.feature.detail.domain.model.MediaDetails
import com.buntupana.tmdb.feature.detail.domain.model.Person
import com.buntupana.tmdb.feature.detail.presentation.R
import com.buntupana.tmdb.feature.detail.presentation.mediaDetailsMovieSample

private const val CAST_NUMBER = 9

@Composable
fun CastHorizontalList(
    modifier: Modifier,
    itemWidth: Dp = 120.dp,
    mediaDetails: MediaDetails,
    onItemClick: (personId: Long) -> Unit,
    onFullCastClick: () -> Unit
) {

    if (mediaDetails.castList.isEmpty()) return

    Column(modifier = modifier.animateContentSize()) {

        Spacer(modifier = Modifier.height(16.dp))

        val castTitle = when (mediaDetails) {
            is MediaDetails.Movie -> stringResource(id = R.string.text_cast_movie)
            is MediaDetails.TvShow -> stringResource(id = R.string.text_cast_tv_show)
        }

        Text(
            modifier = Modifier.padding(horizontal = Dimens.padding.medium),
            text = castTitle,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(Dimens.padding.medium))

        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            item {
                Spacer(modifier = Modifier.width(Dimens.padding.verticalItem))
            }

            items(mediaDetails.castList.take(CAST_NUMBER)) { item: Person.Cast ->

                Spacer(modifier = Modifier.width(Dimens.padding.small))

                PersonItemVertical(
                    itemWidth = itemWidth,
                    personCast = item,
                    onItemClick = onItemClick
                )

                Spacer(modifier = Modifier.width(Dimens.padding.small))
            }

            item {
                Spacer(modifier = Modifier.padding(horizontal = Dimens.padding.tiny))

                ShowMoreButton(
                    modifier = Modifier.padding(bottom = itemWidth),
                    onClick = onFullCastClick
                )

                Spacer(modifier = Modifier.padding(horizontal = Dimens.padding.small))
            }
        }

        Spacer(modifier = Modifier.padding(Dimens.padding.small))

        TextButton(
            onClick = onFullCastClick
        ) {
            Text(
                modifier = Modifier
                    .padding(horizontal = Dimens.padding.small),
                text = stringResource(id = R.string.text_full_cast),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        Spacer(modifier = Modifier.height(Dimens.padding.small))
    }
}

@Preview(showBackground = true)
@Composable
fun CastHorizontalListPreview() {
    CastHorizontalList(
        modifier = Modifier,
        mediaDetails = mediaDetailsMovieSample,
        onFullCastClick = {},
        onItemClick = {}
    )
}

