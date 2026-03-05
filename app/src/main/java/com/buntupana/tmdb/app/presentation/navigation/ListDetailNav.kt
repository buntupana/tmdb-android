package com.buntupana.tmdb.app.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.toArgb
import com.buntupana.tmdb.app.presentation.getViewModel
import com.buntupana.tmdb.app.presentation.nav3.Navigator
import com.buntupana.tmdb.app.presentation.nav3.ResultStore
import com.buntupana.tmdb.app.presentation.nav3.RouteNav3
import com.buntupana.tmdb.feature.lists.presentation.list_detail.ListDetailNavArgs
import com.buntupana.tmdb.feature.lists.presentation.list_detail.ListDetailScreen

@Composable
fun ListDetailNav(
    navigator: Navigator,
    resultStore: ResultStore,
    route: RouteNav3.ListDetail
) {

    val navArgs = ListDetailNavArgs(
        listId = route.listId,
        listName = route.listName,
        description = route.description,
        backdropUrl = route.backdropUrl
    )

//    val result = navRoutesMain.getStateFlowResult<DeleteItemResult>()
//        ?.collectAsStateWithLifecycle()?.value?.let {
//            ListDetailResult.CancelRemoveItem(
//                mediaId = it.mediaId,
//                mediaType = it.mediaType
//            )
//        }

    ListDetailScreen(
        viewModel = getViewModel(navArgs),
        listDetailResult = null,
        onBackClick = { navigator.goBack() },
        onLogoClick = {
//            navRoutesMain.popBackStack(HomeRoute::class)
        },
        onSearchClick = { navigator.navigate(RouteNav3.Search) },
        onMediaClick = { mediaId, mediaType, mainPosterColor ->
            navigator.navigate(
                RouteNav3.MediaDetail(
                    mediaId = mediaId,
                    mediaType = mediaType,
                    backgroundColor = mainPosterColor?.toArgb()
                )
            )
        },
        onUpdateListClick = { listId, listName, listDescription, isPublic ->
//            navRoutesMain.navigate(
//                CreateUpdateListRoute(
//                    listId = listId,
//                    listName = listName,
//                    listDescription = listDescription,
//                    isPublic = isPublic
//                )
//            )
        },
        onDeleteListClick = { listId, listName ->
//            navRoutesMain.navigate(
//                DeleteListRoute(
//                    listId = listId,
//                    listName = listName
//                )
//            )
        },
        onDeleteClick = { itemId, mediaItem, listId ->
//            navRoutesMain.navigate(
//                DeleteItemListRoute(
//                    mediaId = mediaItem.id,
//                    mediaName = mediaItem.name,
//                    mediaType = mediaItem.mediaType,
//                    listType = ListType.CustomList(listId)
//                )
//            )
        }
    )
}