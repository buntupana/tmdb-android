package com.buntupana.tmdb.feature.lists.presentation.list_detail.comp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.buntupana.tmdb.core.ui.composables.ImageFromUrl
import com.buntupana.tmdb.core.ui.composables.top_bar.TopBarLogo
import com.buntupana.tmdb.core.ui.theme.PrimaryColor
import com.panabuntu.tmdb.core.common.util.isNotNullOrBlank

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListDetailTopBar(
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior,
    listName: String,
    description: String,
    backdropUrl: String?,
    shareLink: String?,
    isPublic: Boolean,
    itemTotalCount: Int?,
    onBackClick: () -> Unit,
    onLogoClick: () -> Unit,
    onSearchClick: () -> Unit,
    onEditClick: () -> Unit = {},
    onDeleteClick: () -> Unit = {},
) {
    Box(
        modifier = modifier
    ) {
        var backgroundColor = PrimaryColor

        if (backdropUrl.isNotNullOrBlank()) {
            backgroundColor = backgroundColor.copy(alpha = 0.8f)
            ImageFromUrl(
                modifier = Modifier.matchParentSize(),
                imageUrl = backdropUrl,
            )
        }

        Box(
            modifier = Modifier
                .matchParentSize()
                .background(backgroundColor),
        )

        Column {
            TopBarLogo(
                backgroundColor = Color.Transparent,
                onLogoClick = onLogoClick,
                onBackClick = onBackClick,
                onSearchClick = onSearchClick,
                scrollBehavior = scrollBehavior,
                shareLink = shareLink
            )

            ListDetailHeader(
                modifier = Modifier.fillMaxWidth(),
                listName = listName,
                description = description,
                isPublic = isPublic,
                itemsTotalCount = itemTotalCount,
                onEditClick = onEditClick,
                onDeleteClick = onDeleteClick
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
private fun ListDetailTopBarPreview() {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    ListDetailTopBar(
        listName = "List name",
        description = "List description",
        backdropUrl = null,
        shareLink = null,
        isPublic = true,
        itemTotalCount = 10,
        onBackClick = {},
        onLogoClick = {},
        onSearchClick = {},
        onEditClick = {},
        onDeleteClick = {},
        scrollBehavior = scrollBehavior
    )
}