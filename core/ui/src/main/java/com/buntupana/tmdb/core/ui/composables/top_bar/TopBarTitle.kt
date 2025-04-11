package com.buntupana.tmdb.core.ui.composables.top_bar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.buntupana.tmdb.core.ui.R
import com.buntupana.tmdb.core.ui.theme.Dimens
import com.buntupana.tmdb.core.ui.theme.PrimaryColor
import com.buntupana.tmdb.core.ui.util.IconButton
import com.buntupana.tmdb.core.ui.util.getOnBackgroundColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarTitle(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    onSearchClick: () -> Unit,
    title: String = "",
    backgroundColor: Color,
    scrollBehavior: TopAppBarScrollBehavior? = null
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
                rippleColor = PrimaryColor.getOnBackgroundColor()
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                    contentDescription = "Back",
                    tint = backgroundColor.getOnBackgroundColor()
                )
            }
            
        },
        actions = {
            IconButton(
                modifier = Modifier,
                onClick = onSearchClick,
                rippleColor = PrimaryColor.getOnBackgroundColor()
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
        title = {

            Row(
                modifier = Modifier
                    .fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {

                Text(
                    text = title,
                    color = backgroundColor.getOnBackgroundColor()
                )
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun TopBarTitlePreview() {
    TopBarTitle(
        Modifier.background(Color.Blue),
        title = "Watchlist",
        backgroundColor = Color.Blue,
        onSearchClick = {},
        onBackClick = {}
    )
}