package com.buntupana.tmdb.feature.search.data.remote_data_source

import com.buntupana.tmdb.data.raw.AnyMediaItemRaw
import com.buntupana.tmdb.data.raw.MovieItemRaw
import com.buntupana.tmdb.data.raw.ResponseListRaw
import com.buntupana.tmdb.data.raw.TvShowRaw
import com.buntupana.tmdb.feature.search.data.api.SearchApi
import com.buntupana.tmdb.feature.search.data.raw.PersonRaw
import com.panabuntu.tmdb.core.common.entity.Resource
import javax.inject.Inject

class SearchRemoteDataSource @Inject constructor(
    private val searchApi: SearchApi
) : com.buntupana.tmdb.data.remote_data_source.RemoteDataSource() {

    suspend fun getTrending(): Resource<ResponseListRaw<AnyMediaItemRaw>> {
        return getResourceResult { searchApi.fetchTrending() }
    }

    suspend fun getSearchMedia(searchKey: String): Resource<ResponseListRaw<AnyMediaItemRaw>> {
        return getResourceResult { searchApi.fetchSearchMedia(searchKey) }
    }

    suspend fun getSearchMovies(
        searchKey: String,
        page: Int
    ): Resource<ResponseListRaw<MovieItemRaw>> {
        return getResourceResult { searchApi.fetchSearchMovies(searchKey, page) }
    }

    suspend fun getSearchTvShows(
        searchKey: String,
        page: Int
    ): Resource<ResponseListRaw<TvShowRaw>> {
        return getResourceResult { searchApi.fetchSearchTvShows(searchKey, page) }
    }

    suspend fun getSearchPersons(
        searchKey: String,
        page: Int
    ): Resource<ResponseListRaw<PersonRaw>> {
        return getResourceResult { searchApi.fetchSearchPersons(searchKey, page) }
    }
}