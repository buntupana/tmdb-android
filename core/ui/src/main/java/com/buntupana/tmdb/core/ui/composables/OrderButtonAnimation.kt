package com.buntupana.tmdb.core.ui.composables

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
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import com.buntupana.tmdb.core.ui.theme.Dimens
import com.buntupana.tmdb.core.ui.util.getOnBackgroundColor
import com.panabuntu.tmdb.core.common.model.Order

@Composable
fun OrderButtonAnimation(
    modifier: Modifier = Modifier,
    textColor: Color = Color.Black,
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

    TextButton(
        modifier = modifier,
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

@Preview
@Composable
private fun OrderButtonAnimationPreview() {

    OrderButtonAnimation(
        modifier = Modifier.background(Color.Black),
        textColor = Color.Black.getOnBackgroundColor(),
        text = "Last Added",
        order = Order.DESC,
        onClick = {}
    )
}