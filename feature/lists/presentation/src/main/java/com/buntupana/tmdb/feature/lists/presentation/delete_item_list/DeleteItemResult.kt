package com.buntupana.tmdb.feature.lists.presentation.delete_item_list

import android.os.Parcelable
import com.panabuntu.tmdb.core.common.entity.MediaType
import kotlinx.parcelize.Parcelize

@Parcelize
data class DeleteItemResult(
    val mediaId: Long,
    val mediaType: MediaType
) : Parcelable