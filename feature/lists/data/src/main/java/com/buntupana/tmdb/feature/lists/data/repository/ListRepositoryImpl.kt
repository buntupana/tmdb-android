package com.buntupana.tmdb.feature.lists.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.buntupana.tmdb.core.data.api.GenericRemoteMediator
import com.buntupana.tmdb.core.data.database.TmdbDataBase
import com.buntupana.tmdb.core.data.database.entity.FavoriteEntity
import com.buntupana.tmdb.core.data.database.entity.UserListDetailsEntity
import com.buntupana.tmdb.core.data.database.entity.UserListItemEntity
import com.buntupana.tmdb.core.data.database.entity.WatchlistEntity
import com.buntupana.tmdb.core.data.mapper.toEntity
import com.buntupana.tmdb.core.data.mapper.toItemModel
import com.buntupana.tmdb.core.data.mapper.toMovieItemModel
import com.buntupana.tmdb.core.data.mapper.toTvShowItemModel
import com.buntupana.tmdb.core.data.repository.getAllItemsFromPaging
import com.buntupana.tmdb.core.data.util.getFlowListResult
import com.buntupana.tmdb.core.data.util.getFlowListSEResult
import com.buntupana.tmdb.feature.lists.data.mapper.toEntity
import com.buntupana.tmdb.feature.lists.data.mapper.toModel
import com.buntupana.tmdb.feature.lists.data.remote_data_source.ListRemoteDataSource
import com.buntupana.tmdb.feature.lists.domain.model.MediaItemBasic
import com.buntupana.tmdb.feature.lists.domain.model.UserListDetails
import com.buntupana.tmdb.feature.lists.domain.repository.ListRepository
import com.panabuntu.tmdb.core.common.entity.MediaType
import com.panabuntu.tmdb.core.common.entity.NetworkError
import com.panabuntu.tmdb.core.common.entity.Result
import com.panabuntu.tmdb.core.common.entity.asEmptyDataResult
import com.panabuntu.tmdb.core.common.entity.map
import com.panabuntu.tmdb.core.common.entity.onSuccess
import com.panabuntu.tmdb.core.common.manager.SessionManager
import com.panabuntu.tmdb.core.common.model.MediaItem
import com.panabuntu.tmdb.core.common.model.Order
import com.panabuntu.tmdb.core.common.provider.UrlProvider
import com.panabuntu.tmdb.core.common.util.Const.PAGINATION_SIZE
import com.panabuntu.tmdb.core.common.util.getCurrentUTC
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class ListRepositoryImpl(
    private val listRemoteDataSource: ListRemoteDataSource,
    private val db: TmdbDataBase,
    private val urlProvider: UrlProvider,
    sessionManager: SessionManager
) : ListRepository {

    private val session = sessionManager.session

    private var favoriteMovieTotalCount = MutableStateFlow(0)
    private var favoriteTvShowTotalCount = MutableStateFlow(0)
    private var watchlistMovieTotalCount = MutableStateFlow(0)
    private var watchlistTvShowTotalCount = MutableStateFlow(0)
    private var listsTotalCount = MutableStateFlow(0)

    override suspend fun getListTotalCount(): Flow<Result<Int, NetworkError>> {
        val result = listRemoteDataSource.getLists(
            accountObjectId = session.value.accountDetails?.accountObjectId.orEmpty()
        ).map { result ->
            listsTotalCount.value = result.totalResults
        }

        return flow {
            when (result) {
                is Result.Error -> emit(result)
                is Result.Success -> {
                    emitAll(listsTotalCount.map { Result.Success(it) })
                }
            }
        }
    }

    override suspend fun getLists(justFirstPage: Boolean): Flow<Result<List<UserListDetails>, NetworkError>> {

        val result = if (justFirstPage) {
            listRemoteDataSource.getLists(
                accountObjectId = session.value.accountDetails?.accountObjectId.orEmpty()
            )
        } else {
            getAllItemsFromPaging(
                networkCall = { page ->
                    listRemoteDataSource.getLists(
                        accountObjectId = session.value.accountDetails?.accountObjectId.orEmpty(),
                        page = page
                    )
                }
            )
        }

        return getFlowListResult(
            prevDataBaseQuery = {
                db.userListDetailsDao.clearAll()
            },
            networkCall = { result },
            mapToEntity = { it.toEntity() },
            updateDataBaseQuery = {
                db.userListDetailsDao.upsert(it)
            },
            fetchFromDataBaseQuery = {
                if (justFirstPage) {
                    db.userListDetailsDao.getFirstPage(pageSize = PAGINATION_SIZE)
                } else {
                    db.userListDetailsDao.getAll()
                }
            },
            mapToModel = { userListDetailsList ->
                userListDetailsList.map { entity ->
                    entity.toModel(
                        baseUrlPoster = urlProvider.BASE_URL_POSTER,
                        baseUrlBackdrop = urlProvider.BASE_URL_BACKDROP,
                        shareLink = urlProvider.getListShareLink(entity.id)
                    )
                }
            }
        )
    }

    @OptIn(ExperimentalPagingApi::class)
    override suspend fun getListsPaging(): Flow<PagingData<UserListDetails>> {

        val remoteType = "user_list_details"

        return Pager(
            config = PagingConfig(
                pageSize = PAGINATION_SIZE,
                enablePlaceholders = false
            ),
            remoteMediator = GenericRemoteMediator(
                remoteType = remoteType,
                networkCall = { page ->
                    listRemoteDataSource.getLists(
                        accountObjectId = session.value.accountDetails?.accountObjectId.orEmpty(),
                        page = page,
                    )
                },
                remoteKeyDao = db.remoteKeyDao,
                clearTable = {
                    db.userListDetailsDao.clearAll()
                    db.remoteKeyDao.remoteKeyByType(remoteType)
                },
                insertNetworkResult = { resultList ->
                    db.userListDetailsDao.upsert(resultList.toEntity())
                }
            ),
            pagingSourceFactory = {
                db.userListDetailsDao.getAllPS()
            }
        ).flow.map { pagingData ->
            pagingData.map {
                it.toModel(
                    baseUrlPoster = urlProvider.BASE_URL_POSTER,
                    baseUrlBackdrop = urlProvider.BASE_URL_BACKDROP,
                    shareLink = urlProvider.getListShareLink(it.id)
                )
            }
        }
    }

    override suspend fun checkIfItemInList(
        listId: Long,
        mediaId: Long,
        mediaType: MediaType
    ): Result<Unit, NetworkError> {
        return listRemoteDataSource.checkItemInList(
            listId = listId,
            mediaId = mediaId,
            mediaType = mediaType
        )
    }

    override suspend fun createList(
        name: String,
        description: String,
        isPublic: Boolean
    ): Result<Unit, NetworkError> {
        return listRemoteDataSource.createList(
            name = name,
            description = description,
            isPublic = isPublic,
        ).onSuccess {
            db.userListDetailsDao.upsert(
                UserListDetailsEntity(
                    id = it.id,
                    name = name,
                    description = description,
                    isPublic = isPublic,
                    backdropPath = null,
                    averageRating = null,
                    iso31661 = "",
                    iso6391 = "",
                    itemCount = 0,
                    posterPath = null,
                    revenue = null,
                    runtime = null,
                    createdAt = getCurrentUTC(),
                    updatedAt = getCurrentUTC()
                )
            )
            listsTotalCount.value += 1
        }.asEmptyDataResult()
    }

    override suspend fun updateList(
        listId: Long,
        name: String,
        description: String,
        isPublic: Boolean
    ): Result<Unit, NetworkError> {
        return listRemoteDataSource.updateList(
            listId = listId,
            name = name,
            description = description,
            isPublic = isPublic
        ).onSuccess {
            val entity = db.userListDetailsDao.getById(listId).firstOrNull() ?: return@onSuccess

            db.userListDetailsDao.upsert(
                entity.copy(
                    name = name,
                    description = description,
                    isPublic = isPublic,
                    updatedAt = getCurrentUTC()
                )
            )
        }.asEmptyDataResult()
    }

    override suspend fun deleteList(listId: Long): Result<Unit, NetworkError> {
        return listRemoteDataSource.deleteList(listId = listId)
            .onSuccess {
                db.userListDetailsDao.deleteById(listId)
                listsTotalCount.value -= 1
            }
            .asEmptyDataResult()
    }

    override suspend fun getListDetails(listId: Long): Flow<Result<UserListDetails?, NetworkError>> {

        return flow {
            when (val result = listRemoteDataSource.getListDetail(listId = listId)) {
                is Result.Error -> emit(result)
                is Result.Success -> {
                    val entity = result.data.toEntity()
                    db.userListDetailsDao.upsert(entity)
                    emitAll(
                        db.userListDetailsDao.getById(listId).map {
                            Result.Success(
                                it?.toModel(
                                    baseUrlBackdrop = urlProvider.BASE_URL_BACKDROP,
                                    baseUrlPoster = urlProvider.BASE_URL_POSTER,
                                    shareLink = urlProvider.getListShareLink(it.id)
                                )
                            )
                        })
                }
            }
        }
    }

    @OptIn(ExperimentalPagingApi::class)
    override fun getListItems(listId: Long): Flow<PagingData<MediaItem>> {
        val remoteType = "user_list_$listId"

        return Pager(
            config = PagingConfig(
                pageSize = PAGINATION_SIZE,
                enablePlaceholders = false
            ),
            remoteMediator = GenericRemoteMediator(
                remoteType = remoteType,
                networkCall = { page ->
                    listRemoteDataSource.getListItems(
                        listId = listId,
                        page = page
                    )
                },
                remoteKeyDao = db.remoteKeyDao,
                clearTable = {
                    db.userListItemDao.clearByListId(listId)
                    db.remoteKeyDao.remoteKeyByType(remoteType)
                },
                insertNetworkResult = { resultList ->
                    db.mediaDao.upsertSimple(resultList.toEntity())
                    var addedAt = System.currentTimeMillis()
                    db.userListItemDao.upsert(
                        resultList.map {
                            addedAt += 1
                            UserListItemEntity(
                                listId = listId,
                                mediaId = it.id,
                                mediaType = MediaType.fromValue(it.mediaType),
                                addedAt = addedAt
                            )
                        }
                    )
                }
            ),
            pagingSourceFactory = {
                db.mediaDao.getUserListItem(listId)
            }
        ).flow.map { pagingData ->
            pagingData.map {
                it.toItemModel(
                    baseUrlPoster = urlProvider.BASE_URL_POSTER,
                    baseUrlBackdrop = urlProvider.BASE_URL_BACKDROP
                )
            }
        }
    }

    override suspend fun addMediaItemList(
        listId: Long,
        mediaId: Long,
        mediaType: MediaType
    ): Result<Unit, NetworkError> {
        return listRemoteDataSource.addMediaItemList(
            listId = listId,
            mediaItemList = listOf(MediaItemBasic(mediaId, mediaType))
        ).onSuccess {
            db.userListItemDao.insert(
                listId = listId,
                mediaId = mediaId,
                mediaType = mediaType
            )
            db.userListDetailsDao.addItemCount(listId)
        }.asEmptyDataResult()
    }

    override suspend fun deleteMediaItemList(
        listId: Long,
        mediaId: Long,
        mediaType: MediaType
    ): Result<Unit, NetworkError> {
        return listRemoteDataSource.removeMediaItemList(
            listId = listId,
            mediaItemList = listOf(MediaItemBasic(mediaId, mediaType))
        ).onSuccess {
            db.userListItemDao.delete(
                listId = listId,
                mediaId = mediaId,
                mediaType = mediaType
            )
            db.userListDetailsDao.restItemCount(listId)
        }.asEmptyDataResult()
    }

    override suspend fun getWatchlistMoviesTotalCount(): Flow<Result<Int, NetworkError>> {
        val result = listRemoteDataSource.getWatchlistMovies(
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
        val result = listRemoteDataSource.getWatchlistTvShows(
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

        val result = listRemoteDataSource.getFavoriteMovies(
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
        val result = listRemoteDataSource.getFavoriteTvShows(
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

    override suspend fun getWatchlistMovies(): Flow<Result<List<MediaItem>, NetworkError>> {

        return getFlowListSEResult(
            prevDataBaseQuery = {
                db.watchlistDao.clearByMediaType(mediaType = MediaType.MOVIE)
            },
            networkCall = {
                listRemoteDataSource.getWatchlistMovies(
                    session.value.accountDetails?.accountObjectId.orEmpty()
                )
            },
            mapToEntity = { it.toEntity() },
            updateDataBaseQuery = { resultList ->
                db.mediaDao.upsertSimple(resultList)
                var addedAt = System.currentTimeMillis()
                db.watchlistDao.upsert(
                    resultList.map {
                        addedAt += 1
                        WatchlistEntity(
                            mediaId = it.id,
                            mediaType = MediaType.MOVIE,
                            addedAt = addedAt
                        )
                    }
                )
            },
            fetchFromDataBaseQuery = {
                db.mediaDao.getWatchlist(MediaType.MOVIE, pageSize = PAGINATION_SIZE)
            },
            mapToModel = {
                it.map {
                    it.toMovieItemModel(
                        baseUrlPoster = urlProvider.BASE_URL_POSTER,
                        baseUrlBackdrop = urlProvider.BASE_URL_BACKDROP
                    )
                }
            }
        )
    }

    override suspend fun getFavoriteMovies(): Flow<Result<List<MediaItem>, NetworkError>> {

        return getFlowListSEResult(
            prevDataBaseQuery = {
                db.favoriteDao.clearByMediaType(mediaType = MediaType.MOVIE)
            },
            networkCall = {
                listRemoteDataSource.getFavoriteMovies(
                    session.value.accountDetails?.accountObjectId.orEmpty()
                )
            },
            mapToEntity = { it.toEntity() },
            updateDataBaseQuery = { resultList ->
                db.mediaDao.upsertSimple(resultList)
                var addedAt = System.currentTimeMillis()
                db.favoriteDao.upsert(
                    resultList.map {
                        addedAt += 1
                        FavoriteEntity(
                            mediaId = it.id,
                            mediaType = MediaType.MOVIE,
                            addedAt = addedAt
                        )
                    }
                )
            },
            fetchFromDataBaseQuery = {
                db.mediaDao.getFavorites(MediaType.MOVIE, pageSize = PAGINATION_SIZE)
            },
            mapToModel = {
                it.map {
                    it.toMovieItemModel(
                        baseUrlPoster = urlProvider.BASE_URL_POSTER,
                        baseUrlBackdrop = urlProvider.BASE_URL_BACKDROP
                    )
                }
            }
        )
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
                    listRemoteDataSource.getWatchlistMovies(
                        accountObjectId = session.value.accountDetails?.accountObjectId.orEmpty(),
                        page = page,
                        order = order
                    )
                },
                remoteKeyDao = db.remoteKeyDao,
                clearTable = {
                    db.watchlistDao.clearByMediaType(mediaType = MediaType.MOVIE)
                    db.remoteKeyDao.remoteKeyByType(remoteType)
                },
                insertNetworkResult = { resultList ->
                    db.mediaDao.upsertSimple(resultList.toEntity())
                    var addedAt = System.currentTimeMillis()
                    db.watchlistDao.upsert(
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
                db.mediaDao.getWatchlist(MediaType.MOVIE)
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
                    listRemoteDataSource.getFavoriteMovies(
                        accountObjectId = session.value.accountDetails?.accountObjectId.orEmpty(),
                        page = page,
                        order = order
                    )
                },
                remoteKeyDao = db.remoteKeyDao,
                clearTable = {
                    db.favoriteDao.clearByMediaType(mediaType = MediaType.MOVIE)
                    db.remoteKeyDao.remoteKeyByType(remoteType)
                },
                insertNetworkResult = { resultList ->
                    db.mediaDao.upsertSimple(resultList.toEntity())
                    var addedAt = System.currentTimeMillis()
                    db.favoriteDao.upsert(
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
                db.mediaDao.getFavorites(mediaType = MediaType.MOVIE)
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

    override suspend fun getWatchlistTvShows(): Flow<Result<List<MediaItem>, NetworkError>> {

        return getFlowListSEResult(
            prevDataBaseQuery = {
                db.watchlistDao.clearByMediaType(mediaType = MediaType.TV_SHOW)
            },
            networkCall = {
                listRemoteDataSource.getWatchlistTvShows(
                    session.value.accountDetails?.accountObjectId.orEmpty()
                )
            },
            mapToEntity = { it.toEntity() },
            updateDataBaseQuery = { resultList ->
                db.mediaDao.upsertSimple(resultList)
                var addedAt = System.currentTimeMillis()
                db.watchlistDao.upsert(
                    resultList.map {
                        addedAt += 1
                        WatchlistEntity(
                            mediaId = it.id,
                            mediaType = MediaType.TV_SHOW,
                            addedAt = addedAt
                        )
                    }
                )
            },
            fetchFromDataBaseQuery = {
                db.mediaDao.getWatchlist(MediaType.TV_SHOW, pageSize = PAGINATION_SIZE)
            },
            mapToModel = {
                it.map {
                    it.toTvShowItemModel(
                        baseUrlPoster = urlProvider.BASE_URL_POSTER,
                        baseUrlBackdrop = urlProvider.BASE_URL_BACKDROP
                    )
                }
            }
        )
    }

    override suspend fun getFavoriteTvShows(): Flow<Result<List<MediaItem>, NetworkError>> {

        return getFlowListSEResult(
            prevDataBaseQuery = {
                db.favoriteDao.clearByMediaType(mediaType = MediaType.TV_SHOW)
            },
            networkCall = {
                listRemoteDataSource.getFavoriteTvShows(
                    session.value.accountDetails?.accountObjectId.orEmpty()
                )
            },
            mapToEntity = { it.toEntity() },
            updateDataBaseQuery = { resultList ->
                db.mediaDao.upsertSimple(resultList)
                var addedAt = System.currentTimeMillis()
                db.favoriteDao.upsert(
                    resultList.map {
                        addedAt += 1
                        FavoriteEntity(
                            mediaId = it.id,
                            mediaType = MediaType.TV_SHOW,
                            addedAt = addedAt
                        )
                    }
                )
            },
            fetchFromDataBaseQuery = {
                db.mediaDao.getFavorites(MediaType.TV_SHOW, pageSize = PAGINATION_SIZE)
            },
            mapToModel = {
                it.map {
                    it.toTvShowItemModel(
                        baseUrlPoster = urlProvider.BASE_URL_POSTER,
                        baseUrlBackdrop = urlProvider.BASE_URL_BACKDROP
                    )
                }
            }
        )
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
                    listRemoteDataSource.getWatchlistTvShows(
                        accountObjectId = session.value.accountDetails?.accountObjectId.orEmpty(),
                        page = page,
                        order = order
                    )
                },
                remoteKeyDao = db.remoteKeyDao,
                clearTable = {
                    db.watchlistDao.clearByMediaType(mediaType = MediaType.TV_SHOW)
                    db.remoteKeyDao.remoteKeyByType(remoteType)
                },
                insertNetworkResult = { resultList ->
                    db.mediaDao.upsertSimple(resultList.toEntity())
                    var addedAt = System.currentTimeMillis()
                    db.watchlistDao.upsert(
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
                db.mediaDao.getWatchlist(MediaType.TV_SHOW)
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
                    listRemoteDataSource.getFavoriteTvShows(
                        accountObjectId = session.value.accountDetails?.accountObjectId.orEmpty(),
                        page = page,
                        order = order
                    )
                },
                remoteKeyDao = db.remoteKeyDao,
                clearTable = {
                    db.favoriteDao.clearByMediaType(mediaType = MediaType.TV_SHOW)
                    db.remoteKeyDao.remoteKeyByType(remoteType)
                },
                insertNetworkResult = { resultList ->
                    db.mediaDao.upsertSimple(resultList.toEntity())
                    var addedAt = System.currentTimeMillis()
                    db.favoriteDao.upsert(
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
                db.mediaDao.getFavorites(MediaType.TV_SHOW)
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
        return listRemoteDataSource.setMediaFavorite(
            accountId = session.value.accountDetails?.id ?: 0,
            mediaId = mediaId,
            mediaType = mediaType,
            favorite = isFavorite
        ).onSuccess {
            db.mediaDao.updateFavorite(mediaId, mediaType, isFavorite)
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
                db.favoriteDao.insert(mediaId, mediaType)
            } else {
                db.favoriteDao.delete(mediaId, mediaType)
            }
        }
    }

    override suspend fun setMediaWatchList(
        mediaId: Long,
        mediaType: MediaType,
        isWatchlisted: Boolean
    ): Result<Unit, NetworkError> {
        return listRemoteDataSource.setMediaWatchlist(
            accountId = session.value.accountDetails?.id ?: 0,
            mediaId = mediaId,
            mediaType = mediaType,
            watchlist = isWatchlisted
        ).onSuccess {
            db.mediaDao.updateWatchList(mediaId, mediaType, isWatchlisted)
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
                db.watchlistDao.insert(mediaId, mediaType)
            } else {
                db.watchlistDao.delete(mediaId, mediaType)
            }
        }
    }
}