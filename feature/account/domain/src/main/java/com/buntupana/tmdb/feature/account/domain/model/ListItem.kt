package com.buntupana.tmdb.feature.account.domain.model

import com.panabuntu.tmdb.core.common.model.DefaultItem
import java.time.LocalDate

data class ListItem(
    override val id: Long,
    val name: String,
    val backdropUrl: String?,
    val posterUrl: String?,
    val averageRating: Int?,
    val description: String?,
    val itemCount: Int,
    val isPublic: Boolean,
    val revenue: Int?,
    val runtime: Int?,
    val updatedAt: LocalDate?
) : DefaultItem