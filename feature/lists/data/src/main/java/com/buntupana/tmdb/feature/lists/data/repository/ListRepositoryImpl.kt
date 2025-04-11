package com.buntupana.tmdb.feature.lists.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.buntupana.tmdb.core.data.api.GenericRemoteMediator
import com.buntupana.tmdb.core.data.database.dao.MediaDao
import com.buntupana.tmdb.core.data.database.dao.RemoteKeyDao
import com.buntupana.tmdb.core.data.database.dao.UserListDetailsDao
import com.buntupana.tmdb.core.data.database.dao.UserListItemDao
import com.buntupana.tmdb.core.data.database.entity.UserListDetailsEntity
import com.buntupana.tmdb.core.data.database.entity.UserListItemEntity
import com.buntupana.tmdb.core.data.mapper.toEntity
import com.buntupana.tmdb.core.data.mapper.toItemModel
import com.buntupana.tmdb.core.data.repository.getAllItemsFromPaging
import com.buntupana.tmdb.core.data.util.getFlowListResult
import com.buntupana.tmdb.feature.lists.data.mapper.toEntity
import com.buntupana.tmdb.feature.lists.data.mapper.toModel
import com.buntupana.tmdb.feature.lists.data.remote_data_source.ListRemoteDataSource
import com.buntupana.tmdb.feature.lists.domain.model.MediaItemBasic
import com.buntupana.tmdb.feature.lists.domain.model.UserListDetails
import com.buntupana.tmdb.feature.lists.domain.repository.ListRepository
import com.panabuntu.tmdb.core.common.entity.MediaType
import com.panabuntu.tmdb.core.common.entity.NetworkError
import com.panabuntu.tmdb.core.common.entity.Result
import com.panabuntu.tmdb.core.common.entity.asEmptyDataResult
import com.panabuntu.tmdb.core.common.entity.map
import com.panabuntu.tmdb.core.common.entity.onSuccess
import com.panabuntu.tmdb.core.common.manager.SessionManager
import com.panabuntu.tmdb.core.common.model.MediaItem
import com.panabuntu.tmdb.core.common.provider.UrlProvider
import com.panabuntu.tmdb.core.common.util.Const.PAGINATION_SIZE
import com.panabuntu.tmdb.core.common.util.toFullDateTimeUTC
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.time.LocalDateTime
import javax.inject.Inject

class ListRepositoryImpl @Inject constructor(
    private val listRemoteDataSource: ListRemoteDataSource,
    private val remoteKeyDao: RemoteKeyDao,
    private val mediaDao: MediaDao,
    private val userListDetailsDao: UserListDetailsDao,
    private val userListItemDao: UserListItemDao,
    private val urlProvider: UrlProvider,
    sessionManager: SessionManager
) : ListRepository {

    private val session = sessionManager.session

    private var listsTotalCount = MutableStateFlow(0)

    override suspend fun getListTotalCount(): Flow<Result<Int, NetworkError>> {
        val result = listRemoteDataSource.getLists(
            accountObjectId = session.value.accountDetails?.accountObjectId.orEmpty()
        ).map { result ->
            listsTotalCount.value = result.totalResults
        }

        return flow {
            when (result) {
                is Result.Error -> emit(result)
                is Result.Success -> {
                    emitAll(listsTotalCount.map { Result.Success(it) })
                }
            }
        }
    }

    override suspend fun getLists(justFirstPage: Boolean): Flow<Result<List<UserListDetails>, NetworkError>> {

        val result = if (justFirstPage) {
            listRemoteDataSource.getLists(
                accountObjectId = session.value.accountDetails?.accountObjectId.orEmpty()
            )
        } else {
            getAllItemsFromPaging(
                networkCall = { page ->
                    listRemoteDataSource.getLists(
                        accountObjectId = session.value.accountDetails?.accountObjectId.orEmpty(),
                        page = page
                    )
                }
            )
        }

        return getFlowListResult(
            prevDataBaseQuery = {
                userListDetailsDao.clearAll()
            },
            networkCall = { result },
            mapToEntity = { it.toEntity() },
            updateDataBaseQuery = {
                userListDetailsDao.upsert(it)
            },
            fetchFromDataBaseQuery = { userListDetailsDao.getAll() },
            mapToModel = {
                it.map {
                    it.toModel(
                        baseUrlPoster = urlProvider.BASE_URL_POSTER,
                        baseUrlBackdrop = urlProvider.BASE_URL_BACKDROP
                    )
                }
            }
        )
    }

    @OptIn(ExperimentalPagingApi::class)
    override suspend fun getListsPaging(): Flow<PagingData<UserListDetails>> {

        val remoteType = "user_list_details"

        return Pager(
            config = PagingConfig(
                pageSize = PAGINATION_SIZE,
                enablePlaceholders = false
            ),
            remoteMediator = GenericRemoteMediator(
                remoteType = remoteType,
                networkCall = { page ->
                    listRemoteDataSource.getLists(
                        accountObjectId = session.value.accountDetails?.accountObjectId.orEmpty(),
                        page = page,
                    )
                },
                remoteKeyDao = remoteKeyDao,
                clearTable = {
                    userListDetailsDao.clearAll()
                    remoteKeyDao.remoteKeyByType(remoteType)
                },
                insertNetworkResult = { resultList ->
                    userListDetailsDao.upsert(resultList.toEntity())
                }
            ),
            pagingSourceFactory = {
                userListDetailsDao.getAllPS()
            }
        ).flow.map { pagingData ->
            pagingData.map {
                it.toModel(
                    baseUrlPoster = urlProvider.BASE_URL_POSTER,
                    baseUrlBackdrop = urlProvider.BASE_URL_BACKDROP
                )
            }
        }
    }

    override suspend fun checkIfItemInList(
        listId: Long,
        mediaId: Long,
        mediaType: MediaType
    ): Result<Unit, NetworkError> {
        return listRemoteDataSource.checkItemInList(
            listId = listId,
            mediaId = mediaId,
            mediaType = mediaType
        )
    }

    override suspend fun createList(
        name: String,
        description: String,
        isPublic: Boolean
    ): Result<Unit, NetworkError> {
        return listRemoteDataSource.createList(
            name = name,
            description = description,
            isPublic = isPublic,
        ).onSuccess {
            userListDetailsDao.upsert(
                UserListDetailsEntity(
                    id = it.id,
                    name = name,
                    description = description,
                    isPublic = isPublic,
                    backdropPath = null,
                    averageRating = null,
                    iso31661 = "",
                    iso6391 = "",
                    itemCount = 0,
                    posterPath = null,
                    revenue = null,
                    runtime = null,
                    createdAt = LocalDateTime.now().toFullDateTimeUTC(),
                    updatedAt = LocalDateTime.now().toFullDateTimeUTC()
                )
            )
            listsTotalCount.value += 1
        }.asEmptyDataResult()
    }

    override suspend fun updateList(
        listId: Long,
        name: String,
        description: String,
        isPublic: Boolean
    ): Result<Unit, NetworkError> {
        return listRemoteDataSource.updateList(
            listId = listId,
            name = name,
            description = description,
            isPublic = isPublic
        ).onSuccess {
            val entity = userListDetailsDao.getById(listId).firstOrNull() ?: return@onSuccess

            userListDetailsDao.upsert(
                entity.copy(
                    name = name,
                    description = description,
                    isPublic = isPublic,
                    updatedAt = LocalDateTime.now().toFullDateTimeUTC()
                )
            )
        }.asEmptyDataResult()
    }

    override suspend fun deleteList(listId: Long): Result<Unit, NetworkError> {
        return listRemoteDataSource.deleteList(listId = listId)
            .onSuccess {
                userListDetailsDao.deleteById(listId)
                listsTotalCount.value -= 1
            }
            .asEmptyDataResult()
    }

    override suspend fun getListDetails(listId: Long): Flow<Result<UserListDetails?, NetworkError>> {

        return flow {
            when (val result = listRemoteDataSource.getListDetail(listId = listId)) {
                is Result.Error -> emit(result)
                is Result.Success -> {
                    val entity = result.data.toEntity()
                    userListDetailsDao.upsert(entity)
                    emitAll(
                        userListDetailsDao.getById(listId).map {
                            Result.Success(
                                it?.toModel(
                                    baseUrlBackdrop = urlProvider.BASE_URL_BACKDROP,
                                    baseUrlPoster = urlProvider.BASE_URL_POSTER
                                )
                            )
                        })
                }
            }
        }
    }

    @OptIn(ExperimentalPagingApi::class)
    override fun getListItems(listId: Long): Flow<PagingData<MediaItem>> {
        val remoteType = "user_list_$listId"

        return Pager(
            config = PagingConfig(
                pageSize = PAGINATION_SIZE,
                enablePlaceholders = false
            ),
            remoteMediator = GenericRemoteMediator(
                remoteType = remoteType,
                networkCall = { page ->
                    listRemoteDataSource.getListItems(
                        listId = listId,
                        page = page
                    )
                },
                remoteKeyDao = remoteKeyDao,
                clearTable = {
                    userListItemDao.clearByListId(listId)
                    remoteKeyDao.remoteKeyByType(remoteType)
                },
                insertNetworkResult = { resultList ->
                    mediaDao.upsertSimple(resultList.toEntity())
                    var addedAt = System.currentTimeMillis()
                    userListItemDao.upsert(
                        resultList.map {
                            addedAt += 1
                            UserListItemEntity(
                                listId = listId,
                                mediaId = it.id,
                                mediaType = MediaType.fromValue(it.mediaType),
                                addedAt = addedAt
                            )
                        }
                    )
                }
            ),
            pagingSourceFactory = {
                mediaDao.getUserListItem(listId)
            }
        ).flow.map { pagingData ->
            pagingData.map {
                it.toItemModel(
                    baseUrlPoster = urlProvider.BASE_URL_POSTER,
                    baseUrlBackdrop = urlProvider.BASE_URL_BACKDROP
                )
            }
        }
    }

    override suspend fun addMediaItemList(
        listId: Long,
        mediaId: Long,
        mediaType: MediaType
    ): Result<Unit, NetworkError> {
        return listRemoteDataSource.addMediaItemList(
            listId = listId,
            mediaItemList = listOf(MediaItemBasic(mediaId, mediaType))
        ).onSuccess {
            userListItemDao.insert(
                listId = listId,
                mediaId = mediaId,
                mediaType = mediaType
            )
            userListDetailsDao.addItemCount(listId)
        }.asEmptyDataResult()
    }

    override suspend fun deleteMediaItemList(
        listId: Long,
        mediaId: Long,
        mediaType: MediaType
    ): Result<Unit, NetworkError> {
        return listRemoteDataSource.removeMediaItemList(
            listId = listId,
            mediaItemList = listOf(MediaItemBasic(mediaId, mediaType))
        ).onSuccess {
            userListItemDao.delete(
                listId = listId,
                mediaId = mediaId,
                mediaType = mediaType
            )
            userListDetailsDao.restItemCount(listId)
        }.asEmptyDataResult()
    }
}