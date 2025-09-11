package com.buntupana.tmdb.feature.account.presentation.account.comp

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.buntupana.tmdb.core.ui.composables.ImageFromUrl
import com.buntupana.tmdb.core.ui.theme.AppTheme

@Composable
fun AvatarImage(
    modifier: Modifier = Modifier,
    avatarUrl: String?,
    username: String?,
    avatarSize: Dp = 60.dp,
    setDominantColor: ((dominantColor: Color) -> Unit)? = null
) {
    if (avatarUrl.isNullOrBlank()) {
        Box(
            modifier = modifier
                .size(avatarSize)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.surfaceBright),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = username.orEmpty().first().toString().uppercase(),
                color = MaterialTheme.colorScheme.secondary,
                fontWeight = FontWeight.Bold,
                fontSize = (avatarSize.value * 0.80).sp,
            )
        }
    } else {
        ImageFromUrl(
            modifier = modifier
                .size(avatarSize)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.surfaceBright),
            imageUrl = avatarUrl,
            setDominantColor = setDominantColor
        )
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
private fun AvatarImagePreview() {
    AppTheme {
        AvatarImage(
            avatarUrl = null,
            username = "John"
        )
    }
}