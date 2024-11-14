package com.buntupana.tmdb.feature.account.di

import com.buntupana.tmdb.core.di.CoreCommonModule
import com.buntupana.tmdb.feature.account.data.repository.AccountRepositoryImpl
import com.buntupana.tmdb.feature.account.domain.repository.AccountRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module(includes = [CoreCommonModule::class])
@InstallIn(SingletonComponent::class)
abstract class AccountModule {

    @Singleton
    @Binds
    abstract fun bindLoginRepository(accountRepositoryImpl: AccountRepositoryImpl): AccountRepository
}