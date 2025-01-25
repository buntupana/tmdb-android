package com.buntupana.tmdb.feature.detail.presentation.episodes

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.buntupana.tmdb.core.ui.composables.ErrorAndRetry
import com.buntupana.tmdb.core.ui.composables.TopBarLogo
import com.buntupana.tmdb.core.ui.theme.DetailBackgroundColor
import com.buntupana.tmdb.core.ui.theme.Dimens
import com.buntupana.tmdb.core.ui.util.setStatusNavigationBarColor
import com.buntupana.tmdb.feature.detail.presentation.R
import com.buntupana.tmdb.feature.detail.presentation.common.HeaderSimple
import com.buntupana.tmdb.feature.detail.presentation.common.MediaDetailsLoading
import com.buntupana.tmdb.feature.detail.presentation.episodeSample
import com.buntupana.tmdb.feature.detail.presentation.episodes.comp.EpisodeHorizontal
import com.panabuntu.tmdb.core.common.util.isNotNullOrEmpty
import com.buntupana.tmdb.core.ui.R as RCore

@Composable
fun EpisodesDetailScreen(
    viewModel: EpisodesDetailViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
    onSearchClick: () -> Unit,
    onLogoClick: () -> Unit,
) {
    EpisodesDetailContent(
        state = viewModel.state,
        onBackClick = onBackClick,
        onRetryClick = { viewModel.onEvent(EpisodesDetailEvent.GetEpisodesDetail) },
        onSearchClick = onSearchClick,
        onLogoClick = onLogoClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun EpisodesDetailContent(
    state: EpisodesDetailState,
    onBackClick: () -> Unit,
    onRetryClick: () -> Unit,
    onSearchClick: () -> Unit,
    onLogoClick: () -> Unit
) {

    val systemBackground = MaterialTheme.colorScheme.background

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = Modifier
            .setStatusNavigationBarColor(state.backgroundColor)
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopBarLogo(
                backgroundColor = state.backgroundColor,
                onBackClick = { onBackClick() },
                onSearchClick = { onSearchClick() },
                onLogoClick = { onLogoClick() },
                scrollBehavior = scrollBehavior
            )
        }
    ) { paddingValues ->

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingValues)
        ) {

            item {
                val subtitle = if (state.episodeList.isNotNullOrEmpty()) {
                    pluralStringResource(
                        id = R.plurals.text_episodes_count,
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
                            errorMessage = stringResource(id = RCore.string.message_loading_content_error),
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
