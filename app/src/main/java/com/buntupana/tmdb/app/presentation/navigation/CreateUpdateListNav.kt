package com.buntupana.tmdb.app.presentation.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import com.buntupana.tmdb.core.ui.navigation.NavRoutesMain
import com.buntupana.tmdb.feature.lists.presentation.create_update_list.CreateUpdateListDialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateUpdateListNav(
    navRoutesMain: NavRoutesMain
) {
    CreateUpdateListDialog(
        onDismiss = { navRoutesMain.popBackStack() }
    )
}