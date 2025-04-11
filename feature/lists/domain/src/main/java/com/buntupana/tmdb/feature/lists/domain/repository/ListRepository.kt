package com.buntupana.tmdb.feature.lists.domain.repository

import androidx.paging.PagingData
import com.buntupana.tmdb.feature.lists.domain.model.UserListDetails
import com.panabuntu.tmdb.core.common.entity.MediaType
import com.panabuntu.tmdb.core.common.entity.NetworkError
import com.panabuntu.tmdb.core.common.entity.Result
import com.panabuntu.tmdb.core.common.model.MediaItem
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
}