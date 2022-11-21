package com.buntupana.tmdb.feature.detail.presentation.person

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.buntupana.tmdb.core.R
import com.buntupana.tmdb.core.domain.entity.MediaType
import com.buntupana.tmdb.core.domain.model.Gender
import com.buntupana.tmdb.feature.detail.domain.model.PersonFullDetails
import com.buntupana.tmdb.feature.detail.presentation.DetailNavigator
import com.buntupana.tmdb.feature.detail.presentation.PersonDetailNavArgs
import com.buntupana.tmdb.feature.detail.presentation.person.composable.*
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.jakewharton.threetenabp.AndroidThreeTen
import com.ramcosta.composedestinations.annotation.Destination
import org.threeten.bp.LocalDate

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
                onMediaClick = { id: Long, mediaType: MediaType ->
                    detailNavigator.navigateToMediaDetail(id, mediaType)
                }
            )
        }
    }
}

@Composable
fun PersonDetailContent(
    personDetails: PersonFullDetails,
    onMediaClick: (id: Long, mediaType: MediaType) -> Unit
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
    AndroidThreeTen.init(LocalContext.current)
    val personDetails = PersonFullDetails(
        0L,
        "Sean Connery",
        "testUrl",
        "Acting",
        Gender.MALE,
        LocalDate.now(),
        LocalDate.now(),
        65,
        "Fountainbridge, Edinburgh, Scotland, UK",
        "Sir Thomas Sean Connery (25 August 1930 – 31 October 2020) was a Scottish actor and producer who won an Academy Award, two BAFTA Awards (one being a BAFTA Academy Fellowship Award), and three Golden Globes, including the Cecil B. DeMille Award and a Henrietta Award.\n" +
                "\n" +
                "Connery was the first actor to portray the character James Bond in film, starring in seven Bond films (every film from Dr. No to You Only Live Twice, plus Diamonds Are Forever and Never Say Never Again), between 1962 and 1983. In 1988, Connery won the Academy Award for Best Supporting Actor for his role in The Untouchables. His films also include Marnie (1964), Murder on the Orient Express (1974), The Man Who Would Be King (1975), A Bridge Too Far (1977), Highlander (1986), Indiana Jones and the Last Crusade (1989), The Hunt for Red October (1990), Dragonheart (1996), The Rock (1996), and Finding Forrester (2000).\n" +
                "\n" +
                "Connery was polled in a 2004 The Sunday Herald as \"The Greatest Living Scot\" and in a 2011 EuroMillions survey as \"Scotland's Greatest Living National Treasure\". He was voted by People magazine as both the “Sexiest Man Alive\" in 1989 and the \"Sexiest Man of the Century” in 1999. He received a lifetime achievement award in the United States with a Kennedy Center Honor in 1999. Connery was knighted in the 2000 New Year Honours for services to film drama.\n" +
                "\n" +
                "On 31 October 2020, it was announced that Connery had died at the age of 90.\n" +
                "\n" +
                "Description above from the Wikipedia article Sean Connery, licensed under CC-BY-SA, full list of contributors on Wikipedia",
        emptyList(),
        emptyList(),
        emptyMap(),
        60,
    )

    PersonDetailContent(personDetails = personDetails, onMediaClick = { id, mediaType -> })
}