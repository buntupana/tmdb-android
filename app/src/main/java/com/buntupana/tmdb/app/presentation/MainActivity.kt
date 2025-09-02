package com.buntupana.tmdb.app.presentation

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
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
import com.buntupana.tmdb.core.ui.util.bottomSheet
import com.buntupana.tmdb.core.ui.util.getResult
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
import com.buntupana.tmdb.feature.detail.presentation.rating.RatingDialog
import com.buntupana.tmdb.feature.detail.presentation.rating.RatingMediaType
import com.buntupana.tmdb.feature.detail.presentation.rating.RatingNav
import com.buntupana.tmdb.feature.detail.presentation.seasons.SeasonsDetailNav
import com.buntupana.tmdb.feature.detail.presentation.seasons.SeasonsDetailScreen
import com.buntupana.tmdb.feature.discover.presentation.media_filter.MediaFilterNav
import com.buntupana.tmdb.feature.discover.presentation.media_filter.MediaFilterScreen
import com.buntupana.tmdb.feature.discover.presentation.model.MediaListFilter
import com.buntupana.tmdb.feature.lists.presentation.create_update_list.CreateUpdateListDialog
import com.buntupana.tmdb.feature.lists.presentation.create_update_list.CreateUpdateListNav
import com.buntupana.tmdb.feature.lists.presentation.delete_list.DeleteListDialog
import com.buntupana.tmdb.feature.lists.presentation.delete_list.DeleteListNav
import com.buntupana.tmdb.feature.lists.presentation.list_detail.ListDetailNav
import com.buntupana.tmdb.feature.lists.presentation.list_detail.ListDetailScreen
import com.buntupana.tmdb.feature.lists.presentation.lists.ListsNav
import com.buntupana.tmdb.feature.lists.presentation.lists.ListsScreen
import com.buntupana.tmdb.feature.lists.presentation.manage_lists.ManageListsDialog
import com.buntupana.tmdb.feature.lists.presentation.manage_lists.ManageListsNav
import com.buntupana.tmdb.feature.search.presentation.SearchNav
import com.buntupana.tmdb.feature.search.presentation.SearchScreen
import com.panabuntu.tmdb.core.common.entity.MediaType
import com.panabuntu.tmdb.core.common.provider.UrlProvider
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
class MainActivity : ComponentActivity() {

    private val navRoutesMain: NavRoutesMain by inject()
    private val urlProvider: UrlProvider by inject()

    private val viewModel: MainActivityViewModel by viewModel()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TMDBTheme {
                viewModel.reloadSession.collectAsStateWithLifecycle()
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

                        composable<HomeNav> { entry ->
                            HomeScreen(
                                mediaListFilterResult = entry.getResult<MediaListFilter>(),
                                onSignInClicked = {
                                    navRoutesMain.navigate(SignInNav())
                                },
                                onSearchClicked = {
                                    navRoutesMain.navigate(SearchNav)
                                },
                                onMediaItemClicked = { mediaItemType, mediaItemId, posterDominantColor ->
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
                                onListDetailClick = { listId, listName, description, backdropUrl ->
                                    navRoutesMain.navigate(
                                        ListDetailNav(
                                            listId = listId,
                                            listName = listName,
                                            description = description,
                                            backdropUrl = backdropUrl
                                        )
                                    )
                                },
                                onMovieFilterClick = { mediaListFilter ->
                                    navRoutesMain.navigate(
                                        MediaFilterNav(
                                            mediaListFilter = mediaListFilter
                                        )
                                    )
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
                                },
                                onRatingClick = { mediaId, mediaType, mediaTitle, rating ->

                                    when (mediaType) {
                                        MediaType.MOVIE -> {
                                            RatingMediaType.Movie(
                                                movieId = mediaId,
                                                _title = mediaTitle,
                                                _rating = rating
                                            )
                                        }

                                        MediaType.TV_SHOW -> {
                                            RatingMediaType.TvShow(
                                                tvShowId = mediaId,
                                                _title = mediaTitle,
                                                _rating = rating
                                            )
                                        }
                                    }.let {
                                        navRoutesMain.navigate(
                                            RatingNav(it)
                                        )
                                    }

                                },
                                onManageListClick = { mediaId, mediaType, mediaName, mediaPosterUrl, backgroundColor, releaseYear ->
                                    navRoutesMain.navigate(
                                        ManageListsNav(
                                            mediaId = mediaId,
                                            mediaType = mediaType,
                                            posterUrl = mediaPosterUrl,
                                            mediaName = mediaName,
                                            backgroundColor = backgroundColor,
                                            releaseYear = releaseYear
                                        )
                                    )
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
                                onLogoClick = { navRoutesMain.popBackStack(HomeNav::class) },
                                onRateEpisodeClick = { tvShowId, episodeName: String, seasonNumber, episodeNumber, currentRating ->
                                    navRoutesMain.navigate(
                                        RatingNav(
                                            RatingMediaType.Episode(
                                                tvShowId = tvShowId,
                                                _title = episodeName,
                                                seasonNumber = seasonNumber,
                                                episodeNumber = episodeNumber,
                                                _rating = currentRating
                                            )
                                        )
                                    )
                                },
                                onPersonClick = { personId: Long ->
                                    navRoutesMain.navigate(
                                        PersonDetailNav(personId = personId)
                                    )
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
                            ListsScreen(
                                onBackClick = { navRoutesMain.popBackStack() },
                                onListDetailClick = { listId, listName, description, backdropUrl ->
                                    navRoutesMain.navigate(
                                        ListDetailNav(
                                            listId = listId,
                                            listName = listName,
                                            description = description,
                                            backdropUrl = backdropUrl
                                        )
                                    )
                                },
                                onSearchClick = { navRoutesMain.navigate(SearchNav) },
                                onCreateListDialogClick = {
                                    navRoutesMain.navigate(
                                        CreateUpdateListNav()
                                    )
                                }
                            )
                        }
                        composable<ListDetailNav> {
                            ListDetailScreen(
                                onBackClick = { navRoutesMain.popBackStack() },
                                onLogoClick = { navRoutesMain.popBackStack(HomeNav::class) },
                                onSearchClick = { navRoutesMain.navigate(SearchNav) },
                                onMediaClick = { mediaId, mediaType, mainPosterColor ->
                                    navRoutesMain.navigate(
                                        MediaDetailNav(
                                            mediaId = mediaId,
                                            mediaType = mediaType,
                                            backgroundColor = mainPosterColor?.toArgb()
                                        )
                                    )
                                },
                                onUpdateListClick = { listId, listName, listDescription, isPublic ->
                                    navRoutesMain.navigate(
                                        CreateUpdateListNav(
                                            listId = listId,
                                            listName = listName,
                                            listDescription = listDescription,
                                            isPublic = isPublic
                                        )
                                    )
                                },
                                onDeleteListClick = { listId, listName ->
                                    navRoutesMain.navigate(
                                        DeleteListNav(
                                            listId = listId,
                                            listName = listName
                                        )
                                    )
                                }
                            )
                        }
                        composable<ManageListsNav> {
                            ManageListsDialog(
                                onBackClick = { navRoutesMain.popBackStack() },
                                onLogoClick = { navRoutesMain.popBackStack(HomeNav::class) },
                                onCreateListClick = {
                                    navRoutesMain.navigate(CreateUpdateListNav())
                                }
                            )
                        }
                        composable<MediaFilterNav>(
                            typeMap = MediaFilterNav.typeMap
                        ) {
                            MediaFilterScreen(
                                onBackClick = {
                                    navRoutesMain.popBackStack()
                                },
                                onApplyFilterClick = { mediaListFilter ->
                                    navRoutesMain.saveResult(mediaListFilter)
                                    navRoutesMain.popBackStack()
                                }
                            )
                        }
                        bottomSheet<RatingNav>(
                            typeMap = RatingNav.typeMap
                        ) {
                            RatingDialog(
                                onDismiss = {
                                    navRoutesMain.popBackStack()
                                }
                            )
                        }
                        bottomSheet<CreateUpdateListNav> {
                            CreateUpdateListDialog(
                                onDismiss = { navRoutesMain.popBackStack() }
                            )
                        }
                        bottomSheet<DeleteListNav> {
                            DeleteListDialog(
                                onDismiss = { navRoutesMain.popBackStack() }
                            )
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
