package com.buntupana.tmdb.feature.lists.di

import com.buntupana.tmdb.feature.lists.data.remote_data_source.ListRemoteDataSource
import com.buntupana.tmdb.feature.lists.data.repository.ListRepositoryImpl
import com.buntupana.tmdb.feature.lists.domain.repository.ListRepository
import com.buntupana.tmdb.feature.lists.domain.usecase.CreateListUseCase
import com.buntupana.tmdb.feature.lists.domain.usecase.DeleteItemListUseCase
import com.buntupana.tmdb.feature.lists.domain.usecase.DeleteListUseCase
import com.buntupana.tmdb.feature.lists.domain.usecase.GetFavoriteTotalCountUseCase
import com.buntupana.tmdb.feature.lists.domain.usecase.GetFavoritesUseCase
import com.buntupana.tmdb.feature.lists.domain.usecase.GetListDetailsUseCase
import com.buntupana.tmdb.feature.lists.domain.usecase.GetListItemsPagingUseCase
import com.buntupana.tmdb.feature.lists.domain.usecase.GetListTotalCountUseCase
import com.buntupana.tmdb.feature.lists.domain.usecase.GetListsFromMediaUseCase
import com.buntupana.tmdb.feature.lists.domain.usecase.GetListsPagingUseCase
import com.buntupana.tmdb.feature.lists.domain.usecase.GetListsUseCase
import com.buntupana.tmdb.feature.lists.domain.usecase.GetMovieFavoritesPagingUseCase
import com.buntupana.tmdb.feature.lists.domain.usecase.GetMovieWatchlistPagingUseCase
import com.buntupana.tmdb.feature.lists.domain.usecase.GetTvShowFavoritesPagingUseCase
import com.buntupana.tmdb.feature.lists.domain.usecase.GetTvShowWatchlistPagingUseCase
import com.buntupana.tmdb.feature.lists.domain.usecase.GetWatchlistTotalCountUseCase
import com.buntupana.tmdb.feature.lists.domain.usecase.GetWatchlistUseCase
import com.buntupana.tmdb.feature.lists.domain.usecase.SetListsForMediaUseCase
import com.buntupana.tmdb.feature.lists.domain.usecase.SetMediaFavoriteUseCase
import com.buntupana.tmdb.feature.lists.domain.usecase.SetMediaWatchListUseCase
import com.buntupana.tmdb.feature.lists.domain.usecase.UpdateListUseCase
import com.buntupana.tmdb.feature.lists.presentation.create_update_list.CreateUpdateListViewModel
import com.buntupana.tmdb.feature.lists.presentation.delete_item_list.DeleteItemListViewModel
import com.buntupana.tmdb.feature.lists.presentation.delete_item_watchlist_favorites.DeleteItemWatchlistFavoritesViewModel
import com.buntupana.tmdb.feature.lists.presentation.delete_list.DeleteListViewModel
import com.buntupana.tmdb.feature.lists.presentation.list_detail.ListDetailViewModel
import com.buntupana.tmdb.feature.lists.presentation.lists.ListsViewModel
import com.buntupana.tmdb.feature.lists.presentation.manage_lists.ManageListsViewModel
import com.buntupana.tmdb.feature.lists.presentation.watchlist_favorites.WatchlistFavoritesViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

private val dataModule = module {
    singleOf(::ListRepositoryImpl) bind ListRepository::class
    singleOf(::ListRemoteDataSource)
}

private val domainModule = module {
    factoryOf(::CreateListUseCase)
    factoryOf(::DeleteItemListUseCase)
    factoryOf(::DeleteListUseCase)
    factoryOf(::GetListDetailsUseCase)
    factoryOf(::GetListItemsPagingUseCase)
    factoryOf(::GetListsFromMediaUseCase)
    factoryOf(::GetListsPagingUseCase)
    factoryOf(::GetListsUseCase)
    factoryOf(::GetListTotalCountUseCase)
    factoryOf(::SetListsForMediaUseCase)
    factoryOf(::UpdateListUseCase)
    factoryOf(::GetFavoritesUseCase)
    factoryOf(::GetFavoriteTotalCountUseCase)
    factoryOf(::GetMovieFavoritesPagingUseCase)
    factoryOf(::GetMovieWatchlistPagingUseCase)
    factoryOf(::GetTvShowFavoritesPagingUseCase)
    factoryOf(::GetTvShowWatchlistPagingUseCase)
    factoryOf(::GetWatchlistTotalCountUseCase)
    factoryOf(::GetWatchlistUseCase)
    factoryOf(::SetMediaFavoriteUseCase)
    factoryOf(::SetMediaWatchListUseCase)
    factoryOf(::DeleteItemWatchlistFavoritesViewModel)
}

private val presentationModule = module {
    factoryOf(::CreateUpdateListViewModel)
    factoryOf(::DeleteItemListViewModel)
    factoryOf(::DeleteListViewModel)
    factoryOf(::ListDetailViewModel)
    factoryOf(::ListsViewModel)
    factoryOf(::ManageListsViewModel)
    factoryOf(::WatchlistFavoritesViewModel)
}

val listsModule = module {
    includes(dataModule, domainModule, presentationModule)
}