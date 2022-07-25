package com.buntupana.tmdb.core.presentation.theme

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

object Dimens {
    val carouselMediaItemWidth = 120.dp
    val posterRound = 5.dp

    val padding: Padding = PaddingPhone
    val textSize: TextSize = TextPhone
}

object PaddingPhone : Padding {
    override val small = 8.dp
    override val medium = 16.dp
    override val big = 24.dp
}

object TextPhone: TextSize {
    override val title = 20.sp
    override val body = 12.sp
}

interface Padding {
    val small: Dp
    val medium: Dp
    val big: Dp
}

interface TextSize {
    val title: TextUnit
    val body: TextUnit
}