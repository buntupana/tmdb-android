package com.buntupana.tmdb.feature.account.di

import com.buntupana.tmdb.core.di.CoreModule
import com.buntupana.tmdb.feature.account.data.repository.LoginRepositoryImpl
import com.buntupana.tmdb.feature.account.domain.repository.LoginRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module(includes = [CoreModule::class])
@InstallIn(SingletonComponent::class)
abstract class AccountModule {

    @Singleton
    @Binds
    abstract fun bindLoginRepository(loginRepositoryImpl: LoginRepositoryImpl): LoginRepository
}