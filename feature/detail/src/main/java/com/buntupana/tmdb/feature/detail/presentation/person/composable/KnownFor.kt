package com.buntupana.tmdb.feature.detail.presentation.person.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.buntupana.tmdb.core.domain.entity.MediaType
import com.buntupana.tmdb.core.presentation.composables.ImageFromUrl
import com.buntupana.tmdb.core.presentation.spToDp
import com.buntupana.tmdb.core.presentation.theme.Dimens
import com.buntupana.tmdb.feature.detail.R
import com.buntupana.tmdb.feature.detail.domain.model.CreditPersonItem

@Composable
fun KnownFor(
    itemList: List<CreditPersonItem>,
    onItemClick: (id: Long, mediaType: MediaType) -> Unit
) {

    if (itemList.isEmpty()) {
        return
    }

    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Dimens.padding.medium),
        text = stringResource(id = R.string.text_known_for),
        style = MaterialTheme.typography.titleLarge
    )

    Spacer(modifier = Modifier.height(Dimens.padding.medium))

    LazyRow(
        modifier = Modifier.fillMaxWidth()
    ) {

        item {
            Spacer(modifier = Modifier.width(Dimens.padding.horizontal))
        }

        items(itemList.size) { index ->

            val item = itemList[index]

            Column(
                modifier = Modifier
                    .width(Dimens.carouselMediaItemWidth)
                    .clip(RoundedCornerShape(Dimens.posterRound))
                    .clickable {
                        when (item) {
                            is CreditPersonItem.Movie -> MediaType.MOVIE
                            is CreditPersonItem.TvShow -> MediaType.TV_SHOW
                        }.let { mediaType ->
                            onItemClick(item.id, mediaType)
                        }
                    }
            ) {
                ImageFromUrl(
                    modifier = Modifier
                        .clip(RoundedCornerShape(Dimens.posterRound))
                        .fillMaxWidth()
                        .aspectRatio(Dimens.aspectRatioMediaPoster),
                    imageUrl = item.posterUrl,
                )
                var nameExtraLinesCount by remember {
                    mutableStateOf(0)
                }
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp),
                    text = item.title,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    onTextLayout = {
                        if (it.lineCount < 2) {
                            nameExtraLinesCount = 2 - it.lineCount
                        }
                    }
                )
                // Height we need to fill the view
                val lineHeight =
                    MaterialTheme.typography.bodyLarge.lineHeight * nameExtraLinesCount
                Spacer(
                    modifier = Modifier.height(spToDp(lineHeight))
                )
            }
            if (index < itemList.size - 1) {
                Spacer(modifier = Modifier.width(Dimens.padding.small))
            }
        }

        item {
            Spacer(modifier = Modifier.width(Dimens.padding.horizontal))
        }
    }
}