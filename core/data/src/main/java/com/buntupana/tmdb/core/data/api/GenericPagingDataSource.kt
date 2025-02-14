package com.buntupana.tmdb.core.data.api

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.buntupana.tmdb.core.data.raw.ResponseListRaw
import com.panabuntu.tmdb.core.common.entity.NetworkError
import com.panabuntu.tmdb.core.common.entity.Result
import timber.log.Timber
import java.util.concurrent.CancellationException


class GenericPagingDataSource<ITEM : Any, RAW_ITEM, RESPONSE : ResponseListRaw<RAW_ITEM>>(
    private val networkCall: suspend (Int) -> Result<RESPONSE, NetworkError>,
    private val mapItemList: (List<RAW_ITEM>) -> List<ITEM>
) : PagingSource<Int, ITEM>() {

    private var nextPageNumber: Int = 1

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ITEM> {
        nextPageNumber = params.key ?: 1

        return when (val response = networkCall.invoke(nextPageNumber)) {
            is Result.Success -> {
                try {

                    val items = mapItemList(response.data.results)

                    val prevKey = response.data.page - 1
                    val nextKey = response.data.page + 1

                    LoadResult.Page(
                        items,
                        if (prevKey < 1) null else prevKey,
                        if (items.isEmpty()) null else nextKey
                    )
                } catch (e: Exception) {
                    if (e is CancellationException) {
                        throw e
                    }
                    Timber.e(e, "Mapping error in pagination")
                    LoadResult.Error(Exception())
                }
            }
            is Result.Error -> {
                LoadResult.Error(Exception())
            }
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ITEM>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}