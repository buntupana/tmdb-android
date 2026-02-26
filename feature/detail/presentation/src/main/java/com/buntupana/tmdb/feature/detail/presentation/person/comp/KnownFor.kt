package com.buntupana.tmdb.feature.detail.presentation.person.comp

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.buntupana.tmdb.core.ui.composables.ImageFromUrl
import com.buntupana.tmdb.core.ui.theme.AppTheme
import com.buntupana.tmdb.core.ui.theme.Dimens
import com.buntupana.tmdb.core.ui.util.toDp
import com.buntupana.tmdb.feature.detail.domain.model.CreditPersonItem
import com.buntupana.tmdb.feature.detail.presentation.R
import com.buntupana.tmdb.feature.detail.presentation.creditItemPersonSample
import com.panabuntu.tmdb.core.common.entity.MediaType

@Composable
fun KnownFor(
    modifier: Modifier = Modifier,
    itemList: List<CreditPersonItem>,
    onItemClick: (id: Long, mediaType: MediaType, dominantColor: Color?) -> Unit
) {

    if (itemList.isEmpty()) {
        return
    }

    Column(
        modifier = modifier
    ) {

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Dimens.padding.medium),
            text = stringResource(id = R.string.detail_known_for),
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

                var dominantColoAux: Color? by remember { mutableStateOf(null) }

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
                                onItemClick(item.id, mediaType, dominantColoAux)
                            }
                        }
                ) {
                    ImageFromUrl(
                        modifier = Modifier
                            .clip(RoundedCornerShape(Dimens.posterRound))
                            .fillMaxWidth()
                            .aspectRatio(Dimens.aspectRatioMediaPoster),
                        imageUrl = item.posterUrl,
                    ) { dominantColor ->
                        dominantColoAux = dominantColor
                    }
                    var nameExtraLinesCount by remember {
                        mutableIntStateOf(0)
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
                        modifier = Modifier.height(toDp(lineHeight))
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
}

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    name = "DefaultPreviewLight",
    showBackground = true,
)
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "DefaultPreviewDark",
    showBackground = true,
)
@Composable
private fun KnownForPreview() {
    AppTheme {
        KnownFor(
            itemList = listOf(creditItemPersonSample, creditItemPersonSample, creditItemPersonSample),
            onItemClick = {_,_,_ ->}
        )
    }
}