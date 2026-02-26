package com.buntupana.tmdb.app.presentation.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import com.buntupana.tmdb.core.ui.navigation.NavRoutesMain
import com.buntupana.tmdb.feature.lists.presentation.delete_item_list.DeleteItemListDialog
import com.buntupana.tmdb.feature.lists.presentation.delete_item_list.DeleteItemResult

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeleteItemListNav(
    navRoutesMain: NavRoutesMain
) {
    DeleteItemListDialog(
        onDeleteSuccess = {
            navRoutesMain.popBackStack()
        },
        onDismiss = { mediaId, mediaType ->
            navRoutesMain.saveResult(DeleteItemResult(mediaId, mediaType))
            navRoutesMain.popBackStack()
        }
    )
}