package com.buntupana.tmdb.core.presentation.composables.widget.menu_selector

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.ExperimentalMotionApi
import androidx.constraintlayout.compose.MotionLayout
import com.buntupana.tmdb.core.presentation.brush
import com.buntupana.tmdb.core.presentation.theme.PrimaryDark
import com.buntupana.tmdb.core.presentation.theme.TertiaryDark
import com.buntupana.tmdb.core.presentation.theme.TertiaryLight
import com.buntupana.tmdb.core.presentation.useNonBreakingSpace
import kotlinx.coroutines.launch

@OptIn(ExperimentalMotionApi::class)
@Composable
fun <T : MenuSelectorItem> MenuSelector(
    menuItemSet: Set<T> = emptySet(),
    onItemClick: ((item: T, index: Int) -> Unit)? = null,
    indexSelected: Int = 0,
    expanded: Boolean = false
) {

    val coroutineScope = rememberCoroutineScope()

    var _selectedIndex by remember {
        mutableStateOf(if (indexSelected >= menuItemSet.size || indexSelected < 0) 0 else indexSelected)
    }

    var isExpanded by remember { mutableStateOf(expanded) }
    val animationProgress by animateFloatAsState(
        targetValue = if (isExpanded) 1f else 0f,
        animationSpec = tween(500)
    )

    val constraintSetStart = getConstraintSetStar(menuItemSet, _selectedIndex)
    val constraintSetEnd = getConstraintSetEnd(menuItemSet)

    val scrollState = rememberScrollState()

    MotionLayout(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(
                state = scrollState,
                enabled = isExpanded,
                reverseScrolling = false
            ),
        start = ConstraintSet(constraintSetStart),
        end = ConstraintSet(constraintSetEnd),
        progress = animationProgress,
    ) {

        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(15.dp))
                .background(
                    Brush.horizontalGradient(
                        listOf(
                            TertiaryLight,
                            TertiaryDark
                        )
                    )
                )
                .border(2.dp, PrimaryDark, RoundedCornerShape(15.dp))
                .layoutId("box")
        )

        menuItemSet.forEachIndexed { index, menuItem ->
            if (index != _selectedIndex) {
                Text(
                    text = " ${stringResource(id = menuItem.strRes)} ".useNonBreakingSpace(),
                    color = PrimaryDark,
                    modifier = Modifier
                        .clip(RoundedCornerShape(15.dp))
                        .clickable {
                            coroutineScope.launch {
                                scrollState.animateScrollTo(0)
                            }
                            isExpanded = !isExpanded
                            _selectedIndex = index
                            onItemClick?.invoke(menuItem, index)
                        }
                        .padding(horizontal = 6.dp)
                        .layoutId("item_$index")
                        .defaultMinSize(minWidth = 50.dp),
                    maxLines = 1,
                    overflow = TextOverflow.Visible,
                    textAlign = TextAlign.Center
                )
            }
        }

        SelectedText(
            text = " ${stringResource(id = menuItemSet.elementAt(_selectedIndex).strRes)} ",
            modifier = Modifier.layoutId("item_$_selectedIndex"),
            isExpanded = isExpanded
        ) {
            coroutineScope.launch {
                scrollState.animateScrollTo(0)
            }
            if (isExpanded) {
                onItemClick?.invoke(menuItemSet.elementAt(_selectedIndex), _selectedIndex)
            }
            isExpanded = !isExpanded
        }
    }
}

@Composable
fun SelectedText(
    modifier: Modifier = Modifier,
    text: String = "",
    isExpanded: Boolean = false,
    onClick: (() -> Unit)? = null,
) {

    Row(
        modifier = modifier
            .clip(RoundedCornerShape(15.dp))
            .background(PrimaryDark)
            .padding(horizontal = 16.dp, vertical = 6.dp)
            .clickable {
                onClick?.invoke()
            },
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = text,
            modifier = Modifier
                .brush(Brush.horizontalGradient(listOf(TertiaryLight, TertiaryDark))),
            fontWeight = FontWeight.SemiBold,
            maxLines = 1
        )
        // TODO: add arrow when is not expanded
//        if (isExpanded.not()) {
//            Spacer(modifier = Modifier.width(8.dp))
//            Icon(
//                modifier = Modifier
//                    .rotate(-90f)
//                    .size(14.dp)
//                    .brush(Brush.horizontalGradient(listOf(TertiaryLight, TertiaryDark)))
//                    .layoutId("icon"),
//                painter = painterResource(id = R.drawable.ic_arrow_down),
//                contentDescription = null
//            )
//        } else {
//            Spacer(modifier = Modifier.width(0.dp))
//            Icon(
//                modifier = Modifier
//                    .rotate(-90f)
//                    .size(0.dp)
//                    .brush(Brush.horizontalGradient(listOf(TertiaryLight, TertiaryDark)))
//                    .layoutId("icon"),
//                painter = painterResource(id = R.drawable.ic_arrow_down),
//                contentDescription = null
//            )
//        }
    }
}


private fun <T : MenuSelectorItem> getConstraintSetStar(
    menuItemSet: Set<T>,
    indexSelected: Int
): String {

    var constraintSet = getBoxItem(menuItemSet.size)

    menuItemSet.forEachIndexed { index, _ ->
        constraintSet += getItemStart(index, indexSelected)
    }

    constraintSet = """
        {
         $constraintSet
        }
    """.trimIndent()

    return constraintSet
}

private fun <T : MenuSelectorItem> getConstraintSetEnd(
    menuItemSet: Set<T>
): String {

    var constraintSet = getBoxItem(menuItemSet.size)

    menuItemSet.forEachIndexed { index, _ ->
        constraintSet += getItemEnd(index, menuItemSet.size == index + 1)
    }

    constraintSet = """
        {
         $constraintSet
        }
    """.trimIndent()

    return constraintSet
}

private fun getBoxItem(setSize: Int): String {
    return """
              box: {
                width: 'spread',
                height: 'spread',
                start: ['item_0', 'start'],
                end: ['item_${setSize - 1}', 'end'],
                top: ['parent', 'top'],
                bottom: ['parent', 'bottom']
              },
              icon: {
                width: '0'
              },
    """.trimIndent()
}

private fun getItemStart(itemIndex: Int, selectedIndex: Int): String {

    val startConstraint = if (itemIndex == selectedIndex) {
        """
            end: ['parent', 'end']
        """.trimIndent()
    } else {
        """
            width: 0,
            start: ['item_$selectedIndex', 'start'],
            end: ['item_$selectedIndex', 'end']
        """.trimIndent()
    }

    return """
              item_$itemIndex: {
                $startConstraint
                top: ['parent', 'top'],
                bottom: ['parent', 'bottom']
              },
    """.trimIndent()
}

private fun getItemEnd(itemIndex: Int, isLastItem: Boolean): String {

    val endConstraint = if (isLastItem) "parent" else "item_${itemIndex + 1}"
    val endConstraintValue = if (isLastItem) "end" else "start"

    return """
  
      item_$itemIndex: {
        end: ['$endConstraint', '$endConstraintValue'],
        top: ['parent', 'top'],
        bottom: ['parent', 'bottom']
      },
    """.trimIndent()
}

private fun getTransition(selectedIndex: Int, setSize: Int): String {

    var keyAttributes = ""

    repeat(setSize) { index ->
        if (index != selectedIndex) {
            keyAttributes += """
                 {
                    target: ['item_$index'],
                    frames: [0, 95],
                    scaleX: [0, 1]
                  },
                  
            """.trimIndent()
        }
    }

    return """
        {
              from: 'start',
              to: 'end',
              duration: 3000,
              KeyFrames: {
                KeyAttributes: [
                  $keyAttributes
                ]
              },
        }
    """.trimIndent()
}