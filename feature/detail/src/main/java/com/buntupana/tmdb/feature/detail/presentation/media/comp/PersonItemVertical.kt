package com.buntupana.tmdb.feature.detail.presentation.media.comp

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.buntupana.tmdb.core.domain.model.Gender
import com.buntupana.tmdb.core.presentation.composables.ImagePersonFromUrl
import com.buntupana.tmdb.core.presentation.spToDp
import com.buntupana.tmdb.core.presentation.theme.Dimens

private const val MAX_NAME_LINES = 2
private const val MAX_CHARACTER_LINES = 2

@Composable
fun PersonItemVertical(
    itemWidth: Dp = 120.dp,
    personId: Long,
    name: String,
    gender: Gender,
    profileUrl: String?,
    character: String,
    onItemClick: ((personId: Long) -> Unit)? = null
) {
    Surface(
        shadowElevation = Dimens.cardElevation,
        shape = RoundedCornerShape(Dimens.posterRound),
    ) {
        Box(
            modifier = Modifier
                .width(itemWidth)
                .clickable {
                    onItemClick?.invoke(personId)
                },
        ) {
            Column {
                ImagePersonFromUrl(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(120f / 133f),
                    imageUrl = profileUrl,
                    gender = gender
                )
                Column(modifier = Modifier.padding(Dimens.padding.small)) {
                    var nameExtraLinesCount by remember {
                        mutableIntStateOf(0)
                    }
                    var characterExtraLinesCount by remember {
                        mutableIntStateOf(0)
                    }
                    var nameMaxLines by remember {
                        mutableIntStateOf(2)
                    }
                    var characterMaxLines by remember {
                        mutableIntStateOf(2)
                    }
                    Text(
                        text = name,
                        fontWeight = FontWeight.Bold,
                        maxLines = nameMaxLines,
                        overflow = TextOverflow.Ellipsis,
                        onTextLayout = {
                            characterMaxLines = MAX_CHARACTER_LINES + MAX_NAME_LINES - it.lineCount
                            nameExtraLinesCount = MAX_NAME_LINES - it.lineCount
                        }
                    )
                    Text(
                        text = character,
                        maxLines = characterMaxLines,
                        overflow = TextOverflow.Ellipsis,
                        onTextLayout = {
                            nameMaxLines = MAX_NAME_LINES + MAX_CHARACTER_LINES - it.lineCount
                            characterExtraLinesCount = MAX_CHARACTER_LINES - it.lineCount
                        }
                    )
                    // Height we need to fill the view
                    val lineHeight =
                        MaterialTheme.typography.bodyLarge.lineHeight * (nameExtraLinesCount + characterExtraLinesCount)
                    Spacer(
                        modifier = Modifier.height(spToDp(lineHeight))
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PersonItemVerticalPreview() {
    PersonItemVertical(
        personId = 0L,
        name = "Natalie Portman",
        gender = Gender.FEMALE,
        profileUrl = "",
        character = "Jane Foster / The Mighty Thor"
    )
}