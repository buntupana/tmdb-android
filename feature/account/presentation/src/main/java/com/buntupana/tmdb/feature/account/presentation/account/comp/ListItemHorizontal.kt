package com.buntupana.tmdb.feature.account.presentation.account.comp

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.buntupana.tmdb.core.ui.composables.ImageFromUrl
import com.buntupana.tmdb.core.ui.theme.Dimens
import com.buntupana.tmdb.core.ui.theme.PlaceHolderColor
import com.buntupana.tmdb.core.ui.theme.PrimaryColor
import com.buntupana.tmdb.feature.lists.domain.model.ListItem
import com.panabuntu.tmdb.core.common.util.isNotNullOrBlank

@Composable
fun ListItemHorizontal(
    modifier: Modifier = Modifier,
    width: Dp = 200.dp,
    listItem: ListItem,
    onListClick: (listId: Long, listName: String, description: String?, backdropUrl: String?) -> Unit,
) {

    var dominantColor by remember { mutableStateOf<Color?>(null) }

    Box(
        modifier = modifier
            .width(width)
            .aspectRatio(16f / 10f)
            .clip(RoundedCornerShape(Dimens.posterRound))
            .background(PlaceHolderColor)
            .clickable {
                onListClick(listItem.id, listItem.name, listItem.description, listItem.backdropUrl)
            }
    ) {

        var backgroundColor = PrimaryColor

        if (listItem.backdropUrl.isNotNullOrBlank()) {
            backgroundColor = backgroundColor.copy(alpha = 0.8f)
            ImageFromUrl(
                modifier = Modifier.fillMaxSize(),
                imageUrl = listItem.backdropUrl,
                showPlaceHolder = true
            ) { extractedColor ->
                dominantColor = extractedColor
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundColor)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = Dimens.padding.horizontal),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            BasicText(
                text = listItem.name,
                autoSize = TextAutoSize.StepBased(minFontSize = 18.sp, stepSize = 10.sp),
                maxLines = 3,
                color = { Color.White },
                style = MaterialTheme.typography.titleLarge.merge(textAlign = TextAlign.Center),
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ListItemHorizontalPreview() {
    ListItemHorizontal(
        width = 200.dp,
        listItem = ListItem(
            id = 1,
            name = "List Name",
            description = "List Description",
            backdropUrl = null,
            posterUrl = null,
            itemCount = 0,
            isPublic = false,
            revenue = 0L,
            runtime = null,
            averageRating = null,
            updatedAt = null
        ),
        onListClick = { _, _, _, _ -> }
    )
}

@Composable
fun ListItemHorizontalPlaceHolder(
    modifier: Modifier = Modifier,
    width: Dp = 200.dp
) {
    Box(
        modifier = modifier
            .width(width)
            .aspectRatio(16f / 10f)
            .clip(RoundedCornerShape(Dimens.posterRound))
            .background(PlaceHolderColor)
    ) {
    }
}

@Preview(showBackground = true)
@Composable
private fun ListItemHorizontalPlaceHolderPreview() {
    ListItemHorizontalPlaceHolder()
}

