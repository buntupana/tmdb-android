package com.buntupana.tmdb.feature.lists.presentation.manage_lists

import com.buntupana.tmdb.feature.lists.domain.model.MediaList

sealed class ManageListsEvent {

    data object GetLists : ManageListsEvent()

    data class AddToList(val mediaList: MediaList): ManageListsEvent()

    data class DeleteFromList(val mediaList: MediaList): ManageListsEvent()

    data object Confirm : ManageListsEvent()
}