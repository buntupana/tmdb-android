package com.buntupana.tmdb.feature.discover.presentation

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.ExperimentalMotionApi
import androidx.constraintlayout.compose.MotionLayout
import androidx.constraintlayout.compose.Transition
import com.buntupana.tmdb.core.presentation.theme.PrimaryDark
import com.buntupana.tmdb.core.presentation.theme.TertiaryDark
import com.buntupana.tmdb.core.presentation.theme.TertiaryLight
import com.buntupana.tmdb.feature.discover.R

@OptIn(ExperimentalMotionApi::class)
@Composable
fun <T : DropMenuItem> Selector(
    menuItemList: List<T> = emptyList(),
    indexSelected: Int = 0
) {

    val context = LocalContext.current

    val motionSceneJson = remember {
        context.resources.openRawResource(R.raw.filter_scene).readBytes().decodeToString()
    }

    var _indexSelected by remember {
        mutableStateOf(if (indexSelected >= menuItemList.size || indexSelected < 0) 0 else indexSelected)
    }

    var animateToEnd by remember { mutableStateOf(false) }
    val progress by animateFloatAsState(
        targetValue = if (animateToEnd) 1f else 0f,
        animationSpec = tween(500)
    )

    var resultStart = getBoxItemStart(menuItemList.size)
    var resultEnd = getBoxItemEnd(menuItemList.size, _indexSelected)

    menuItemList.forEachIndexed { index, menuItem ->
        resultStart += getItemStart(index, _indexSelected)
        resultEnd += getItemEnd(index, menuItemList.size == index + 1)
    }

    resultStart = """
        {
         $resultStart
        }
    """.trimIndent()

    resultEnd = """
        {
         $resultEnd
        }
    """.trimIndent()

    val resultTransition = getTransition(_indexSelected, menuItemList.size)


    MotionLayout(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        start = ConstraintSet(resultStart),
        end = ConstraintSet(resultEnd),
        transition = Transition(resultTransition),
//        motionScene = MotionScene(motionSceneJson),
        progress = progress,
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

        menuItemList.forEachIndexed { index, menuItem ->
            if (index != _indexSelected) {
                Text(
                    text = stringResource(id = menuItem.strRes),
                    color = PrimaryDark,
                    modifier = Modifier
                        .clip(RoundedCornerShape(15.dp))
                        .padding(horizontal = 6.dp)
                        .clickable {
                            _indexSelected = index
                            animateToEnd = !animateToEnd
                        }
                        .layoutId("item_$index")
                )
            }
        }

        SelectedText(
            text = stringResource(id = menuItemList[_indexSelected].strRes),
            modifier = Modifier.layoutId("item_$_indexSelected"),
        ) {
            animateToEnd = !animateToEnd
        }
    }
}

private fun getBoxItemEnd(listSize: Int, selectedIndex: Int): String {
    return """
              box: {
                width: 'spread',
                height: '40',
                start: ['item_0', 'start'],
                end: ['item_${listSize - 1}', 'end'],
                top: ['item_$selectedIndex', 'top'],
                bottom: ['item_$selectedIndex', 'bottom']
              },
    """.trimIndent()
}

private fun getBoxItemStart(selectedIndex: Int): String {
    return """
              box: {
                width: 'spread',
                height: 'spread',
                start: ['item_$selectedIndex', 'start', -40],
                end: ['item_${selectedIndex}', 'end'],
                top: ['item_$selectedIndex', 'top'],
                bottom: ['item_$selectedIndex', 'bottom']
              },
    """.trimIndent()
}

private fun getItemStart(itemIndex: Int, selectedIndex: Int): String {

    val constraint: String
    val startConstraint: String
    if (itemIndex == selectedIndex) {
        constraint = "parent"
        startConstraint = ""
    } else {
        constraint = "item_$selectedIndex"
//        startConstraint = "start: ['$constraint', start],"
        startConstraint = ""
    }

    return """
              item_$itemIndex: {
                $startConstraint
                end: ['$constraint', 'end'],
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

private fun getTransition(selectedIndex: Int, listSize: Int): String {


    var keyAttributes = ""

    repeat(listSize) { index ->
        if (index != selectedIndex) {
            keyAttributes += """
                 {
                    target: ['item_$index'],
                    frames: [0, 20],
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