package com.buntupana.tmdb.feature.discover.di

import com.buntupana.tmdb.core.di.CoreCommonModule
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module(includes = [CoreCommonModule::class])
@InstallIn(SingletonComponent::class)
object DiscoverModule {

}