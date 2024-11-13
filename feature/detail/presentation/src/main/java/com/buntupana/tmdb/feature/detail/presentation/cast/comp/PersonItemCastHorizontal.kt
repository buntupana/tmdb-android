package com.buntupana.tmdb.feature.detail.presentation.cast.comp

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import com.buntupana.tmdb.core.ui.composables.ImagePersonFromUrl
import com.buntupana.tmdb.core.ui.theme.Dimens
import com.buntupana.tmdb.feature.detail.domain.model.Person
import com.buntupana.tmdb.feature.detail.presentation.R
import com.buntupana.tmdb.feature.detail.presentation.castTvShowPersonSample

@Composable
fun PersonItemCastHorizontal(
    modifier: Modifier = Modifier,
    person: Person,
    onClick: (personId: Long) -> Unit
) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick(person.id) }
            .padding(horizontal = Dimens.padding.horizontal, vertical = Dimens.padding.verticalItem)
            .height(IntrinsicSize.Min)
    ) {

        ImagePersonFromUrl(
            modifier = Modifier
                .size(Dimens.imageSize.personHeightSmall)
                .clip(RoundedCornerShape(Dimens.posterRound)),
            imageUrl = person.profileUrl,
            gender = person.gender
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(start = Dimens.padding.small),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = person.name,
                fontWeight = FontWeight.Bold,
                fontSize = MaterialTheme.typography.titleMedium.fontSize,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            when (person) {
                is Person.Cast.Movie -> MovieCredits(description = person.character)
                is Person.Crew.Movie -> MovieCredits(description = person.job)
                is Person.Cast.TvShow -> TvShowCredits(
                    roleEpisodeCountPairList = person.roleList.map {
                        Pair(
                            it.character,
                            it.episodeCount
                        )
                    })

                is Person.Crew.TvShow -> TvShowCredits(
                    roleEpisodeCountPairList = person.jobList.map {
                        Pair(
                            it.job,
                            it.episodeCount
                        )
                    })
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun TvShowCredits(
    modifier: Modifier = Modifier,
    roleEpisodeCountPairList: List<Pair<String, Int>>
) {

    Text(
        buildAnnotatedString {

            roleEpisodeCountPairList.forEachIndexed { index, pair ->
                append("${pair.first} ")
                withStyle(
                    style = SpanStyle(
                        brush = SolidColor(MaterialTheme.colorScheme.onBackground),
                        alpha = 0.5f
                    )
                ) {
                    append(
                        "(${
                            pluralStringResource(
                                id = R.plurals.text_episodes_count,
                                count = pair.second,
                                pair.second
                            )
                        })"
                    )
                }
                if (index + 1 < roleEpisodeCountPairList.size) {
                    append(", ")
                }
            }
        },
        modifier = modifier
    )
}

@Composable
private fun MovieCredits(
    description: String
) {
    Text(
        text = description
    )
}

@Preview
@Composable
fun PersonItemHorizontalPreview() {
    PersonItemCastHorizontal(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxWidth(),
        person = castTvShowPersonSample,
        onClick = {}
    )
}
