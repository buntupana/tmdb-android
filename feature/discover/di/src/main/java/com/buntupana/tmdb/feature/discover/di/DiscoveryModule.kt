package com.buntupana.tmdb.feature.discover.di

import com.buntupana.tmdb.feature.discover.data.remote_data_source.DiscoverRemoteDataSource
import com.buntupana.tmdb.feature.discover.data.repository.DiscoverRepositoryImpl
import com.buntupana.tmdb.feature.discover.domain.repository.DiscoverRepository
import com.buntupana.tmdb.feature.discover.domain.usecase.GetFilteredMoviesPagingUseCase
import com.buntupana.tmdb.feature.discover.domain.usecase.GetFilteredTvShowsPagingUseCase
import com.buntupana.tmdb.feature.discover.domain.usecase.GetFreeToWatchMediaListUseCase
import com.buntupana.tmdb.feature.discover.domain.usecase.GetPopularMoviesListUseCase
import com.buntupana.tmdb.feature.discover.domain.usecase.GetTrendingMediaListUseCase
import com.buntupana.tmdb.feature.discover.presentation.discover.DiscoverViewModel
import com.buntupana.tmdb.feature.discover.presentation.media_filter.MediaFilterViewModel
import com.buntupana.tmdb.feature.discover.presentation.media_list.MediaListViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

private val dataModule = module {
    singleOf(::DiscoverRepositoryImpl) bind DiscoverRepository::class
    singleOf(::DiscoverRemoteDataSource)
}

private val domainModule = module {
    factoryOf(::GetFilteredMoviesPagingUseCase)
    factoryOf(::GetFilteredTvShowsPagingUseCase)
    factoryOf(::GetFreeToWatchMediaListUseCase)
    factoryOf(::GetPopularMoviesListUseCase)
    factoryOf(::GetTrendingMediaListUseCase)
}

private val presentationModule = module {
    factoryOf(::DiscoverViewModel)
    factoryOf(::MediaFilterViewModel)
    factoryOf(::MediaListViewModel)
}

val discoverModule = module {
    includes(dataModule, domainModule, presentationModule)
}