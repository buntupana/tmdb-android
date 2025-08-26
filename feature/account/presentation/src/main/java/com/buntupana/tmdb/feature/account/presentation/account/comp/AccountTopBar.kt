package com.buntupana.tmdb.feature.account.presentation.account.comp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.buntupana.tmdb.core.ui.theme.Dimens
import com.buntupana.tmdb.core.ui.theme.PrimaryColor
import com.buntupana.tmdb.core.ui.theme.SecondaryColor
import com.buntupana.tmdb.core.ui.util.getOnBackgroundColor
import com.buntupana.tmdb.feature.account.presentation.R

@Composable
fun AccountTopBar(
    modifier: Modifier = Modifier,
    avatarUrl: String? = null,
    username: String? = null
) {
    Box(
        modifier = modifier
            .background(PrimaryColor)
    ) {
        Image(
            modifier = Modifier
                .matchParentSize()
                .fillMaxWidth()
                .height(100.dp)
                .background(PrimaryColor),
            painter = painterResource(R.drawable.img_account_background),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            colorFilter = ColorFilter.tint(SecondaryColor)
        )
        Row(
            modifier = Modifier
                .windowInsetsPadding(WindowInsets.statusBars)
                .padding(Dimens.padding.medium),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AvatarImage(
                username = username,
                avatarUrl = avatarUrl,
            )
            Spacer(Modifier.padding(horizontal = Dimens.padding.vertical))
            Text(
                text = username.orEmpty(),
                color = PrimaryColor.getOnBackgroundColor(),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AccountTopBarPreview() {
    AccountTopBar(
        modifier = Modifier.fillMaxWidth(),
        avatarUrl = "",
        username = "John"
    )
}

