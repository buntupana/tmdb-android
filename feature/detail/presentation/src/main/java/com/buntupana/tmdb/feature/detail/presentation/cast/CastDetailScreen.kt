package com.buntupana.tmdb.feature.detail.presentation.cast

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.buntupana.tmdb.core.ui.R
import com.buntupana.tmdb.core.ui.composables.CircularProgressIndicatorDelayed
import com.buntupana.tmdb.core.ui.composables.ErrorAndRetry
import com.buntupana.tmdb.core.ui.composables.HeaderSimple
import com.buntupana.tmdb.core.ui.composables.top_bar.TopBarLogo
import com.buntupana.tmdb.core.ui.theme.AppTheme
import com.buntupana.tmdb.core.ui.theme.Dimens
import com.buntupana.tmdb.core.ui.util.SetSystemBarsColors
import com.buntupana.tmdb.core.ui.util.paddingValues
import com.buntupana.tmdb.feature.detail.presentation.cast.comp.castList
import com.buntupana.tmdb.feature.detail.presentation.mediaDetailsMovieSample
import com.panabuntu.tmdb.core.common.entity.MediaType
import org.koin.androidx.compose.koinViewModel

@Composable
fun CastDetailScreen(
    viewModel: CastDetailViewModel = koinViewModel(),
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
    )

    val systemBackground = MaterialTheme.colorScheme.background

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = Modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            Column {
                TopBarLogo(
                    modifier = Modifier.background(backgroundColor),
                    backgroundColor = backgroundColor,
                    onBackClick = { onBackClick() },
                    onSearchClick = { onSearchClick() },
                    onLogoClick = { onLogoClick() },
                    scrollBehavior = scrollBehavior
                )
                HeaderSimple(
                    backgroundColor = backgroundColor,
                    posterUrl = state.posterUrl,
                    mediaName = state.mediaName,
                    releaseYear = state.releaseYear,
                    setDominantColor = { backgroundColor = it }
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

        if (state.isGetContentError) {
            ErrorAndRetry(
                modifier = Modifier
                    .padding(top = paddingValues.calculateTopPadding() + Dimens.errorAndRetryTopPadding)
                    .padding(horizontal = Dimens.padding.horizontal)
                    .fillMaxWidth(),
                errorMessage = stringResource(id = R.string.message_loading_content_error),
                onRetryClick = onRetryClick
            )
        }

        AnimatedVisibility(
            visible = state.isLoading.not() && state.isGetContentError.not(),
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .paddingValues(top = { paddingValues.calculateTopPadding() })
            ) {
                castList(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(systemBackground),
                    personCastList = state.personCastList,
                    personCrewMap = state.personCrewMap,
                    onPersonClick = { onPersonClick(it) }
                )

                item {
                    Spacer(modifier = Modifier.padding(bottom = paddingValues.calculateBottomPadding()))
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
fun CastDetailScreenPreview() {
    AppTheme {
        CastDetailContent(
            state = CastDetailState(
                isLoading = false,
                mediaId = 0,
                mediaType = MediaType.MOVIE,
                mediaName = "Pain Hustlers",
                releaseYear = "2023",
                posterUrl = "",
                backgroundColor = null,
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
}