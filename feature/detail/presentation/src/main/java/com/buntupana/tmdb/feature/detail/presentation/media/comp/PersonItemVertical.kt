package com.buntupana.tmdb.feature.detail.presentation.media.comp

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.buntupana.tmdb.core.ui.composables.ImagePersonFromUrl
import com.buntupana.tmdb.core.ui.theme.AppTheme
import com.buntupana.tmdb.core.ui.theme.Dimens
import com.buntupana.tmdb.feature.detail.domain.model.Person
import com.buntupana.tmdb.feature.detail.presentation.R
import com.buntupana.tmdb.feature.detail.presentation.castTvShowPersonSample

@Composable
fun PersonItemVertical(
    modifier: Modifier = Modifier,
    itemWidth: Dp = 120.dp,
    personCast: Person.Cast,
    onItemClick: ((personId: Long) -> Unit)? = null
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = Dimens.cardElevation),
        shape = RoundedCornerShape(Dimens.posterRound),
    ) {
        Box(
            modifier = Modifier
                .width(itemWidth)
                .clickable {
                    onItemClick?.invoke(personCast.id)
                },
        ) {
            Column {
                ImagePersonFromUrl(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(12f / 13.3f),
                    imageUrl = personCast.profileUrl,
                    gender = personCast.gender
                )


                val (character, episodeCount) = when (personCast) {
                    is Person.Cast.Movie -> {
                        personCast.character to 0
                    }

                    is Person.Cast.TvShow -> {
                        personCast.roleList.joinToString("/") { it.character } to personCast.totalEpisodeCount
                    }
                }

                Box {

                    NameAndCharacter(
                        modifier = Modifier.padding(Dimens.padding.small),
                        name = personCast.name,
                        character = character,
                        episodesCount = episodeCount
                    )
                }
            }
        }
    }
}

@Composable
private fun NameAndCharacter(
    modifier: Modifier = Modifier,
    name: String,
    character: String,
    episodesCount: Int
) {

    val height = MaterialTheme.typography.bodyMedium.lineHeight.value * 5

    Column(modifier = modifier.height(height.dp)) {

        var nameLinesCount by remember {
            mutableIntStateOf(2)
        }

        var characterMaxLines = when (nameLinesCount) {
            1 -> 3
            2 -> 2
            else -> 2
        }

        if (episodesCount == 0) characterMaxLines++

        Text(
            text = name,
            fontWeight = FontWeight.Bold,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            onTextLayout = {
                nameLinesCount = it.lineCount
            }
        )
        Text(
            text = character,
            maxLines = characterMaxLines,
            overflow = TextOverflow.Ellipsis
        )
        if (episodesCount > 0) {
            Text(
                modifier = Modifier.alpha(0.5f),
                text = pluralStringResource(
                    id = R.plurals.text_episodes_count,
                    count = episodesCount,
                    episodesCount
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PersonItemVerticalPreview() {
    AppTheme(darkTheme = true) {
        PersonItemVertical(
            personCast = castTvShowPersonSample
        )
    }
}