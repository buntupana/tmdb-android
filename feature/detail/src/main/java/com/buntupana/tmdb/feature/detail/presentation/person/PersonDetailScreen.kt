package com.buntupana.tmdb.feature.detail.presentation.person

import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.hilt.navigation.compose.hiltViewModel
import com.buntupana.tmdb.core.R
import com.buntupana.tmdb.core.domain.entity.MediaType
import com.buntupana.tmdb.feature.detail.domain.model.PersonFullDetails
import com.buntupana.tmdb.feature.detail.presentation.DetailNavigator
import com.buntupana.tmdb.feature.detail.presentation.PersonDetailNavArgs
import com.buntupana.tmdb.feature.detail.presentation.common.TopBar
import com.buntupana.tmdb.feature.detail.presentation.person.comp.CreditsFilter
import com.buntupana.tmdb.feature.detail.presentation.person.comp.HeaderContent
import com.buntupana.tmdb.feature.detail.presentation.person.comp.KnownFor
import com.buntupana.tmdb.feature.detail.presentation.person.comp.PersonalInfo
import com.buntupana.tmdb.feature.detail.presentation.person.comp.credits
import com.buntupana.tmdb.feature.detail.presentation.personDetailsSample
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.ramcosta.composedestinations.annotation.Destination

@Destination(
    navArgsDelegate = PersonDetailNavArgs::class
)
@Composable
fun PersonDetailScreen(
    viewModel: PersonDetailViewModel = hiltViewModel(),
    detailNavigator: DetailNavigator
) {

    val systemUiController = rememberSystemUiController()

    systemUiController.setSystemBarsColor(MaterialTheme.colorScheme.background)

    if (viewModel.state.personDetails != null) {
        viewModel.state.personDetails?.let {
            PersonDetailContent(
                it,
                onBackClick = { detailNavigator.navigateBack() },
                onSearchClick = { detailNavigator.navigateToSearch() },
                onMediaClick = { id, mediaType, dominantColor ->
                    detailNavigator.navigateToMediaDetail(id, mediaType, dominantColor)
                }
            )
        }
    }
}

@Composable
fun PersonDetailContent(
    personDetails: PersonFullDetails,
    onBackClick: () -> Unit,
    onSearchClick: () -> Unit,
    onMediaClick: (id: Long, mediaType: MediaType, dominantColor: Color?) -> Unit
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

    val departmentMap = personDetails.creditMap.keys.associateWith { it }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        item {
            TopBar(
                backgroundColor = MaterialTheme.colorScheme.background,
                onBackClick = { onBackClick() },
                onSearchClick = { onSearchClick() })
        }

        item {
            HeaderContent(personDetails = personDetails)
        }

        item {
            PersonalInfo(personDetails = personDetails)
        }

        item {
            KnownFor(
                itemList = personDetails.knownFor,
                onItemClick = onMediaClick
            )
        }

        item {
            CreditsFilter(
                mainDepartment = personDetails.knownForDepartment,
                mediaTypeMap,
                departmentMap,
                mediaTypeSelected,
                departmentSelected
            ) { mediaType, department ->
                mediaTypeSelected = mediaType
                departmentSelected = department
            }
        }

        credits(
            personName = personDetails.name,
            creditMap = personDetails.creditMap,
            mainDepartment = personDetails.knownForDepartment,
            mediaTypeSelected = mediaTypeSelected,
            departmentSelected = departmentSelected,
            onItemClick = onMediaClick
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PersonDetailsContentPreview() {

    PersonDetailContent(
        personDetails = personDetailsSample,
        onBackClick = {},
        onSearchClick = {},
        onMediaClick = { id, mediaType, dominantColor -> }
    )
}