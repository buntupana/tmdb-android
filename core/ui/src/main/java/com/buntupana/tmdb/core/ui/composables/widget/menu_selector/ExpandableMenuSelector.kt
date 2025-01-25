package com.buntupana.tmdb.core.ui.composables.widget.menu_selector

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.buntupana.tmdb.core.ui.R
import com.buntupana.tmdb.core.ui.theme.PrimaryColor
import com.buntupana.tmdb.core.ui.theme.TertiaryDarkColor
import com.buntupana.tmdb.core.ui.theme.TertiaryLightColor
import com.buntupana.tmdb.core.ui.util.dpToPx
import com.buntupana.tmdb.core.ui.util.pxToDp


@Composable
fun <T : ExpandableMenuSelectorItem> ExpandableMenuSelector(
    modifier: Modifier = Modifier,
    menuItemSet: Set<T> = emptySet(),
    onItemClick: ((item: T, index: Int) -> Unit) = { _, _ -> },
    defaultIndexSelected: Int = 0,
    defaultCollapsed: Boolean = true,
    horizontalPadding: Dp = 0.dp,
    menuAlign: ExpandableMenuSelectorAlign = ExpandableMenuSelectorAlign.START
) {

    var selectedIndex by remember {
        mutableIntStateOf(defaultIndexSelected)
    }

    val textWidths = remember { mutableStateListOf(*List(menuItemSet.size) { 0 }.toTypedArray()) }

    var isCollapsed by remember { mutableStateOf(defaultCollapsed) }

    val scrollState = rememberScrollState()

    Box(
        modifier = modifier
            .horizontalScroll(scrollState)
            .padding(
                start = if (menuAlign == ExpandableMenuSelectorAlign.START) horizontalPadding else  0.dp,
                end = if (menuAlign == ExpandableMenuSelectorAlign.END) horizontalPadding else 0.dp
            ),
        contentAlignment = if (menuAlign == ExpandableMenuSelectorAlign.END) Alignment.CenterEnd else Alignment.CenterStart
    ) {

        val backGroundCollapsedWidth = if (textWidths[selectedIndex] == 0) {
            MIN_UNSELECTED_ITEM_WIDTH.dpToPx().toInt()
        } else {
            textWidths[selectedIndex]
        }
        val backGroundExpandedWidth = textWidths.sum()


        val offsetXBackground by animateIntAsState(
            targetValue = if (isCollapsed) backGroundCollapsedWidth else backGroundExpandedWidth,
            animationSpec = tween(durationMillis = ANIMATION_DURATION),
            label = "collapsingBackground"
        )

        val paddedModifier = if (menuAlign == ExpandableMenuSelectorAlign.END) {
            Modifier.padding(start = 32.dp)
        } else {
            Modifier.padding(end = 32.dp)
        }

        Box(
            modifier = paddedModifier
                .width(width = offsetXBackground.pxToDp())
                .clip(RoundedCornerShape(ROUNDED_CORNER_RADIUS))
                .border(BorderStroke(2.dp, PrimaryColor), RoundedCornerShape(ROUNDED_CORNER_RADIUS))
                .background(
                    brush = Brush.horizontalGradient(
                        listOf(
                            TertiaryLightColor,
                            TertiaryDarkColor
                        )
                    )
                )
        ) {
            Text(
                modifier = Modifier
                    .padding(vertical = 6.dp),
                text = ""
            )
        }

        menuItemSet.forEachIndexed { index, item ->

            val isSelected = index == selectedIndex
            val transitionXValue = textWidths.subList(0, index).sum().toFloat()

            val offsetX by animateFloatAsState(
                targetValue = if (isCollapsed) 0f else transitionXValue,
                animationSpec = tween(durationMillis = ANIMATION_DURATION),
                label = "offsetX"
            )

            val scaling by animateFloatAsState(
                targetValue = if (isCollapsed) 0f else 1f,
                animationSpec = tween(durationMillis = ANIMATION_DURATION),
                label = "fading"
            )

            val textWidth = textWidths[index]

            val widthAnimation by animateIntAsState(
                targetValue = if (isCollapsed) textWidth else 0,
                animationSpec = tween(durationMillis = ANIMATION_DURATION),
                label = "width"
            )

            val auxModifier = if (isSelected.not()) {
                Modifier
                    .width(widthAnimation.pxToDp())
                Modifier
            } else {
                Modifier
            }

            SelectedText(
                text = stringResource(item.strRes),
                isSelected = isSelected,
                modifier = auxModifier
                    .zIndex(if (isSelected) 1f else 0f)
                    .onGloballyPositioned {
                        textWidths[index] = it.size.width
                    }
                    .graphicsLayer {
                        translationX = if (menuAlign == ExpandableMenuSelectorAlign.END) -offsetX else offsetX
                        if (isSelected.not()) {
                            scaleX = scaling
                        }
                    },
                isCollapsed = isCollapsed,
                onClick = {
                    selectedIndex = index
                    if (isCollapsed.not() && isSelected.not()) {
                        onItemClick(item, index)
                    }
                    isCollapsed = isCollapsed.not()
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ExpandableMenuSelectorPreview() {
    ExpandableMenuSelector(
        menuItemSet = ExpandableMenuSelectorItemSample.entries.toSet(),
        menuAlign = ExpandableMenuSelectorAlign.START,
        defaultCollapsed = true
    )
}

enum class ExpandableMenuSelectorItemSample : ExpandableMenuSelectorItem {
    STREAMING{
        override val strRes: Int = R.string.text_menu_selector_sample_streaming
    },
    ON_TV{
        override val strRes: Int = R.string.text_menu_selector_sample_on_tv
    },
    FOR_RENT{
        override val strRes: Int = R.string.text_menu_selector_sample_for_rent
    },
    IN_THEATRES{
        override val strRes: Int = R.string.text_menu_selector_sample_in_theatres
    }
}