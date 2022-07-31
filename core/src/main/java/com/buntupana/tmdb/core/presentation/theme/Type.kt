package com.buntupana.tmdb.core.presentation.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.buntupana.tmdb.core.R

val SourceSansProFont = FontFamily(
    Font(R.font.source_sans_pro_regular, FontWeight.Normal),
    Font(R.font.source_sans_pro_bold, FontWeight.Bold),
    Font(R.font.source_sans_pro_light, FontWeight.Light),
    Font(R.font.source_sans_pro_black, FontWeight.Black),
    Font(R.font.source_sans_pro_extra_light, FontWeight.ExtraLight),
    Font(R.font.source_sans_pro_semibold, FontWeight.SemiBold),
    // Italic
    Font(R.font.source_sans_pro_it, FontWeight.Normal, FontStyle.Italic),
    Font(R.font.source_sans_pro_bold_it, FontWeight.Bold, FontStyle.Italic),
    Font(R.font.source_sans_pro_light_it, FontWeight.Light, FontStyle.Italic),
    Font(R.font.source_sans_pro_black_it, FontWeight.Black, FontStyle.Italic),
    Font(R.font.source_sans_pro_extra_light_it, FontWeight.ExtraLight, FontStyle.Italic),
    Font(R.font.source_sans_pro_semibold_it, FontWeight.SemiBold, FontStyle.Italic)
)

val HkFontFamily = FontFamily(
    Font(R.font.hk_super_round_bold)
)

// Set of Material typography styles to start with
val Typography = Typography(
    bodyMedium = TextStyle(
        fontFamily = SourceSansProFont,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp,
    ),
    bodyLarge = TextStyle(
        fontFamily = SourceSansProFont,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    titleLarge = TextStyle(
        fontFamily = SourceSansProFont,
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = SourceSansProFont,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
)
