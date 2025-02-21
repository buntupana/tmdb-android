package com.buntupana.tmdb.core.ui.util

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.text.Spanned
import androidx.annotation.StringRes
import androidx.browser.customtabs.CustomTabColorSchemeParams
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.LocalRippleConfiguration
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RippleConfiguration
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.fromHtml
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.DialogWindowProvider
import androidx.core.text.HtmlCompat.TO_HTML_PARAGRAPH_LINES_INDIVIDUAL
import androidx.core.text.htmlEncode
import androidx.core.text.toHtml
import androidx.core.view.WindowCompat
import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.DialogNavigator
import androidx.navigation.compose.DialogNavigatorDestinationBuilder
import androidx.navigation.get
import com.buntupana.tmdb.core.ui.R
import com.buntupana.tmdb.core.ui.theme.Dimens
import com.buntupana.tmdb.core.ui.theme.PrimaryColor
import com.panabuntu.tmdb.core.common.model.Gender
import kotlin.reflect.KType

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

fun Modifier.clickableTextPadding(): Modifier {
    return padding(horizontal = Dimens.padding.medium, vertical = Dimens.padding.small)
}

@Composable
fun Modifier.setStatusNavigationBarColor(
    backgroundColor: Color = PrimaryColor
): Modifier {

    val view = LocalView.current
    val isLightStatus = backgroundColor.getOnBackgroundColor() != Color.White

    (view.context as? Activity)?.run {
        WindowCompat.getInsetsController(
            window,
            view
        ).run {
            isAppearanceLightStatusBars = isLightStatus
//            isAppearanceLightNavigationBars = isLightStatus
        }
    }

    return fillMaxSize()
        .background(backgroundColor)
        .safeDrawingPadding()
        .background(MaterialTheme.colorScheme.background)
}

fun Modifier.isVisible(isVisible: Boolean, animateSize: Boolean = false): Modifier {

    val (alpha, modifier) = if (isVisible) {
        1f to Modifier
    } else {
        0f to Modifier.size(0.dp)
    }
    return alpha(alpha)
        .then(
            if (animateSize) Modifier.animateContentSize() else Modifier
        )
        .then(
            modifier
        )
}

fun Modifier.isInvisible(isInvisible: Boolean): Modifier {
    return alpha(if (isInvisible) 0f else 1f)
}

@Composable
fun Modifier.fadeIn(enabled: Boolean, durationMillis: Int = 400): Modifier {

    var triggerAnimation by remember { mutableStateOf(enabled) }

    val alpha by animateFloatAsState(
        targetValue = if (triggerAnimation) 0f else 1f,
        animationSpec = tween(durationMillis = durationMillis)
    )

    triggerAnimation = false

    return alpha(alpha)
}

@Composable
fun Int.toDp() = with(LocalDensity.current) { this@toDp.toDp() }

@Composable
fun Float.toDp() = with(LocalDensity.current) { this@toDp.toDp() }

@Composable
fun Dp.dpToPx() = with(LocalDensity.current) { this@dpToPx.toPx() }

@Composable
fun dpToSp(dp: Dp) = with(LocalDensity.current) { dp.toSp() }

@Composable
fun spToDp(sp: TextUnit) = with(LocalDensity.current) { sp.toDp() }

@Composable
@ReadOnlyComposable
fun annotatedStringResource(
    @StringRes id: Int,
): AnnotatedString {
    val text = LocalContext.current.resources.getText(id)
    val html = if (text is Spanned) {
        text.toHtml(TO_HTML_PARAGRAPH_LINES_INDIVIDUAL)
    } else {
        text.toString()
    }
    return AnnotatedString.fromHtml(html)
}

@Composable
@ReadOnlyComposable
fun annotatedStringResource(
    @StringRes id: Int,
    vararg formatArgs: Any,
): AnnotatedString {
    val text = LocalContext.current.resources.getText(id)
    val html = if (text is Spanned) {
        text.toHtml(TO_HTML_PARAGRAPH_LINES_INDIVIDUAL)
    } else {
        text.toString()
    }
    val encodedArgs = formatArgs.map { if (it is String) it.htmlEncode() else it }.toTypedArray()
    return AnnotatedString.fromHtml(html.format(*encodedArgs))
}


inline fun <reified T : Any> NavGraphBuilder.bottomSheet(
    typeMap: Map<KType, @JvmSuppressWildcards NavType<*>> = emptyMap(),
    deepLinks: List<NavDeepLink> = emptyList(),
    dialogProperties: DialogProperties = DialogProperties(),
    noinline content: @Composable () -> Unit
) {
    destination(
        DialogNavigatorDestinationBuilder(
            provider[DialogNavigator::class],
            T::class,
            typeMap,
            dialogProperties,
            {
                (LocalView.current.parent as DialogWindowProvider).window.setDimAmount(0f)
                content()
            }
        )
            .apply { deepLinks.forEach { deepLink -> deepLink(deepLink) } }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IconButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    rippleColor: Color= MaterialTheme.colorScheme.background,
    enabled: Boolean = true,
    colors: IconButtonColors = IconButtonDefaults.iconButtonColors(),
    interactionSource: MutableInteractionSource? = null,
    content: @Composable () -> Unit
) {
    val configuration = RippleConfiguration(color = rippleColor)

    CompositionLocalProvider(
        LocalRippleConfiguration provides configuration
    ) {
        androidx.compose.material3.IconButton(
            onClick = onClick,
            modifier = modifier,
            enabled = enabled,
            colors = colors,
            interactionSource = interactionSource,
            content = content
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    rippleColor: Color = MaterialTheme.colorScheme.background,
    enabled: Boolean = true,
    shape: Shape = ButtonDefaults.textShape,
    colors: ButtonColors = ButtonDefaults.textButtonColors(),
    elevation: ButtonElevation? = null,
    border: BorderStroke? = null,
    contentPadding: PaddingValues = ButtonDefaults.TextButtonContentPadding,
    interactionSource: MutableInteractionSource? = null,
    content: @Composable RowScope.() -> Unit
) {
    val configuration = RippleConfiguration(color = rippleColor)

    CompositionLocalProvider(
        LocalRippleConfiguration provides configuration
    ) {
        androidx.compose.material3.TextButton(
            onClick = onClick,
            modifier = modifier,
            enabled = enabled,
            shape = shape,
            colors = colors,
            elevation = elevation,
            border = border,
            contentPadding = contentPadding,
            interactionSource = interactionSource,
            content = content
        )
    }
}

@Composable
fun Modifier.clickableWithRipple(
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    color: Color = MaterialTheme.colorScheme.background,
    bounded: Boolean = true,
    enabled: Boolean = true,
    onClickLabel: String? = null,
    role: Role? = null,
    onClick: () -> Unit
): Modifier {
    return this.clickable(
        interactionSource = interactionSource,
        indication = ripple(
            color = color,
            bounded = bounded
        ),
        enabled = enabled,
        onClickLabel = onClickLabel,
        role = role,
        onClick = onClick
    )
}

