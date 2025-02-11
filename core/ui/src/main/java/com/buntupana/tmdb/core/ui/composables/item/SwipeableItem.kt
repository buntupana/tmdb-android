package com.buntupana.tmdb.core.ui.composables.item

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.buntupana.tmdb.core.ui.theme.Dimens
import com.buntupana.tmdb.core.ui.util.mediaItemMovie
import kotlinx.coroutines.launch
import timber.log.Timber
import kotlin.math.abs
import kotlin.math.roundToInt

enum class ActionsAlign {
    START, END
}

@Composable
fun SwipeableItem(
    modifier: Modifier = Modifier,
    actionsAlign: ActionsAlign = ActionsAlign.START,
    isRevealed: Boolean = false,
    onExpanded: () -> Unit = {},
    onCollapsed: () -> Unit = {},
    actions: @Composable (revealPercent: Float) -> Unit,
    content: @Composable () -> Unit
) {

    var contextMenuWidth by remember { mutableFloatStateOf(0f) }

    Timber.d("SwipeableItem: menuWidth: = $contextMenuWidth")

    val offset = remember {
        Animatable(initialValue = 0f)
    }

    Timber.d("SwipeableItem: offset: = ${offset.value}")

    val scope = rememberCoroutineScope()

    LaunchedEffect(isRevealed, contextMenuWidth) {

        if (isRevealed) {
            when(actionsAlign) {
                ActionsAlign.START -> offset.animateTo(contextMenuWidth)
                ActionsAlign.END -> offset.animateTo(-contextMenuWidth)
            }
        } else {
            offset.animateTo(0f)
        }
    }

    Box(
        modifier = modifier.height(IntrinsicSize.Min)
    ) {

        val contentAlignment = when (actionsAlign) {
            ActionsAlign.START -> Alignment.CenterStart
            ActionsAlign.END -> Alignment.CenterEnd
        }

        Box(
            modifier = Modifier
                .fillMaxHeight()
                .align(contentAlignment)
                .onSizeChanged {
                    contextMenuWidth = it.width.toFloat()
                },
        ) {
            actions(abs( offset.value / contextMenuWidth))
        }

        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .offset {
                    IntOffset(offset.value.roundToInt(), 0)
                }
                .pointerInput(contextMenuWidth) {
                    detectHorizontalDragGestures(
                        onHorizontalDrag = { _, dragAmount ->

                            var newOffset = offset.value + dragAmount

                            newOffset = when (actionsAlign) {
                                ActionsAlign.START -> newOffset.coerceIn(0f, contextMenuWidth)
                                ActionsAlign.END -> newOffset.coerceIn(-contextMenuWidth, 0f)
                            }

                            scope.launch {
                                offset.snapTo(newOffset)
                            }
                        },
                        onDragEnd = {

                            if (abs(offset.value) >= contextMenuWidth / 2) {
                                scope.launch {
                                    when (actionsAlign) {
                                        ActionsAlign.START -> offset.animateTo(contextMenuWidth)
                                        ActionsAlign.END -> offset.animateTo(-contextMenuWidth)
                                    }
                                    onExpanded()
                                }
                            } else {
                                scope.launch {
                                    offset.animateTo(0f)
                                    onCollapsed()
                                }
                            }
                        }
                    )
                },
            color = Color.Transparent
        ) {
            content()
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SwipeableItemPreview() {
    SwipeableItem(
        modifier = Modifier
            .fillMaxWidth(),
        isRevealed = true,
        actionsAlign = ActionsAlign.END,
        actions = {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(end = Dimens.padding.horizontal)
                    .padding(vertical = Dimens.padding.verticalItem)
                    .clip(
                        RoundedCornerShape(
                            topEnd = Dimens.posterRound,
                            bottomEnd = Dimens.posterRound
                        )
                    )
                    .background(MaterialTheme.colorScheme.error)
                    .padding(horizontal = 50.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Rounded.Delete,
                    contentDescription = null,
                    tint = Color.White
                )
            }
        },
        content = {
            MediaItemHorizontal(
                modifier = Modifier,
                mediaId = 0,
                title = mediaItemMovie.name,
                posterUrl = mediaItemMovie.posterUrl,
                overview = mediaItemMovie.overview,
                releaseDate = mediaItemMovie.releaseDate,
                onMediaClick = { _, _ ->
                }
            )
        }
    )
}