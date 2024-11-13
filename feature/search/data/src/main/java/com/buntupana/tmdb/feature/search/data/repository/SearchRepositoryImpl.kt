package com.buntupana.tmdb.feature.search.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.buntupana.tmdb.feature.search.data.mapper.toModel
import com.buntupana.tmdb.feature.search.data.mapper.toSearchModel
import com.buntupana.tmdb.feature.search.data.remote_data_source.SearchRemoteDataSource
import com.panabuntu.tmdb.core.common.GenericPagingDataSource
import com.panabuntu.tmdb.core.common.entity.Resource
import com.panabuntu.tmdb.core.common.mapper.toModel
import com.panabuntu.tmdb.core.common.networkResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val searchRemoteDataSource: SearchRemoteDataSource
) : com.buntupana.tmdb.feature.search.domain.repository.SearchRepository {

    override suspend fun getTrendingMedia(): Resource<List<com.buntupana.tmdb.feature.search.domain.model.SearchItem>> {
        return networkResult(
            networkCall = { searchRemoteDataSource.getTrending() },
            mapResponse = { response -> response.results.toSearchModel() }
        )
    }

    override suspend fun getSearchMedia(searchKey: String): Resource<List<com.buntupana.tmdb.feature.search.domain.model.SearchItem>> {
        return networkResult(
            networkCall = { searchRemoteDataSource.getSearchMedia(searchKey) },
            mapResponse = { response -> response.results.toSearchModel() }
        )
    }

    override suspend fun getSearchMovies(searchKey: String): Flow<PagingData<com.panabuntu.tmdb.core.common.model.MediaItem.Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                GenericPagingDataSource(
                    networkCall = { page ->
                        searchRemoteDataSource.getSearchMovies(searchKey, page)
                    },
                    mappItem = {
                        it.toModel()
                    }
                )
            }
        ).flow
    }

    override suspend fun getSearchMoviesResultCount(searchKey: String): Resource<Int> {
        return networkResult(
            networkCall = { searchRemoteDataSource.getSearchMovies(searchKey, 1) },
            mapResponse = { it.totalResults }
        )
    }

    override suspend fun getSearchTvShows(searchKey: String): Flow<PagingData<com.panabuntu.tmdb.core.common.model.MediaItem.TvShow>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                GenericPagingDataSource(
                    networkCall = { page ->
                        searchRemoteDataSource.getSearchTvShows(searchKey, page)
                    },
                    mappItem = {
                        it.toModel()
                    }
                )
            }
        ).flow
    }

    override suspend fun getSearchTvShowsResultCount(searchKey: String): Resource<Int> {
        return networkResult(
            networkCall = { searchRemoteDataSource.getSearchTvShows(searchKey, 1) },
            mapResponse = { it.totalResults }
        )
    }

    override suspend fun getSearchPersons(searchKey: String): Flow<PagingData<com.panabuntu.tmdb.core.common.model.PersonItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                GenericPagingDataSource(
                    networkCall = { page ->
                        searchRemoteDataSource.getSearchPersons(searchKey, page)
                    },
                    mappItem = {
                        it.toModel()
                    }
                )
            }
        ).flow
    }

    override suspend fun getSearchPersonsCount(searchKey: String): Resource<Int> {
        return networkResult(
            networkCall = { searchRemoteDataSource.getSearchPersons(searchKey, 1) },
            mapResponse = { it.totalResults }
        )
    }
}