package com.buntupana.tmdb.feature.account.di

import com.buntupana.tmdb.core.di.CoreCommonModule
import com.buntupana.tmdb.feature.account.data.repository.AccountRepositoryImpl
import com.buntupana.tmdb.feature.account.data.repository.ListRepositoryImpl
import com.buntupana.tmdb.feature.account.domain.repository.AccountRepository
import com.buntupana.tmdb.feature.account.domain.repository.ListRepository
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
    abstract fun bindAccountRepository(accountRepositoryImpl: AccountRepositoryImpl): AccountRepository

    @Singleton
    @Binds
    abstract fun bindListRepository(listRepositoryImpl: ListRepositoryImpl): ListRepository
}