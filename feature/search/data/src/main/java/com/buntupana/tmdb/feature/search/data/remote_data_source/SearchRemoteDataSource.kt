package com.buntupana.tmdb.feature.search.data.remote_data_source

import com.buntupana.tmdb.core.data.raw.AnyMediaItemRaw
import com.buntupana.tmdb.core.data.raw.MovieItemRaw
import com.buntupana.tmdb.core.data.raw.ResponseListRaw
import com.buntupana.tmdb.core.data.raw.TvShowRaw
import com.buntupana.tmdb.core.data.remote_data_source.RemoteDataSource
import com.buntupana.tmdb.feature.search.data.raw.PersonRaw
import com.panabuntu.tmdb.core.common.entity.NetworkError
import com.panabuntu.tmdb.core.common.entity.Result
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import javax.inject.Inject

class SearchRemoteDataSource @Inject constructor(
    private val httpClient: HttpClient
) : RemoteDataSource() {

    suspend fun getTrending(): Result<ResponseListRaw<AnyMediaItemRaw>, NetworkError> {
        return getResult {
            httpClient.get("trending/all/day")
        }
    }

    suspend fun getSearchMedia(searchKey: String): Result<ResponseListRaw<AnyMediaItemRaw>, NetworkError> {
        return getResult {
            httpClient.get("search/multi") {
                parameter("query", searchKey)
            }
        }
    }

    suspend fun getSearchMovies(
        searchKey: String,
        page: Int
    ): Result<ResponseListRaw<MovieItemRaw>, NetworkError> {
        return getResult {
            httpClient.get("search/movie") {
                parameter("query", searchKey)
                parameter("page", page)
            }
        }
    }

    suspend fun getSearchTvShows(
        searchKey: String,
        page: Int
    ): Result<ResponseListRaw<TvShowRaw>, NetworkError> {
        return getResult {
            httpClient.get("search/tv") {
                parameter("query", searchKey)
                parameter("page", page)
            }
        }
    }

    suspend fun getSearchPersons(
        searchKey: String,
        page: Int
    ): Result<ResponseListRaw<PersonRaw>, NetworkError> {
        return getResult {
            httpClient.get("search/person") {
                parameter("query", searchKey)
                parameter("page", page)
            }
        }
    }
}