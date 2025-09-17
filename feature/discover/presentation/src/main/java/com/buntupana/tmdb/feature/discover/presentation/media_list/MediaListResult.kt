package com.buntupana.tmdb.feature.discover.presentation.media_list

import android.os.Parcelable
import com.buntupana.tmdb.feature.discover.presentation.model.MediaListFilter
import kotlinx.parcelize.Parcelize

@Parcelize
sealed class MediaListResult : Parcelable {
    data class ApplyFilter(val mediaListFilter: MediaListFilter) : MediaListResult()
}