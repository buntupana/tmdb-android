package com.buntupana.tmdb.feature.discover.presentation.media_filter

import android.os.Parcelable
import com.buntupana.tmdb.feature.discover.presentation.model.MediaListFilter
import kotlinx.parcelize.Parcelize

@Parcelize
class MediaFilterResult(
    val mediaListFilter: MediaListFilter
) : Parcelable