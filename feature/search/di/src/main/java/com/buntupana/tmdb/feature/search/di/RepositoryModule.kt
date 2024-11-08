package com.buntupana.tmdb.feature.search.di

import com.buntupana.tmdb.feature.search.data.repository.SearchRepositoryImpl
import com.buntupana.tmdb.feature.search.domain.repository.SearchRepository
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
    abstract fun bindSearchRepository(searchRepository: SearchRepositoryImpl): SearchRepository
}