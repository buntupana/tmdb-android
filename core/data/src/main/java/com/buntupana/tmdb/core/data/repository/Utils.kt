package com.buntupana.tmdb.core.data.repository

import com.buntupana.tmdb.core.data.raw.ResponseListRaw
import com.panabuntu.tmdb.core.common.entity.NetworkError
import com.panabuntu.tmdb.core.common.entity.Result
import com.panabuntu.tmdb.core.common.entity.onError
import com.panabuntu.tmdb.core.common.entity.onSuccess

suspend fun <RAW, MODEL> getAllItemsFromPaging(
    networkCall: suspend (page: Int) -> Result<ResponseListRaw<RAW>, NetworkError>,
    mapItemList: (List<RAW>) -> List<MODEL>
): Result<List<MODEL>, NetworkError> {

    var page: Int? = 1
    val lists = mutableListOf<MODEL>()
    var networkError: NetworkError? = null

    while (page != null) {
        networkCall(page).onSuccess {
            if (it.results.isNotEmpty()) {
                lists.addAll(mapItemList(it.results))
                page = it.page + 1
            } else {
                page = null
            }
        }.onError {
            networkError = it
            page = null
        }
    }

    return if (networkError != null) {
        Result.Error(networkError!!)
    } else {
        Result.Success(lists)
    }
}