package com.buntupana.tmdb.feature.account.di

import com.buntupana.tmdb.feature.account.data.remote_data_source.AccountRemoteDataSource
import com.buntupana.tmdb.feature.account.data.repository.AccountRepositoryImpl
import com.buntupana.tmdb.feature.account.domain.repository.AccountRepository
import com.buntupana.tmdb.feature.account.domain.usecase.CreateAuthenticationUrlUseCase
import com.buntupana.tmdb.feature.account.domain.usecase.CreateSessionUseCase
import com.buntupana.tmdb.feature.account.domain.usecase.ReloadAccountDetailsUseCase
import com.buntupana.tmdb.feature.account.domain.usecase.SignOutUseCase
import com.buntupana.tmdb.feature.account.presentation.account.AccountViewModel
import com.buntupana.tmdb.feature.account.presentation.sign_in.SignInViewModel
import com.buntupana.tmdb.feature.account.presentation.sign_out.SignOutViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

private val dataModule = module {
    singleOf(::AccountRepositoryImpl) bind AccountRepository::class
    singleOf(::AccountRemoteDataSource)
}

private val domainModule = module {
    factoryOf(::CreateAuthenticationUrlUseCase)
    factoryOf(::CreateSessionUseCase)
    factoryOf(::ReloadAccountDetailsUseCase)
    factoryOf(::SignOutUseCase)
}

private val presentationModule = module {
    factoryOf(::AccountViewModel)
    factoryOf(::SignInViewModel)
    factoryOf(::SignOutViewModel)
}

val accountModule = module {
    includes(dataModule, domainModule, presentationModule)
}