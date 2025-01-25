package com.buntupana.tmdb.feature.detail.presentation.cast

import androidx.compose.foundation.background
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.buntupana.tmdb.core.ui.R
import com.buntupana.tmdb.core.ui.composables.ErrorAndRetry
import com.buntupana.tmdb.core.ui.composables.TopBarLogo
import com.buntupana.tmdb.core.ui.theme.DetailBackgroundColor
import com.buntupana.tmdb.core.ui.util.setStatusNavigationBarColor
import com.buntupana.tmdb.feature.detail.presentation.cast.comp.castList
import com.buntupana.tmdb.feature.detail.presentation.common.HeaderSimple
import com.buntupana.tmdb.feature.detail.presentation.common.MediaDetailsLoading
import com.buntupana.tmdb.feature.detail.presentation.mediaDetailsMovieSample
import com.panabuntu.tmdb.core.common.entity.MediaType

@Composable
fun CastDetailScreen(
    viewModel: CastDetailViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
    onSearchClick: () -> Unit,
    onPersonClick: (personId: Long) -> Unit,
    onLogoClick: () -> Unit

) {
    CastDetailContent(
        state = viewModel.state,
        onBackClick = onBackClick,
        onRetryClick = { viewModel.onEvent(CastDetailEvent.GetCredits) },
        onSearchClick = onSearchClick,
        onPersonClick = onPersonClick,
        onLogoClick = onLogoClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
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

    val systemBackground = MaterialTheme.colorScheme.background

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = Modifier
            .setStatusNavigationBarColor(backgroundColor)
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopBarLogo(
                modifier = Modifier.background(backgroundColor),
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
                    mediaName = state.mediaName,
                    releaseYear = state.releaseYear,
                    setDominantColor = { backgroundColor = it }
                )
            }

            item {
                when {
                    state.isLoading -> {
                        MediaDetailsLoading()
                    }

                    state.isGetContentError -> {
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