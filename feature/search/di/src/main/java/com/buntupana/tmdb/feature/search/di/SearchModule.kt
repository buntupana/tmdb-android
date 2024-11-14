package com.buntupana.tmdb.feature.search.di

import com.buntupana.tmdb.core.di.CoreCommonModule
import com.buntupana.tmdb.feature.search.data.api.SearchApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module(includes = [CoreCommonModule::class])
@InstallIn(SingletonComponent::class)
object SearchModule {

    @Singleton
    @Provides
    fun provideDiscoverApi(retrofit: Retrofit): SearchApi {
        return retrofit.create(SearchApi::class.java)
    }
}