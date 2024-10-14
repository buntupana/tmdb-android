package com.buntupana.tmdb.feature.detail.presentation.media

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.buntupana.tmdb.core.R
import com.buntupana.tmdb.core.domain.entity.MediaType
import com.buntupana.tmdb.core.presentation.composables.ErrorAndRetry
import com.buntupana.tmdb.core.presentation.theme.DetailBackgroundColor
import com.buntupana.tmdb.core.presentation.theme.Dimens
import com.buntupana.tmdb.core.presentation.util.getOnBackgroundColor
import com.buntupana.tmdb.feature.detail.domain.model.MediaDetails
import com.buntupana.tmdb.feature.detail.domain.model.Season
import com.buntupana.tmdb.feature.detail.presentation.DetailNavigator
import com.buntupana.tmdb.feature.detail.presentation.common.MediaDetailsLoading
import com.buntupana.tmdb.feature.detail.presentation.common.TopBar
import com.buntupana.tmdb.feature.detail.presentation.media.comp.CastHorizontalList
import com.buntupana.tmdb.feature.detail.presentation.media.comp.Header
import com.buntupana.tmdb.feature.detail.presentation.media.comp.MainInfo
import com.buntupana.tmdb.feature.detail.presentation.media.comp.RecommendationsHorizontal
import com.buntupana.tmdb.feature.detail.presentation.media.comp.SeasonsSection
import com.buntupana.tmdb.feature.detail.presentation.mediaDetailsTvShowSample
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun MediaDetailScreen(
    viewModel: MediaDetailViewModel = hiltViewModel(),
    detailNavigator: DetailNavigator
) {

    MediaDetailContent(
        state = viewModel.state,
        onBackClick = { detailNavigator.navigateBack() },
        onSearchClick = { detailNavigator.navigateToSearch() },
        onPersonClick = { personId ->
            detailNavigator.navigateToPerson(personId)
        },
        onFullCastClick = { mediaDetails, mediaType, backgroundColor ->
            detailNavigator.navigateToFullCast(
                mediaId = mediaDetails.id,
                mediaType = mediaType,
                mediaTitle = mediaDetails.title,
                mediaReleaseYear = mediaDetails.releaseDate?.year.toString(),
                mediaPosterUrl = mediaDetails.posterUrl,
                backgroundColor = backgroundColor
            )
        },
        onSeasonClick = { tvShowId, season, backgroundColor ->
            detailNavigator.navigateToEpisodes(
                tvShowId = tvShowId,
                seasonName = season.name,
                seasonNumber = season.seasonNumber ?: 0,
                posterUrl = season.posterUrl,
                backgroundColor = backgroundColor,
                releaseYear = season.airDate?.year.toString()
            )
        },
        onAllSeasonsClick = { mediaDetails, backgroundColor ->
            detailNavigator.navigateToSeasons(
                tvShowId = mediaDetails.id,
                tvShowTitle = mediaDetails.title,
                releaseYear = mediaDetails.releaseDate?.year.toString(),
                posterUrl = mediaDetails.posterUrl,
                backgroundColor = backgroundColor
            )
        },
        onRecommendationClick = { mediaId, mediaType ->
            detailNavigator.navigateToMediaDetail(
                mediaId = mediaId,
                mediaType = mediaType,
                backgroundColor = null
            )
        },
        onRetryClick = {
            viewModel.onEvent(MediaDetailEvent.GetMediaDetails)
        },
        onLogoClick = {
            detailNavigator.navigateToMainScreen()
        }
    )
}

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
    onLogoClick: () -> Unit
) {

    val scrollState = rememberScrollState()

    var backgroundColor by remember {
        mutableStateOf(state.backgroundColor)
    }

    val systemUiController = rememberSystemUiController()

    systemUiController.setSystemBarsColor(backgroundColor)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {

        TopBar(
            modifier = Modifier.background(backgroundColor),
            textColor = backgroundColor.getOnBackgroundColor(),
            onSearchClick = { onSearchClick() },
            onBackClick = { onBackClick() },
            onLogoClick = { onLogoClick() }
        )

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
        onLogoClick = {}
    )
}