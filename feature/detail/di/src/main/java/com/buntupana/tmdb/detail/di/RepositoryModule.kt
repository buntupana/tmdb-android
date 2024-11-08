package com.buntupana.tmdb.detail.di

import com.buntupana.tmdb.feature.detail.data.repository.DetailRepositoryImpl
import com.buntupana.tmdb.feature.detail.domain.repository.DetailRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindDiscoverRepository(discoverRepositoryImpl: DetailRepositoryImpl): DetailRepository
}