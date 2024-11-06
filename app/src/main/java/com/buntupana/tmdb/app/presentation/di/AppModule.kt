package com.buntupana.tmdb.app.presentation.di

import com.buntupana.tmdb.app.presentation.navigation.NavRoutesMain
import com.buntupana.tmdb.app.presentation.navigation.NavRoutesMainImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideNavRoutesMain(): NavRoutesMain {
        return NavRoutesMainImpl()
    }

}