package com.buntupana.tmdb.app.presentation.navigation

import androidx.compose.runtime.Composable
import com.buntupana.tmdb.app.presentation.home.HomeRoute
import com.buntupana.tmdb.core.ui.navigation.NavRoutesMain
import com.buntupana.tmdb.feature.lists.presentation.create_update_list.CreateUpdateListRoute
import com.buntupana.tmdb.feature.lists.presentation.manage_lists.ManageListsScreen

@Composable
fun ManageListsNav(
    navRoutesMain: NavRoutesMain
) {
    ManageListsScreen(
        onBackClick = { navRoutesMain.popBackStack() },
        onLogoClick = { navRoutesMain.popBackStack(HomeRoute::class) },
        onCreateListClick = {
            navRoutesMain.navigate(CreateUpdateListRoute())
        }
    )
}