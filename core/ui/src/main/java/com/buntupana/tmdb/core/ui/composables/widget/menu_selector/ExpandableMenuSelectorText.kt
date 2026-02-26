package com.buntupana.tmdb.core.ui.composables.widget.menu_selector

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.buntupana.tmdb.core.ui.R
import com.buntupana.tmdb.core.ui.theme.AppTheme
import com.buntupana.tmdb.core.ui.theme.StaticColor
import com.buntupana.tmdb.core.ui.util.brush

@Composable
fun SelectedText(
    modifier: Modifier = Modifier,
    text: String = "",
    isCollapsed: Boolean = false,
    isSelected: Boolean = false,
    onClick: (() -> Unit) = {},
) {

    if (isSelected) {
        Row(
            modifier = modifier
                .clip(RoundedCornerShape(ROUNDED_CORNER_RADIUS))
                .background(MaterialTheme.colorScheme.primaryContainer)
                .clickable { onClick() }
                .padding(horizontal = 16.dp, vertical = 6.dp)
                .brush(
                    Brush.horizontalGradient(
                        listOf(
                            StaticColor.expandableMenuSelectorLight,
                            StaticColor.expandableMenuSelectorDark
                        )
                    )
                ),
            verticalAlignment = Alignment.CenterVertically,
        ) {

            val textModifier = if (isCollapsed.not()) {
                Modifier.padding(horizontal = 8.dp)
            } else Modifier

            Text(
                modifier = textModifier,
                text = text,
                fontWeight = FontWeight.SemiBold,
                maxLines = 1,
                overflow = TextOverflow.Visible,
                textAlign = TextAlign.Center
            )

            if (isCollapsed) {
                Image(
                    modifier = Modifier
                        .padding(start = 2.dp)
                        .size(16.dp),
                    painter = painterResource(id = R.drawable.ic_arrow_right),
                    contentDescription = "Arrow Right",
                    alignment = Alignment.Center
                )
            }

        }
    } else {
        Text(
            text = text,
            modifier = modifier
                .clip(RoundedCornerShape(ROUNDED_CORNER_RADIUS))
                .clickable {
                    onClick()
                }
                .defaultMinSize(minWidth = MIN_UNSELECTED_ITEM_WIDTH)
                .padding(horizontal = 12.dp, vertical = 6.dp),
            fontWeight = FontWeight.SemiBold,
            maxLines = 1,
            overflow = TextOverflow.Visible,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.primaryContainer
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
fun SelectedTextPreview() {
    AppTheme {
        SelectedText(
            text = "Button",
            isSelected = true,
            isCollapsed = true
        )
    }
}