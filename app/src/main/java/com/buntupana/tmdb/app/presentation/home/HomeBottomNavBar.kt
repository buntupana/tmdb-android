package com.buntupana.tmdb.app.presentation.home

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.buntupana.tmdb.core.ui.theme.AppTheme

@Composable
fun HomeBottomNavBar(
    modifier: Modifier = Modifier
) {

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
private fun HomeBottomNavBarPreview() {
    AppTheme {
        HomeBottomNavBar()
    }
}