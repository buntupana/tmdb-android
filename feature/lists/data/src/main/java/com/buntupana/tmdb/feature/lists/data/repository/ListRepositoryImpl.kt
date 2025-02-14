package com.buntupana.tmdb.feature.lists.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.buntupana.tmdb.core.data.api.GenericPagingDataSource
import com.buntupana.tmdb.core.data.mapper.toModel
import com.buntupana.tmdb.core.data.repository.getAllItemsFromPaging
import com.buntupana.tmdb.feature.lists.data.mapper.toModel
import com.buntupana.tmdb.feature.lists.data.remote_data_source.ListRemoteDataSource
import com.buntupana.tmdb.feature.lists.domain.model.ListDetail
import com.buntupana.tmdb.feature.lists.domain.model.ListItem
import com.buntupana.tmdb.feature.lists.domain.model.MediaItemBasic
import com.buntupana.tmdb.feature.lists.domain.repository.ListRepository
import com.panabuntu.tmdb.core.common.entity.MediaType
import com.panabuntu.tmdb.core.common.entity.NetworkError
import com.panabuntu.tmdb.core.common.entity.Result
import com.panabuntu.tmdb.core.common.entity.asEmptyDataResult
import com.panabuntu.tmdb.core.common.entity.map
import com.panabuntu.tmdb.core.common.manager.SessionManager
import com.panabuntu.tmdb.core.common.model.MediaItem
import com.panabuntu.tmdb.core.common.provider.UrlProvider
import com.panabuntu.tmdb.core.common.util.Const.PAGINATION_SIZE
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ListRepositoryImpl @Inject constructor(
    private val listRemoteDataSource: ListRemoteDataSource,
    private val urlProvider: UrlProvider,
    sessionManager: SessionManager
) : ListRepository {

    private val session = sessionManager.session

    override suspend fun getListTotalCount(): Result<Int, NetworkError> {
        return listRemoteDataSource.getLists(
            accountObjectId = session.value.accountDetails?.accountObjectId.orEmpty()
        ).map { result ->
            result.totalResults
        }
    }

    override suspend fun getLists(justFirstPage: Boolean): Result<List<ListItem>, NetworkError> {

        return if (justFirstPage) {
            listRemoteDataSource.getLists(
                accountObjectId = session.value.accountDetails?.accountObjectId.orEmpty()
            ).map { result ->
                result.results.toModel(
                    baseUrlPoster = urlProvider.BASE_URL_POSTER,
                    baseUrlBackdrop = urlProvider.BASE_URL_BACKDROP
                )
            }
        } else {
            getAllItemsFromPaging(
                networkCall = { page ->
                    listRemoteDataSource.getLists(
                        accountObjectId = session.value.accountDetails?.accountObjectId.orEmpty(),
                        page = page
                    )
                },
                mapItemList = {
                    it.toModel(
                        baseUrlPoster = urlProvider.BASE_URL_POSTER,
                        baseUrlBackdrop = urlProvider.BASE_URL_BACKDROP
                    )
                }
            )
        }
    }

    override suspend fun getListsPaging(): Flow<PagingData<ListItem>> {

        return Pager(
            config = PagingConfig(
                pageSize = PAGINATION_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                GenericPagingDataSource(
                    networkCall = { page ->
                        listRemoteDataSource.getLists(
                            accountObjectId = session.value.accountDetails?.accountObjectId.orEmpty(),
                            page = page
                        )
                    },
                    mapItemList = {
                        it.toModel(
                            baseUrlPoster = urlProvider.BASE_URL_POSTER,
                            baseUrlBackdrop = urlProvider.BASE_URL_BACKDROP
                        )
                    }
                )
            }
        ).flow
    }

    override suspend fun checkIfitemInList(
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
            isPublic = isPublic
        ).asEmptyDataResult()
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
        ).asEmptyDataResult()
    }

    override suspend fun deleteList(listId: Long): Result<Unit, NetworkError> {
        return listRemoteDataSource.deleteList(listId = listId).asEmptyDataResult()
    }

    override suspend fun addMediaItemList(
        listId: Long,
        mediaId: Long,
        mediaType: MediaType
    ): Result<Unit, NetworkError> {
        return listRemoteDataSource.addMediaItemList(
            listId = listId,
            mediaItemList = listOf(MediaItemBasic(mediaId, mediaType))
        ).asEmptyDataResult()
    }

    override suspend fun deleteMediaItemList(
        listId: Long,
        mediaId: Long,
        mediaType: MediaType
    ): Result<Unit, NetworkError> {
        return listRemoteDataSource.removeMediaItemList(
            listId = listId,
            mediaItemList = listOf(MediaItemBasic(mediaId, mediaType))
        ).asEmptyDataResult()
    }

    override suspend fun getListDetails(listId: Long): Result<ListDetail, NetworkError> {
        return listRemoteDataSource.getListDetail(listId).map {
            it.toModel(baseUrlBackdrop = urlProvider.BASE_URL_BACKDROP)
        }
    }

    override fun getListItems(listId: Long): Flow<PagingData<MediaItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGINATION_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                GenericPagingDataSource(
                    networkCall = { page ->
                        listRemoteDataSource.getListItems(
                            listId = listId,
                            page = page
                        )
                    },
                    mapItemList = {
                        it.toModel(
                            baseUrlPoster = urlProvider.BASE_URL_POSTER,
                            baseUrlBackdrop = urlProvider.BASE_URL_BACKDROP
                        )
                    }
                )
            }
        ).flow
    }
}