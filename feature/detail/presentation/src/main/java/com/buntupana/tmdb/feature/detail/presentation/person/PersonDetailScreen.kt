package com.buntupana.tmdb.feature.detail.presentation.person

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.buntupana.tmdb.core.ui.R
import com.buntupana.tmdb.core.ui.composables.ErrorAndRetry
import com.buntupana.tmdb.core.ui.util.setStatusNavigationBarColor
import com.buntupana.tmdb.feature.detail.presentation.common.MediaDetailsLoading
import com.buntupana.tmdb.feature.detail.presentation.common.TopBar
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

    val mediaTypeMap = mapOf(
        R.string.text_movies to stringResource(id = R.string.text_movies),
        R.string.text_tv_shows to stringResource(id = R.string.text_tv_shows)
    )

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = Modifier
            .setStatusNavigationBarColor(MaterialTheme.colorScheme.background)
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopBar(
                backgroundColor = MaterialTheme.colorScheme.background,
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

            when {
                state.isLoading -> {
                    item {
                        MediaDetailsLoading(backgroundColor = MaterialTheme.colorScheme.background)
                    }
                }

                state.isGetPersonError -> {
                    item {
                        ErrorAndRetry(
                            modifier = Modifier
                                .padding(vertical = 200.dp)
                                .fillMaxSize(),
                            errorMessage = stringResource(id = R.string.message_loading_content_error),
                            onRetryClick = onRetryClick
                        )
                    }
                }

                state.personDetails != null -> {

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
                }
            }
        }
    }
}

@Preview(showBackground = true)
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