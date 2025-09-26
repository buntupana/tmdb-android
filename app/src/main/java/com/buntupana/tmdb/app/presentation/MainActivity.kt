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
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navDeepLink
import com.buntupana.tmdb.app.presentation.home.HomeRoute
import com.buntupana.tmdb.app.presentation.navigation.CastDetailNav
import com.buntupana.tmdb.app.presentation.navigation.CreateUpdateListNav
import com.buntupana.tmdb.app.presentation.navigation.DeleteItemListNav
import com.buntupana.tmdb.app.presentation.navigation.DeleteListNav
import com.buntupana.tmdb.app.presentation.navigation.EpisodesDetailNav
import com.buntupana.tmdb.app.presentation.navigation.HomeNav
import com.buntupana.tmdb.app.presentation.navigation.ListDetailNav
import com.buntupana.tmdb.app.presentation.navigation.ListsNav
import com.buntupana.tmdb.app.presentation.navigation.ManageListsNav
import com.buntupana.tmdb.app.presentation.navigation.MediaDetailsNav
import com.buntupana.tmdb.app.presentation.navigation.MediaFilterNav
import com.buntupana.tmdb.app.presentation.navigation.PersonDetailNav
import com.buntupana.tmdb.app.presentation.navigation.RatingNav
import com.buntupana.tmdb.app.presentation.navigation.SearchNav
import com.buntupana.tmdb.app.presentation.navigation.SeasonsDetailNav
import com.buntupana.tmdb.app.presentation.navigation.SignInNav
import com.buntupana.tmdb.app.presentation.navigation.WatchlistFavoritesNav
import com.buntupana.tmdb.core.ui.navigation.NavRoutesMain
import com.buntupana.tmdb.core.ui.snackbar.SnackbarController
import com.buntupana.tmdb.core.ui.theme.AppTheme
import com.buntupana.tmdb.core.ui.util.ObserveAsEvents
import com.buntupana.tmdb.core.ui.util.bottomSheet
import com.buntupana.tmdb.feature.account.presentation.sign_in.SignInRoute
import com.buntupana.tmdb.feature.detail.presentation.cast.CastDetailRoute
import com.buntupana.tmdb.feature.detail.presentation.episodes.EpisodesDetailRoute
import com.buntupana.tmdb.feature.detail.presentation.media.MediaDetailRoute
import com.buntupana.tmdb.feature.detail.presentation.person.PersonDetailRoute
import com.buntupana.tmdb.feature.detail.presentation.rating.RatingRoute
import com.buntupana.tmdb.feature.detail.presentation.seasons.SeasonsDetailRoute
import com.buntupana.tmdb.feature.discover.presentation.media_filter.MediaFilterRoute
import com.buntupana.tmdb.feature.lists.presentation.create_update_list.CreateUpdateListRoute
import com.buntupana.tmdb.feature.lists.presentation.delete_item_list.DeleteItemListRoute
import com.buntupana.tmdb.feature.lists.presentation.delete_list.DeleteListRoute
import com.buntupana.tmdb.feature.lists.presentation.list_detail.ListDetailRoute
import com.buntupana.tmdb.feature.lists.presentation.lists.ListsRoute
import com.buntupana.tmdb.feature.lists.presentation.manage_lists.ManageListsRoute
import com.buntupana.tmdb.feature.lists.presentation.watchlist_favorites.WatchListFavoritesRoute
import com.buntupana.tmdb.feature.search.presentation.SearchRoute
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme {
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
                        startDestination = HomeRoute
                    ) {

                        composable<HomeRoute> {
                            HomeNav(navRoutesMain)
                        }

                        composable<SignInRoute>(
                            deepLinks = listOf(
                                navDeepLink<SignInRoute>(basePath = urlProvider.SIGN_IN_DEEP_LINK_URL)
                            ),
                        ) {
                            SignInNav(navRoutesMain)
                        }

                        composable<SearchRoute> {
                            SearchNav(navRoutesMain)
                        }

                        composable<MediaDetailRoute> {
                            MediaDetailsNav(navRoutesMain)
                        }

                        composable<CastDetailRoute> {
                            CastDetailNav(navRoutesMain)
                        }

                        composable<PersonDetailRoute> {
                            PersonDetailNav(navRoutesMain)
                        }

                        composable<SeasonsDetailRoute> {
                            SeasonsDetailNav(navRoutesMain)
                        }

                        composable<EpisodesDetailRoute> {
                            EpisodesDetailNav(navRoutesMain)
                        }

                        composable<WatchListFavoritesRoute> {
                            WatchlistFavoritesNav(navRoutesMain)
                        }

                        composable<ListsRoute> {
                            ListsNav(navRoutesMain)
                        }

                        composable<ListDetailRoute> { entry ->
                            ListDetailNav(navRoutesMain)
                        }

                        composable<ManageListsRoute> {
                            ManageListsNav(navRoutesMain)
                        }

                        composable<MediaFilterRoute>(typeMap = MediaFilterRoute.typeMap) {
                            MediaFilterNav(navRoutesMain)
                        }

                        bottomSheet<RatingRoute>(typeMap = RatingRoute.typeMap) {
                            RatingNav(navRoutesMain)
                        }

                        bottomSheet<CreateUpdateListRoute> {
                            CreateUpdateListNav(navRoutesMain)
                        }

                        bottomSheet<DeleteListRoute> {
                            DeleteListNav(navRoutesMain)
                        }

                        bottomSheet<DeleteItemListRoute>(
                            typeMap = DeleteItemListRoute.typeMap
                        ) {
                            DeleteItemListNav(navRoutesMain)
                        }
                    }
                }
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        Timber.d("onNewIntent() called with: intent = [$intent]")
        super.onNewIntent(intent)
        navRoutesMain.handleDeepLink(intent)
    }
}
