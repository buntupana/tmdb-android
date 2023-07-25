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

    const val aspectRatioMediaPoster = 2f/3f

    // Top bar
    val topBarHeight = 58.dp
    val topBarIconSize = 60.dp
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
}

object TextPhone: TextSize {
    override val title = 20.sp
    override val body = 12.sp
}

interface Padding {
    val tiny: Dp
    val small: Dp
    val medium: Dp
    val big: Dp
    val huge: Dp
    val horizontal: Dp
    val vertical: Dp
    val betweenTexts: Dp
}

interface TextSize {
    val title: TextUnit
    val body: TextUnit
}