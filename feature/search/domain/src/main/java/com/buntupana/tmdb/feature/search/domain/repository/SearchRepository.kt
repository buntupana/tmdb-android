package com.buntupana.tmdb.feature.search.domain.repository

import androidx.paging.PagingData
import com.buntupana.tmdb.feature.search.domain.model.SearchItem
import com.panabuntu.tmdb.core.common.entity.NetworkError
import com.panabuntu.tmdb.core.common.entity.Result
import com.panabuntu.tmdb.core.common.model.MediaItem
import com.panabuntu.tmdb.core.common.model.PersonItem
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    suspend fun getSearchMedia(searchKey: String): Result<List<SearchItem>, NetworkError>
    suspend fun getSearchMovies(searchKey: String): Flow<PagingData<MediaItem.Movie>>
    suspend fun getSearchPersons(searchKey: String): Flow<PagingData<PersonItem>>
    suspend fun getSearchTvShows(searchKey: String): Flow<PagingData<MediaItem.TvShow>>
    suspend fun getSearchMoviesResultCount(searchKey: String): Result<Int, NetworkError>
    suspend fun getSearchTvShowsResultCount(searchKey: String): Result<Int, NetworkError>
    suspend fun getSearchPersonsCount(searchKey: String): Result<Int, NetworkError>
    suspend fun getTrendingMedia(): Result<List<SearchItem>, NetworkError>
}