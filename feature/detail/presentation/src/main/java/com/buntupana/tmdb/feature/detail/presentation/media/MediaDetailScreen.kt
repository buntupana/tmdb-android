package com.buntupana.tmdb.feature.detail.presentation.media

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsBottomHeight
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.buntupana.tmdb.core.ui.R
import com.buntupana.tmdb.core.ui.composables.CircularProgressIndicatorDelayed
import com.buntupana.tmdb.core.ui.composables.ErrorAndRetry
import com.buntupana.tmdb.core.ui.composables.TopBarLogo
import com.buntupana.tmdb.core.ui.theme.DetailBackgroundColor
import com.buntupana.tmdb.core.ui.theme.Dimens
import com.buntupana.tmdb.core.ui.util.getOnBackgroundColor
import com.buntupana.tmdb.core.ui.util.setStatusBarLightStatusFromBackground
import com.buntupana.tmdb.feature.detail.domain.model.MediaDetails
import com.buntupana.tmdb.feature.detail.domain.model.Season
import com.buntupana.tmdb.feature.detail.presentation.media.comp.AccountBar
import com.buntupana.tmdb.feature.detail.presentation.media.comp.AdditionalInfo
import com.buntupana.tmdb.feature.detail.presentation.media.comp.CastHorizontalList
import com.buntupana.tmdb.feature.detail.presentation.media.comp.Header
import com.buntupana.tmdb.feature.detail.presentation.media.comp.MainInfo
import com.buntupana.tmdb.feature.detail.presentation.media.comp.RecommendationsHorizontal
import com.buntupana.tmdb.feature.detail.presentation.media.comp.SeasonsSection
import com.buntupana.tmdb.feature.detail.presentation.mediaDetailsTvShowSample
import com.buntupana.tmdb.feature.detail.presentation.person.comp.ExternalLinksRow
import com.panabuntu.tmdb.core.common.entity.MediaType

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
    onLogoClick: () -> Unit,
    onRatingClick: (mediaId: Long, mediaType: MediaType, mediaTitle: String, rating: Int?) -> Unit,
    onManageListClick: (mediaId: Long, mediaType: MediaType) -> Unit
) {

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
            onRatingClick(
                viewModel.state.mediaId,
                viewModel.state.mediaType,
                viewModel.state.mediaDetails?.title.orEmpty(),
                viewModel.state.mediaDetails?.userRating
            )
        },
        onListClick = {
            onManageListClick(viewModel.state.mediaId, viewModel.state.mediaType)
        }
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

    setStatusBarLightStatusFromBackground(
        LocalView.current,
        backgroundColor
    )

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        containerColor = backgroundColor,
        modifier = Modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopBarLogo(
                backgroundColor = backgroundColor,
                scrollBehavior = scrollBehavior,
                onBackClick = { onBackClick() },
                onSearchClick = { onSearchClick() },
                onLogoClick = { onLogoClick() },
                shareLink = state.mediaDetails?.shareLink
            )
        },
        bottomBar = {

            if (state.isUserLoggedIn.not() || state.isLoading || state.isGetContentError) return@Scaffold

            Box(
                modifier = Modifier.background(backgroundColor)
            ) {

                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .background(backgroundColor.getOnBackgroundColor().copy(alpha = 0.05f))
                ) { }

                Column {
                    AccountBar(
                        modifier = Modifier
                            .fillMaxWidth(),
                        iconColor = backgroundColor.getOnBackgroundColor(),
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
                    Spacer(
                        modifier = Modifier
                            .windowInsetsBottomHeight(
                                WindowInsets.systemBars
                            )
                    )
                }
            }
        }

    ) { paddingValues ->

        if (state.isLoading) {

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicatorDelayed(
                    color = backgroundColor.getOnBackgroundColor()
                )
            }
        }

        if (state.isGetContentError) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(backgroundColor)
            ) {

                ErrorAndRetry(
                    modifier = Modifier
                        .padding(vertical = 200.dp)
                        .fillMaxSize(),
                    errorMessage = stringResource(id = R.string.message_loading_content_error),
                    textColor = backgroundColor.getOnBackgroundColor(),
                    onRetryClick = onRetryClick
                )
            }

            return@Scaffold
        }

        AnimatedVisibility(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = paddingValues.calculateTopPadding()),
            visible = state.isLoading.not(),
            enter = fadeIn(),
            exit = fadeOut()
        ) {

            if (state.mediaDetails == null) {
                return@AnimatedVisibility
            }

            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .verticalScroll(scrollState)
            ) {

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
                        onCreatorClick = onPersonClick,
                        onRatingClick = onRatingClick
                    )
                }

                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .background(MaterialTheme.colorScheme.background)
                ) {

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
                                onSeasonClick(state.mediaId, season, backgroundColor)
                            },
                            onAllSeasonsClick = {
                                onAllSeasonsClick(
                                    state.mediaDetails,
                                    backgroundColor
                                )
                            }
                        )
                    }

                    RecommendationsHorizontal(
                        modifier = Modifier.fillMaxWidth(),
                        mediaItemList = state.mediaDetails.recommendationList,
                        onItemClick = onRecommendationClick
                    )

                    Spacer(modifier = Modifier.height(Dimens.padding.medium))

                    ExternalLinksRow(
                        modifier = Modifier.padding(start = 10.dp),
                        externalLinkList = state.mediaDetails.externalLinkList
                    )

                    Spacer(modifier = Modifier.height(Dimens.padding.big))

                    AdditionalInfo(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = Dimens.padding.horizontal),
                        mediaDetails = state.mediaDetails
                    )

                    Spacer(
                        modifier = Modifier
                            .height(
                                paddingValues.calculateBottomPadding() + Dimens.padding.big
                            )
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, heightDp = 2000)
@Composable
fun MediaDetailScreenPreview() {

    MediaDetailContent(
        state = MediaDetailState(
            isUserLoggedIn = true,
            isLoading = false,
            isGetContentError = false,
            mediaId = 0L,
            mediaType = MediaType.MOVIE,
            mediaDetails = mediaDetailsTvShowSample,
            backgroundColor = DetailBackgroundColor
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