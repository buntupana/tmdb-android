package com.buntupana.tmdb.feature.lists.di

import com.buntupana.tmdb.core.data.database.TmdbDataBase
import com.buntupana.tmdb.core.di.CoreCommonModule
import com.buntupana.tmdb.feature.lists.data.remote_data_source.ListRemoteDataSource
import com.buntupana.tmdb.feature.lists.data.repository.ListRepositoryImpl
import com.buntupana.tmdb.feature.lists.domain.repository.ListRepository
import com.panabuntu.tmdb.core.common.manager.SessionManager
import com.panabuntu.tmdb.core.common.provider.UrlProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module(includes = [CoreCommonModule::class])
@InstallIn(SingletonComponent::class)
class ListsModule {

    @Singleton
    @Provides
    fun providesListRepository(
        listRemoteDataSource: ListRemoteDataSource,
        sessionManager: SessionManager,
        urlProvider: UrlProvider,
        db: TmdbDataBase,
    ): ListRepository {
        return ListRepositoryImpl(
            listRemoteDataSource = listRemoteDataSource,
            sessionManager = sessionManager,
            urlProvider = urlProvider,
            db = db
        )
    }
}