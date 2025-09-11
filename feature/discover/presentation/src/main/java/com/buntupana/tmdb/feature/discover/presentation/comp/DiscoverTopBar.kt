package com.buntupana.tmdb.feature.discover.presentation.comp

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.buntupana.tmdb.core.ui.R
import com.buntupana.tmdb.core.ui.composables.widget.AppIconButton
import com.buntupana.tmdb.core.ui.theme.AppTheme
import com.buntupana.tmdb.core.ui.theme.Dimens
import com.buntupana.tmdb.core.ui.util.getOnBackgroundColor
import com.buntupana.tmdb.core.ui.R as RCore

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiscoverTopBar(
    modifier: Modifier = Modifier,
    onSearchClick: () -> Unit
) {

    TopAppBar(
        modifier = modifier,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            scrolledContainerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        title = {
            Image(
                modifier = Modifier
                    .padding(horizontal = Dimens.padding.medium),
                painter = painterResource(id = RCore.drawable.ic_logo_short),
                contentDescription = null,
//                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimaryContainer)
            )
        },
        actions = {
            AppIconButton(
                modifier = Modifier,
                colors = IconButtonDefaults.iconButtonColors(
                    contentColor = MaterialTheme.colorScheme.primaryContainer.getOnBackgroundColor()
                ),
                onClick = onSearchClick
            ) {
                Icon(
                    modifier = Modifier
                        .padding(horizontal = Dimens.padding.small),
                    painter = painterResource(R.drawable.ic_search),
                    contentDescription = "Search",
                )
            }
        }
    )
}


@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    name = "DefaultPreviewLight",
    showBackground = true,
)
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "DefaultPreviewDark",
    showBackground = true,
)
@Composable
private fun DiscoverTopBarPreview() {
    AppTheme {
        DiscoverTopBar {}
    }
}