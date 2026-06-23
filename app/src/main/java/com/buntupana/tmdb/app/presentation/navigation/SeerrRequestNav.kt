package com.buntupana.tmdb.app.presentation.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import com.buntupana.tmdb.core.ui.navigation.NavRoutesMain
import com.buntupana.tmdb.feature.seer.presentation.request.SeerrRequestDialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SeerrRequestNav(
    navRoutesMain: NavRoutesMain,
) {
    SeerrRequestDialog(
        onDismiss = { navRoutesMain.popBackStack() }
    )
}