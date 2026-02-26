package com.buntupana.tmdb.core.data.api

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.buntupana.tmdb.core.data.database.dao.RemoteKeyDao
import com.buntupana.tmdb.core.data.database.entity.RemoteKeyEntity
import com.buntupana.tmdb.core.data.raw.ResponseListRaw
import com.panabuntu.tmdb.core.common.entity.NetworkError
import com.panabuntu.tmdb.core.common.entity.Result


@OptIn(ExperimentalPagingApi::class)
class GenericRemoteMediator<MODEL : Any, RAW_ITEM, RESPONSE: ResponseListRaw<RAW_ITEM>>(
    private val remoteType: String,
    private val networkCall: suspend (Int) -> Result<RESPONSE, NetworkError>,
    private val remoteKeyDao: RemoteKeyDao,
    private val clearTable: suspend () -> Unit = {},
    private val insertNetworkResult: suspend (resultList: List<RAW_ITEM>) -> Unit = {}
) : RemoteMediator<Int, MODEL>() {

    override suspend fun initialize(): InitializeAction {
        clearTable()
        return super.initialize()
    }

    override suspend fun load(loadType: LoadType, state: PagingState<Int, MODEL>): MediatorResult {
        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> {
                    return MediatorResult.Success(endOfPaginationReached = true)
                }
                // Query remoteKeyDao for the next RemoteKey.
                LoadType.APPEND -> {
                    val remoteKey = remoteKeyDao.remoteKeyByType(remoteType)
                    if (remoteKey?.nextKey == null) {
                        return MediatorResult.Success(endOfPaginationReached = true)
                    }

                    remoteKey.nextKey
                }
            }

            val response = when(val response = networkCall(loadKey)){
                is Result.Error -> return MediatorResult.Error(Exception())
                is Result.Success -> response
            }

            if (loadType == LoadType.REFRESH) {
                clearTable()
            }

            // Update RemoteKey for this query.

            val isLastPage = response.data.page == response.data.totalPages

            val nextKey = if (isLastPage) null else loadKey + 1

            remoteKeyDao.upsert(RemoteKeyEntity(remoteType, nextKey))

            insertNetworkResult(response.data.results)

            MediatorResult.Success(
                endOfPaginationReached = isLastPage
            )

        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }
}