package com.buntupana.tmdb.feature.lists.presentation.list_detail

import com.panabuntu.tmdb.core.common.model.DefaultItem
import com.panabuntu.tmdb.core.common.model.MediaItem

data class ItemListViewEntity (
    override val id: String,
    val mediaItem: MediaItem,
    var isDeleteRevealed: Boolean = false
):DefaultItem