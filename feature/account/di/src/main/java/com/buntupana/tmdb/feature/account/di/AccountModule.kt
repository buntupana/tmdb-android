package com.buntupana.tmdb.feature.account.di

import com.buntupana.tmdb.feature.account.data.repository.LoginRepositoryImpl
import com.buntupana.tmdb.feature.account.domain.repository.LoginRepository
import com.panabuntu.tmdb.core.common.di.CoreCommonModule
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
    abstract fun bindLoginRepository(loginRepositoryImpl: LoginRepositoryImpl): LoginRepository
}