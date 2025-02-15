package com.buntupana.tmdb.core.data.util

import com.panabuntu.tmdb.core.common.entity.NetworkError
import com.panabuntu.tmdb.core.common.entity.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

suspend fun <RAW, ENTITY, MODEL> getFlowResult(
    prevDataBaseQuery: suspend () -> Unit = {},
    networkCall: suspend () -> Result<RAW, NetworkError>,
    mapToEntity: (raw: RAW) -> ENTITY,
    updateDataBaseQuery: suspend (raw: ENTITY) -> Unit = {},
    dataBaseQuery: suspend () -> Flow<ENTITY>,
    mapToModel: (entity: ENTITY) -> MODEL
): Flow<Result<MODEL, NetworkError>> {

    return when (val result = networkCall()) {
        is Result.Error -> flowOf(result)
        is Result.Success -> {
            prevDataBaseQuery()
            val entity = mapToEntity(result.data)
            updateDataBaseQuery(entity)
            dataBaseQuery().map { Result.Success(mapToModel(it)) }
        }
    }
}