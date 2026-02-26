package com.buntupana.tmdb.feature.lists.presentation.list_detail

import android.os.Parcelable
import com.panabuntu.tmdb.core.common.entity.MediaType
import kotlinx.parcelize.Parcelize

@Parcelize
sealed class ListDetailResult : Parcelable {
    data class CancelRemoveItem(val mediaId: Long, val mediaType: MediaType) : ListDetailResult()
}