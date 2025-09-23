package com.buntupana.tmdb.feature.detail.presentation.episodes

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.buntupana.tmdb.core.ui.composables.CircularProgressIndicatorDelayed
import com.buntupana.tmdb.core.ui.composables.ErrorAndRetry
import com.buntupana.tmdb.core.ui.composables.HeaderSimple
import com.buntupana.tmdb.core.ui.composables.top_bar.TopBarLogo
import com.buntupana.tmdb.core.ui.theme.AppTheme
import com.buntupana.tmdb.core.ui.theme.Dimens
import com.buntupana.tmdb.core.ui.util.SetSystemBarsColors
import com.buntupana.tmdb.core.ui.util.paddingValues
import com.buntupana.tmdb.feature.detail.domain.model.Episode
import com.buntupana.tmdb.feature.detail.presentation.R
import com.buntupana.tmdb.feature.detail.presentation.episodeSample
import com.buntupana.tmdb.feature.detail.presentation.episodes.comp.EpisodeHorizontal
import com.panabuntu.tmdb.core.common.util.isNotNullOrEmpty
import org.koin.androidx.compose.koinViewModel
import com.buntupana.tmdb.core.ui.R as RCore

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EpisodesDetailScreen(
    viewModel: EpisodesDetailViewModel = koinViewModel(),
    onBackClick: () -> Unit,
    onSearchClick: () -> Unit,
    onLogoClick: () -> Unit,
    onRateEpisodeClick: (tvShowId: Long, episodeName: String, seasonNumber: Int, episodeNumber: Int, currentRating: Int?) -> Unit,
    onPersonClick: (personId: Long) -> Unit
) {

    var bottomSheetEpisode by remember { mutableStateOf<Episode?>(null) }

    EpisodesDetailContent(
        state = viewModel.state,
        onBackClick = onBackClick,
        onRetryClick = { viewModel.onEvent(EpisodesDetailEvent.GetEpisodesDetail) },
        onSearchClick = onSearchClick,
        onLogoClick = onLogoClick,
        onRateEpisodeClick = onRateEpisodeClick,
        onEpisodeShowMoreClick = { episode ->
            bottomSheetEpisode = episode
        }
    )

    EpisodeCastDialog(
        showDialog = bottomSheetEpisode != null,
        episode = bottomSheetEpisode,
        onDismiss = {
            bottomSheetEpisode = null
        },
        onPersonClick = { personId ->
            bottomSheetEpisode = null
            onPersonClick(personId)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun EpisodesDetailContent(
    state: EpisodesDetailState,
    onBackClick: () -> Unit,
    onRetryClick: () -> Unit,
    onSearchClick: () -> Unit,
    onLogoClick: () -> Unit,
    onRateEpisodeClick: (tvShowId: Long, episodeName: String, seasonNumber: Int, episodeNumber: Int, currentRating: Int?) -> Unit = { _, _, _, _, _ -> },
    onEpisodeShowMoreClick: (episode: Episode) -> Unit
) {

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    val defaultBackgroundColor = MaterialTheme.colorScheme.surfaceDim

    var backgroundColor by remember {
        if (state.backgroundColor == null) {
            mutableStateOf(defaultBackgroundColor)
        } else {
            mutableStateOf(Color(state.backgroundColor))
        }
    }

    SetSystemBarsColors(
        statusBarColor = backgroundColor,
        navigationBarColor = backgroundColor,
        translucentNavigationBar = true
    )

    Scaffold(
        modifier = Modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            Column {

                TopBarLogo(
                    backgroundColor = backgroundColor,
                    onBackClick = { onBackClick() },
                    onSearchClick = { onSearchClick() },
                    onLogoClick = { onLogoClick() },
                    scrollBehavior = scrollBehavior
                )

                val subtitle = if (state.episodeList.isNotNullOrEmpty()) {
                    pluralStringResource(
                        id = R.plurals.detail_episodes_count,
                        count = state.episodeList.size,
                        state.episodeList.size
                    )
                } else {
                    null
                }

                HeaderSimple(
                    backgroundColor = backgroundColor,
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
        }

        if (state.isGetEpisodesError) {

            ErrorAndRetry(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = paddingValues.calculateTopPadding() + Dimens.errorAndRetryTopPadding)
                    .padding(horizontal = Dimens.padding.horizontal),
                errorMessage = stringResource(id = RCore.string.common_loading_content_error),
                onRetryClick = onRetryClick
            )
        }

        AnimatedVisibility(
            visible = state.isLoading.not() && state.isGetEpisodesError.not(),
            enter = fadeIn(),
            exit = fadeOut()
        ) {

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .paddingValues(top = { paddingValues.calculateTopPadding() })
            ) {

                if (state.episodeList.isNullOrEmpty()) return@LazyColumn

                items(
                    count = state.episodeList.size,
                    key = { index -> state.episodeList[index].id }
                ) { index ->

                    val episode = state.episodeList[index]

                    EpisodeHorizontal(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                horizontal = Dimens.padding.horizontal,
                                vertical = Dimens.padding.vertical
                            ),
                        seasonNumber = state.seasonNumber,
                        episode = episode,
                        isLogged = state.isLogged,
                        onSeeMoreClick = {
                            onEpisodeShowMoreClick(episode)
                        },
                        onRateClick = {
                            onRateEpisodeClick(
                                state.tvShowId,
                                episode.name,
                                state.seasonNumber,
                                episode.episodeNumber,
                                episode.userRating
                            )
                        }
                    )
                }

                item {
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .paddingValues(bottom = { Dimens.padding.small + paddingValues.calculateBottomPadding() })
                    )
                }
            }
        }
    }
}

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    name = "DefaultPreviewLight",
    showBackground = true,
)
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "DefaultPreviewDark",
    showBackground = true,
)
@Composable
fun EpisodesDetailScreenPreview() {
    AppTheme {
        EpisodesDetailContent(
            state = EpisodesDetailState(
                isLoading = true,
                tvShowId = 0L,
                sessionName = "Jack Reacher",
                isGetEpisodesError = true,
                posterUrl = "asdf",
                releaseYear = "2003",
                backgroundColor = null,
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
            onLogoClick = {},
            onRateEpisodeClick = { _, _, _, _, _ -> },
            onEpisodeShowMoreClick = {}
        )
    }
}
