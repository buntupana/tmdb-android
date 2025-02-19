package com.buntupana.tmdb.feature.lists.presentation.list_detail

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.panabuntu.tmdb.core.common.model.DefaultItem
import com.panabuntu.tmdb.core.common.model.MediaItem

data class ItemListViewEntity (
    override val id: String,
    val mediaItem: MediaItem,
    val isDeleteRevealed: MutableState<Boolean> = mutableStateOf(false)
):DefaultItem