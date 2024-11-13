package com.buntupana.tmdb.core.ui.di

import com.buntupana.tmdb.core.ui.navigation.NavRoutesMain
import com.buntupana.tmdb.core.ui.navigation.NavRoutesMainImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CoreUiModule {

    @Provides
    @Singleton
    fun provideNavRouteMain(): NavRoutesMain {
        return NavRoutesMainImpl()
    }

}