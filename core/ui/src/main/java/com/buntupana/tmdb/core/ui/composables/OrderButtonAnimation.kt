package com.buntupana.tmdb.core.ui.composables

import android.content.res.Configuration
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.repeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowDownward
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.buntupana.tmdb.core.ui.composables.widget.AppTextButton
import com.buntupana.tmdb.core.ui.theme.AppTheme
import com.buntupana.tmdb.core.ui.theme.Dimens
import com.buntupana.tmdb.core.ui.util.getOnBackgroundColor
import com.panabuntu.tmdb.core.common.model.Order

@Composable
fun OrderButtonAnimation(
    modifier: Modifier = Modifier,
    textColor: Color = MaterialTheme.colorScheme.background.getOnBackgroundColor(),
    text: String,
    onClick: () -> Unit,
    order: Order
) {

    var initialOrder by remember { mutableStateOf(order) }
    val angle by animateFloatAsState(
        targetValue = if (initialOrder == Order.ASC) 180f else 0f,
        animationSpec = repeatable(
            animation = tween(300, easing = LinearEasing),
            iterations = 1
        )
    )

    AppTextButton(
        modifier = modifier,
        rippleColor = textColor,
        onClick = {
            initialOrder = when (initialOrder) {
                Order.ASC -> Order.DESC
                Order.DESC -> Order.ASC
            }
            onClick()
        }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = text,
                color = textColor
            )

            Icon(
                modifier = Modifier
                    .padding(start = Dimens.padding.tiny)
                    .rotate(angle),
                imageVector = Icons.Rounded.ArrowDownward,
                contentDescription = null,
                tint = textColor
            )
        }
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
private fun OrderButtonAnimationPreview() {
    AppTheme {
        OrderButtonAnimation(
            modifier = Modifier.background(MaterialTheme.colorScheme.background),
            textColor = MaterialTheme.colorScheme.background.getOnBackgroundColor(),
            text = "Last Added",
            order = Order.DESC,
            onClick = {}
        )
    }
}