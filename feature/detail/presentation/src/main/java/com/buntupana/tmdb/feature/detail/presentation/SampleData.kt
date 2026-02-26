package com.buntupana.tmdb.feature.detail.presentation


import com.buntupana.tmdb.core.ui.util.mediaItemMovie
import com.buntupana.tmdb.core.ui.util.mediaItemTvShow
import com.buntupana.tmdb.feature.detail.domain.model.CreditPersonItem
import com.buntupana.tmdb.feature.detail.domain.model.Episode
import com.buntupana.tmdb.feature.detail.domain.model.ExternalLink
import com.buntupana.tmdb.feature.detail.domain.model.Job
import com.buntupana.tmdb.feature.detail.domain.model.MediaDetails
import com.buntupana.tmdb.feature.detail.domain.model.Person
import com.buntupana.tmdb.feature.detail.domain.model.PersonFullDetails
import com.buntupana.tmdb.feature.detail.domain.model.Providers
import com.buntupana.tmdb.feature.detail.domain.model.Role
import com.buntupana.tmdb.feature.detail.domain.model.Season
import com.panabuntu.tmdb.core.common.model.Gender
import java.time.LocalDate

val externalLinkListSample = listOf(
    ExternalLink.HomePage(""),
    ExternalLink.FacebookLink(""),
    ExternalLink.XLink(""),
    ExternalLink.InstagramLink(""),
    ExternalLink.TiktokLink(""),
    ExternalLink.ImdbLink("")
)

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
    externalLinks = externalLinkListSample,
    knownFor = emptyList(),
    creditMap = emptyMap(),
    knownCredits = 60,
    shareLink = "asdf"
)

val castMoviePersonSample = Person.Cast.Movie(
    id = 0L,
    name = "Jason Momoa",
    gender = Gender.MALE,
    profileUrl = "",
    character = "Aquaman"
)

val castTvShowPersonSample = Person.Cast.TvShow(
    id = 0L,
    name = "Jason Momoa",
    gender = Gender.MALE,
    profileUrl = "",
    totalEpisodeCount = 140,
    roleList = listOf(
        Role(character = "The Flash", episodeCount = 140),
        Role(character = "The Flash", episodeCount = 1),
        Role(character = "The Flash", episodeCount = 1),
        Role(character = "The Flash", episodeCount = 1),
        Role(character = "The Flash", episodeCount = 1),
        Role(character = "The Flash", episodeCount = 1),
    )
)

val crewMoviePersonSample = Person.Crew.Movie(
    id = 0L,
    name = "Stanley Kubrik",
    gender = Gender.MALE,
    profileUrl = "",
    department = "Direction",
    job = "Director"
)

val crewTvShowPersonSample = Person.Crew.TvShow(
    id = 0L,
    name = "Stanley Kubrik",
    gender = Gender.MALE,
    profileUrl = "",
    department = "Direction",
    jobList = listOf(
        Job("Director", 9),
        Job("Director", 9),
        Job("Director", 9),
        Job("Director", 9)
    ),
    totalEpisodeCount = 102
)

val mediaDetailsMovieSample = MediaDetails.Movie(
    id = 0L,
    title = "Thor: Love and Thunder",
    originalTitle = "Thor: Thunder and Love",
    posterUrl = "test",
    backdropUrl = "test",
    trailerUrl = "test",
    overview = "After his retirement is interrupted by Gorr the God Butcher, a galactic killer who seeks the extinction of the gods, Thor enlists the help of King Valkyrie, Korg, and ex-girlfriend Jane Foster, who now inexplicably wields Mjolnir as the Mighty Thor. Together they embark upon a harrowing cosmic adventure to uncover the mystery of the God Butcher’s vengeance and stop him before it’s too late.",
    tagLine = "The one is not the only.",
    releaseDate = LocalDate.now(),
    localReleaseDate = "10-11-20",
    voteAverage = 60,
    voteCount = 4000,
    runTime = 120,
    genreList = listOf("Action", "Adventure", "Fantasy", "Comedy", "Drama", "Western", "Thriller", "Fantasy"),
    ageCertification = "18",
    creatorList = emptyList(),
    castList = listOf(
        castMoviePersonSample,
        castMoviePersonSample,
        castMoviePersonSample,
        castMoviePersonSample
    ),
    crewList = listOf(
        crewMoviePersonSample,
        crewMoviePersonSample,
        crewMoviePersonSample,
        crewMoviePersonSample
    ),
    localCountryCodeRelease = "ES",
    recommendationList = listOf(
        mediaItemMovie,
        mediaItemTvShow,
        mediaItemMovie,
        mediaItemTvShow,
        mediaItemMovie,
        mediaItemTvShow,
    ),
    isFavorite = false,
    isWatchlisted = false,
    userRating = 30,
    isRateable = true,
    status = "Released",
    originalLanguage = "en",
    budget = 200_000_000,
    revenue = 250_000_000,
    externalLinkList = externalLinkListSample,
    shareLink = "shareLink",
    providers = Providers(
        justWatchLink = "justWatchLink",
        logoUrlList = listOf("", "", "", "")
    )
)

val creditItemPersonSample = CreditPersonItem.Movie(
    id = 0L,
    title = "Thor Ragnarock",
    department = "Acting",
    role = "Acting",
    posterUrl = null,
    backdropUrl = null,
    popularity = 0f,
    userScore = 0,
    voteCount = 0,
    releaseDate = null,
    castOrder = 0
)

val episodeSample = Episode(
    id = 0L,
    showId = 0L,
    name = "A Night at the Symphony and the White Sparrow",
    airDate = LocalDate.now(),
    episodeNumber = 4,
    overview = "We don't have an overview translated in English. Help us expand our database by adding one.",
    runtime = 67,
    seasonNumber = 2,
    stillUrl = "",
    voteAverage = 7.2f,
    voteCount = 24,
    userRating = 20,
    isRateable = true,
    castList = listOf(castTvShowPersonSample),
    personCrewMap = mapOf()
)

val seasonSample = Season(
    id = 0L,
    name = "Season 1",
    airDate = LocalDate.now(),
    episodeCount = 8,
    overview = "Based on\"Bad Luck and Trouble,\" when members of Reacher's old military unit start turning up dead, Reacher has just one thing on his mind—revenge.",
    posterUrl = null,
    seasonNumber = 1,
    voteAverage = 7f
)

val mediaDetailsTvShowSample = MediaDetails.TvShow(
    id = 0L,
    title = "Generation Kill",
    originalTitle = "Kill Generation",
    posterUrl = "test",
    backdropUrl = "test",
    trailerUrl = "test",
    overview = "After his retirement is interrupted by Gorr the God Butcher, a galactic killer who seeks the extinction of the gods, Thor enlists the help of King Valkyrie, Korg, and ex-girlfriend Jane Foster, who now inexplicably wields Mjolnir as the Mighty Thor. Together they embark upon a harrowing cosmic adventure to uncover the mystery of the God Butcher’s vengeance and stop him before it’s too late.",
    tagLine = "The one is not the only.",
    releaseDate = LocalDate.now(),
    voteAverage = 60,
    voteCount = 50_000,
    runTime = 120,
    genreList = listOf("Action", "Adventure", "Fantasy"),
    ageCertification = "18",
    creatorList = emptyList(),
    castList = listOf(
        castTvShowPersonSample,
        castTvShowPersonSample,
        castTvShowPersonSample,
        castTvShowPersonSample
    ),
    crewList = listOf(
        crewTvShowPersonSample,
        crewTvShowPersonSample,
        crewTvShowPersonSample,
        crewTvShowPersonSample
    ),
    recommendationList = listOf(
        mediaItemMovie,
        mediaItemTvShow,
        mediaItemMovie,
        mediaItemTvShow,
        mediaItemMovie,
        mediaItemTvShow
    ),
    seasonList = listOf(
        seasonSample
    ),
    nextEpisode = episodeSample,
    lastEpisode = episodeSample,
    isInAir = true,
    isFavorite = false,
    isWatchlisted = false,
    userRating = 20,
    isRateable = true,
    status = "Ended",
    originalLanguage = "ja",
    type = "Scripted",
    externalLinkList = externalLinkListSample,
    shareLink = "shareLink",
    providers = Providers(
        justWatchLink = "justWatchLink",
        logoUrlList = listOf("", "", "", "")
    )
)
