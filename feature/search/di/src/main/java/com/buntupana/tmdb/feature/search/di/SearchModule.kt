package com.buntupana.tmdb.feature.search.di

import com.buntupana.tmdb.feature.search.data.remote_data_source.SearchRemoteDataSource
import com.buntupana.tmdb.feature.search.data.repository.SearchRepositoryImpl
import com.buntupana.tmdb.feature.search.domain.repository.SearchRepository
import com.buntupana.tmdb.feature.search.domain.usecase.GetSearchMediaUseCase
import com.buntupana.tmdb.feature.search.domain.usecase.GetSearchMoviesUseCase
import com.buntupana.tmdb.feature.search.domain.usecase.GetSearchPersonsUseCase
import com.buntupana.tmdb.feature.search.domain.usecase.GetSearchResultCountUseCase
import com.buntupana.tmdb.feature.search.domain.usecase.GetSearchTvShowsUseCase
import com.buntupana.tmdb.feature.search.domain.usecase.GetTrendingMediaUseCase
import com.buntupana.tmdb.feature.search.presentation.SearchViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

private val dataModule = module {
    singleOf(::SearchRepositoryImpl) bind SearchRepository::class
    singleOf(::SearchRemoteDataSource)
}

private val domainModule = module {
    factoryOf(::GetSearchMediaUseCase)
    factoryOf(::GetSearchMoviesUseCase)
    factoryOf(::GetSearchPersonsUseCase)
    factoryOf(::GetSearchResultCountUseCase)
    factoryOf(::GetSearchTvShowsUseCase)
    factoryOf(::GetTrendingMediaUseCase)
}

private val presentationModule = module {
    factoryOf(::SearchViewModel)
}

val detailModule = module {
    includes(dataModule, domainModule, presentationModule)
}