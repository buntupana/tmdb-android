package com.buntupana.tmdb.feature.discover.di

import com.buntupana.tmdb.core.di.CoreModule
import com.buntupana.tmdb.feature.discover.data.api.DiscoverApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module(includes = [CoreModule::class])
@InstallIn(SingletonComponent::class)
object DiscoverModule {

    @Singleton
    @Provides
    fun provideDiscoverApi(retrofit: Retrofit): DiscoverApi {
        return retrofit.create(DiscoverApi::class.java)
    }

}