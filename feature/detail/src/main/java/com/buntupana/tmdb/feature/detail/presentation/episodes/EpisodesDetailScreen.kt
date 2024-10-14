package com.buntupana.tmdb.feature.detail.presentation.episodes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.buntupana.tmdb.core.R
import com.buntupana.tmdb.core.presentation.composables.ErrorAndRetry
import com.buntupana.tmdb.core.presentation.theme.DetailBackgroundColor
import com.buntupana.tmdb.core.presentation.theme.Dimens
import com.buntupana.tmdb.core.presentation.util.getOnBackgroundColor
import com.buntupana.tmdb.core.presentation.util.isNotNullOrEmpty
import com.buntupana.tmdb.feature.detail.presentation.DetailNavigator
import com.buntupana.tmdb.feature.detail.presentation.common.HeaderSimple
import com.buntupana.tmdb.feature.detail.presentation.common.MediaDetailsLoading
import com.buntupana.tmdb.feature.detail.presentation.common.TopBar
import com.buntupana.tmdb.feature.detail.presentation.episodeSample
import com.buntupana.tmdb.feature.detail.presentation.episodes.comp.EpisodeHorizontal
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun EpisodesDetailScreen(
    viewModel: EpisodesDetailViewModel = hiltViewModel(),
    detailNavigator: DetailNavigator
) {
    EpisodesDetailContent(
        state = viewModel.state,
        onBackClick = { detailNavigator.navigateBack() },
        onRetryClick = { viewModel.onEvent(EpisodesDetailEvent.GetEpisodesDetail) },
        onSearchClick = { detailNavigator.navigateToSearch() },
        onLogoClick = { detailNavigator.navigateToMainScreen() }
    )
}

@Composable
private fun EpisodesDetailContent(
    state: EpisodesDetailState,
    onBackClick: () -> Unit,
    onRetryClick: () -> Unit,
    onSearchClick: () -> Unit,
    onLogoClick: () -> Unit
) {

    val systemUiController = rememberSystemUiController()

    systemUiController.setSystemBarsColor(state.backgroundColor)

    val systemBackground = MaterialTheme.colorScheme.background

    // Added to avoid showing background in top when scrolling effect
    Box(
        modifier = Modifier
            .fillMaxSize()
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
    ) {

        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(state.backgroundColor)
            ) {
                TopBar(
                    textColor = state.backgroundColor.getOnBackgroundColor(),
                    onSearchClick = { onSearchClick() },
                    onBackClick = { onBackClick() },
                    onLogoClick = { onLogoClick() }
                )

                val subtitle = if (state.episodeList.isNotNullOrEmpty()) {
                    pluralStringResource(
                        id = com.buntupana.tmdb.feature.detail.R.plurals.text_episodes_count,
                        count = state.episodeList.size,
                        state.episodeList.size
                    )
                } else {
                    null
                }

                HeaderSimple(
                    backgroundColor = state.backgroundColor,
                    posterUrl = state.posterUrl,
                    mediaName = state.sessionName,
                    subtitle = subtitle,
                    releaseYear = state.releaseYear,
                )
            }
        }

        item {
            when {
                state.isLoading -> {
                    MediaDetailsLoading(
                        backgroundColor = systemBackground
                    )
                }

                state.isGetEpisodesError -> {
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

        if (state.episodeList.isNullOrEmpty()) return@LazyColumn

        items(state.episodeList.size) { index ->
            EpisodeHorizontal(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = Dimens.padding.horizontal,
                        vertical = Dimens.padding.vertical
                    ),
                episode = state.episodeList[index],
                onItemClick = {}
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

@Preview(showBackground = true, heightDp = 2000)
@Composable
fun EpisodesDetailScreenPreview() {
    EpisodesDetailContent(
        state = EpisodesDetailState(
            isLoading = false,
            tvShowId = 0L,
            sessionName = "Jack Reacher",
            posterUrl = null,
            releaseYear = "2003",
            backgroundColor = DetailBackgroundColor,
            seasonNumber = 3,
            episodeList = listOf(
                episodeSample,
                episodeSample,
                episodeSample
            )
        ),
        onBackClick = {},
        onRetryClick = {},
        onSearchClick = { },
        onLogoClick = {}
    )
}
