package com.buntupana.tmdb.app.presentation.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import com.buntupana.tmdb.core.ui.navigation.NavRoutesMain
import com.buntupana.tmdb.feature.detail.presentation.rating.RatingDialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RatingNav(
    navRoutesMain: NavRoutesMain
) {
    RatingDialog(
        onDismiss = {
            navRoutesMain.popBackStack()
        }
    )
}