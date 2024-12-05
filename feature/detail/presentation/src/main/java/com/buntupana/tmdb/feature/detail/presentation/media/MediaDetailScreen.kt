package com.buntupana.tmdb.feature.detail.presentation.media

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.buntupana.tmdb.core.ui.R
import com.buntupana.tmdb.core.ui.composables.ErrorAndRetry
import com.buntupana.tmdb.core.ui.theme.DetailBackgroundColor
import com.buntupana.tmdb.core.ui.theme.Dimens
import com.buntupana.tmdb.core.ui.util.getOnBackgroundColor
import com.buntupana.tmdb.core.ui.util.setStatusNavigationBarColor
import com.buntupana.tmdb.feature.detail.domain.model.MediaDetails
import com.buntupana.tmdb.feature.detail.domain.model.Season
import com.buntupana.tmdb.feature.detail.presentation.common.MediaDetailsLoading
import com.buntupana.tmdb.feature.detail.presentation.common.TopBar
import com.buntupana.tmdb.feature.detail.presentation.media.comp.AccountBar
import com.buntupana.tmdb.feature.detail.presentation.media.comp.CastHorizontalList
import com.buntupana.tmdb.feature.detail.presentation.media.comp.Header
import com.buntupana.tmdb.feature.detail.presentation.media.comp.MainInfo
import com.buntupana.tmdb.feature.detail.presentation.media.comp.RecommendationsHorizontal
import com.buntupana.tmdb.feature.detail.presentation.media.comp.SeasonsSection
import com.buntupana.tmdb.feature.detail.presentation.mediaDetailsTvShowSample
import com.buntupana.tmdb.feature.detail.presentation.rating.RatingDialog
import com.buntupana.tmdb.feature.detail.presentation.rating.RatingNav
import com.panabuntu.tmdb.core.common.entity.MediaType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MediaDetailScreen(
    viewModel: MediaDetailViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
    onSearchClick: () -> Unit,
    onPersonClick: (personId: Long) -> Unit,
    onFullCastClick: (mediaId: Long, mediaType: MediaType, mediaTitle: String, mediaReleaseYear: String?, mediaPosterUrl: String?, backgroundColor: Color) -> Unit,
    onSeasonClick: (tvShowId: Long, seasonName: String, seasonNumber: Int, posterUrl: String?, backgroundColor: Color, releaseYear: String?) -> Unit,
    onAllSeasonsClick: (tvShowId: Long, tvShowTitle: String, releaseYear: String?, posterUrl: String?, backgroundColor: Color) -> Unit,
    onRecommendationClick: (mediaId: Long, mediaType: MediaType, backgroundColor: Color?) -> Unit,
    onLogoClick: () -> Unit
) {

    var showBottomSheet by remember { mutableStateOf(false) }

    MediaDetailContent(
        state = viewModel.state,
        onBackClick = onBackClick,
        onSearchClick = onSearchClick,
        onPersonClick = onPersonClick,
        onFullCastClick = { mediaDetails, mediaType, backgroundColor ->
            onFullCastClick(
                mediaDetails.id,
                mediaType,
                mediaDetails.title,
                mediaDetails.releaseDate?.year.toString(),
                mediaDetails.posterUrl,
                backgroundColor
            )
        },
        onSeasonClick = { tvShowId, season, backgroundColor ->
            onSeasonClick(
                tvShowId,
                season.name,
                season.seasonNumber ?: 0,
                season.posterUrl,
                backgroundColor,
                season.airDate?.year.toString()
            )
        },
        onAllSeasonsClick = { mediaDetails, backgroundColor ->
            onAllSeasonsClick(
                mediaDetails.id,
                mediaDetails.title,
                mediaDetails.releaseDate?.year.toString(),
                mediaDetails.posterUrl,
                backgroundColor
            )
        },
        onRecommendationClick = { mediaId, mediaType ->
            onRecommendationClick(mediaId, mediaType, null)
        },
        onRetryClick = {
            viewModel.onEvent(MediaDetailEvent.GetMediaDetails)
        },
        onLogoClick = onLogoClick,
        onFavoriteClick = {
            viewModel.onEvent(MediaDetailEvent.SetFavorite)
        },
        onWatchlistClick = {
            viewModel.onEvent(MediaDetailEvent.SetWatchList)
        },
        onRatingClick = {
            showBottomSheet = true
        },
        onListClick = {

        }
    )

    RatingDialog(
        ratingNav = RatingNav(
            mediaType = viewModel.state.mediaType,
            mediaId = viewModel.state.mediaId,
            mediaTitle = viewModel.state.mediaDetails?.title.orEmpty(),
            rating = viewModel.state.mediaDetails?.userRating
        ),
        showDialog = showBottomSheet,
        onRatingSuccess = { rating ->
            showBottomSheet = false
            viewModel.onEvent(MediaDetailEvent.OnRatingSuccess(rating))
        },
        onDismiss = { showBottomSheet = false }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MediaDetailContent(
    state: MediaDetailState,
    onBackClick: () -> Unit,
    onSearchClick: () -> Unit,
    onPersonClick: (personId: Long) -> Unit,
    onFullCastClick: (mediaDetails: MediaDetails, mediaType: MediaType, backgroundColor: Color) -> Unit,
    onSeasonClick: (tvShowId: Long, season: Season, backgroundColor: Color) -> Unit,
    onAllSeasonsClick: (mediaDetails: MediaDetails.TvShow, backgroundColor: Color) -> Unit,
    onRecommendationClick: (mediaId: Long, mediaType: MediaType) -> Unit,
    onRetryClick: () -> Unit,
    onLogoClick: () -> Unit,
    onFavoriteClick: () -> Unit,
    onWatchlistClick: () -> Unit,
    onRatingClick: () -> Unit,
    onListClick: () -> Unit
) {

    val scrollState = rememberScrollState()

    var backgroundColor by remember {
        mutableStateOf(state.backgroundColor)
    }

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = Modifier
            .setStatusNavigationBarColor(backgroundColor)
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopBar(
                backgroundColor = backgroundColor,
                scrollBehavior = scrollBehavior,
                onBackClick = { onBackClick() },
                onSearchClick = { onSearchClick() },
                onLogoClick = { onLogoClick() }
            )
        },
        bottomBar = {
            if (state.isLoading.not() && state.isGetContentError.not() && state.isUserLoggedIn) {
                AccountBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(backgroundColor),
                    backgroundColor = backgroundColor,
                    isFavorite = state.mediaDetails?.isFavorite ?: false,
                    isWatchListed = state.mediaDetails?.isWatchlisted ?: false,
                    isFavoriteLoading = state.isFavoriteLoading,
                    isWatchlistLoading = state.isWatchlistLoading,
                    userRating = state.mediaDetails?.userRating,
                    isRateable = state.mediaDetails?.isRateable ?: false,
                    isRatingLoading = state.isRatingLoading,
                    onFavoriteClick = onFavoriteClick,
                    onWatchlistClick = onWatchlistClick,
                    onRatingClick = onRatingClick,
                    onListClick = onListClick
                )
            }
        }

    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .verticalScroll(scrollState)
        ) {

            when {
                state.isLoading -> {
                    MediaDetailsLoading()
                }

                state.isGetContentError -> {
                    ErrorAndRetry(
                        modifier = Modifier
                            .padding(vertical = 200.dp)
                            .fillMaxSize(),
                        errorMessage = stringResource(id = R.string.message_loading_content_error),
                        onRetryClick = onRetryClick
                    )
                }

                state.mediaDetails != null -> {
                    Column(
                        Modifier.background(backgroundColor)
                    ) {

                        Header(
                            mediaDetails = state.mediaDetails,
                            backgroundColor = backgroundColor
                        ) { dominantColor ->
                            if (dominantColor != backgroundColor) {
                                backgroundColor = dominantColor
                            }
                        }

                        MainInfo(
                            mediaDetails = state.mediaDetails,
                            textColor = backgroundColor.getOnBackgroundColor(),
                            onItemClick = onPersonClick
                        )
                    }

                    CastHorizontalList(
                        modifier = Modifier.background(MaterialTheme.colorScheme.background),
                        mediaDetails = state.mediaDetails,
                        onItemClick = onPersonClick,
                        onFullCastClick = {
                            onFullCastClick(
                                state.mediaDetails,
                                state.mediaType,
                                state.backgroundColor
                            )
                        }
                    )

                    if (state.mediaDetails is MediaDetails.TvShow) {
                        SeasonsSection(
                            seasonList = state.mediaDetails.seasonList,
                            isInAir = state.mediaDetails.isInAir,
                            lastEpisode = state.mediaDetails.lastEpisode,
                            nextEpisode = state.mediaDetails.nextEpisode,
                            onLastSeasonClick = { season ->
                                onSeasonClick(state.mediaId, season, state.backgroundColor)
                            },
                            onAllSeasonsClick = {
                                onAllSeasonsClick(
                                    state.mediaDetails,
                                    state.backgroundColor
                                )
                            }
                        )
                    }

                    RecommendationsHorizontal(
                        modifier = Modifier.fillMaxWidth(),
                        mediaItemList = state.mediaDetails.recommendationList,
                        onItemClick = onRecommendationClick
                    )

                    Spacer(modifier = Modifier.padding(Dimens.padding.vertical))
                }
            }
        }
    }
}

@Preview(heightDp = 2000)
@Composable
fun MediaDetailScreenPreview() {

    MediaDetailContent(
        state = MediaDetailState(
            mediaId = 0L,
            mediaType = MediaType.MOVIE,
            mediaDetails = mediaDetailsTvShowSample,
            backgroundColor = DetailBackgroundColor,
            isGetContentError = false
        ),
        onBackClick = {},
        onSearchClick = {},
        onPersonClick = {},
        onFullCastClick = { _, _, _ -> },
        onSeasonClick = { _, _, _ -> },
        onAllSeasonsClick = { _, _ -> },
        onRecommendationClick = { _, _ -> },
        onRetryClick = {},
        onLogoClick = {},
        onFavoriteClick = {},
        onWatchlistClick = {},
        onRatingClick = {},
        onListClick = {}
    )
}