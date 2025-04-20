package com.buntupana.tmdb.feature.account.presentation.account.comp

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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.buntupana.tmdb.core.ui.composables.ImageFromUrl
import com.buntupana.tmdb.core.ui.theme.SecondaryColor

@Composable
fun AvatarImage(
    modifier: Modifier = Modifier,
    avatarUrl: String?,
    username: String?,
    setDominantColor: ((dominantColor: Color) -> Unit)? = null
) {
    if (avatarUrl.isNullOrBlank()) {
        Box(
            modifier = modifier
                .size(100.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.background),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = username.orEmpty().first().toString().uppercase(),
                color = SecondaryColor,
                fontWeight = FontWeight.Bold,
                fontSize = 80.dp.value.sp,
            )
        }
    } else {
        ImageFromUrl(
            modifier = modifier
                .size(100.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.background),
            imageUrl = avatarUrl,
            setDominantColor = setDominantColor
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun AvatarImagePreview() {
    AvatarImage(
        avatarUrl = null,
        username = "John"
    )
}