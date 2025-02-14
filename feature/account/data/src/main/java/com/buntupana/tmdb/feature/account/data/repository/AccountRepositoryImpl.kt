package com.buntupana.tmdb.feature.account.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.buntupana.tmdb.core.data.api.GenericPagingDataSource
import com.buntupana.tmdb.core.data.mapper.toModel
import com.buntupana.tmdb.feature.account.data.mapper.toModel
import com.buntupana.tmdb.feature.account.data.remote_data_source.AccountRemoteDataSource
import com.buntupana.tmdb.feature.account.domain.model.UserCredentials
import com.buntupana.tmdb.feature.account.domain.repository.AccountRepository
import com.panabuntu.tmdb.core.common.entity.MediaType
import com.panabuntu.tmdb.core.common.entity.NetworkError
import com.panabuntu.tmdb.core.common.entity.Result
import com.panabuntu.tmdb.core.common.entity.map
import com.panabuntu.tmdb.core.common.manager.SessionManager
import com.panabuntu.tmdb.core.common.model.AccountDetails
import com.panabuntu.tmdb.core.common.model.MediaItem
import com.panabuntu.tmdb.core.common.model.Order
import com.panabuntu.tmdb.core.common.provider.UrlProvider
import com.panabuntu.tmdb.core.common.util.Const.PAGINATION_SIZE
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AccountRepositoryImpl @Inject constructor(
    private val accountRemoteDataSource: AccountRemoteDataSource,
    private val urlProvider: UrlProvider,
    private val sessionManager: SessionManager
) : AccountRepository {

    private val session = sessionManager.session

    override suspend fun createRequestToken(): Result<String, NetworkError> {
        return accountRemoteDataSource.createRequestToken().map { it.requestToken }
    }

    override suspend fun requestAccessToken(requestToken: String): Result<UserCredentials, NetworkError> {
        return accountRemoteDataSource.requestAccessToken(requestToken).map {
            UserCredentials(accessToken = it.accessToken, accountId = it.accountId)
        }
    }

    override suspend fun getSessionId(accessToken: String): Result<String, NetworkError> {
        return accountRemoteDataSource.getSessionId(accessToken).map { it.sessionId }
    }

    override suspend fun deleteSession(accessToken: String): Result<Unit, NetworkError> {
        return accountRemoteDataSource.deleteSession(accessToken)
    }

    override suspend fun getAccountDetails(sessionId: String): Result<AccountDetails, NetworkError> {
        return accountRemoteDataSource.getAccountDetails(sessionId)
            .map { it.toModel(baseUrlAvatar = urlProvider.BASE_URL_AVATAR) }
    }

    override suspend fun getWatchlistMoviesTotalCount(): Result<Int, NetworkError> {
        return accountRemoteDataSource.getWatchlistMovies(
            session.value.accountDetails?.accountObjectId.orEmpty()
        ).map { result ->
            result.totalResults
        }
    }

    override suspend fun getFavoriteMoviesTotalCount(): Result<Int, NetworkError> {
        return accountRemoteDataSource.getFavoriteMovies(
            session.value.accountDetails?.accountObjectId.orEmpty()
        ).map { result ->
            result.totalResults
        }
    }

    override suspend fun getWatchlistMovies(): Result<List<MediaItem>, NetworkError> {
        return accountRemoteDataSource.getWatchlistMovies(
            session.value.accountDetails?.accountObjectId.orEmpty()
        ).map { result ->
            result.results.toModel(
                baseUrlPoster = urlProvider.BASE_URL_POSTER,
                baseUrlBackdrop = urlProvider.BASE_URL_BACKDROP
            )
        }
    }

    override suspend fun getFavoriteMovies(): Result<List<MediaItem>, NetworkError> {
        return accountRemoteDataSource.getFavoriteMovies(
            session.value.accountDetails?.accountObjectId.orEmpty()
        ).map { result ->
            result.results.toModel(
                baseUrlPoster = urlProvider.BASE_URL_POSTER,
                baseUrlBackdrop = urlProvider.BASE_URL_BACKDROP
            )
        }
    }

    override suspend fun getWatchlistMoviePaging(order: Order): Flow<PagingData<MediaItem.Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGINATION_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                GenericPagingDataSource(
                    networkCall = { page ->
                        accountRemoteDataSource.getWatchlistMovies(
                            accountObjectId = session.value.accountDetails?.accountObjectId.orEmpty(),
                            page = page,
                            order = order
                        )
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

    override suspend fun getFavoriteMoviePaging(order: Order): Flow<PagingData<MediaItem.Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGINATION_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                GenericPagingDataSource(
                    networkCall = { page ->
                        accountRemoteDataSource.getFavoriteMovies(
                            accountObjectId = session.value.accountDetails?.accountObjectId.orEmpty(),
                            page = page,
                            order = order
                        )
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

    override suspend fun getWatchlistTvShowsTotalCount(): Result<Int, NetworkError> {
        return accountRemoteDataSource.getWatchlistTvShows(
            session.value.accountDetails?.accountObjectId.orEmpty()
        ).map { result ->
            result.totalResults
        }
    }

    override suspend fun getFavoriteTvShowsTotalCount(): Result<Int, NetworkError> {
        return accountRemoteDataSource.getFavoriteTvShows(
            session.value.accountDetails?.accountObjectId.orEmpty()
        ).map { result ->
            result.totalResults
        }
    }

    override suspend fun getWatchlistTvShows(): Result<List<MediaItem>, NetworkError> {
        return accountRemoteDataSource.getWatchlistTvShows(
            session.value.accountDetails?.accountObjectId.orEmpty()
        ).map { result ->

            result.results.toModel(
                baseUrlPoster = urlProvider.BASE_URL_POSTER,
                baseUrlBackdrop = urlProvider.BASE_URL_BACKDROP
            )
        }
    }

    override suspend fun getFavoriteTvShows(): Result<List<MediaItem>, NetworkError> {
        return accountRemoteDataSource.getFavoriteTvShows(
            session.value.accountDetails?.accountObjectId.orEmpty()
        ).map { result ->

            result.results.toModel(
                baseUrlPoster = urlProvider.BASE_URL_POSTER,
                baseUrlBackdrop = urlProvider.BASE_URL_BACKDROP
            )
        }
    }

    override suspend fun getWatchlistTvShowPaging(order: Order): Flow<PagingData<MediaItem.TvShow>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGINATION_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                GenericPagingDataSource(
                    networkCall = { page ->
                        accountRemoteDataSource.getWatchlistTvShows(
                            accountObjectId = session.value.accountDetails?.accountObjectId.orEmpty(),
                            page = page,
                            order = order
                        )
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

    override suspend fun getFavoriteTvShowPaging(order: Order): Flow<PagingData<MediaItem.TvShow>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGINATION_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                GenericPagingDataSource(
                    networkCall = { page ->
                        accountRemoteDataSource.getFavoriteTvShows(
                            accountObjectId = session.value.accountDetails?.accountObjectId.orEmpty(),
                            page = page,
                            order = order
                        )
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

    override suspend fun setMediaFavorite(
        mediaId: Long,
        mediaType: MediaType,
        favorite: Boolean
    ): Result<Unit, NetworkError> {
        return accountRemoteDataSource.setMediaFavorite(
            accountId = sessionManager.session.value.accountDetails?.id ?: 0,
            mediaId = mediaId,
            mediaType = mediaType,
            favorite = favorite
        )
    }

    override suspend fun setMediaWatchList(
        mediaId: Long,
        mediaType: MediaType,
        watchlist: Boolean
    ): Result<Unit, NetworkError> {
        return accountRemoteDataSource.setMediaWatchlist(
            accountId = sessionManager.session.value.accountDetails?.id ?: 0,
            mediaId = mediaId,
            mediaType = mediaType,
            watchlist = watchlist
        )
    }

    override suspend fun addMediaRating(
        mediaType: MediaType,
        mediaId: Long,
        value: Int?
    ): Result<Unit, NetworkError> {

        return if (value == null || value == 0) {
            accountRemoteDataSource.deleteMediaRating(
                sessionId = sessionManager.session.value.sessionId,
                mediaType = mediaType,
                mediaId = mediaId
            )
        } else {
            accountRemoteDataSource.addMediaRating(
                sessionId = sessionManager.session.value.sessionId,
                mediaType = mediaType,
                mediaId = mediaId,
                value = value
            )
        }
    }
}