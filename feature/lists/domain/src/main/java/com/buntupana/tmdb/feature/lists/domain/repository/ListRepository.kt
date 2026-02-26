package com.buntupana.tmdb.feature.lists.domain.repository

import androidx.paging.PagingData
import com.buntupana.tmdb.feature.lists.domain.model.UserListDetails
import com.panabuntu.tmdb.core.common.entity.MediaType
import com.panabuntu.tmdb.core.common.entity.NetworkError
import com.panabuntu.tmdb.core.common.entity.Result
import com.panabuntu.tmdb.core.common.model.MediaItem
import com.panabuntu.tmdb.core.common.model.Order
import kotlinx.coroutines.flow.Flow

interface ListRepository {

    suspend fun getListTotalCount(): Flow<Result<Int, NetworkError>>

    suspend fun getListsPaging(): Flow<PagingData<UserListDetails>>

    suspend fun checkIfItemInList(
        listId: Long,
        mediaId: Long,
        mediaType: MediaType
    ): Result<Unit, NetworkError>

    suspend fun createList(
        name: String,
        description: String,
        isPublic: Boolean
    ): Result<Unit, NetworkError>

    suspend fun updateList(
        listId: Long,
        name: String,
        description: String,
        isPublic: Boolean
    ): Result<Unit, NetworkError>

    suspend fun deleteList(listId: Long): Result<Unit, NetworkError>

    suspend fun addMediaItemList(
        listId: Long,
        mediaId: Long,
        mediaType: MediaType
    ): Result<Unit, NetworkError>

    suspend fun deleteMediaItemList(
        listId: Long,
        mediaId: Long,
        mediaType: MediaType
    ): Result<Unit, NetworkError>

    suspend fun getListDetails(listId: Long): Flow<Result<UserListDetails?, NetworkError>>

    fun getListItems(listId: Long): Flow<PagingData<MediaItem>>

    suspend fun getLists(justFirstPage: Boolean = false): Flow<Result<List<UserListDetails>, NetworkError>>

    suspend fun getWatchlistMoviePaging(order: Order): Flow<PagingData<MediaItem.Movie>>

    suspend fun getWatchlistTvShowPaging(order: Order): Flow<PagingData<MediaItem.TvShow>>

    suspend fun getWatchlistMovies(): Flow<Result<List<MediaItem>, NetworkError>>

    suspend fun getWatchlistTvShows(): Flow<Result<List<MediaItem>, NetworkError>>

    suspend fun setMediaFavorite(
        mediaId: Long,
        mediaType: MediaType,
        isFavorite: Boolean
    ): Result<Unit, NetworkError>

    suspend fun setMediaWatchList(
        mediaId: Long,
        mediaType: MediaType,
        isWatchlisted: Boolean
    ): Result<Unit, NetworkError>

    suspend fun getWatchlistMoviesTotalCount(): Flow<Result<Int, NetworkError>>

    suspend fun getWatchlistTvShowsTotalCount(): Flow<Result<Int, NetworkError>>

    suspend fun getFavoriteMoviesTotalCount(): Flow<Result<Int, NetworkError>>

    suspend fun getFavoriteMovies(): Flow<Result<List<MediaItem>, NetworkError>>

    suspend fun getFavoriteMoviePaging(order: Order): Flow<PagingData<MediaItem.Movie>>

    suspend fun getFavoriteTvShowsTotalCount(): Flow<Result<Int, NetworkError>>

    suspend fun getFavoriteTvShows(): Flow<Result<List<MediaItem>, NetworkError>>

    suspend fun getFavoriteTvShowPaging(order: Order): Flow<PagingData<MediaItem.TvShow>>
}