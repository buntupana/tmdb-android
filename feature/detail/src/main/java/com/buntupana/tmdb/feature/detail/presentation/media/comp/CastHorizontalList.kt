package com.buntupana.tmdb.feature.detail.presentation.media.comp

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.buntupana.tmdb.core.presentation.theme.Dimens
import com.buntupana.tmdb.feature.detail.R
import com.buntupana.tmdb.feature.detail.domain.model.MediaDetails
import com.buntupana.tmdb.feature.detail.domain.model.Person
import com.buntupana.tmdb.feature.detail.presentation.mediaDetailsMovieSample

@Composable
fun CastHorizontalList(
    modifier: Modifier,
    mediaDetails: MediaDetails,
    onItemClick: (personId: Long) -> Unit,
    onFullCastClick: () -> Unit
) {

    val castNumber = 9

    if (mediaDetails.castList.isNotEmpty()) {

        Column(modifier = modifier) {

            Spacer(modifier = Modifier.height(16.dp))

            val castTitle = when (mediaDetails) {
                is MediaDetails.Movie -> stringResource(id = R.string.text_cast_movie)
                is MediaDetails.TvShow -> stringResource(id = R.string.text_cast_tv_show)
            }

            Text(
                modifier = Modifier.padding(horizontal = Dimens.padding.medium),
                text = castTitle,
                fontSize = Dimens.textSize.title,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            LazyRow(
                modifier = Modifier.fillMaxWidth()
            ) {
                item {
                    Spacer(modifier = Modifier.width(Dimens.padding.verticalItem))
                }
                items(mediaDetails.castList.take(castNumber)) { item: Person.Cast ->
                    Spacer(modifier = Modifier.width(Dimens.padding.small))
                    PersonItemVertical(
                        personId = item.id,
                        name = item.name,
                        gender = item.gender,
                        profileUrl = item.profileUrl,
                        character = item.character,
                        onItemClick = onItemClick
                    )
                    Spacer(modifier = Modifier.width(Dimens.padding.small))
                }
                item {
                    Spacer(modifier = Modifier.width(Dimens.padding.vertical))
                }
            }

            Spacer(modifier = Modifier.padding(Dimens.padding.medium))

            Text(
                modifier = Modifier
                    .padding(horizontal = Dimens.padding.medium)
                    .clickable { onFullCastClick() },
                text = stringResource(id = R.string.text_full_cast),
                fontSize = Dimens.textSize.title,
            )

            Spacer(modifier = Modifier.height(Dimens.padding.vertical))
        }
    }
}

@Preview
@Composable
fun CastHorizontalListPreview() {
    CastHorizontalList(
        modifier = Modifier,
        mediaDetails = mediaDetailsMovieSample,
        onFullCastClick = {},
        onItemClick = {}
    )
}

