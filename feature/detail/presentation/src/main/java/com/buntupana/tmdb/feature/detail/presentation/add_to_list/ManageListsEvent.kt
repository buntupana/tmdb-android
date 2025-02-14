package com.buntupana.tmdb.feature.detail.presentation.add_to_list

import com.buntupana.tmdb.feature.account.domain.model.ListItem

sealed class ManageListsEvent {

    data class Init(val navArgs: ManageListsNav) : ManageListsEvent()

    data class AddToList(val listItem: ListItem): ManageListsEvent()

    data class DeleteFromList(val listItem: ListItem): ManageListsEvent()

    data object Confirm : ManageListsEvent()
}