package com.buntupana.tmdb.core.ui.composables.top_bar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.buntupana.tmdb.core.ui.R
import com.buntupana.tmdb.core.ui.composables.widget.AppIconButton
import com.buntupana.tmdb.core.ui.theme.AppTheme
import com.buntupana.tmdb.core.ui.theme.Dimens
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
            AppIconButton(
                modifier = Modifier,
                onClick = onBackClick,
                rippleColor = MaterialTheme.colorScheme.onPrimary
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                    contentDescription = "Back",
                    tint = backgroundColor.getOnBackgroundColor()
                )
            }
            
        },
        actions = {
            AppIconButton(
                modifier = Modifier,
                onClick = onSearchClick,
                rippleColor = MaterialTheme.colorScheme.onPrimary
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
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {

                Text(
                    text = title,
                    color = backgroundColor.getOnBackgroundColor(),
                    fontWeight = FontWeight.Bold
                )
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun TopBarTitlePreview() {
    AppTheme {
        TopBarTitle(
            title = "Watchlist",
            backgroundColor = Color.Gray,
            onSearchClick = {},
            onBackClick = {}
        )
    }
}