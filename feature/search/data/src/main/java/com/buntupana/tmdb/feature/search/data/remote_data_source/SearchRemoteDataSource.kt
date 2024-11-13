package com.buntupana.tmdb.feature.search.data.remote_data_source

import com.buntupana.tmdb.feature.search.data.api.SearchApi
import com.buntupana.tmdb.feature.search.data.raw.PersonRaw
import com.panabuntu.tmdb.core.common.entity.Resource
import com.panabuntu.tmdb.core.common.remote_data_source.RemoteDataSource
import javax.inject.Inject

class SearchRemoteDataSource @Inject constructor(
    private val searchApi: SearchApi
) : RemoteDataSource() {

    suspend fun getTrending(): Resource<com.panabuntu.tmdb.core.common.raw.ResponseListRaw<com.panabuntu.tmdb.core.common.raw.AnyMediaItemRaw>> {
        return getResourceResult { searchApi.fetchTrending() }
    }

    suspend fun getSearchMedia(searchKey: String): Resource<com.panabuntu.tmdb.core.common.raw.ResponseListRaw<com.panabuntu.tmdb.core.common.raw.AnyMediaItemRaw>> {
        return getResourceResult { searchApi.fetchSearchMedia(searchKey) }
    }

    suspend fun getSearchMovies(
        searchKey: String,
        page: Int
    ): Resource<com.panabuntu.tmdb.core.common.raw.ResponseListRaw<com.panabuntu.tmdb.core.common.raw.MovieItemRaw>> {
        return getResourceResult { searchApi.fetchSearchMovies(searchKey, page) }
    }

    suspend fun getSearchTvShows(
        searchKey: String,
        page: Int
    ): Resource<com.panabuntu.tmdb.core.common.raw.ResponseListRaw<com.panabuntu.tmdb.core.common.raw.TvShowRaw>> {
        return getResourceResult { searchApi.fetchSearchTvShows(searchKey, page) }
    }

    suspend fun getSearchPersons(
        searchKey: String,
        page: Int
    ): Resource<com.panabuntu.tmdb.core.common.raw.ResponseListRaw<PersonRaw>> {
        return getResourceResult { searchApi.fetchSearchPersons(searchKey, page) }
    }
}