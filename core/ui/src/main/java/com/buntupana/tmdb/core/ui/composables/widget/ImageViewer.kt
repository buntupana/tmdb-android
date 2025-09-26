package com.buntupana.tmdb.core.ui.composables.widget

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.carousel.HorizontalMultiBrowseCarousel
import androidx.compose.material3.carousel.rememberCarouselState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.buntupana.tmdb.core.ui.composables.CircularProgressIndicatorDelayed
import com.buntupana.tmdb.core.ui.composables.ImageFromUrl
import com.buntupana.tmdb.core.ui.theme.AppTheme
import com.buntupana.tmdb.core.ui.theme.Dimens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageViewer(
    modifier: Modifier = Modifier,
    imageUrlList: List<String>? = emptyList(),
    onDismiss: () -> Unit
) {

    Box(
        modifier = modifier
            .background(Color.Black.copy(alpha = 0.7f))
            .clickable(
                onClick = onDismiss,
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ),
        contentAlignment = Alignment.Center
    ) {

        if (imageUrlList.isNullOrEmpty()) {
            CircularProgressIndicatorDelayed()
        } else {
            HorizontalMultiBrowseCarousel(
                state = rememberCarouselState { imageUrlList.count() },
                modifier = Modifier
                    .clickable(enabled = false, onClick = {})
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(vertical = Dimens.padding.vertical),
                preferredItemWidth = 99_999.dp,
                itemSpacing = Dimens.padding.small,
                contentPadding = PaddingValues(horizontal = Dimens.padding.horizontal),
            ) { i ->
                val item = imageUrlList[i]
                ImageFromUrl(
                    modifier = Modifier
                        .fillMaxWidth()
                        .maskClip(MaterialTheme.shapes.extraLarge),
                    imageUrl = item,
                    contentDescription = null,
                    contentScale = ContentScale.Fit
                )
            }
        }

    }

}

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    name = "DefaultPreviewLight",
    showBackground = true
)
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "DefaultPreviewDark",
    showBackground = true
)
@Composable
private fun ImageViewerPreview() {
    AppTheme {
        ImageViewer(
            modifier = Modifier.fillMaxSize(),
            onDismiss = {},
        )
    }
}