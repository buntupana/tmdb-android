package com.buntupana.tmdb.feature.account.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.buntupana.tmdb.core.data.api.GenericPagingDataSource
import com.buntupana.tmdb.feature.account.data.mapper.toModel
import com.buntupana.tmdb.feature.account.data.remote_data_source.ListRemoteDataSource
import com.buntupana.tmdb.feature.account.domain.model.ListItem
import com.buntupana.tmdb.feature.account.domain.model.MediaItemBasic
import com.buntupana.tmdb.feature.account.domain.repository.ListRepository
import com.panabuntu.tmdb.core.common.entity.NetworkError
import com.panabuntu.tmdb.core.common.entity.Result
import com.panabuntu.tmdb.core.common.entity.asEmptyDataResult
import com.panabuntu.tmdb.core.common.entity.map
import com.panabuntu.tmdb.core.common.manager.SessionManager
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
            session.value.accountDetails?.accountObjectId.orEmpty()
        ).map { result ->
            result.totalResults
        }
    }

    override suspend fun getLists(): Result<List<ListItem>, NetworkError> {
        return listRemoteDataSource.getLists(
            session.value.accountDetails?.accountObjectId.orEmpty()
        ).map { result ->
            result.results.toModel(
                baseUrlPoster = urlProvider.BASE_URL_POSTER,
                baseUrlBackdrop = urlProvider.BASE_URL_BACKDROP
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
                    mapItem = {
                        it.toModel(
                            baseUrlPoster = urlProvider.BASE_URL_POSTER,
                            baseUrlBackdrop = urlProvider.BASE_URL_BACKDROP
                        )
                    }
                )
            }
        ).flow
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

    override suspend fun removeList(listId: Long): Result<Unit, NetworkError> {
        return listRemoteDataSource.removeList(listId = listId).asEmptyDataResult()
    }

    override suspend fun addMediaItemList(
        listId: Long,
        mediaItem: MediaItemBasic
    ): Result<Unit, NetworkError> {
        return listRemoteDataSource.addMediaItemList(
            listId = listId,
            mediaItemList = listOf(mediaItem)
        ).asEmptyDataResult()
    }

    override suspend fun removeMediaItemList(
        listId: Long,
        mediaItem: MediaItemBasic
    ): Result<Unit, NetworkError> {
        return listRemoteDataSource.removeMediaItemList(
            listId = listId,
            mediaItemList = listOf(mediaItem)
        ).asEmptyDataResult()
    }
}