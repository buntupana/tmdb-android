package com.buntupana.tmdb.core.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import kotlin.math.ceil
import kotlin.math.roundToInt

@Composable
fun <T> NestedVerticalLazyGrid(
    modifier: Modifier = Modifier,
    columns: Int = 1,
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
                        } else {
                            return@repeat
                        }
                    }
                }
            }
        }
    }
}