package com.buntupana.tmdb.feature.discover.presentation.media_list.comp

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.FilterList
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
import com.buntupana.tmdb.core.ui.composables.widget.AppTextWithIconButton
import com.buntupana.tmdb.core.ui.theme.AppTheme
import com.buntupana.tmdb.core.ui.util.getOnBackgroundColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MediaListTopBar(
    modifier: Modifier = Modifier,
    filterName: String,
    onDefaultFiltersClick: () -> Unit,
    onFilterClick: () -> Unit
) {
    TopAppBar(
        modifier = modifier
            .fillMaxWidth(),
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        title = {
//            AppButton(
//                onClick = onDefaultFiltersClick,
//            ) {
//                Icon(
//                    painter = painterResource(R.drawable.ic_arrow_down),
//                    contentDescription = null
//                )
//            }
            AppTextWithIconButton(
                onClick = onDefaultFiltersClick,
                text = filterName,
                painter = painterResource(R.drawable.ic_arrow_down)
            )
        },
        actions = {
            AppIconButton(
                onClick = onFilterClick,
                colors = IconButtonDefaults.iconButtonColors(
                    contentColor = MaterialTheme.colorScheme.primaryContainer.getOnBackgroundColor()
                ),
            ) {
                Icon(
                    imageVector = Icons.Rounded.FilterList,
                    contentDescription = null
                )
            }
        }
    )
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
private fun MediaListTopBarPreview() {
    AppTheme {
        MediaListTopBar(
            filterName = "Popular",
            onDefaultFiltersClick = {},
            onFilterClick = {}
        )
    }
}