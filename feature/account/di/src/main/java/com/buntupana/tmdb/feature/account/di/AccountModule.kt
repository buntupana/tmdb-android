package com.buntupana.tmdb.feature.account.di

import com.buntupana.tmdb.core.data.database.dao.FavoriteDao
import com.buntupana.tmdb.core.data.database.dao.MediaDao
import com.buntupana.tmdb.core.data.database.dao.RemoteKeyDao
import com.buntupana.tmdb.core.data.database.dao.WatchlistDao
import com.buntupana.tmdb.core.di.CoreCommonModule
import com.buntupana.tmdb.feature.account.data.remote_data_source.AccountRemoteDataSource
import com.buntupana.tmdb.feature.account.data.repository.AccountRepositoryImpl
import com.buntupana.tmdb.feature.account.domain.repository.AccountRepository
import com.panabuntu.tmdb.core.common.manager.SessionManager
import com.panabuntu.tmdb.core.common.provider.UrlProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module(includes = [CoreCommonModule::class])
@InstallIn(SingletonComponent::class)
class AccountModule {

    @Singleton
    @Provides
    fun providesAccountRepository(
        accountRemoteDataSource: AccountRemoteDataSource,
        mediaDao: MediaDao,
        watchlistDao: WatchlistDao,
        favoriteDao: FavoriteDao,
        remoteKeyDao: RemoteKeyDao,
        sessionManager: SessionManager,
        urlProvider: UrlProvider
    ): AccountRepository {
        return AccountRepositoryImpl(
            accountRemoteDataSource = accountRemoteDataSource,
            mediaDao = mediaDao,
            watchlistDao = watchlistDao,
            favoriteDao = favoriteDao,
            remoteKeyDao = remoteKeyDao,
            sessionManager = sessionManager,
            urlProvider = urlProvider
        )
    }
}