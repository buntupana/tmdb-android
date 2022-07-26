package com.buntupana.tmdb.core.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.ceil
import kotlin.math.roundToInt

@Composable
fun <T> NestedVerticalLazyGrid(
    modifier: Modifier = Modifier,
    columns: Int = 1,
    columnSeparation: Dp = 0.dp,
    rowSeparation: Dp = 0.dp,
    itemList: List<T> = emptyList(),
    itemContent: @Composable BoxScope.(T) -> Unit,
) {

    if (itemList.isNotEmpty()) {
        val rowsCount = ceil((itemList.size.toDouble() / columns.toDouble())).roundToInt()
        Column(modifier = modifier) {
            repeat(rowsCount) { rowIndex ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    repeat(columns) { columnIndex ->
                        val itemIndex = rowIndex * columns + columnIndex
                        if (itemIndex < itemList.size) {
                            Box(modifier = Modifier.weight(1f)) {
                                itemContent(itemList[itemIndex])
                            }
                            if(columnIndex < columns - 1) {
                                Spacer(modifier = Modifier.width(columnSeparation))
                            }
                        } else {
                            return@repeat
                        }
                    }
                }
                if(rowIndex < rowsCount - 1) {
                    Spacer(modifier = Modifier.height(rowSeparation))
                }
            }
        }
    }
}