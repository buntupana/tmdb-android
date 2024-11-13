package com.buntupana.tmdb.detail.di

import com.buntupana.tmdb.core.di.CoreModule
import com.buntupana.tmdb.feature.detail.data.api.DetailApi
import com.buntupana.tmdb.feature.detail.data.remote_data_source.DetailRemoteDataSource
import com.buntupana.tmdb.feature.detail.data.repository.DetailRepositoryImpl
import com.buntupana.tmdb.feature.detail.domain.repository.DetailRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module(includes = [CoreModule::class])
@InstallIn(SingletonComponent::class)
class DetailModule {

    @Singleton
    @Provides
    fun provideDiscoverApi(retrofit: Retrofit): DetailApi {
        return retrofit.create(DetailApi::class.java)
    }

    @Singleton
    @Provides
    fun bindDetailRepository(detailRemoteDataSource: DetailRemoteDataSource): DetailRepository {
        return DetailRepositoryImpl(detailRemoteDataSource)
    }
}