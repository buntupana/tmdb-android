package com.buntupana.tmdb.feature.detail.presentation.media

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import com.buntupana.tmdb.core.presentation.composables.ErrorAndRetry
import com.buntupana.tmdb.core.presentation.theme.DetailBackgroundColor
import com.buntupana.tmdb.core.presentation.util.getOnBackgroundColor
import com.buntupana.tmdb.feature.detail.presentation.DetailNavigator
import com.buntupana.tmdb.feature.detail.presentation.common.MediaDetailsLoading
import com.buntupana.tmdb.feature.detail.presentation.common.TopBar
import com.buntupana.tmdb.feature.detail.presentation.media.comp.CastHorizontalList
import com.buntupana.tmdb.feature.detail.presentation.media.comp.Header
import com.buntupana.tmdb.feature.detail.presentation.media.comp.MainInfo
import com.buntupana.tmdb.feature.detail.presentation.mediaDetailsMovieSample
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.ramcosta.composedestinations.annotation.Destination

@Destination(
    navArgsDelegate = MediaDetailNavArgs::class
)
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
        onFullCastClick = {

        },
        onRetryClick = {
            viewModel.onEvent(MediaDetailEvent.GetMediaDetails)
        }
    )
}

@Composable
fun MediaDetailContent(
    state: MediaDetailState,
    onBackClick: () -> Unit,
    onSearchClick: () -> Unit,
    onPersonClick: (personId: Long) -> Unit,
    onFullCastClick: () -> Unit,
    onRetryClick: () -> Unit
) {

    val scrollState = rememberScrollState()

    var backgroundColor by remember {
        mutableStateOf(state.backgroundColor)
    }

    val systemUiController = rememberSystemUiController()

    systemUiController.setSystemBarsColor(backgroundColor)

    // Added to avoid showing background in top when scrolling effect
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .background(backgroundColor)
    ) {

        TopBar(
            textColor = backgroundColor.getOnBackgroundColor(),
            onSearchClick = { onSearchClick() },
            onBackClick = { onBackClick() }
        )

        when {
            state.isLoading -> {
                MediaDetailsLoading(
                    backgroundColor = backgroundColor
                )
            }

            state.isGetContentError -> {
                ErrorAndRetry(
                    modifier = Modifier
                        .padding(vertical = 200.dp)
                        .fillMaxSize(),
                    textColor = backgroundColor.getOnBackgroundColor(),
                    errorMessage = stringResource(id = R.string.message_loading_content_error),
                    onRetryClick = onRetryClick
                )
            }

            state.mediaDetails != null -> {
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

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White)
                ) {
                    CastHorizontalList(
                        modifier = Modifier.background(MaterialTheme.colorScheme.background),
                        mediaDetails = state.mediaDetails,
                        onItemClick = onPersonClick,
                        onFullCastClick = { onFullCastClick }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun MediaDetailScreenPreview() {

    MediaDetailContent(
        state = MediaDetailState(
            mediaDetails = mediaDetailsMovieSample,
            backgroundColor = DetailBackgroundColor,
            isGetContentError = true
        ),
        onBackClick = {},
        onSearchClick = {},
        onPersonClick = {},
        onFullCastClick = {},
        onRetryClick = {}
    )
}