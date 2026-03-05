package com.buntupana.tmdb.app.presentation.navigation

import androidx.compose.runtime.Composable
import com.buntupana.tmdb.app.presentation.getViewModel
import com.buntupana.tmdb.app.presentation.nav3.Navigator
import com.buntupana.tmdb.app.presentation.nav3.RouteNav3
import com.buntupana.tmdb.feature.lists.presentation.lists.ListsScreen

@Composable
fun ListsNav(
    navigator: Navigator
) {
    ListsScreen(
        viewModel = getViewModel(),
        onBackClick = { navigator.goBack() },
        onListDetailClick = { listId, listName, description, backdropUrl ->
            navigator.navigate(
                RouteNav3.ListDetail(
                    listId = listId,
                    listName = listName,
                    description = description,
                    backdropUrl = backdropUrl
                )
            )
        },
        onSearchClick = { navigator.navigate(RouteNav3.Search) },
        onCreateListDialogClick = {
//            navRoutesMain.navigate(
//                CreateUpdateListRoute()
//            )
        }
    )
}