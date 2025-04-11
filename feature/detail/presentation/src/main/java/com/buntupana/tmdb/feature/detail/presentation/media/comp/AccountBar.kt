package com.buntupana.tmdb.feature.detail.presentation.media.comp

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.buntupana.tmdb.core.ui.theme.Dimens
import com.buntupana.tmdb.core.ui.theme.FavoriteColor
import com.buntupana.tmdb.core.ui.theme.RatingColor
import com.buntupana.tmdb.core.ui.theme.WatchListColor
import com.buntupana.tmdb.core.ui.util.IconButton
import com.buntupana.tmdb.core.ui.util.getOnBackgroundColor
import com.panabuntu.tmdb.core.common.util.Const.ANIM_DURATION
import com.buntupana.tmdb.core.ui.R as RCore

@Composable
fun AccountBar(
    modifier: Modifier = Modifier,
    iconColor: Color = MaterialTheme.colorScheme.onBackground,
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

    val favoriteTint = getColor(
        isActive = isFavorite,
        activeColor = FavoriteColor,
        inactiveColor = iconColor
    )

    val animateFavoriteColor by animateColorAsState(
        targetValue = favoriteTint,
        animationSpec = tween(durationMillis = ANIM_DURATION)
    )

    val watchListTint = getColor(
        isActive = isWatchListed,
        activeColor = WatchListColor,
        inactiveColor = iconColor
    )

    val animateWatchlistColor by animateColorAsState(
        targetValue = watchListTint,
        animationSpec = tween(durationMillis = ANIM_DURATION)
    )

    val ratingTint =
        getColor(
            isActive = userRating != null,
            activeColor = RatingColor,
            inactiveColor = iconColor
        )

    val animateRatingColor by animateColorAsState(
        targetValue = ratingTint,
        animationSpec = tween(durationMillis = ANIM_DURATION)
    )

    Row(
        modifier = modifier
            .clickable(enabled = false) {}
            .padding(Dimens.padding.medium),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.Center
        ) {

            IconButton(
                onClick = onListClick,
                rippleColor = iconColor,
            ) {
                Icon(
                    modifier = Modifier,
                    painter = painterResource(RCore.drawable.ic_list),
                    contentDescription = null,
                    tint = iconColor
                )
            }
        }

        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.Center
        ) {
            IconButton(
                rippleColor = iconColor,
                onClick = onFavoriteClick,
                enabled = isFavoriteLoading.not()
            ) {
                Icon(
                    modifier = Modifier,
                    painter = painterResource(RCore.drawable.ic_favourite),
                    contentDescription = null,
                    tint = animateFavoriteColor
                )
            }
        }
        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.Center
        ) {
            IconButton(
                rippleColor = iconColor,
                onClick = onWatchlistClick,
                enabled = isWatchlistLoading.not()
            ) {
                Icon(
                    modifier = Modifier,
                    painter = painterResource(RCore.drawable.ic_watchlist),
                    contentDescription = null,
                    tint = animateWatchlistColor
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
                IconButton(
                    rippleColor = iconColor,
                    onClick = onRatingClick,
                    enabled = isRatingLoading.not()
                ) {
                    Icon(
                        painter = painterResource(RCore.drawable.ic_star_solid),
                        contentDescription = null,
                        tint = animateRatingColor
                    )
                }
            }
        }
    }
}

private fun getColor(isActive: Boolean, activeColor: Color, inactiveColor: Color): Color {
    return if (isActive) {
        activeColor
    } else {
        inactiveColor
    }
}

@Preview(showBackground = true)
@Composable
fun AccountBarPreview() {

    var isFavorite by remember { mutableStateOf(true) }
    var isWatchListed by remember { mutableStateOf(true) }
    var userRating by remember { mutableStateOf<Int?>(20) }

    val backgroundColor = Color.Gray

    AccountBar(
        modifier = Modifier
            .fillMaxWidth()
            .background(backgroundColor)
        ,
        iconColor = backgroundColor.getOnBackgroundColor(),
        isFavorite = isFavorite,
        isWatchListed = isWatchListed,
        userRating = userRating,
        isRateable = true,
        isFavoriteLoading = false,
        isWatchlistLoading = false,
        isRatingLoading = false,
        onListClick = {},
        onFavoriteClick = {
            isFavorite = isFavorite.not()
        },
        onWatchlistClick = {
            isWatchListed = isWatchListed.not()
        },
        onRatingClick = {
            userRating = if (userRating == null) 20  else null
        }
    )
}