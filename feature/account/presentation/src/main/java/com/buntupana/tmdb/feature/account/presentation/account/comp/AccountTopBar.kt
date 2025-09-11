package com.buntupana.tmdb.feature.account.presentation.account.comp

import android.content.res.Configuration
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.buntupana.tmdb.core.ui.theme.AppTheme
import com.buntupana.tmdb.core.ui.theme.Dimens
import com.buntupana.tmdb.core.ui.util.getOnBackgroundColor
import com.buntupana.tmdb.feature.account.presentation.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountTopBar(
    modifier: Modifier = Modifier,
    avatarUrl: String? = null,
    username: String? = null
) {

    Box(
        modifier = modifier
            .background(MaterialTheme.colorScheme.primaryContainer)
    ) {
        Image(
            modifier = Modifier
                .matchParentSize()
                .fillMaxWidth()
                .height(100.dp)
                .background(MaterialTheme.colorScheme.primaryContainer),
            painter = painterResource(R.drawable.img_account_background),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.secondary)
        )
        TopAppBar(
            modifier = modifier
                .windowInsetsPadding(WindowInsets.statusBars),
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.Transparent
            ),
            title = {
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
                        color = MaterialTheme.colorScheme.primaryContainer.getOnBackgroundColor(),
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
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
fun AccountTopBarPreview() {
    AppTheme {
        AccountTopBar(
            modifier = Modifier.fillMaxWidth(),
            avatarUrl = "",
            username = "John"
        )
    }
}
