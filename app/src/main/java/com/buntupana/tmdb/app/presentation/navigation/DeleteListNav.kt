package com.buntupana.tmdb.app.presentation.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import com.buntupana.tmdb.core.ui.navigation.NavRoutesMain
import com.buntupana.tmdb.feature.lists.presentation.delete_list.DeleteListDialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeleteListNav(
    navRoutesMain: NavRoutesMain
) {
    DeleteListDialog(
        onDismiss = { navRoutesMain.popBackStack() }
    )
}