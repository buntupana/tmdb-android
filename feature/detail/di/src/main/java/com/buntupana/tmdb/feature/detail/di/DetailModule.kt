package com.buntupana.tmdb.feature.detail.di

import com.buntupana.tmdb.feature.detail.data.remote_data_source.DetailRemoteDataSource
import com.buntupana.tmdb.feature.detail.data.repository.DetailRepositoryImpl
import com.buntupana.tmdb.feature.detail.domain.repository.DetailRepository
import com.buntupana.tmdb.feature.detail.domain.usecase.AddEpisodeRatingUseCase
import com.buntupana.tmdb.feature.detail.domain.usecase.AddMediaRatingUseCase
import com.buntupana.tmdb.feature.detail.domain.usecase.GetMovieCreditsUseCase
import com.buntupana.tmdb.feature.detail.domain.usecase.GetMovieDetailsUseCase
import com.buntupana.tmdb.feature.detail.domain.usecase.GetPersonDetailsUseCase
import com.buntupana.tmdb.feature.detail.domain.usecase.GetSeasonDetailsUseCase
import com.buntupana.tmdb.feature.detail.domain.usecase.GetTvShowCreditsUseCase
import com.buntupana.tmdb.feature.detail.domain.usecase.GetTvShowDetailsUseCase
import com.buntupana.tmdb.feature.detail.domain.usecase.GetTvShowSeasonsUseCase
import com.buntupana.tmdb.feature.detail.presentation.cast.CastDetailViewModel
import com.buntupana.tmdb.feature.detail.presentation.episodes.EpisodesDetailViewModel
import com.buntupana.tmdb.feature.detail.presentation.media.MediaDetailViewModel
import com.buntupana.tmdb.feature.detail.presentation.person.PersonDetailViewModel
import com.buntupana.tmdb.feature.detail.presentation.rating.RatingViewModel
import com.buntupana.tmdb.feature.detail.presentation.seasons.SeasonDetailViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

private val dataModule = module {
    singleOf(::DetailRepositoryImpl) bind DetailRepository::class
    singleOf(::DetailRemoteDataSource)
}

private val domainModule = module {
    factoryOf(::AddEpisodeRatingUseCase)
    factoryOf(::GetMovieCreditsUseCase)
    factoryOf(::GetMovieDetailsUseCase)
    factoryOf(::GetPersonDetailsUseCase)
    factoryOf(::GetSeasonDetailsUseCase)
    factoryOf(::GetTvShowCreditsUseCase)
    factoryOf(::GetTvShowDetailsUseCase)
    factoryOf(::GetTvShowSeasonsUseCase)
}

private val presentationModule = module {
    factoryOf(::CastDetailViewModel)
    factoryOf(::EpisodesDetailViewModel)
    factoryOf(::MediaDetailViewModel)
    factoryOf(::PersonDetailViewModel)
    factoryOf(::RatingViewModel)
    factoryOf(::SeasonDetailViewModel)
    factoryOf(::AddMediaRatingUseCase)
}

val detailModule = module {
    includes(dataModule, domainModule, presentationModule)
}