package com.buntupana.tmdb.feature.account.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.buntupana.tmdb.core.data.api.GenericRemoteMediator
import com.buntupana.tmdb.core.data.database.dao.FavoriteDao
import com.buntupana.tmdb.core.data.database.dao.MediaDao
import com.buntupana.tmdb.core.data.database.dao.RemoteKeyDao
import com.buntupana.tmdb.core.data.database.dao.WatchlistDao
import com.buntupana.tmdb.core.data.database.entity.FavoriteEntity
import com.buntupana.tmdb.core.data.database.entity.WatchlistEntity
import com.buntupana.tmdb.core.data.mapper.toEntity
import com.buntupana.tmdb.core.data.mapper.toModel
import com.buntupana.tmdb.core.data.mapper.toMovieItemModel
import com.buntupana.tmdb.core.data.mapper.toTvShowItemModel
import com.buntupana.tmdb.feature.account.data.mapper.toModel
import com.buntupana.tmdb.feature.account.data.remote_data_source.AccountRemoteDataSource
import com.buntupana.tmdb.feature.account.domain.model.UserCredentials
import com.buntupana.tmdb.feature.account.domain.repository.AccountRepository
import com.panabuntu.tmdb.core.common.entity.MediaType
import com.panabuntu.tmdb.core.common.entity.NetworkError
import com.panabuntu.tmdb.core.common.entity.Result
import com.panabuntu.tmdb.core.common.entity.map
import com.panabuntu.tmdb.core.common.entity.onSuccess
import com.panabuntu.tmdb.core.common.manager.SessionManager
import com.panabuntu.tmdb.core.common.model.AccountDetails
import com.panabuntu.tmdb.core.common.model.MediaItem
import com.panabuntu.tmdb.core.common.model.Order
import com.panabuntu.tmdb.core.common.provider.UrlProvider
import com.panabuntu.tmdb.core.common.util.Const.PAGINATION_SIZE
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AccountRepositoryImpl @Inject constructor(
    private val accountRemoteDataSource: AccountRemoteDataSource,
    private val remoteKeyDao: RemoteKeyDao,
    private val mediaDao: MediaDao,
    private val watchlistDao: WatchlistDao,
    private val favoriteDao: FavoriteDao,
    private val urlProvider: UrlProvider,
    private val sessionManager: SessionManager
) : AccountRepository {

    private val session = sessionManager.session

    private var favoriteMovieTotalCount = MutableStateFlow(0)
    private var favoriteTvShowTotalCount = MutableStateFlow(0)
    private var watchlistMovieTotalCount = MutableStateFlow(0)
    private var watchlistTvShowTotalCount = MutableStateFlow(0)

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

    override suspend fun getWatchlistMoviesTotalCount(): Flow<Result<Int, NetworkError>> {
        val result = accountRemoteDataSource.getWatchlistMovies(
            session.value.accountDetails?.accountObjectId.orEmpty()
        ).onSuccess { result ->
            watchlistMovieTotalCount.value = result.totalResults
        }
        return flow {
            when (result) {
                is Result.Error -> emit(result)
                is Result.Success -> {
                    emitAll(watchlistMovieTotalCount.map { Result.Success(it) })
                }
            }
        }
    }

    override suspend fun getWatchlistTvShowsTotalCount(): Flow<Result<Int, NetworkError>> {
        val result = accountRemoteDataSource.getWatchlistTvShows(
            session.value.accountDetails?.accountObjectId.orEmpty()
        ).onSuccess { result ->
            watchlistTvShowTotalCount.value = result.totalResults
        }
        return flow {
            when (result) {
                is Result.Error -> emit(result)
                is Result.Success -> {
                    emitAll(watchlistTvShowTotalCount.map { Result.Success(it) })
                }
            }
        }
    }

    override suspend fun getFavoriteMoviesTotalCount(): Flow<Result<Int, NetworkError>> {

        val result = accountRemoteDataSource.getFavoriteMovies(
            session.value.accountDetails?.accountObjectId.orEmpty()
        ).onSuccess {
            favoriteMovieTotalCount.value = it.totalResults
        }

        return flow {
            when (result) {
                is Result.Error -> emit(result)
                is Result.Success -> {
                    emitAll(favoriteMovieTotalCount.map { Result.Success(it) })
                }
            }
        }
    }

    override suspend fun getFavoriteTvShowsTotalCount(): Flow<Result<Int, NetworkError>> {
        val result = accountRemoteDataSource.getFavoriteTvShows(
            session.value.accountDetails?.accountObjectId.orEmpty()
        ).onSuccess { result ->
            favoriteTvShowTotalCount.value = result.totalResults
        }

        return flow {
            when (result) {
                is Result.Error -> emit(result)
                is Result.Success -> {
                    emitAll(favoriteTvShowTotalCount.map { Result.Success(it) })
                }
            }
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

    @OptIn(ExperimentalPagingApi::class)
    override suspend fun getWatchlistMoviePaging(order: Order): Flow<PagingData<MediaItem.Movie>> {

        val remoteType = "watchlist_movie"

        return Pager(
            config = PagingConfig(
                pageSize = PAGINATION_SIZE,
                enablePlaceholders = false
            ),
            remoteMediator = GenericRemoteMediator(
                remoteType = remoteType,
                networkCall = { page ->
                    accountRemoteDataSource.getWatchlistMovies(
                        accountObjectId = session.value.accountDetails?.accountObjectId.orEmpty(),
                        page = page,
                        order = order
                    )
                },
                remoteKeyDao = remoteKeyDao,
                clearTable = {
                    watchlistDao.clearByMediaType(mediaType = MediaType.MOVIE)
                    remoteKeyDao.remoteKeyByType(remoteType)
                },
                insertNetworkResult = { resultList ->
                    mediaDao.upsertSimple(resultList.toEntity())
                    var addedAt = System.currentTimeMillis()
                    watchlistDao.upsert(
                        resultList.map {
                            addedAt += 1
                            WatchlistEntity(
                                mediaId = it.id,
                                mediaType = MediaType.MOVIE,
                                addedAt = addedAt
                            )
                        }
                    )
                }
            ),
            pagingSourceFactory = {
                mediaDao.getWatchlist(MediaType.MOVIE)
            }
        ).flow.map { pagingData ->
            pagingData.map {
                it.toMovieItemModel(
                    baseUrlPoster = urlProvider.BASE_URL_POSTER,
                    baseUrlBackdrop = urlProvider.BASE_URL_BACKDROP
                )
            }
        }
    }

    @OptIn(ExperimentalPagingApi::class)
    override suspend fun getFavoriteMoviePaging(order: Order): Flow<PagingData<MediaItem.Movie>> {

        val remoteType = "favorite_movie"

        return Pager(
            config = PagingConfig(
                pageSize = PAGINATION_SIZE,
                enablePlaceholders = false
            ),
            remoteMediator = GenericRemoteMediator(
                remoteType = remoteType,
                networkCall = { page ->
                    accountRemoteDataSource.getFavoriteMovies(
                        accountObjectId = session.value.accountDetails?.accountObjectId.orEmpty(),
                        page = page,
                        order = order
                    )
                },
                remoteKeyDao = remoteKeyDao,
                clearTable = {
                    favoriteDao.clearByMediaType(mediaType = MediaType.MOVIE)
                    remoteKeyDao.remoteKeyByType(remoteType)
                },
                insertNetworkResult = { resultList ->
                    mediaDao.upsertSimple(resultList.toEntity())
                    var addedAt = System.currentTimeMillis()
                    favoriteDao.upsert(
                        resultList.map {
                            addedAt += 1
                            FavoriteEntity(
                                mediaId = it.id,
                                mediaType = MediaType.MOVIE,
                                addedAt = addedAt
                            )
                        }
                    )
                }
            ),
            pagingSourceFactory = {
                mediaDao.getFavorites(mediaType = MediaType.MOVIE)
            }
        ).flow.map { pagingData ->
            pagingData.map {
                it.toMovieItemModel(
                    baseUrlPoster = urlProvider.BASE_URL_POSTER,
                    baseUrlBackdrop = urlProvider.BASE_URL_BACKDROP
                )
            }
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

    @OptIn(ExperimentalPagingApi::class)
    override suspend fun getWatchlistTvShowPaging(order: Order): Flow<PagingData<MediaItem.TvShow>> {

        val remoteType = "watchlist_tv_show"

        return Pager(
            config = PagingConfig(
                pageSize = PAGINATION_SIZE,
                enablePlaceholders = false
            ),
            remoteMediator = GenericRemoteMediator(
                remoteType = remoteType,
                networkCall = { page ->
                    accountRemoteDataSource.getWatchlistTvShows(
                        accountObjectId = session.value.accountDetails?.accountObjectId.orEmpty(),
                        page = page,
                        order = order
                    )
                },
                remoteKeyDao = remoteKeyDao,
                clearTable = {
                    watchlistDao.clearByMediaType(mediaType = MediaType.TV_SHOW)
                    remoteKeyDao.remoteKeyByType(remoteType)
                },
                insertNetworkResult = { resultList ->
                    mediaDao.upsertSimple(resultList.toEntity())
                    var addedAt = System.currentTimeMillis()
                    watchlistDao.upsert(
                        resultList.map {
                            addedAt += 1
                            WatchlistEntity(
                                mediaId = it.id,
                                mediaType = MediaType.TV_SHOW,
                                addedAt = addedAt
                            )
                        }
                    )
                }
            ),
            pagingSourceFactory = {
                mediaDao.getWatchlist(MediaType.TV_SHOW)
            }
        ).flow.map { pagingData ->
            pagingData.map {
                it.toTvShowItemModel(
                    baseUrlPoster = urlProvider.BASE_URL_POSTER,
                    baseUrlBackdrop = urlProvider.BASE_URL_BACKDROP
                )
            }
        }
    }

    @OptIn(ExperimentalPagingApi::class)
    override suspend fun getFavoriteTvShowPaging(order: Order): Flow<PagingData<MediaItem.TvShow>> {

        val remoteType = "favorite_tv_show"

        return Pager(
            config = PagingConfig(
                pageSize = PAGINATION_SIZE,
                enablePlaceholders = false
            ),
            remoteMediator = GenericRemoteMediator(
                remoteType = remoteType,
                networkCall = { page ->
                    accountRemoteDataSource.getFavoriteTvShows(
                        accountObjectId = session.value.accountDetails?.accountObjectId.orEmpty(),
                        page = page,
                        order = order
                    )
                },
                remoteKeyDao = remoteKeyDao,
                clearTable = {
                    favoriteDao.clearByMediaType(mediaType = MediaType.TV_SHOW)
                    remoteKeyDao.remoteKeyByType(remoteType)
                },
                insertNetworkResult = { resultList ->
                    mediaDao.upsertSimple(resultList.toEntity())
                    var addedAt = System.currentTimeMillis()
                    favoriteDao.upsert(
                        resultList.map {
                            addedAt += 1
                            FavoriteEntity(
                                mediaId = it.id,
                                mediaType = MediaType.TV_SHOW,
                                addedAt = addedAt
                            )
                        }
                    )
                }
            ),
            pagingSourceFactory = {
                mediaDao.getFavorites(MediaType.TV_SHOW)
            }
        ).flow.map { pagingData ->
            pagingData.map {
                it.toTvShowItemModel(
                    baseUrlPoster = urlProvider.BASE_URL_POSTER,
                    baseUrlBackdrop = urlProvider.BASE_URL_BACKDROP
                )
            }
        }
    }

    override suspend fun setMediaFavorite(
        mediaId: Long,
        mediaType: MediaType,
        isFavorite: Boolean
    ): Result<Unit, NetworkError> {
        return accountRemoteDataSource.setMediaFavorite(
            accountId = sessionManager.session.value.accountDetails?.id ?: 0,
            mediaId = mediaId,
            mediaType = mediaType,
            favorite = isFavorite
        ).onSuccess {
            mediaDao.updateFavorite(mediaId, mediaType, isFavorite)
            when (mediaType) {
                MediaType.MOVIE -> {
                    if (isFavorite) {
                        favoriteMovieTotalCount.value += 1
                    } else {
                        favoriteMovieTotalCount.value -= 1
                    }
                }

                MediaType.TV_SHOW -> {
                    if (isFavorite) {
                        favoriteTvShowTotalCount.value += 1
                    } else {
                        favoriteTvShowTotalCount.value -= 1
                    }
                }
            }
            if (isFavorite) {
                favoriteDao.insert(mediaId, mediaType)
            } else {
                favoriteDao.delete(mediaId, mediaType)
            }
        }
    }

    override suspend fun setMediaWatchList(
        mediaId: Long,
        mediaType: MediaType,
        isWatchlisted: Boolean
    ): Result<Unit, NetworkError> {
        return accountRemoteDataSource.setMediaWatchlist(
            accountId = sessionManager.session.value.accountDetails?.id ?: 0,
            mediaId = mediaId,
            mediaType = mediaType,
            watchlist = isWatchlisted
        ).onSuccess {
            mediaDao.updateWatchList(mediaId, mediaType, isWatchlisted)
            when (mediaType) {
                MediaType.MOVIE -> {

                    if (isWatchlisted) {
                        watchlistMovieTotalCount.value += 1
                    } else {
                        watchlistMovieTotalCount.value -= 1
                    }
                }

                MediaType.TV_SHOW -> {
                    if (isWatchlisted) {
                        watchlistTvShowTotalCount.value += 1
                    } else {
                        watchlistTvShowTotalCount.value -= 1
                    }
                }
            }
            if (isWatchlisted) {
                watchlistDao.insert(mediaId, mediaType)
            } else {
                watchlistDao.delete(mediaId, mediaType)
            }
        }
    }

    override suspend fun addMediaRating(
        mediaType: MediaType,
        mediaId: Long,
        value: Int?
    ): Result<Unit, NetworkError> {

        val result = if (value == null || value == 0) {
            accountRemoteDataSource.deleteMediaRating(
                sessionId = sessionManager.session.value.sessionId,
                mediaType = mediaType,
                mediaId = mediaId
            ).onSuccess {
                mediaDao.updateRating(mediaId, mediaType, value)
            }
        } else {
            accountRemoteDataSource.addMediaRating(
                sessionId = sessionManager.session.value.sessionId,
                mediaType = mediaType,
                mediaId = mediaId,
                rating = value
            ).onSuccess {
                mediaDao.updateRating(mediaId, mediaType, value)
            }
        }

        return result
    }
}