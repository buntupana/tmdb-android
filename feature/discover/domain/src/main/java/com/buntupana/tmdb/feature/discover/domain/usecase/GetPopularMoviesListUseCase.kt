package com.buntupana.tmdb.feature.discover.domain.usecase

import com.buntupana.tmdb.feature.discover.domain.entity.MonetizationType
import com.buntupana.tmdb.feature.discover.domain.entity.PopularType
import com.buntupana.tmdb.feature.discover.domain.repository.DiscoverRepository
import com.panabuntu.tmdb.core.common.entity.Resource
import com.panabuntu.tmdb.core.common.model.MediaItem
import com.panabuntu.tmdb.core.common.usecase.UseCaseResource
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class GetPopularMoviesListUseCase @Inject constructor(
    private val discoverRepository: DiscoverRepository
) : UseCaseResource<PopularType, List<MediaItem>>() {

    override suspend fun getSource(params: PopularType): Resource<List<MediaItem>> {

        return when (params) {
            PopularType.STREAMING -> {
                coroutineScope {
                    val movieItemListDef =
                        async { discoverRepository.getMoviesPopular(MonetizationType.FLAT_RATE) }
                    val tvShowItemListDef =
                        async { discoverRepository.getTvShowsPopular(MonetizationType.FLAT_RATE) }

                    val movieItemList = movieItemListDef.await()
                    val tvShowItemList = tvShowItemListDef.await()

                    when {
                        movieItemList is Resource.Error -> movieItemList
                        tvShowItemList is Resource.Error -> tvShowItemList
                        movieItemList is Resource.Success && tvShowItemList is Resource.Success -> {
                            val result = movieItemList.data + tvShowItemList.data
                            Resource.Success(result.shuffled())
                        }
                        else -> {
                            movieItemList
                        }
                    }
                }
            }
            PopularType.ON_TV -> discoverRepository.getTvShowOnAir()
            PopularType.FOR_RENT -> discoverRepository.getMoviesPopular(MonetizationType.RENT)
            PopularType.IN_THEATRES -> discoverRepository.getMoviesInTheatres()
        }
    }
}