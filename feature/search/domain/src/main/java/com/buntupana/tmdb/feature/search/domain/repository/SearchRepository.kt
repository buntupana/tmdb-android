package com.buntupana.tmdb.feature.search.domain.repository

import androidx.paging.PagingData
import com.buntupana.tmdb.feature.search.domain.model.SearchItem
import com.panabuntu.tmdb.core.common.entity.Resource
import com.panabuntu.tmdb.core.common.model.MediaItem
import com.panabuntu.tmdb.core.common.model.PersonItem
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    suspend fun getSearchMedia(searchKey: String): Resource<List<SearchItem>>
    suspend fun getSearchMovies(searchKey: String): Flow<PagingData<MediaItem.Movie>>
    suspend fun getSearchPersons(searchKey: String): Flow<PagingData<PersonItem>>
    suspend fun getSearchTvShows(searchKey: String): Flow<PagingData<MediaItem.TvShow>>
    suspend fun getSearchMoviesResultCount(searchKey: String): Resource<Int>
    suspend fun getSearchTvShowsResultCount(searchKey: String): Resource<Int>
    suspend fun getSearchPersonsCount(searchKey: String): Resource<Int>
    suspend fun getTrendingMedia(): Resource<List<SearchItem>>
}