package com.buntupana.tmdb.feature.detail.presentation.media.comp

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.buntupana.tmdb.core.ui.theme.Dimens
import com.buntupana.tmdb.core.ui.theme.FavoriteColor
import com.buntupana.tmdb.core.ui.theme.RatingColor
import com.buntupana.tmdb.core.ui.theme.WatchListColor
import com.buntupana.tmdb.core.ui.util.getOnBackgroundColor
import com.buntupana.tmdb.core.ui.R as RCore

@Composable
fun AccountBar(
    modifier: Modifier = Modifier,
    backgroundColor: Color,
    isFavorite: Boolean,
    isWatchListed: Boolean,
    userRating: Int?,
    isRateable: Boolean,
    isFavoriteLoading: Boolean,
    isWatchlistLoading: Boolean,
    isRatingLoading: Boolean,
    onListClick: () -> Unit,
    onFavoriteClick: () -> Unit,
    onWatchlistClick: () -> Unit,
    onRatingClick: () -> Unit
) {
    Row(
        modifier = modifier
            .background(backgroundColor)
            .padding(Dimens.padding.medium)
    ) {

        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.Center
        ) {

            IconButton(
                onClick = onListClick
            ) {
                Icon(
                    modifier = Modifier,
                    painter = painterResource(RCore.drawable.ic_list),
                    contentDescription = null,
                    tint = backgroundColor.getOnBackgroundColor()
                )
            }
        }

        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.Center
        ) {
            val favoriteTint = if (isFavorite) {
                FavoriteColor
            } else {
                backgroundColor.getOnBackgroundColor()
            }
            IconButton(
                onClick = onFavoriteClick,
                enabled = isFavoriteLoading.not()
            ) {
                Icon(
                    modifier = Modifier,
                    painter = painterResource(RCore.drawable.ic_favourite),
                    contentDescription = null,
                    tint = favoriteTint
                )
            }
        }
        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.Center
        ) {
            val watchListTint = if (isWatchListed) {
                WatchListColor
            } else {
                backgroundColor.getOnBackgroundColor()
            }

            IconButton(
                onClick = onWatchlistClick,
                enabled = isWatchlistLoading.not()
            ) {
                Icon(
                    modifier = Modifier,
                    painter = painterResource(RCore.drawable.ic_watchlist),
                    contentDescription = null,
                    tint = watchListTint
                )
            }
        }

        if (isRateable) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .animateContentSize(),
                contentAlignment = Alignment.Center
            ) {

                val rateTint = if (userRating != null) {
                    RatingColor
                } else {
                    backgroundColor.getOnBackgroundColor()
                }

                if (userRating != null) {
                    TextButton(
                        onClick = onRatingClick,
                        enabled = isRatingLoading.not()
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Icon(
                                modifier = Modifier,
                                painter = painterResource(RCore.drawable.ic_star_solid),
                                contentDescription = null,
                                tint = rateTint
                            )

                            Text(
                                modifier = Modifier.padding(start = Dimens.padding.tiny),
                                text = "$userRating%",
                                color = rateTint
                            )
                        }
                    }
                } else {

                    IconButton(
                        onClick = onRatingClick,
                        enabled = isRatingLoading.not()
                    ) {
                        Icon(
                            modifier = Modifier,
                            painter = painterResource(RCore.drawable.ic_star_solid),
                            contentDescription = null,
                            tint = rateTint
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AccountBarPreview() {
    AccountBar(
        modifier = Modifier.fillMaxWidth(),
        backgroundColor = Color.Blue,
        isFavorite = true,
        isWatchListed = false,
        userRating = 20,
        isRateable = true,
        isFavoriteLoading = false,
        isWatchlistLoading = false,
        isRatingLoading = false,
        onListClick = {},
        onFavoriteClick = {},
        onRatingClick = {},
        onWatchlistClick = {}
    )
}