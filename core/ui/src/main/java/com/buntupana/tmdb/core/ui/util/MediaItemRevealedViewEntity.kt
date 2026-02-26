package com.buntupana.tmdb.core.ui.util

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.panabuntu.tmdb.core.common.model.DefaultItem
import com.panabuntu.tmdb.core.common.model.MediaItem

data class MediaItemRevealedViewEntity (
    override val id: String,
    val mediaItem: MediaItem,
    val isDeleteRevealed: MutableState<Boolean> = mutableStateOf(false)
): DefaultItem