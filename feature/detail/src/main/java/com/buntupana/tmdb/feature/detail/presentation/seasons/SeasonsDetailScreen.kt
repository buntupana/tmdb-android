package com.buntupana.tmdb.feature.detail.presentation.seasons

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
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
import com.buntupana.tmdb.core.presentation.composables.ErrorAndRetry
import com.buntupana.tmdb.core.presentation.theme.DetailBackgroundColor
import com.buntupana.tmdb.core.presentation.util.getOnBackgroundColor
import com.buntupana.tmdb.core.presentation.util.setStatusNavigationBarColor
import com.buntupana.tmdb.feature.detail.domain.model.Season
import com.buntupana.tmdb.feature.detail.presentation.DetailNavigator
import com.buntupana.tmdb.feature.detail.presentation.common.HeaderSimple
import com.buntupana.tmdb.feature.detail.presentation.common.MediaDetailsLoading
import com.buntupana.tmdb.feature.detail.presentation.common.TopBar
import com.buntupana.tmdb.feature.detail.presentation.seasonSample
import com.buntupana.tmdb.feature.detail.presentation.seasons.comp.SeasonItem

@Composable
fun SeasonsDetailScreen(
    viewModel: SeasonDetailViewModel = hiltViewModel(),
    detailNavigator: DetailNavigator
) {

    SeasonsContent(
        state = viewModel.state,
        onBackClick = { detailNavigator.navigateBack() },
        onRetryClick = { viewModel.onEvent(SeasonsDetailEvent.GetSeasons) },
        onSearchClick = { detailNavigator.navigateToSearch() },
        onLogoClick = { detailNavigator.navigateToMainScreen() },
        onSeasonClick = { tvShowId, season, backgroundColor ->
            detailNavigator.navigateToEpisodes(
                tvShowId = tvShowId,
                seasonName = season.name,
                seasonNumber = season.seasonNumber ?: 0,
                posterUrl = season.posterUrl,
                backgroundColor = backgroundColor,
                releaseYear = season.airDate?.year.toString()
            )
        }
    )
}

@Composable
private fun SeasonsContent(
    state: SeasonsDetailState,
    onBackClick: () -> Unit,
    onRetryClick: () -> Unit,
    onSearchClick: () -> Unit,
    onLogoClick: () -> Unit,
    onSeasonClick: (
        tvShowId: Long,
        season: Season,
        backgroundColor: Color,
    ) -> Unit
) {

    var backgroundColor by remember {
        mutableStateOf(state.backgroundColor)
    }

    Column(
        modifier = Modifier
            .setStatusNavigationBarColor(backgroundColor)
    ) {

        TopBar(
            modifier = Modifier.background(backgroundColor),
            textColor = backgroundColor.getOnBackgroundColor(),
            onSearchClick = { onSearchClick() },
            onBackClick = { onBackClick() },
            onLogoClick = { onLogoClick() }
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            item {
                HeaderSimple(
                    backgroundColor = backgroundColor,
                    posterUrl = state.posterUrl,
                    mediaName = state.tvShowName,
                    releaseYear = state.releaseYear,
                    setDominantColor = { backgroundColor = it }
                )
            }

            item {
                when {
                    state.isLoading -> {
                        MediaDetailsLoading()
                    }

                    state.isGetSeasonsError -> {
                        ErrorAndRetry(
                            modifier = Modifier
                                .padding(vertical = 200.dp)
                                .fillMaxSize(),
                            errorMessage = stringResource(id = R.string.message_loading_content_error),
                            onRetryClick = onRetryClick
                        )
                    }
                }
            }

            if (state.seasonList.isNullOrEmpty()) return@LazyColumn

            items(state.seasonList.size) { index ->
                val season = state.seasonList[index]

                if (index != 0) {
                    HorizontalDivider()
                }

                SeasonItem(
                    tvShowName = state.tvShowName,
                    season = season,
                    onSeasonClick = {
                        onSeasonClick(state.tvShowId, season, backgroundColor)
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true, heightDp = 2000)
@Composable
private fun SeasonsScreenPreview() {
    SeasonsContent(
        state = SeasonsDetailState(
            tvShowId = 0L,
            tvShowName = "Jack Reacher",
            posterUrl = null,
            releaseYear = "2003",
            backgroundColor = DetailBackgroundColor,
            seasonList = listOf(seasonSample, seasonSample, seasonSample)
        ),
        onBackClick = {},
        onRetryClick = {},
        onSearchClick = { },
        onLogoClick = {},
        onSeasonClick = { _, _, _ -> }
    )
}