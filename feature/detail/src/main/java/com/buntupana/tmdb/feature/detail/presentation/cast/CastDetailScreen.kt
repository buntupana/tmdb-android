package com.buntupana.tmdb.feature.detail.presentation.cast

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.buntupana.tmdb.core.presentation.theme.DetailBackgroundColor
import com.buntupana.tmdb.core.presentation.util.getOnBackgroundColor
import com.buntupana.tmdb.feature.detail.presentation.DetailNavigator
import com.buntupana.tmdb.feature.detail.presentation.cast.comp.CastHeader
import com.buntupana.tmdb.feature.detail.presentation.cast.comp.castList
import com.buntupana.tmdb.feature.detail.presentation.common.TopBar
import com.buntupana.tmdb.feature.detail.presentation.mediaDetailsMovieSample
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.ramcosta.composedestinations.annotation.Destination

@Destination(
    navArgsDelegate = CastDetailNavArgs::class
)
@Composable
fun CastDetailScreen(
    viewModel: CastDetailViewModel = hiltViewModel(),
    detailNavigator: DetailNavigator
) {
    CastDetailContent(
        state = viewModel.state,
        onBackClick = { detailNavigator.navigateBack() },
        onSearchClick = { detailNavigator.navigateToSearch() },
        onPersonClick = { personId -> detailNavigator.navigateToPerson(personId) },
        onLogoClick = { detailNavigator.navigateToMainScreen() }
    )
}

@Composable
fun CastDetailContent(
    state: CastDetailState,
    onBackClick: () -> Unit,
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
            mediaName = "Pain Hustlers",
            releaseYear = "2023",
            posterUrl = "",
            backgroundColor = DetailBackgroundColor,
            personCastList = mediaDetailsMovieSample.castList,
            personCrewMap = mediaDetailsMovieSample.crewList.groupBy { it.department }
        ),
        onBackClick = {},
        onSearchClick = {},
        onPersonClick = {},
        onLogoClick = {}
    )
}