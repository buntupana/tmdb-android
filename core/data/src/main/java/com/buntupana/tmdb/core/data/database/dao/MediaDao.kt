package com.buntupana.tmdb.core.data.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.buntupana.tmdb.core.data.database.entity.MediaEntity
import com.buntupana.tmdb.core.data.database.entity.MediaSimpleEntity
import com.panabuntu.tmdb.core.common.entity.MediaType
import kotlinx.coroutines.flow.Flow

@Dao
abstract class MediaDao {

    @Upsert
    abstract suspend fun upsert(mediaEntity: MediaEntity)

    @Upsert
    abstract suspend fun upsert(mediaEntityList: List<MediaEntity>)

    @Upsert(entity = MediaEntity::class)
    abstract suspend fun upsertSimple(mediaSimpleEntityList: List<MediaSimpleEntity>)

    @Query("SELECT * FROM media WHERE id = :mediaId AND mediaType = :mediaType")
    abstract fun get(mediaId: Long, mediaType: MediaType): Flow<MediaEntity>

    @Query(
        """
        SELECT media.* FROM media 
        INNER JOIN watchlist ON media.id = watchlist.mediaId 
        WHERE watchlist.mediaType = :mediaType
        ORDER BY watchlist.addedAt ASC
    """
    )
    abstract fun getWatchlist(mediaType: MediaType): PagingSource<Int, MediaEntity>

    @Query(
        """
        SELECT media.* FROM media 
        INNER JOIN favorite ON media.id = favorite.mediaId 
        WHERE favorite.mediaType = :mediaType
        ORDER BY favorite.addedAt ASC
    """
    )
    abstract fun getFavorites(mediaType: MediaType): PagingSource<Int, MediaEntity>

    @Query(
        """
        SELECT media.* FROM media 
        INNER JOIN user_list_item ON media.id = user_list_item.mediaId
        WHERE user_list_item.listId = :userListId
        ORDER BY user_list_item.addedAt ASC
    """
    )
    abstract fun getUserListItem(userListId: Long): PagingSource<Int, MediaEntity>

    @Query("UPDATE media SET isFavorite = :isFavorite WHERE id = :id AND mediaType = :mediaType")
    abstract suspend fun updateFavorite(id: Long, mediaType: MediaType, isFavorite: Boolean)

    @Query("UPDATE media SET isWatchListed = :isWatchListed WHERE id = :id AND mediaType = :mediaType")
    abstract suspend fun updateWatchList(id: Long, mediaType: MediaType, isWatchListed: Boolean)

    @Query("UPDATE media SET userRating = :rating WHERE id = :id AND mediaType = :mediaType")
    abstract suspend fun updateRating(id: Long, mediaType: MediaType, rating: Int?)

    @Transaction
    open suspend fun updateRatingAndWatchlist(id: Long, mediaType: MediaType, rating: Int?) {
        updateRating(id = id, mediaType = mediaType, rating = rating)
        if (rating != null && rating != 0) {
            updateWatchList(id = id, mediaType = mediaType, isWatchListed = false)
        }
    }

    @Query("DELETE FROM media WHERE id = :mediaId AND mediaType = :mediaType")
    abstract suspend fun deleteSeasonEpisodes(mediaId: Long, mediaType: String)

    @Delete
    abstract suspend fun delete(episodesEntityList: List<MediaEntity>)

    @Query("DELETE FROM media")
    abstract suspend fun clearAll()
}