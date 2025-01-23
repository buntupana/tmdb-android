package com.buntupana.tmdb.core.ui.composables.item

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.buntupana.tmdb.core.ui.R
import com.buntupana.tmdb.core.ui.theme.Dimens
import com.buntupana.tmdb.core.ui.theme.FavoriteColor
import com.buntupana.tmdb.core.ui.theme.GrayColor


@Composable
fun MediaItemHorizontalWatchlist(
    modifier: Modifier = Modifier,
    mediaId: Long,
    title: String,
    posterUrl: String?,
    overview: String,
    releaseDate: String,
    rating: Int?,
    isFavorite: Boolean,
    onMediaClick: ((mediaId: Long, mainPosterColor: Color) -> Unit),
) {
    Surface(
        modifier = modifier
            .padding(
                horizontal = Dimens.padding.horizontal,
                vertical = Dimens.padding.verticalItem
            ),
        shape = RoundedCornerShape(Dimens.posterRound),
        shadowElevation = Dimens.cardElevation
    ) {
        Column {
            MediaItemHorizontalBase(
                modifier = modifier.height(Dimens.imageSize.posterHeight),
                onMediaClick = onMediaClick,
                mediaId = mediaId,
                title = title,
                posterUrl = posterUrl,
                overview = overview,
                releaseDate = releaseDate
            )
            HorizontalDivider()

            Row(
                modifier = Modifier
                    .padding(horizontal = Dimens.padding.small)
                    .padding(top = Dimens.padding.vertical)
            ) {
                Row(
                    modifier = Modifier
                        .weight(1f),
                ) {
                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(Dimens.posterRound))
                            .clickable { }
                            .padding(
                                vertical = Dimens.padding.tiny,
                                horizontal = Dimens.padding.horizontal
                            ),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Icon(
                            modifier = Modifier
                                .clip(CircleShape)
                                .border(1.dp, GrayColor, CircleShape)
                                .padding(5.dp),
                            painter = painterResource(R.drawable.ic_star),
                            contentDescription = null,
                            tint = GrayColor
                        )

                        Spacer(modifier = Modifier.width(Dimens.padding.small))
                        Text(
                            text = "Rate it!",
                            color = GrayColor
                        )
                    }
                }
                Row(
                    modifier = Modifier
                        .weight(1f),
                ) {
                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(Dimens.posterRound))
                            .clickable { }
                            .padding(
                                vertical = Dimens.padding.tiny,
                                horizontal = Dimens.padding.horizontal
                            ),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        if (isFavorite) {
                            Icon(
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .background(FavoriteColor, CircleShape)
                                    .padding(5.dp),
                                painter = painterResource(R.drawable.ic_favourite),
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.background
                            )
                        } else {
                            Icon(
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .border(1.dp, GrayColor, CircleShape)
                                    .padding(5.dp),
                                painter = painterResource(R.drawable.ic_favourite),
                                contentDescription = null,
                                tint = GrayColor
                            )
                        }

                        Spacer(modifier = Modifier.width(Dimens.padding.small))
                        Text(
                            text = "Favorite",
                            color = GrayColor
                        )
                    }
                }
            }
            Row(
                modifier = Modifier
                    .padding(horizontal = Dimens.padding.small)
                    .padding(vertical = Dimens.padding.vertical)
            ) {
                Row(
                    modifier = Modifier
                        .weight(1f),
                ) {
                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(Dimens.posterRound))
                            .clickable { }
                            .padding(
                                vertical = Dimens.padding.tiny,
                                horizontal = Dimens.padding.horizontal
                            ),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Icon(
                            modifier = Modifier
                                .clip(CircleShape)
                                .border(1.dp, GrayColor, CircleShape)
                                .padding(5.dp),
                            painter = painterResource(R.drawable.ic_list),
                            contentDescription = null,
                            tint = GrayColor
                        )

                        Spacer(modifier = Modifier.width(Dimens.padding.small))
                        Text(
                            text = "Add to list",
                            color = GrayColor
                        )
                    }
                }
                Row(
                    modifier = Modifier
                        .weight(1f),
                ) {
                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(Dimens.posterRound))
                            .clickable { }
                            .padding(
                                vertical = Dimens.padding.tiny,
                                horizontal = Dimens.padding.horizontal
                            ),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            modifier = Modifier
                                .clip(CircleShape)
                                .border(1.dp, GrayColor, CircleShape)
                                .padding(5.dp),
                            painter = painterResource(R.drawable.ic_cancel),
                            contentDescription = null,
                            tint = GrayColor
                        )

                        Spacer(modifier = Modifier.width(Dimens.padding.small))
                        Text(
                            text = "Remove",
                            color = GrayColor
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MediaItemHorizontalWatchlistPreview() {
    MediaItemHorizontalWatchlist(
        modifier = Modifier
            .fillMaxWidth(),
        mediaId = 0L,
        title = "Thor: Love and Thunder",
        posterUrl = null,
        overview = "After his retirement is interrupted by Gorr the God Butcher, a galactic killer who seeks the extinction of the gods, Thor enlists the help of King",
        releaseDate = "10-11-20",
        rating = null,
        isFavorite = true,
        onMediaClick = { _, _ -> }
    )
}