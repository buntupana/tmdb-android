package com.buntupana.tmdb.app.presentation.navigation

import androidx.compose.runtime.Composable
import com.buntupana.tmdb.core.ui.navigation.NavRoutesMain
import com.buntupana.tmdb.feature.lists.presentation.create_update_list.CreateUpdateListRoute
import com.buntupana.tmdb.feature.lists.presentation.list_detail.ListDetailRoute
import com.buntupana.tmdb.feature.lists.presentation.lists.ListsScreen
import com.buntupana.tmdb.feature.search.presentation.SearchRoute

@Composable
fun ListsNav(
    navRoutesMain: NavRoutesMain
) {
    ListsScreen(
        onBackClick = { navRoutesMain.popBackStack() },
        onListDetailClick = { listId, listName, description, backdropUrl ->
            navRoutesMain.navigate(
                ListDetailRoute(
                    listId = listId,
                    listName = listName,
                    description = description,
                    backdropUrl = backdropUrl
                )
            )
        },
        onSearchClick = { navRoutesMain.navigate(SearchRoute) },
        onCreateListDialogClick = {
            navRoutesMain.navigate(
                CreateUpdateListRoute()
            )
        }
    )
}