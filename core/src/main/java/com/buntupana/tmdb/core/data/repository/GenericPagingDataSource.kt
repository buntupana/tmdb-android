package com.buntupana.tmdb.core.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.buntupana.tmdb.core.data.raw.ResponseListRaw
import com.buntupana.tmdb.core.domain.entity.Resource
import timber.log.Timber
import java.util.concurrent.CancellationException


class GenericPagingDataSource<ITEM : Any, RAW_ITEM, RESPONSE : ResponseListRaw<RAW_ITEM>>(
    private val networkCall: suspend (Int) -> Resource<RESPONSE>,
    private val mappItem: (RAW_ITEM) -> ITEM
) : PagingSource<Int, ITEM>() {

    private var lastCursor: Int = 1

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ITEM> {
        lastCursor = params.key ?: 1

        return when(val response = networkCall.invoke(lastCursor)) {
            is Resource.Success -> {
                try {
                    val items = response.data.results.map {
                        mappItem(it)
                    }

                    val prevKey = response.data.page - 1
                    val nextKey = response.data.page + 1

                    LoadResult.Page(
                        items,
                        if(prevKey < 1) null else prevKey,
                        nextKey
                    )
                } catch (e: Exception) {
                    if (e is CancellationException) {
                        throw e
                    }
                    Timber.e(e, "Mapping error in pagination")
                    LoadResult.Error(Exception())
                }
            }
            is Resource.Error -> {
                LoadResult.Error(Exception(response.message))
            }
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ITEM>): Int {
        return lastCursor
    }
}