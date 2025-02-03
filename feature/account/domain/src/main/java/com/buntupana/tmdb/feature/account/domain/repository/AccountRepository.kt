package com.buntupana.tmdb.feature.account.domain.repository


import androidx.paging.PagingData
import com.buntupana.tmdb.feature.account.domain.model.UserCredentials
import com.panabuntu.tmdb.core.common.entity.MediaType
import com.panabuntu.tmdb.core.common.entity.NetworkError
import com.panabuntu.tmdb.core.common.entity.Result
import com.panabuntu.tmdb.core.common.model.AccountDetails
import com.panabuntu.tmdb.core.common.model.MediaItem
import com.panabuntu.tmdb.core.common.model.Order
import kotlinx.coroutines.flow.Flow

interface AccountRepository {

    suspend fun createRequestToken() : Result<String, NetworkError>

    suspend fun requestAccessToken(requestToken: String): Result<UserCredentials, NetworkError>

    suspend fun getSessionId(accessToken: String): Result<String, NetworkError>

    suspend fun getAccountDetails(sessionId: String): Result<AccountDetails, NetworkError>

    suspend fun deleteSession(accessToken: String): Result<Unit, NetworkError>

    suspend fun getWatchlistMoviePaging(order: Order): Flow<PagingData<MediaItem.Movie>>

    suspend fun getWatchlistTvShowPaging(order: Order): Flow<PagingData<MediaItem.TvShow>>

    suspend fun getWatchlistMovies(): Result<List<MediaItem>, NetworkError>

    suspend fun getWatchlistTvShows(): Result<List<MediaItem>, NetworkError>

    suspend fun setMediaFavorite(
        mediaId: Long,
        mediaType: MediaType,
        favorite: Boolean
    ): Result<Unit, NetworkError>

    suspend fun setMediaWatchList(
        mediaId: Long,
        mediaType: MediaType,
        watchlist: Boolean
    ): Result<Unit, NetworkError>

    suspend fun addMediaRating(
        mediaType: MediaType,
        mediaId: Long,
        value: Int?
    ): Result<Unit, NetworkError>

    suspend fun getWatchlistMoviesTotalCount(): Result<Int, NetworkError>

    suspend fun getWatchlistTvShowsTotalCount(): Result<Int, NetworkError>

    suspend fun getFavoriteMoviesTotalCount(): Result<Int, NetworkError>

    suspend fun getFavoriteMovies(): Result<List<MediaItem>, NetworkError>

    suspend fun getFavoriteMoviePaging(order: Order): Flow<PagingData<MediaItem.Movie>>

    suspend fun getFavoriteTvShowsTotalCount(): Result<Int, NetworkError>

    suspend fun getFavoriteTvShows(): Result<List<MediaItem>, NetworkError>

    suspend fun getFavoriteTvShowPaging(order: Order): Flow<PagingData<MediaItem.TvShow>>
}