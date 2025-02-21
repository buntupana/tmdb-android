package com.buntupana.tmdb.feature.discover.presentation.comp

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.buntupana.tmdb.core.ui.R
import com.buntupana.tmdb.core.ui.theme.Dimens
import com.buntupana.tmdb.core.ui.theme.PrimaryColor
import com.buntupana.tmdb.core.ui.theme.SecondaryColor
import com.buntupana.tmdb.core.ui.util.IconButton
import com.buntupana.tmdb.core.ui.util.getOnBackgroundColor
import com.buntupana.tmdb.core.ui.R as RCore

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    modifier: Modifier = Modifier,
    onSearchClick: () -> Unit
) {

    TopAppBar(
        modifier = modifier,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = PrimaryColor,
            scrolledContainerColor = PrimaryColor
        ),
        title = {
            Image(
                modifier = Modifier
                    .padding(vertical = Dimens.padding.small, horizontal = Dimens.padding.medium),
                painter = painterResource(id = RCore.drawable.ic_logo_short),
                contentDescription = null
            )
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
                    tint = SecondaryColor
                )
            }
        }
    )
}

@Preview
@Composable
private fun TopBarPreview() {
    TopBar {

    }
}