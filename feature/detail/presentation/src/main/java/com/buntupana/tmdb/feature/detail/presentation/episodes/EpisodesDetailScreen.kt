package com.buntupana.tmdb.feature.detail.presentation.episodes

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.buntupana.tmdb.core.ui.composables.CircularProgressIndicatorDelayed
import com.buntupana.tmdb.core.ui.composables.ErrorAndRetry
import com.buntupana.tmdb.core.ui.composables.TopBarLogo
import com.buntupana.tmdb.core.ui.theme.DetailBackgroundColor
import com.buntupana.tmdb.core.ui.theme.Dimens
import com.buntupana.tmdb.core.ui.util.fadeIn
import com.buntupana.tmdb.core.ui.util.setStatusBarLightStatusFromBackground
import com.buntupana.tmdb.feature.detail.presentation.R
import com.buntupana.tmdb.feature.detail.presentation.common.HeaderSimple
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

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    val fadeInEnabled = remember { state.isLoading }

    setStatusBarLightStatusFromBackground(
        LocalView.current,
        state.backgroundColor
    )

    Scaffold(
        modifier = Modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            Column {

                TopBarLogo(
                    backgroundColor = state.backgroundColor,
                    onBackClick = { onBackClick() },
                    onSearchClick = { onSearchClick() },
                    onLogoClick = { onLogoClick() },
                    scrollBehavior = scrollBehavior
                )

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
        }
    ) { paddingValues ->


        if (state.isLoading) {

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicatorDelayed()
            }

            return@Scaffold
        }

        if (state.isGetEpisodesError) {
            ErrorAndRetry(
                modifier = Modifier
                    .padding(vertical = 200.dp)
                    .fillMaxSize(),
                errorMessage = stringResource(id = RCore.string.message_loading_content_error),
                onRetryClick = onRetryClick
            )

            return@Scaffold
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = paddingValues.calculateTopPadding())
                .fadeIn(fadeInEnabled)
        ) {

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
                        .padding(bottom = Dimens.padding.small + paddingValues.calculateBottomPadding())
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
            isLoading = true,
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
