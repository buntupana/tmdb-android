package com.buntupana.tmdb.feature.search.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.buntupana.tmdb.core.data.api.GenericPagingDataSource
import com.buntupana.tmdb.core.data.mapper.toModel
import com.buntupana.tmdb.feature.search.data.mapper.toModel
import com.buntupana.tmdb.feature.search.data.mapper.toSearchModel
import com.buntupana.tmdb.feature.search.data.remote_data_source.SearchRemoteDataSource
import com.buntupana.tmdb.feature.search.domain.model.SearchItem
import com.buntupana.tmdb.feature.search.domain.repository.SearchRepository
import com.panabuntu.tmdb.core.common.entity.NetworkError
import com.panabuntu.tmdb.core.common.entity.Result
import com.panabuntu.tmdb.core.common.entity.map
import com.panabuntu.tmdb.core.common.model.MediaItem
import com.panabuntu.tmdb.core.common.model.PersonItem
import com.panabuntu.tmdb.core.common.provider.UrlProvider
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val searchRemoteDataSource: SearchRemoteDataSource,
    private val urlProvider: UrlProvider
) : SearchRepository {

    override suspend fun getTrendingMedia(): Result<List<SearchItem>, NetworkError> {
        return searchRemoteDataSource.getTrending()
            .map {
                it.results.toSearchModel(baseUrlPoster = urlProvider.BASE_URL_POSTER)
            }
    }

    override suspend fun getSearchMedia(searchKey: String): Result<List<SearchItem>, NetworkError> {
        return searchRemoteDataSource.getSearchMedia(searchKey = searchKey)
            .map {
                it.results.toSearchModel(baseUrlPoster = urlProvider.BASE_URL_POSTER)
            }
    }

    override suspend fun getSearchMovies(searchKey: String): Flow<PagingData<MediaItem.Movie>> {
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
                    mapItemList = {
                        it.toModel(
                            baseUrlPoster = urlProvider.BASE_URL_POSTER,
                            baseUrlBackdrop = urlProvider.BASE_URL_BACKDROP
                        )
                    }
                )
            }
        ).flow
    }

    override suspend fun getSearchMoviesResultCount(searchKey: String): Result<Int, NetworkError> {
        return searchRemoteDataSource.getSearchMovies(searchKey = searchKey, page = 1)
            .map {
                it.totalResults
            }
    }

    override suspend fun getSearchTvShows(searchKey: String): Flow<PagingData<MediaItem.TvShow>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                GenericPagingDataSource(
                    networkCall = { page ->
                        searchRemoteDataSource.getSearchTvShows(searchKey = searchKey, page = page)
                    },
                    mapItemList = {
                        it.toModel(
                            baseUrlPoster = urlProvider.BASE_URL_POSTER,
                            baseUrlBackdrop = urlProvider.BASE_URL_BACKDROP
                        )
                    }
                )
            }
        ).flow
    }

    override suspend fun getSearchTvShowsResultCount(searchKey: String): Result<Int, NetworkError> {
        return searchRemoteDataSource.getSearchTvShows(searchKey = searchKey, page = 1)
            .map { it.totalResults }
    }

    override suspend fun getSearchPersons(searchKey: String): Flow<PagingData<PersonItem>> {
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
                    mapItemList = {
                        it.toModel(baseUrlProfile = urlProvider.BASE_URL_PROFILE)
                    }
                )
            }
        ).flow
    }

    override suspend fun getSearchPersonsCount(searchKey: String): Result<Int, NetworkError> {
        return searchRemoteDataSource.getSearchPersons(searchKey = searchKey, page = 1)
            .map { it.totalResults }
    }
}