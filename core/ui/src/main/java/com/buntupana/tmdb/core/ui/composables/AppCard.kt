package com.buntupana.tmdb.core.ui.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.buntupana.tmdb.core.ui.theme.AppTheme
import com.buntupana.tmdb.core.ui.theme.Dimens

/**
 * AppCard is a thin wrapper around Material3 Card that centralizes the default colors
 * (and a few other defaults) so the app can change the default Card appearance from
 * a single place (useful for applying the same color to all "news" cards, etc.).
 *
 * To change the default container color for all AppCard usages, edit the `colors`
 * default below (e.g., use MaterialTheme.colorScheme.surface, surfaceVariant, or a
 * custom token).
 */
@Composable
fun AppCard(
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(Dimens.posterRound),
    colors: CardColors = CardDefaults.cardColors(
        containerColor = MaterialTheme.colorScheme.surfaceContainer
    ),
    elevation: CardElevation = CardDefaults.cardElevation(defaultElevation = Dimens.cardElevation),
    border: BorderStroke? = null,
    enabled: Boolean = true,
    onClick: (() -> Unit)? = null,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable ColumnScope.() -> Unit,
) {
    if (onClick != null) {
        Card(
            modifier = modifier,
            shape = shape,
            colors = colors,
            elevation = elevation,
            border = border,
            enabled = enabled,
            onClick = onClick,
            interactionSource = interactionSource,
            content = content
        )
    } else {
        Card(
            modifier = modifier,
            shape = shape,
            colors = colors,
            elevation = elevation,
            border = border,
            content = content
        )
    }
}

@Preview
@Composable
fun AppCardPreviewLight() {
    AppTheme(darkTheme = false) {
        AppCard(
            modifier = Modifier
                .padding(16.dp)
                .size(200.dp)
        ) {
            Text(text = "This is an AppCard")
        }
    }
}

@Preview
@Composable
fun AppCardPreviewDark() {
    AppTheme(darkTheme = true) {
        AppCard(
            modifier = Modifier
                .padding(16.dp)
                .size(200.dp)
        ) {
            Text(text = "This is an AppCard")
        }
    }
}


