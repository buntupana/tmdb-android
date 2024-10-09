package com.buntupana.tmdb.feature.detail.presentation.cast

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.buntupana.tmdb.core.R
import com.buntupana.tmdb.core.domain.entity.MediaType
import com.buntupana.tmdb.core.presentation.composables.ErrorAndRetry
import com.buntupana.tmdb.core.presentation.theme.DetailBackgroundColor
import com.buntupana.tmdb.core.presentation.util.getOnBackgroundColor
import com.buntupana.tmdb.feature.detail.presentation.DetailNavigator
import com.buntupana.tmdb.feature.detail.presentation.cast.comp.CastHeader
import com.buntupana.tmdb.feature.detail.presentation.cast.comp.castList
import com.buntupana.tmdb.feature.detail.presentation.common.MediaDetailsLoading
import com.buntupana.tmdb.feature.detail.presentation.common.TopBar
import com.buntupana.tmdb.feature.detail.presentation.mediaDetailsMovieSample
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun CastDetailScreen(
    viewModel: CastDetailViewModel = hiltViewModel(),
    detailNavigator: DetailNavigator
) {
    CastDetailContent(
        state = viewModel.state,
        onBackClick = { detailNavigator.navigateBack() },
        onRetryClick = { viewModel.onEvent(CastDetailEvent.GetCredits)},
        onSearchClick = { detailNavigator.navigateToSearch() },
        onPersonClick = { personId -> detailNavigator.navigateToPerson(personId) },
        onLogoClick = { detailNavigator.navigateToMainScreen() }
    )
}

@Composable
fun CastDetailContent(
    state: CastDetailState,
    onBackClick: () -> Unit,
    onRetryClick: () -> Unit,
    onSearchClick: () -> Unit,
    onPersonClick: (personId: Long) -> Unit,
    onLogoClick: () -> Unit
) {

    var backgroundColor by remember {
        mutableStateOf(state.backgroundColor)
    }

    val systemUiController = rememberSystemUiController()

    systemUiController.setSystemBarsColor(backgroundColor)

    val systemBackground = MaterialTheme.colorScheme.background

    // Added to avoid showing background in top when scrolling effect
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
    ) {

        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(backgroundColor)
            ) {

                TopBar(
                    textColor = backgroundColor.getOnBackgroundColor(),
                    onSearchClick = { onSearchClick() },
                    onBackClick = { onBackClick() },
                    onLogoClick = { onLogoClick() }
                )

                CastHeader(
                    backgroundColor = backgroundColor,
                    posterUrl = state.posterUrl,
                    mediaName = state.mediaName,
                    releaseYear = state.releaseYear,
                    setDominantColor = { backgroundColor = it }
                )
            }
        }

        item {
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
            }
        }

        castList(
            modifier = Modifier
                .fillMaxWidth()
                .background(systemBackground),
            personCastList = state.personCastList,
            personCrewMap = state.personCrewMap,
            onPersonClick = { onPersonClick(it) }
        )
    }
}

@Preview
@Composable
fun CastDetailScreenPreview() {

    CastDetailContent(
        state = CastDetailState(
            mediaId = 0,
            mediaType = MediaType.MOVIE,
            mediaName = "Pain Hustlers",
            releaseYear = "2023",
            posterUrl = "",
            backgroundColor = DetailBackgroundColor,
            personCastList = mediaDetailsMovieSample.castList,
            personCrewMap = mediaDetailsMovieSample.crewList.groupBy { it.department }
        ),
        onBackClick = {},
        onRetryClick = {},
        onSearchClick = {},
        onPersonClick = {},
        onLogoClick = {}
    )
}