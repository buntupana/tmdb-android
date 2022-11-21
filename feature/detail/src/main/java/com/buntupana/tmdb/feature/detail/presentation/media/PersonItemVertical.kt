package com.buntupana.tmdb.feature.detail.presentation.media

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.buntupana.tmdb.core.presentation.spToDp
import com.buntupana.tmdb.core.presentation.theme.Dimens

private const val MAX_NAME_LINES = 2
private const val MAX_CHARACTER_LINES = 2

@Composable
fun PersonItemVertical(
    itemWidth: Dp = 120.dp,
    personId: Long,
    name: String,
    profileUrl: String,
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
                AsyncImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(120f / 133f),
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(profileUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = null
                )
                Column(modifier = Modifier.padding(Dimens.padding.small)) {
                    var nameExtraLinesCount by remember {
                        mutableStateOf(0)
                    }
                    var characterExtraLinesCount by remember {
                        mutableStateOf(0)
                    }
                    var nameMaxLines by remember {
                        mutableStateOf(2)
                    }
                    var characterMaxLines by remember {
                        mutableStateOf(2)
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
        profileUrl = "",
        character = "Jane Foster / The Mighty Thor"
    )
}