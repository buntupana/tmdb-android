package com.buntupana.tmdb.feature.detail.presentation.person

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.buntupana.tmdb.core.domain.model.Gender
import com.buntupana.tmdb.core.presentation.composables.ExpandableText
import com.buntupana.tmdb.core.presentation.composables.ImageFromUrl
import com.buntupana.tmdb.core.presentation.composables.TitleAndSubtitle
import com.buntupana.tmdb.core.presentation.theme.Dimens
import com.buntupana.tmdb.core.presentation.util.getString
import com.buntupana.tmdb.core.presentation.util.ifNull
import com.buntupana.tmdb.core.presentation.util.toLocalFormat
import com.buntupana.tmdb.feature.detail.R
import com.buntupana.tmdb.feature.detail.domain.model.ExternalLink
import com.buntupana.tmdb.feature.detail.domain.model.PersonFullDetails
import com.buntupana.tmdb.feature.detail.presentation.DetailNavigator
import com.buntupana.tmdb.feature.detail.presentation.PersonDetailNavArgs
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.ramcosta.composedestinations.annotation.Destination
import org.threeten.bp.LocalDate
import com.buntupana.tmdb.core.R as RCore

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
                it
            )
        }
    }
}

@Composable
fun PersonDetailContent(
    personDetails: PersonFullDetails
) {

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
    ) {

        HeaderContent(personDetails = personDetails)

        PersonalInfo(personDetails = personDetails)
    }
}

@Composable
private fun HeaderContent(
    personDetails: PersonFullDetails
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(Dimens.padding.medium),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (personDetails.profileUrl.isNotBlank()) {
            ImageFromUrl(
                modifier = Modifier
                    .size(160.dp)
                    .clip(RoundedCornerShape(Dimens.posterRound)),
                imageUrl = personDetails.profileUrl
            )
        }
        Spacer(modifier = Modifier.height(Dimens.padding.medium))
        Text(
            text = personDetails.name,
            fontWeight = FontWeight.Bold,
            fontSize = 32.sp
        )
        if (personDetails.externalLinks.isNotEmpty()) {

            Spacer(modifier = Modifier.height(Dimens.padding.small))

            Row {
                val uriHandler = LocalUriHandler.current
                personDetails.externalLinks.forEachIndexed { index, externalLink ->

                    val iconResId = when (externalLink) {
                        is ExternalLink.FacebookLink -> R.drawable.ic_facebook
                        is ExternalLink.HomePage -> RCore.drawable.ic_link
                        is ExternalLink.ImdbLink -> RCore.drawable.ic_link
                        is ExternalLink.InstagramLink -> R.drawable.ic_instagram
                        is ExternalLink.TwitterLink -> R.drawable.ic_twitter
                    }

                    Image(
                        modifier = Modifier
                            .size(32.dp)
                            .clickable {
                                uriHandler.openUri(externalLink.link)
                            },
                        painter = painterResource(id = iconResId),
                        contentDescription = null,
                    )

                    if (index < personDetails.externalLinks.size - 1) {
                        Spacer(modifier = Modifier.width(Dimens.padding.small))
                    }
                }
            }
        }
    }
}

@Composable
fun PersonalInfo(
    personDetails: PersonFullDetails
) {

    Column(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = Dimens.padding.medium, vertical = Dimens.padding.small)
    ) {

        Spacer(modifier = Modifier.height(Dimens.padding.small))

        Text(
            text = stringResource(id = R.string.text_personal_info),
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleLarge
        )

        Row {
            if (personDetails.knownForDepartment.isNotBlank()) {
                TitleAndSubtitle(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    title = stringResource(id = R.string.text_known_for),
                    subtitle = personDetails.knownForDepartment
                )
            }
            TitleAndSubtitle(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                title = stringResource(id = R.string.text_known_credits),
                subtitle = personDetails.knownCredits.toString()
            )
        }

        TitleAndSubtitle(
            title = stringResource(id = R.string.text_gender),
            subtitle = personDetails.gender.getString()
        )

        val ageString = if (personDetails.age > 0) {
            "(${stringResource(id = R.string.text_age, personDetails.age)})"
        } else ""

        val birthdayString = if (personDetails.deathDate == null) {
            (personDetails.birthDate?.toLocalFormat().ifNull { " - " } + (" $ageString"))
        } else {
            personDetails.birthDate?.toLocalFormat() ?: " - "
        }

        TitleAndSubtitle(
            title = stringResource(id = R.string.text_birthdate),
            subtitle = birthdayString
        )

        if (personDetails.deathDate != null) {
            TitleAndSubtitle(
                title = stringResource(id = R.string.text_day_of_death),
                subtitle = personDetails.deathDate.toLocalFormat() + " $ageString"
            )
        }
        TitleAndSubtitle(
            title = stringResource(id = R.string.text_place_birth),
            subtitle = personDetails.placeOfBirth.ifBlank { " - " }
        )

        Spacer(modifier = Modifier.height(Dimens.padding.small))

        Text(
            text = stringResource(id = R.string.text_biography),
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(Dimens.padding.small))

        ExpandableText(text = personDetails.biography.ifBlank {
            stringResource(
                id = R.string.text_no_biography,
                personDetails.name
            )
        })

        Spacer(Modifier.height(Dimens.padding.small))

        Text(
            text = "Known For",
            style = MaterialTheme.typography.titleLarge
        )
    }
}


@Preview(showBackground = true)
@Composable
fun PersonDetailsContentPreview() {

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
        60
    )

    PersonDetailContent(personDetails = personDetails)
}