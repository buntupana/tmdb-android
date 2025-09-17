package com.buntupana.tmdb.app.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.buntupana.tmdb.app.presentation.home.HomeRoute
import com.buntupana.tmdb.core.ui.navigation.NavRoutesMain
import com.buntupana.tmdb.feature.detail.presentation.media.MediaDetailRoute
import com.buntupana.tmdb.feature.lists.presentation.create_update_list.CreateUpdateListRoute
import com.buntupana.tmdb.feature.lists.presentation.delete_item_list.DeleteItemListRoute
import com.buntupana.tmdb.feature.lists.presentation.delete_item_list.DeleteItemResult
import com.buntupana.tmdb.feature.lists.presentation.delete_item_list.ListType
import com.buntupana.tmdb.feature.lists.presentation.delete_list.DeleteListRoute
import com.buntupana.tmdb.feature.lists.presentation.list_detail.ListDetailResult
import com.buntupana.tmdb.feature.lists.presentation.list_detail.ListDetailScreen
import com.buntupana.tmdb.feature.search.presentation.SearchRoute

@Composable
fun ListDetailNav(
    navRoutesMain: NavRoutesMain
) {
    val result = navRoutesMain.getStateFlowResult<DeleteItemResult>()
        ?.collectAsStateWithLifecycle()?.value?.let {
            ListDetailResult.CancelRemoveItem(
                mediaId = it.mediaId,
                mediaType = it.mediaType
            )
        }

    ListDetailScreen(
        listDetailResult = result,
        onBackClick = { navRoutesMain.popBackStack() },
        onLogoClick = { navRoutesMain.popBackStack(HomeRoute::class) },
        onSearchClick = { navRoutesMain.navigate(SearchRoute) },
        onMediaClick = { mediaId, mediaType, mainPosterColor ->
            navRoutesMain.navigate(
                MediaDetailRoute(
                    mediaId = mediaId,
                    mediaType = mediaType,
                    backgroundColor = mainPosterColor?.toArgb()
                )
            )
        },
        onUpdateListClick = { listId, listName, listDescription, isPublic ->
            navRoutesMain.navigate(
                CreateUpdateListRoute(
                    listId = listId,
                    listName = listName,
                    listDescription = listDescription,
                    isPublic = isPublic
                )
            )
        },
        onDeleteListClick = { listId, listName ->
            navRoutesMain.navigate(
                DeleteListRoute(
                    listId = listId,
                    listName = listName
                )
            )
        },
        onDeleteClick = { itemId, mediaItem, listId ->
            navRoutesMain.navigate(
                DeleteItemListRoute(
                    mediaId = mediaItem.id,
                    mediaName = mediaItem.name,
                    mediaType = mediaItem.mediaType,
                    listType = ListType.CustomList(listId)
                )
            )
        }
    )
}