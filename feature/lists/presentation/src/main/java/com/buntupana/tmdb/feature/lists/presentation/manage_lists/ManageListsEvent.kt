package com.buntupana.tmdb.feature.lists.presentation.manage_lists

import com.buntupana.tmdb.feature.lists.domain.model.UserListDetails

sealed class ManageListsEvent {

    data object GetLists : ManageListsEvent()

    data class AddToList(val mediaList: UserListDetails): ManageListsEvent()

    data class DeleteFromList(val mediaList: UserListDetails): ManageListsEvent()

    data object Confirm : ManageListsEvent()
}