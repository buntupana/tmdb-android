package com.buntupana.tmdb.core.ui.util

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.browser.customtabs.CustomTabColorSchemeParams
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.core.view.WindowCompat
import com.buntupana.tmdb.core.ui.R
import com.buntupana.tmdb.core.ui.theme.Dimens
import com.buntupana.tmdb.core.ui.theme.PrimaryColor
import com.panabuntu.tmdb.core.common.model.Gender

//@Composable
fun getCustomTabIntent(url: String): Intent {
    val customTabsIntent = CustomTabsIntent.Builder()
        .setDefaultColorSchemeParams(
            CustomTabColorSchemeParams.Builder()
                .setToolbarColor(PrimaryColor.toArgb())
                .build()
        )
        .setShowTitle(true)
        .build()

    customTabsIntent.intent.data = Uri.parse(url)

    return customTabsIntent.intent
}

@Composable
fun Gender.getString(): String {
    return when (this) {
        Gender.NOT_SPECIFIED -> stringResource(id = R.string.text_gender_not_specified)
        Gender.FEMALE -> stringResource(id = R.string.text_gender_female)
        Gender.MALE -> stringResource(id = R.string.text_gender_male)
        Gender.NON_BINARY -> stringResource(id = R.string.text_gender_no_binary)
    }
}

fun Modifier.clickableIcon(): Modifier {
    return padding(Dimens.padding.small).size(Dimens.icon)
}

fun Modifier.clickableTextPadding(): Modifier {
    return padding(horizontal = Dimens.padding.medium, vertical = Dimens.padding.small)
}

@Composable
fun Modifier.setStatusNavigationBarColor(
    backgroundColor: Color = PrimaryColor
): Modifier {
    val view = LocalView.current
    val window = (view.context as Activity).window
    val isLightStatus = backgroundColor.getOnBackgroundColor() != Color.White
    WindowCompat.getInsetsController(
        window,
        view
    ).isAppearanceLightStatusBars = isLightStatus

    WindowCompat.getInsetsController(
        window,
        view
    ).isAppearanceLightNavigationBars = isLightStatus

    return fillMaxSize()
        .background(backgroundColor)
        .safeDrawingPadding()
        .background(MaterialTheme.colorScheme.background)
}

@Composable
fun Int.pxToDp() = with(LocalDensity.current) { this@pxToDp.toDp() }

@Composable
fun Dp.dpToPx() = with(LocalDensity.current) { this@dpToPx.toPx() }

@Composable
fun dpToSp(dp: Dp) = with(LocalDensity.current) { dp.toSp() }

@Composable
fun spToDp(sp: TextUnit) = with(LocalDensity.current) { sp.toDp() }

