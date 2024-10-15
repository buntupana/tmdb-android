package com.buntupana.tmdb.feature.detail.presentation.person

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
import com.buntupana.tmdb.core.domain.entity.MediaType
import com.buntupana.tmdb.core.presentation.composables.ErrorAndRetry
import com.buntupana.tmdb.core.presentation.theme.PersonBackgroundColor
import com.buntupana.tmdb.core.presentation.util.getOnBackgroundColor
import com.buntupana.tmdb.core.presentation.util.setStatusNavigationBarColor
import com.buntupana.tmdb.feature.detail.presentation.DetailNavigator
import com.buntupana.tmdb.feature.detail.presentation.common.MediaDetailsLoading
import com.buntupana.tmdb.feature.detail.presentation.common.TopBar
import com.buntupana.tmdb.feature.detail.presentation.person.comp.CreditsFilter
import com.buntupana.tmdb.feature.detail.presentation.person.comp.HeaderContent
import com.buntupana.tmdb.feature.detail.presentation.person.comp.KnownFor
import com.buntupana.tmdb.feature.detail.presentation.person.comp.PersonalInfo
import com.buntupana.tmdb.feature.detail.presentation.person.comp.credits
import com.buntupana.tmdb.feature.detail.presentation.personDetailsSample

@Composable
fun PersonDetailScreen(
    viewModel: PersonDetailViewModel = hiltViewModel(),
    detailNavigator: DetailNavigator
) {

    PersonDetailContent(
        viewModel.state,
        onBackClick = { detailNavigator.navigateBack() },
        onSearchClick = { detailNavigator.navigateToSearch() },
        onRetryClick = {
            viewModel.onEvent(PersonDetailEvent.GetPersonDetails)
        },
        onMediaClick = { id, mediaType, dominantColor ->
            detailNavigator.navigateToMediaDetail(id, mediaType, dominantColor)
        },
        onLogoClick = {
            detailNavigator.navigateToMainScreen()
        }
    )
}

@Composable
fun PersonDetailContent(
    state: PersonDetailState,
    onBackClick: () -> Unit,
    onSearchClick: () -> Unit,
    onRetryClick: () -> Unit,
    onMediaClick: (id: Long, mediaType: MediaType, dominantColor: Color?) -> Unit,
    onLogoClick: () -> Unit
) {

    var mediaTypeSelected by remember {
        mutableStateOf<Int?>(null)
    }

    var departmentSelected by remember {
        mutableStateOf<String?>(null)
    }

    val mediaTypeMap = mapOf(
        R.string.text_movies to stringResource(id = R.string.text_movies),
        R.string.text_tv_shows to stringResource(id = R.string.text_tv_shows)
    )

    LazyColumn(
        modifier = Modifier
            .setStatusNavigationBarColor(MaterialTheme.colorScheme.background)
    ) {
        item {
            TopBar(
                textColor = PersonBackgroundColor.getOnBackgroundColor(),
                onBackClick = { onBackClick() },
                onSearchClick = { onSearchClick() },
                onLogoClick = { onLogoClick() }
            )
        }

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
                        mediaTypeSelected = mediaTypeSelected,
                        departmentSelected = departmentSelected
                    ) { mediaType, department ->
                        mediaTypeSelected = mediaType
                        departmentSelected = department
                    }
                }

                credits(
                    personName = state.personDetails.name,
                    creditMap = state.personDetails.creditMap,
                    mainDepartment = state.personDetails.knownForDepartment,
                    mediaTypeSelected = mediaTypeSelected,
                    departmentSelected = departmentSelected,
                    onItemClick = onMediaClick
                )
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
        onLogoClick = {}
    )
}