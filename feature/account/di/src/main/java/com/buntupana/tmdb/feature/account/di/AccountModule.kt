package com.buntupana.tmdb.feature.account.di

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
        sessionManager: SessionManager,
        urlProvider: UrlProvider
    ): AccountRepository {
        return AccountRepositoryImpl(
            accountRemoteDataSource = accountRemoteDataSource,
            sessionManager = sessionManager,
            urlProvider = urlProvider
        )
    }
}