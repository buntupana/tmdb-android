package com.buntupana.tmdb.feature.lists.domain.model

import com.panabuntu.tmdb.core.common.model.DefaultItem
import java.time.LocalDate

data class MediaList(
    override val id: Long,
    val name: String,
    val backdropUrl: String?,
    val posterUrl: String?,
    val averageRating: Int?,
    val description: String?,
    val itemCount: Int,
    val isPublic: Boolean,
    val revenue: Long?,
    val runtime: Int?,
    val updatedAt: LocalDate?
) : DefaultItem