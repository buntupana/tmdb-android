package com.buntupana.tmdb.core.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.buntupana.tmdb.core.data.database.entity.EpisodeEntity
import kotlinx.coroutines.flow.Flow

@Dao
abstract class EpisodesDao {

    @Upsert
    abstract suspend fun upsertEpisodes(episodesEntityList: List<EpisodeEntity>)

    @Query("SELECT * FROM episodeentity WHERE showId = :showId AND seasonNumber = :seasonNumber")
    abstract fun getEpisodes(showId: Long, seasonNumber: Int): Flow<List<EpisodeEntity>>

    @Query("DELETE FROM episodeentity WHERE showId = :showId AND seasonNumber = :seasonNumber")
    abstract suspend fun deleteSeasonEpisodes(showId: Long, seasonNumber: Int)

    @Delete
    abstract suspend fun deleteEpisodes(episodesEntityList: List<EpisodeEntity>)

    @Query("UPDATE episodeentity SET userRating = :rating WHERE showId = :tvShowId AND seasonNumber = :seasonNumber AND episodeNumber = :episodeNumber")
    abstract suspend fun updateRating(
        tvShowId: Long,
        seasonNumber: Int,
        episodeNumber: Int,
        rating: Int?
    )

    @Query("DELETE FROM episodeentity")
    abstract suspend fun clearAll()

}