package com.buntupana.tmdb.feature.lists.presentation.lists.comp

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.buntupana.tmdb.core.ui.composables.ImageFromUrl
import com.buntupana.tmdb.core.ui.theme.Dimens
import com.buntupana.tmdb.core.ui.theme.PrimaryColor
import com.buntupana.tmdb.feature.lists.domain.model.MediaList
import com.buntupana.tmdb.feature.lists.presentation.util.listItemMediaLists
import com.buntupana.tmdb.feature.presentation.R
import com.panabuntu.tmdb.core.common.util.isNotNullOrBlank
import com.buntupana.tmdb.core.ui.R as RCore

@Composable
fun ListItemVertical(
    modifier: Modifier,
    mediaList: MediaList,
    onItemClick: (listId: Long, listName: String, description: String?, backdropUrl: String?) -> Unit,
) {
    var dominantColor by remember { mutableStateOf<Color?>(null) }


    Surface(
        modifier = modifier
            .padding(
                horizontal = Dimens.padding.horizontal,
                vertical = Dimens.padding.verticalItem
            )
            .clickable {
                onItemClick(mediaList.id, mediaList.name, mediaList.description, mediaList.backdropUrl)
            },
        shape = RoundedCornerShape(Dimens.posterRound),
        shadowElevation = Dimens.cardElevation
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16f / 10f)
            ) {

                var backgroundColor = PrimaryColor

                if (mediaList.backdropUrl.isNotNullOrBlank()) {
                    backgroundColor = backgroundColor.copy(alpha = 0.8f)
                    ImageFromUrl(
                        modifier = Modifier.fillMaxSize(),
                        imageUrl = mediaList.backdropUrl,
                        showPlaceHolder = true
                    ) { extractedColor ->
                        dominantColor = extractedColor
                    }
                }


                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(backgroundColor)
                )

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = Dimens.padding.horizontal),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {

                    BasicText(
                        text = mediaList.name,
                        autoSize = TextAutoSize.StepBased(minFontSize = 18.sp, stepSize = 10.sp),
                        maxLines = 3,
                        color = { Color.White },
                        style = MaterialTheme.typography.titleLarge.merge(textAlign = TextAlign.Center),
                        overflow = TextOverflow.Ellipsis
                    )

                    Row(
                        modifier = Modifier.padding(top = Dimens.padding.medium)
                    ) {
                        Text(
                            text = stringResource(
                                RCore.string.text_num_items,
                                mediaList.itemCount
                            ),
                            color = Color.White,
                            style = MaterialTheme.typography.bodyLarge
                        )

                        val listTypeResId = if (mediaList.isPublic) {
                            R.string.text_public
                        } else {
                            R.string.text_private
                        }

                        Text(
                            modifier = Modifier
                                .padding(start = Dimens.padding.medium)
                                .clip(RoundedCornerShape(Dimens.posterRound))
                                .background(Color.Gray)
                                .padding(horizontal = Dimens.padding.small),
                            text = stringResource(listTypeResId).uppercase(),
                            color = Color.White,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }

            if (mediaList.description.isNullOrBlank()) return@Column

            Text(
                modifier = Modifier.padding(
                    horizontal = Dimens.padding.horizontal,
                    Dimens.padding.vertical
                ),
                text = mediaList.description!!
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ListItemVerticalPreview() {
    ListItemVertical(
        modifier = Modifier,
        mediaList = listItemMediaLists.first().copy(name = "adsf asdf asdf asdf adf asf"),
        onItemClick = { _, _, _, _ -> }
    )
}