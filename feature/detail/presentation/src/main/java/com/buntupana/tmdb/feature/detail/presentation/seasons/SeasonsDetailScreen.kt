package com.buntupana.tmdb.feature.detail.presentation.seasons

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
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
import com.buntupana.tmdb.core.ui.util.setStatusNavigationBarColor
import com.buntupana.tmdb.feature.detail.domain.model.Season
import com.buntupana.tmdb.feature.detail.presentation.common.HeaderSimple
import com.buntupana.tmdb.feature.detail.presentation.common.MediaDetailsLoading
import com.buntupana.tmdb.feature.detail.presentation.common.TopBar
import com.buntupana.tmdb.feature.detail.presentation.seasonSample
import com.buntupana.tmdb.feature.detail.presentation.seasons.comp.SeasonItem

@Composable
fun SeasonsDetailScreen(
    viewModel: SeasonDetailViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
    onSearchClick: () -> Unit,
    onLogoClick: () -> Unit,
    onSeasonClick: (tvShowId: Long, seasonName: String, seasonNumber: Int, posterUrl: String?, backgroundColor: Color, releaseYear: String?) -> Unit,
) {

    SeasonsContent(
        state = viewModel.state,
        onBackClick = onBackClick,
        onRetryClick = { viewModel.onEvent(SeasonsDetailEvent.GetSeasons) },
        onSearchClick = onSearchClick,
        onLogoClick = onLogoClick,
        onSeasonClick = { tvShowId, season, backgroundColor ->
            onSeasonClick(
                tvShowId,
                season.name,
                season.seasonNumber ?: 0,
                season.posterUrl,
                backgroundColor,
                season.airDate?.year.toString()
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
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

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = Modifier
            .setStatusNavigationBarColor(backgroundColor)
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopBar(
                backgroundColor = backgroundColor,
                onBackClick = { onBackClick() },
                onSearchClick = { onSearchClick() },
                onLogoClick = { onLogoClick() },
                scrollBehavior = scrollBehavior
            )
        }
    ) { paddingValues ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
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

            item {
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(Dimens.padding.small)
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