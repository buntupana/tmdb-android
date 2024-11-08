package com.buntupana.tmdb.feature.discover.di

import com.buntupana.tmdb.feature.discover.data.repository.DiscoverRepositoryImpl
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
    abstract fun bindDiscoverRepository(discoverRepositoryImpl: DiscoverRepositoryImpl): com.buntupana.tmdb.feature.discover.domain.repository.DiscoverRepository
}