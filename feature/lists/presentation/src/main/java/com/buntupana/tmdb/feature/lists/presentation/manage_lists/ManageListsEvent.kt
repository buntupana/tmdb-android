package com.buntupana.tmdb.feature.lists.presentation.manage_lists

import com.buntupana.tmdb.feature.lists.domain.model.ListItem

sealed class ManageListsEvent {

    data object GetLists : ManageListsEvent()

    data class AddToList(val listItem: ListItem): ManageListsEvent()

    data class DeleteFromList(val listItem: ListItem): ManageListsEvent()

    data object Confirm : ManageListsEvent()
}