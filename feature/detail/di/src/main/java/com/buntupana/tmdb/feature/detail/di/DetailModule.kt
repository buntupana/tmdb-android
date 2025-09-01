package com.buntupana.tmdb.feature.detail.di

import com.buntupana.tmdb.core.data.database.TmdbDataBase
import com.buntupana.tmdb.core.di.CoreCommonModule
import com.buntupana.tmdb.feature.detail.data.remote_data_source.DetailRemoteDataSource
import com.buntupana.tmdb.feature.detail.data.repository.DetailRepositoryImpl
import com.buntupana.tmdb.feature.detail.domain.repository.DetailRepository
import com.panabuntu.tmdb.core.common.manager.SessionManager
import com.panabuntu.tmdb.core.common.provider.UrlProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module(includes = [CoreCommonModule::class])
@InstallIn(SingletonComponent::class)
class DetailModule {

    @Singleton
    @Provides
    fun bindDetailRepository(
        detailRemoteDataSource: DetailRemoteDataSource,
        db: TmdbDataBase,
        urlProvider: UrlProvider,
        sessionManager: SessionManager
    ): DetailRepository {
        return DetailRepositoryImpl(
            detailRemoteDataSource = detailRemoteDataSource,
            urlProvider = urlProvider,
            sessionManager = sessionManager,
            db = db
        )
    }
}