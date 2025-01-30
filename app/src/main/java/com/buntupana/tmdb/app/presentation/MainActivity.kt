package com.buntupana.tmdb.app.presentation

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navDeepLink
import com.buntupana.tmdb.app.presentation.home.HomeNav
import com.buntupana.tmdb.app.presentation.home.HomeScreen
import com.buntupana.tmdb.core.ui.filter_type.MediaFilter
import com.buntupana.tmdb.core.ui.navigation.NavRoutesMain
import com.buntupana.tmdb.core.ui.snackbar.SnackbarController
import com.buntupana.tmdb.core.ui.theme.TMDBTheme
import com.buntupana.tmdb.core.ui.util.ObserveAsEvents
import com.buntupana.tmdb.feature.account.presentation.lists.ListsNav
import com.buntupana.tmdb.feature.account.presentation.lists.ListsScreen
import com.buntupana.tmdb.feature.account.presentation.sign_in.SignInNav
import com.buntupana.tmdb.feature.account.presentation.sign_in.SignInScreen
import com.buntupana.tmdb.feature.account.presentation.watchlist_favorites.ScreenType
import com.buntupana.tmdb.feature.account.presentation.watchlist_favorites.WatchListFavoritesNav
import com.buntupana.tmdb.feature.account.presentation.watchlist_favorites.WatchlistScreen
import com.buntupana.tmdb.feature.detail.presentation.cast.CastDetailNav
import com.buntupana.tmdb.feature.detail.presentation.cast.CastDetailScreen
import com.buntupana.tmdb.feature.detail.presentation.episodes.EpisodesDetailNav
import com.buntupana.tmdb.feature.detail.presentation.episodes.EpisodesDetailScreen
import com.buntupana.tmdb.feature.detail.presentation.media.MediaDetailNav
import com.buntupana.tmdb.feature.detail.presentation.media.MediaDetailScreen
import com.buntupana.tmdb.feature.detail.presentation.person.PersonDetailNav
import com.buntupana.tmdb.feature.detail.presentation.person.PersonDetailScreen
import com.buntupana.tmdb.feature.detail.presentation.seasons.SeasonsDetailNav
import com.buntupana.tmdb.feature.detail.presentation.seasons.SeasonsDetailScreen
import com.buntupana.tmdb.feature.search.presentation.SearchNav
import com.buntupana.tmdb.feature.search.presentation.SearchScreen
import com.panabuntu.tmdb.core.common.entity.MediaType
import com.panabuntu.tmdb.core.common.provider.UrlProvider
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var navRoutesMain: NavRoutesMain

    @Inject
    lateinit var urlProvider: UrlProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TMDBTheme {

                val context = LocalContext.current

                val navController = rememberNavController()
                navRoutesMain.init(navController)

                val snackbarHostState = remember { SnackbarHostState() }

                val scope = rememberCoroutineScope()

                ObserveAsEvents(
                    SnackbarController.events,
                    snackbarHostState
                ) { event ->
                    scope.launch {
                        val result = snackbarHostState.showSnackbar(
                            message = event.message.asString(context),
                            actionLabel = event.action?.name,
                            duration = event.snackbarDuration
                        )

                        if (result == SnackbarResult.ActionPerformed) {
                            event.action?.action?.invoke()
                        }
                    }
                }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
                ) {

                    NavHost(
                        modifier = Modifier.fillMaxSize(),
                        navController = navController,
                        startDestination = HomeNav
                    ) {

                        composable<HomeNav> {
                            HomeScreen(
                                onSignInClicked = {
                                    navRoutesMain.navigate(SignInNav())
                                },
                                onSearchClicked = {
                                    navRoutesMain.navigate(SearchNav)
                                },
                                onMediaItemClicked = { mediaItemId, mediaItemType, posterDominantColor ->
                                    navRoutesMain.navigate(
                                        MediaDetailNav(
                                            mediaId = mediaItemId,
                                            mediaType = mediaItemType,
                                            backgroundColor = posterDominantColor.toArgb()
                                        )
                                    )
                                },
                                onWatchListClick = { mediaType ->
                                    val mediaFilter = when (mediaType) {
                                        MediaType.MOVIE -> MediaFilter.MOVIES
                                        MediaType.TV_SHOW -> MediaFilter.TV_SHOWS
                                    }
                                    navRoutesMain.navigate(
                                        WatchListFavoritesNav(
                                            ScreenType.WATCHLIST,
                                            mediaFilter
                                        )
                                    )
                                },
                                onFavoritesClick = { mediaType ->
                                    val mediaFilter = when (mediaType) {
                                        MediaType.MOVIE -> MediaFilter.MOVIES
                                        MediaType.TV_SHOW -> MediaFilter.TV_SHOWS
                                    }
                                    navRoutesMain.navigate(
                                        WatchListFavoritesNav(
                                            ScreenType.FAVORITES,
                                            mediaFilter
                                        )
                                    )
                                },
                                onListsClick = {
                                    navRoutesMain.navigate(ListsNav)
                                },
                                onListDetailClicked = { listId, dominantColor ->

                                }
                            )
                        }
                        composable<SignInNav>(
                            deepLinks = listOf(
                                navDeepLink<SignInNav>(basePath = urlProvider.SIGN_IN_DEEP_LINK_URL)
                            ),
                        ) {
                            SignInScreen(
                                onNavigateBack = {
                                    if (navRoutesMain.isCurrentDestination(SignInNav::class)) {
                                        navRoutesMain.popBackStack()
                                    }
                                }
                            )
                        }
                        composable<SearchNav> {
                            SearchScreen(
                                onMediaClick = { mediaItemId, mediaItemType, posterDominantColor ->
                                    navRoutesMain.navigate(
                                        MediaDetailNav(
                                            mediaId = mediaItemId,
                                            mediaType = mediaItemType,
                                            backgroundColor = posterDominantColor?.toArgb()
                                        )
                                    )
                                },
                                onPersonClick = { personId ->
                                    navRoutesMain.navigate(
                                        PersonDetailNav(personId = personId)
                                    )
                                }
                            )
                        }
                        composable<MediaDetailNav> {
                            MediaDetailScreen(
                                onBackClick = navRoutesMain::popBackStack,
                                onSearchClick = { navRoutesMain.navigate(SearchNav) },
                                onPersonClick = { personId ->
                                    navRoutesMain.navigate(
                                        PersonDetailNav(personId = personId)
                                    )
                                },
                                onFullCastClick = { mediaId, mediaType, mediaTitle, mediaReleaseYear, mediaPosterUrl, backgroundColor ->
                                    navRoutesMain.navigate(
                                        CastDetailNav(
                                            mediaId = mediaId,
                                            mediaType = mediaType,
                                            mediaTitle = mediaTitle,
                                            releaseYear = mediaReleaseYear,
                                            posterUrl = mediaPosterUrl,
                                            backgroundColor = backgroundColor.toArgb()
                                        )
                                    )
                                },
                                onSeasonClick = { tvShowId, seasonName, seasonNumber, posterUrl, backgroundColor, releaseYear ->
                                    navRoutesMain.navigate(
                                        EpisodesDetailNav(
                                            tvShowId = tvShowId,
                                            seasonName = seasonName,
                                            seasonNumber = seasonNumber,
                                            posterUrl = posterUrl,
                                            backgroundColor = backgroundColor.toArgb(),
                                            releaseYear = releaseYear
                                        )
                                    )
                                },
                                onAllSeasonsClick = { tvShowId, tvShowTitle, releaseYear, posterUrl, backgroundColor ->
                                    navRoutesMain.navigate(
                                        SeasonsDetailNav(
                                            tvShowId = tvShowId,
                                            tvShowName = tvShowTitle,
                                            releaseYear = releaseYear,
                                            posterUrl = posterUrl,
                                            backgroundColor = backgroundColor.toArgb()
                                        )
                                    )
                                },
                                onRecommendationClick = { mediaId, mediaType, backgroundColor ->
                                    navRoutesMain.navigate(
                                        MediaDetailNav(
                                            mediaId = mediaId,
                                            mediaType = mediaType,
                                            backgroundColor = backgroundColor?.toArgb()
                                        )
                                    )
                                },
                                onLogoClick = {
                                    navController.popBackStack(HomeNav, false)
                                }
                            )
                        }
                        composable<CastDetailNav> {
                            CastDetailScreen(
                                onBackClick = { navRoutesMain.popBackStack() },
                                onSearchClick = { navRoutesMain.navigate(SearchNav) },
                                onPersonClick = { personId ->
                                    navRoutesMain.navigate(
                                        PersonDetailNav(personId = personId)
                                    )
                                },
                                onLogoClick = {
                                    navRoutesMain.popBackStack(HomeNav::class)
                                }
                            )
                        }
                        composable<PersonDetailNav> {
                            PersonDetailScreen(
                                onBackClick = { navRoutesMain.popBackStack() },
                                onSearchClick = { navRoutesMain.navigate(SearchNav) },
                                onMediaClick = { mediaItemId, mediaItemType, posterDominantColor ->
                                    navRoutesMain.navigate(
                                        MediaDetailNav(
                                            mediaId = mediaItemId,
                                            mediaType = mediaItemType,
                                            backgroundColor = posterDominantColor?.toArgb()
                                        )
                                    )
                                },
                                onLogoClick = {
                                    navRoutesMain.popBackStack(HomeNav::class)
                                }
                            )
                        }
                        composable<SeasonsDetailNav> {
                            SeasonsDetailScreen(
                                onBackClick = { navRoutesMain.popBackStack() },
                                onSearchClick = { navRoutesMain.navigate(SearchNav) },
                                onSeasonClick = { tvShowId, seasonName, seasonNumber, posterUrl, backgroundColor, releaseYear ->
                                    navRoutesMain.navigate(
                                        EpisodesDetailNav(
                                            tvShowId = tvShowId,
                                            seasonName = seasonName,
                                            seasonNumber = seasonNumber,
                                            posterUrl = posterUrl,
                                            backgroundColor = backgroundColor.toArgb(),
                                            releaseYear = releaseYear
                                        )
                                    )
                                },
                                onLogoClick = {
                                    navRoutesMain.popBackStack(HomeNav::class)
                                }
                            )
                        }
                        composable<EpisodesDetailNav> {
                            EpisodesDetailScreen(
                                onBackClick = { navRoutesMain.popBackStack() },
                                onSearchClick = { navRoutesMain.navigate(SearchNav) },
                                onLogoClick = {
                                    navRoutesMain.popBackStack(HomeNav::class)
                                }
                            )
                        }
                        composable<WatchListFavoritesNav> {
                            WatchlistScreen(
                                onBackClick = { navRoutesMain.popBackStack() },
                                onSearchClick = { navRoutesMain.navigate(SearchNav) },
                                onMediaClick = { mediaId, mediaType, mainPosterColor ->
                                    navRoutesMain.navigate(
                                        MediaDetailNav(
                                            mediaId = mediaId,
                                            mediaType = mediaType,
                                            backgroundColor = mainPosterColor?.toArgb()
                                        )
                                    )
                                }
                            )
                        }
                        composable<ListsNav> {
                            ListsScreen()
                        }
                    }
                }
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        Timber.d("onNewIntent() called with: intent = [$intent]")
        super.onNewIntent(intent)
        navRoutesMain.handleDeepLing(intent)
    }
}
