package com.buntupana.tmdb.feature.search.presentation.comp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.buntupana.tmdb.core.presentation.theme.Dimens
import com.buntupana.tmdb.core.presentation.theme.PrimaryColor
import com.buntupana.tmdb.feature.search.presentation.R
import com.buntupana.tmdb.core.R as RCore

@Composable
fun SuggestionItem(
    mediaItem: com.buntupana.tmdb.feature.search.domain.model.SearchItem,
    showItemIcon: Boolean = false,
    clickable: (mediaItem: com.buntupana.tmdb.feature.search.domain.model.SearchItem) -> Unit
) {
    val focusManager = LocalFocusManager.current

    val suggestionIconResId: Int
    val auxResId: Int

    if (showItemIcon) {
        when (mediaItem) {
            is com.buntupana.tmdb.feature.search.domain.model.SearchItem.Movie -> {
                suggestionIconResId = R.drawable.ic_movie
                auxResId = R.string.text_in_movies
            }

            is com.buntupana.tmdb.feature.search.domain.model.SearchItem.Person -> {
                suggestionIconResId = R.drawable.ic_person
                auxResId = R.string.text_in_person
            }

            is com.buntupana.tmdb.feature.search.domain.model.SearchItem.TvShow -> {
                suggestionIconResId = R.drawable.ic_tv_show
                auxResId = R.string.text_in_tv_shows
            }
        }
    } else {
        suggestionIconResId = RCore.drawable.ic_search
        auxResId = 0
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
            .clickable {
                focusManager.clearFocus()
                clickable(mediaItem)
            }
    ) {
        Row(
            modifier = Modifier
                .padding(
                    horizontal = Dimens.padding.medium,
                    vertical = Dimens.padding.small
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier.size(30.dp),
                painter = painterResource(id = suggestionIconResId),
                contentDescription = null,
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground)
            )
            Spacer(modifier = Modifier.width(Dimens.padding.small))
            if (showItemIcon) {

                Text(
                    text = buildAnnotatedString {
                        withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                            append(mediaItem.name)
                        }
                        append(" ")
                        append(stringResource(auxResId))
                    },
                    fontSize = MaterialTheme.typography.titleMedium.fontSize
                )
            } else {
                Text(
                    text = mediaItem.name,
                    fontSize = MaterialTheme.typography.titleMedium.fontSize
                )
            }
        }
        HorizontalDivider(
            color = PrimaryColor,
        )
    }
}

@Preview
@Composable
fun SuggestionItemPreview() {
    SuggestionItem(
        mediaItem = com.buntupana.tmdb.feature.search.domain.model.SearchItem.Movie(
            id = 0,
            name = "Terminator",
            originalName = null,
            imageUrl = null,
            originalLanguage = null,
            popularity = 0f,
            voteAverage = 0,
            voteCount = 0
        ),
        showItemIcon = true,
        clickable = {}
    )
}