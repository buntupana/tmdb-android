package com.buntupana.tmdb.feature.detail.presentation

import com.buntupana.tmdb.core.domain.model.Gender
import com.buntupana.tmdb.feature.detail.domain.model.CastPersonItem
import com.buntupana.tmdb.feature.detail.domain.model.MediaDetails
import com.buntupana.tmdb.feature.detail.domain.model.PersonFullDetails
import java.time.LocalDate


val personDetailsSample = PersonFullDetails(
    id = 0L,
    name = "Sean Connery",
    profileUrl = "testUrl",
    knownForDepartment = "Acting",
    gender = Gender.MALE,
    birthDate = LocalDate.now(),
    deathDate = LocalDate.now(),
    age = 65,
    placeOfBirth = "Fountainbridge, Edinburgh, Scotland, UK",
    biography = "Sir Thomas Sean Connery (25 August 1930 – 31 October 2020) was a Scottish actor and producer who won an Academy Award, two BAFTA Awards (one being a BAFTA Academy Fellowship Award), and three Golden Globes, including the Cecil B. DeMille Award and a Henrietta Award.\n" +
            "\n" +
            "Connery was the first actor to portray the character James Bond in film, starring in seven Bond films (every film from Dr. No to You Only Live Twice, plus Diamonds Are Forever and Never Say Never Again), between 1962 and 1983. In 1988, Connery won the Academy Award for Best Supporting Actor for his role in The Untouchables. His films also include Marnie (1964), Murder on the Orient Express (1974), The Man Who Would Be King (1975), A Bridge Too Far (1977), Highlander (1986), Indiana Jones and the Last Crusade (1989), The Hunt for Red October (1990), Dragonheart (1996), The Rock (1996), and Finding Forrester (2000).\n" +
            "\n" +
            "Connery was polled in a 2004 The Sunday Herald as \"The Greatest Living Scot\" and in a 2011 EuroMillions survey as \"Scotland's Greatest Living National Treasure\". He was voted by People magazine as both the “Sexiest Man Alive\" in 1989 and the \"Sexiest Man of the Century” in 1999. He received a lifetime achievement award in the United States with a Kennedy Center Honor in 1999. Connery was knighted in the 2000 New Year Honours for services to film drama.\n" +
            "\n" +
            "On 31 October 2020, it was announced that Connery had died at the age of 90.\n" +
            "\n" +
            "Description above from the Wikipedia article Sean Connery, licensed under CC-BY-SA, full list of contributors on Wikipedia",
    externalLinks = emptyList(),
    knownFor = emptyList(),
    creditMap = emptyMap(),
    knownCredits = 60,
)

val castPersonItemSample = CastPersonItem(
    id = 0L,
    name = "Aquaman",
    profileUrl = "",
    ""
)

val mediaDetailsMovieSample = MediaDetails.Movie(
    id = 0L,
    title = "Thor: Love and Thunder",
    posterUrl = "test",
    backdropUrl = "test",
    trailerUrl = "test",
    overview = "After his retirement is interrupted by Gorr the God Butcher, a galactic killer who seeks the extinction of the gods, Thor enlists the help of King Valkyrie, Korg, and ex-girlfriend Jane Foster, who now inexplicably wields Mjolnir as the Mighty Thor. Together they embark upon a harrowing cosmic adventure to uncover the mystery of the God Butcher’s vengeance and stop him before it’s too late.",
    tagLine = "The one is not the only.",
    releaseDate = LocalDate.now(),
    localReleaseDate = "10-11-20",
    userScore = 60,
    runTime = 120,
    genreList = listOf("Action", "Adventure", "Fantasy"),
    ageCertification = "18",
    creatorList = emptyList(),
    castList = listOf(castPersonItemSample, castPersonItemSample, castPersonItemSample, castPersonItemSample),
    crewList = emptyList(),
    localCountryCodeRelease = "ES"
)
