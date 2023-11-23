package com.buntupana.tmdb.core.presentation.theme

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

object Dimens {

    val icon = 24.dp

    val carouselMediaItemWidth = 120.dp
    val posterRound = 5.dp
    val cardElevation = 4.dp

    val padding: Padding = PaddingPhone
    val textSize: TextSize = TextPhone

    const val aspectRatioMediaPoster = 2f / 3f
    const val aspectRatioMediaBackdrop = 20f / 9f

    // Top bar
    val topBarHeight = 58.dp
    val topBarIconSize = 60.dp

    val imageSize = ImagePhone
}

object PaddingPhone : Padding {
    override val tiny = 4.dp
    override val small = 8.dp
    override val medium = 16.dp
    override val big = 24.dp
    override val huge = 32.dp
    override val betweenTexts = tiny
    override val horizontal = medium
    override val vertical = medium
    override val verticalItem = small
}

object TextPhone : TextSize {
    override val title = 20.sp
    override val body = 14.sp
}

object ImagePhone : ImageSize {
    override val personHeightSmall = 80.dp
    override val personHeight = 100.dp
    override val personHeightBig = 160.dp
    override val posterHeightSmall = 120.dp
    override val posterHeight = 120.dp

}

interface Padding {
    val tiny: Dp
    val small: Dp
    val medium: Dp
    val big: Dp
    val huge: Dp
    val horizontal: Dp
    val vertical: Dp
    val verticalItem: Dp
    val betweenTexts: Dp
}

interface TextSize {
    val title: TextUnit
    val body: TextUnit
}

interface ImageSize {
    val personHeightSmall: Dp
    val personHeight: Dp
    val personHeightBig: Dp
    val posterHeightSmall: Dp
    val posterHeight: Dp
}