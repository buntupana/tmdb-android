package com.buntupana.tmdb.core.data.util

import com.buntupana.tmdb.core.data.raw.ResponseListRaw
import com.panabuntu.tmdb.core.common.entity.NetworkError
import com.panabuntu.tmdb.core.common.entity.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

suspend fun <RAW, ENTITY, MODEL> getFlowResult(
    prevDataBaseQuery: suspend () -> Unit = {},
    networkCall: suspend () -> Result<RAW, NetworkError>,
    mapToEntity: (raw: RAW) -> ENTITY,
    updateDataBaseQuery: suspend (entity: ENTITY) -> Unit = {},
    fetchFromDataBaseQuery: suspend () -> Flow<ENTITY>,
    mapToModel: (entity: ENTITY) -> MODEL
): Flow<Result<MODEL, NetworkError>> {

    return when (val result = networkCall()) {
        is Result.Error -> flowOf(result)
        is Result.Success -> {
            prevDataBaseQuery()
            val entity = mapToEntity(result.data)
            updateDataBaseQuery(entity)
            fetchFromDataBaseQuery().map {
                Result.Success(mapToModel(it))
            }
        }
    }
}

suspend fun <RAW, ENTITY, MODEL> getFlowListResult(
    prevDataBaseQuery: suspend () -> Unit = {},
    networkCall: suspend () -> Result<ResponseListRaw<RAW>, NetworkError>,
    mapToEntity: (rawList: List<RAW>) -> List<ENTITY>,
    updateDataBaseQuery: suspend (entityList: List<ENTITY>) -> Unit = {},
    fetchFromDataBaseQuery: suspend () -> Flow<List<ENTITY>>,
    mapToModel: (entityList: List<ENTITY>) -> List<MODEL>
): Flow<Result<List<MODEL>, NetworkError>> {

    return when (val result = networkCall()) {
        is Result.Error -> flowOf(result)
        is Result.Success -> {
            prevDataBaseQuery()
            val entity = mapToEntity(result.data.results)
            updateDataBaseQuery(entity)

            fetchFromDataBaseQuery().map {
                if (it.isEmpty()) {
                    Result.Success(emptyList())
                } else {
                    Result.Success(mapToModel(it))
                }
            }
        }
    }
}