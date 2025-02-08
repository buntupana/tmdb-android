package com.buntupana.tmdb.core.ui.composables.list

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.panabuntu.tmdb.core.common.model.DefaultItem

@Composable
fun <L : DefaultItem> LazyColumnGeneric(
    modifier: Modifier = Modifier,
    state: LazyListState = rememberLazyListState(),
    topPadding: Dp = 0.dp,
    bottomPadding: Dp = 0.dp,
    animateItem: Boolean = false,
    itemList: LazyPagingItems<L>?,
    headerContent: @Composable () -> Unit = {},
    noResultContent: @Composable () -> Unit = {},
    placeHolder: (@Composable () -> Unit)? = null,
    itemContent: @Composable LazyItemScope.(item: L) -> Unit
) {

    itemList ?: return

    if (itemList.itemCount == 0 && itemList.loadState.refresh is LoadState.NotLoading) {
        Column {
            headerContent()
            noResultContent()
        }
    }

    LazyColumn(
        modifier = modifier,
        state = state,
        userScrollEnabled = (itemList.loadState.refresh !is LoadState.Loading && itemList.itemCount != 0)
    ) {
        item {
            headerContent()
        }

        lazyContentGeneric(
            itemList = itemList,
            initialPadding = topPadding,
            finalPadding = bottomPadding,
            animateItem = animateItem,
            placeHolder = placeHolder
        ) { item ->
            itemContent(item)
        }
    }
}

@Composable
fun <L : DefaultItem> LazyRowGeneric(
    modifier: Modifier = Modifier,
    state: LazyListState = rememberLazyListState(),
    startPadding: Dp = 0.dp,
    endPadding: Dp = 0.dp,
    animateItem: Boolean = false,
    itemList: LazyPagingItems<L>,
    noResultContent: @Composable () -> Unit = {},
    placeHolder: (@Composable () -> Unit)? = null,
    itemContent: @Composable LazyItemScope.(item: L) -> Unit,
) {

    if (itemList.itemCount == 0 && itemList.loadState.refresh is LoadState.NotLoading) {
        noResultContent()
        return
    }

    LazyRow(
        modifier = modifier,
        state = state,
        userScrollEnabled = (itemList.loadState.refresh !is LoadState.Loading && itemList.itemCount != 0)
    ) {
        lazyContentGeneric(
            itemList = itemList,
            initialPadding = startPadding,
            finalPadding = endPadding,
            animateItem = animateItem,
            placeHolder = placeHolder
        ) { item ->
            itemContent(item)
        }
    }
}

fun <L : DefaultItem> LazyListScope.lazyContentGeneric(
    itemList: LazyPagingItems<L>,
    initialPadding: Dp,
    finalPadding: Dp,
    animateItem: Boolean = false,
    placeHolder: (@Composable () -> Unit)?,
    itemContent: @Composable LazyItemScope.(item: L) -> Unit
) {

    item {
        Spacer(modifier = Modifier.height(initialPadding))
    }

    lazyListLoadStateGeneric(
        loadState = itemList.loadState.prepend,
        itemCount = itemList.itemCount,
        retry = { itemList.retry() },
        placeHolder = placeHolder
    )

    lazyListLoadStateGeneric(
        loadState = itemList.loadState.refresh,
        itemCount = itemList.itemCount,
        isRefresh = true,
        retry = { itemList.retry() },
        placeHolder = placeHolder
    )

    items(
        count = itemList.itemCount,
        key = { index ->
            if (animateItem) itemList[index]?.id ?: index else index
        }
    ) { index ->
        val item = itemList[index] ?: return@items
        itemContent(item)
    }

    lazyListLoadStateGeneric(
        loadState = itemList.loadState.append,
        itemCount = itemList.itemCount,
        retry = { itemList.retry() },
        placeHolder = placeHolder
    )

    item {
        Spacer(modifier = Modifier.height(finalPadding))
    }
}
