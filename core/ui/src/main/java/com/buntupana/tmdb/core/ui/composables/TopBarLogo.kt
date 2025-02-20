package com.buntupana.tmdb.core.ui.composables

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalMinimumInteractiveComponentSize
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.buntupana.tmdb.core.ui.R
import com.buntupana.tmdb.core.ui.theme.Dimens
import com.buntupana.tmdb.core.ui.util.getOnBackgroundColor
import com.panabuntu.tmdb.core.common.util.isNotNullOrBlank

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarLogo(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    onSearchClick: () -> Unit,
    onLogoClick: () -> Unit,
    backgroundColor: Color,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    shareLink: String? = null
) {

    TopAppBar(
        modifier = modifier,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = backgroundColor,
            scrolledContainerColor = backgroundColor
        ),
        scrollBehavior = scrollBehavior,
        navigationIcon = {
            IconButton(
                modifier = Modifier,
                onClick = onBackClick,
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                    contentDescription = "Back",
                    tint = backgroundColor.getOnBackgroundColor()
                )
            }

        },
        title = {

            val startMargin = if (shareLink.isNotNullOrBlank()) {
                LocalMinimumInteractiveComponentSize.current
            } else {
                0.dp
            }
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = startMargin),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {

                Image(
                    modifier = Modifier
                        .size(Dimens.topBarIconSize)
                        .clickable {
                            onLogoClick()
                        },
                    painter = painterResource(id = R.drawable.img_logo),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(backgroundColor.getOnBackgroundColor())
                )
            }
        },
        actions = {

            if (shareLink.isNotNullOrBlank()) {

                val sendIntent = Intent(Intent.ACTION_SEND).apply {
                    putExtra(Intent.EXTRA_TEXT, shareLink)
                    type = "text/plain"
                }

                val shareIntent = Intent.createChooser(sendIntent, null)

                val context = LocalContext.current

                IconButton(
                    modifier = Modifier,
                    onClick = {
                        context.startActivity(shareIntent)
                    },
                ) {
                    Icon(
                        modifier = Modifier
                            .padding(horizontal = Dimens.padding.small),
                        imageVector = Icons.Rounded.Share,
                        contentDescription = "Share",
                        tint = backgroundColor.getOnBackgroundColor()
                    )
                }
            }

            IconButton(
                modifier = Modifier,
                onClick = onSearchClick,
            ) {
                Icon(
                    modifier = Modifier
                        .padding(horizontal = Dimens.padding.small),
                    painter = painterResource(R.drawable.ic_search),
                    contentDescription = "Search",
                    tint = backgroundColor.getOnBackgroundColor()
                )
            }
        },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun TopBarLogoPreview() {
    TopBarLogo(
        Modifier.background(Color.Blue),
        backgroundColor = Color.Blue,
        shareLink = "asdf",
        onSearchClick = {},
        onBackClick = {},
        onLogoClick = {}
    )
}