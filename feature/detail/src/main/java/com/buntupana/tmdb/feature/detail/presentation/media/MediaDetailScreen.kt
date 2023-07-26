package com.buntupana.tmdb.feature.detail.presentation.media

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.buntupana.tmdb.core.presentation.theme.DetailBackgroundColor
import com.buntupana.tmdb.core.presentation.util.getOnBackgroundColor
import com.buntupana.tmdb.feature.detail.presentation.DetailNavigator
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

        }
    )
}

@Composable
fun MediaDetailContent(
    state: DetailScreenState,
    onBackClick: () -> Unit,
    onSearchClick: () -> Unit,
    onPersonClick: (personId: Long) -> Unit,
    onFullCastClick: () -> Unit
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

        if (state.mediaDetails != null) {
            Header(
                isLoading = state.isLoading,
                mediaDetails = state.mediaDetails,
                backgroundColor = backgroundColor
            ) { dominantColor ->
                if (dominantColor != backgroundColor) {
                    backgroundColor = dominantColor
                }
            }

            MainInfo(
                isLoading = state.isLoading,
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
        } else {

        }
    }
}

@Preview
@Composable
fun MediaDetailScreenPreview() {

    MediaDetailContent(
        state = DetailScreenState(
            mediaDetails = mediaDetailsMovieSample,
            backgroundColor = DetailBackgroundColor
        ),
        onBackClick = {},
        onSearchClick = {},
        onPersonClick = {},
        onFullCastClick = {}
    )
}