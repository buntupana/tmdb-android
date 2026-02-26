package com.buntupana.tmdb.core.ui.composables.widget

import android.content.res.Configuration
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.LocalRippleConfiguration
import androidx.compose.material3.RippleConfiguration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.buntupana.tmdb.core.ui.R
import com.buntupana.tmdb.core.ui.theme.Dimens
import com.buntupana.tmdb.core.ui.util.getOnBackgroundColor

@Composable
fun AppIconButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    colors: IconButtonColors = IconButtonDefaults.iconButtonColors(),
    rippleColor: Color = colors.contentColor.getOnBackgroundColor().getOnBackgroundColor(),
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource? = null,
    content: @Composable () -> Unit
) {
    val configuration = RippleConfiguration(color = rippleColor)

    CompositionLocalProvider(
        LocalRippleConfiguration provides configuration
    ) {
        IconButton(
            onClick = onClick,
            modifier = modifier,
            enabled = enabled,
            colors = colors,
            interactionSource = interactionSource,
            content = content
        )
    }
}

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    name = "DefaultPreviewLight",
    showBackground = true
)
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "DefaultPreviewDark",
    showBackground = true
)
@Composable
private fun AppIconButtonPreview() {
    AppIconButton(
        modifier = Modifier,
        onClick = {},
        enabled = true,
        colors = IconButtonDefaults.iconButtonColors(),
        content = {
            Icon(
                modifier = Modifier
                    .padding(horizontal = Dimens.padding.small),
                painter = painterResource(R.drawable.ic_search),
                contentDescription = "Search",
            )
        }
    )
}