package com.buntupana.tmdb.feature.account.di

import com.buntupana.tmdb.feature.account.data.remote_data_source.AccountRemoteDataSource
import com.buntupana.tmdb.feature.account.data.repository.AccountRepositoryImpl
import com.buntupana.tmdb.feature.account.domain.repository.AccountRepository
import com.buntupana.tmdb.feature.account.domain.usecase.AddMediaRatingUseCase
import com.buntupana.tmdb.feature.account.domain.usecase.CreateAuthenticationUrlUseCase
import com.buntupana.tmdb.feature.account.domain.usecase.CreateSessionUseCase
import com.buntupana.tmdb.feature.account.domain.usecase.GetFavoriteTotalCountUseCase
import com.buntupana.tmdb.feature.account.domain.usecase.GetFavoritesUseCase
import com.buntupana.tmdb.feature.account.domain.usecase.GetMovieFavoritesPagingUseCase
import com.buntupana.tmdb.feature.account.domain.usecase.GetMovieWatchlistPagingUseCase
import com.buntupana.tmdb.feature.account.domain.usecase.GetTvShowFavoritesPagingUseCase
import com.buntupana.tmdb.feature.account.domain.usecase.GetTvShowWatchlistPagingUseCase
import com.buntupana.tmdb.feature.account.domain.usecase.GetWatchlistTotalCountUseCase
import com.buntupana.tmdb.feature.account.domain.usecase.GetWatchlistUseCase
import com.buntupana.tmdb.feature.account.domain.usecase.ReloadAccountDetailsUseCase
import com.buntupana.tmdb.feature.account.domain.usecase.SetMediaFavoriteUseCase
import com.buntupana.tmdb.feature.account.domain.usecase.SetMediaWatchListUseCase
import com.buntupana.tmdb.feature.account.domain.usecase.SignOutUseCase
import com.buntupana.tmdb.feature.account.presentation.account.AccountViewModel
import com.buntupana.tmdb.feature.account.presentation.sign_in.SignInViewModel
import com.buntupana.tmdb.feature.account.presentation.sign_out.SignOutViewModel
import com.buntupana.tmdb.feature.account.presentation.watchlist_favorites.WatchlistFavoritesViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

private val dataModule = module {
    singleOf(::AccountRepositoryImpl) bind AccountRepository::class
    singleOf(::AccountRemoteDataSource)
}

private val domainModule = module {
    factoryOf(::AddMediaRatingUseCase)
    factoryOf(::CreateAuthenticationUrlUseCase)
    factoryOf(::CreateSessionUseCase)
    factoryOf(::GetFavoritesUseCase)
    factoryOf(::GetFavoriteTotalCountUseCase)
    factoryOf(::GetMovieFavoritesPagingUseCase)
    factoryOf(::GetMovieWatchlistPagingUseCase)
    factoryOf(::GetTvShowFavoritesPagingUseCase)
    factoryOf(::GetTvShowWatchlistPagingUseCase)
    factoryOf(::GetWatchlistTotalCountUseCase)
    factoryOf(::GetWatchlistUseCase)
    factoryOf(::ReloadAccountDetailsUseCase)
    factoryOf(::SetMediaFavoriteUseCase)
    factoryOf(::SetMediaWatchListUseCase)
    factoryOf(::SignOutUseCase)
}

private val presentationModule = module {
    factoryOf(::AccountViewModel)
    factoryOf(::SignInViewModel)
    factoryOf(::SignOutViewModel)
    factoryOf(::WatchlistFavoritesViewModel)
}

val accountModule = module {
    includes(dataModule, domainModule, presentationModule)
}