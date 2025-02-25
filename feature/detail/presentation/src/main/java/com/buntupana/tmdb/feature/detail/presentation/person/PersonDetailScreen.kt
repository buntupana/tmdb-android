package com.buntupana.tmdb.feature.detail.presentation.person

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.buntupana.tmdb.core.ui.R
import com.buntupana.tmdb.core.ui.composables.CircularProgressIndicatorDelayed
import com.buntupana.tmdb.core.ui.composables.ErrorAndRetry
import com.buntupana.tmdb.core.ui.composables.TopBarLogo
import com.buntupana.tmdb.core.ui.util.setStatusBarLightStatusFromBackground
import com.buntupana.tmdb.feature.detail.presentation.person.comp.CreditsFilter
import com.buntupana.tmdb.feature.detail.presentation.person.comp.HeaderContent
import com.buntupana.tmdb.feature.detail.presentation.person.comp.KnownFor
import com.buntupana.tmdb.feature.detail.presentation.person.comp.PersonalInfo
import com.buntupana.tmdb.feature.detail.presentation.person.comp.credits
import com.buntupana.tmdb.feature.detail.presentation.personDetailsSample
import com.panabuntu.tmdb.core.common.entity.MediaType

@Composable
fun PersonDetailScreen(
    viewModel: PersonDetailViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
    onSearchClick: () -> Unit,
    onMediaClick: (mediaItemId: Long, mediaType: MediaType, mainPosterColor: Color?) -> Unit,
    onLogoClick: () -> Unit
) {

    PersonDetailContent(
        viewModel.state,
        onBackClick = onBackClick,
        onSearchClick = onSearchClick,
        onRetryClick = {
            viewModel.onEvent(PersonDetailEvent.GetPersonDetails)
        },
        onMediaClick = onMediaClick,
        onLogoClick = onLogoClick,
        mediaTypeAndDepartmentSelected = { mediaType, department ->
            viewModel.onEvent(PersonDetailEvent.SelectMediaTypeAndDepartment(mediaType, department))
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersonDetailContent(
    state: PersonDetailState,
    onBackClick: () -> Unit,
    onSearchClick: () -> Unit,
    onRetryClick: () -> Unit,
    onMediaClick: (id: Long, mediaType: MediaType, dominantColor: Color?) -> Unit,
    onLogoClick: () -> Unit,
    mediaTypeAndDepartmentSelected: (mediaType: Int?, department: String?) -> Unit
) {

    setStatusBarLightStatusFromBackground(
        LocalView.current,
        MaterialTheme.colorScheme.background
    )

    val mediaTypeMap = mapOf(
        R.string.text_movies to stringResource(id = R.string.text_movies),
        R.string.text_tv_shows to stringResource(id = R.string.text_tv_shows)
    )

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = Modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopBarLogo(
                backgroundColor = MaterialTheme.colorScheme.background,
                onBackClick = { onBackClick() },
                onSearchClick = { onSearchClick() },
                onLogoClick = { onLogoClick() },
                scrollBehavior = scrollBehavior,
                shareLink = state.personDetails?.shareLink
            )
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

        if (state.isGetPersonError) {

            ErrorAndRetry(
                modifier = Modifier
                    .padding(vertical = 200.dp)
                    .fillMaxSize(),
                errorMessage = stringResource(id = R.string.message_loading_content_error),
                onRetryClick = onRetryClick
            )
        }

        AnimatedVisibility(
            visible = state.isLoading.not() && state.isGetPersonError.not(),
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = paddingValues.calculateTopPadding())
            ) {

                if (state.personDetails == null) {
                    return@LazyColumn
                }

                val departmentMap = state.personDetails.creditMap.keys.associateWith { it }

                item {
                    HeaderContent(personDetails = state.personDetails)
                }

                item {
                    PersonalInfo(personDetails = state.personDetails)
                }

                item {
                    KnownFor(
                        itemList = state.personDetails.knownFor,
                        onItemClick = onMediaClick
                    )
                }

                item {
                    CreditsFilter(
                        mainDepartment = state.personDetails.knownForDepartment,
                        mediaTypeMap = mediaTypeMap,
                        departmentMap = departmentMap,
                        mediaTypeSelected = state.mediaTypeSelected,
                        departmentSelected = state.departmentSelected
                    ) { mediaType, department ->
                        mediaTypeAndDepartmentSelected(mediaType, department)
                    }
                }

                credits(
                    personName = state.personDetails.name,
                    creditMap = state.personDetails.creditMap,
                    mainDepartment = state.personDetails.knownForDepartment,
                    mediaTypeSelected = state.mediaTypeSelected,
                    departmentSelected = state.departmentSelected,
                    onItemClick = onMediaClick
                )

                item {
                    Spacer(modifier = Modifier.padding(vertical = paddingValues.calculateBottomPadding()))
                }
            }
        }
    }
}

@Preview(showBackground = true, heightDp = 1000)
@Composable
fun PersonDetailsContentPreview() {

    PersonDetailContent(
        state = PersonDetailState(
            isLoading = false,
            isGetPersonError = false,
            personDetails = personDetailsSample
        ),
        onBackClick = {},
        onSearchClick = {},
        onRetryClick = {},
        onMediaClick = { _, _, _ -> },
        onLogoClick = {},
        mediaTypeAndDepartmentSelected = { _, _ -> }
    )
}