package com.buntupana.tmdb.core.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "episode")
data class EpisodeEntity(
    @PrimaryKey
    val id: Long,
    val showId: Long? = null,
    val seasonNumber: Int,
    val episodeNumber: Int,
    val name: String,
    val overview: String? = null,
    val productionCode: String? = null,
    val runtime: Int? = null,
    val airDate: String? = null,
    val stillPath: String? = null,
    val voteAverage: Float? = null,
    val episodeType: String? = null,
    val voteCount: Int? = null,
    val guestStarList: String,
    val crewList: String,
    val userRating: Int?
)