package com.buntupana.tmdb.app.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.buntupana.tmdb.core.ui.navigation.NavRoutesMain

@Composable
fun NavigationRoot(
    modifier: Modifier = Modifier,
    navController: NavController,
    navRoutesMain: NavRoutesMain
) {

//    NavHost(
//        modifier = Modifier.fillMaxSize(),
//        navController = navController,
//        startDestination = HomeRoute
//    ) {
//
//        composable<> {
//            HomeNav(navRoutesMain)
//        }
//
//        composable<SignInRoute>(
//            deepLinks = listOf(
//                navDeepLink<SignInRoute>(basePath = urlProvider.SIGN_IN_DEEP_LINK_URL)
//            ),
//        ) {
//            SignInNav(navRoutesMain)
//        }
//
//        composable<SearchRoute> {
//            SearchNav(navRoutesMain)
//        }
//
//        composable<MediaDetailDeepLinkRoute>(
//            deepLinks = listOf(
//                navDeepLink<MediaDetailDeepLinkRoute>(basePath = urlProvider.BASE_DEEP_LINK_MOVIE_URL),
//                navDeepLink<MediaDetailDeepLinkRoute>(basePath = urlProvider.BASE_DEPP_LINK_TV_SHOW_URL)
//            ),
//        ) { entry ->
//
//            val route = entry.toRoute<MediaDetailDeepLinkRoute>()
//
//            val movieId = route.movieId?.substringBefore("-")?.toLong()
//            val tvShowId = route.tvShowId?.substringBefore("-")?.toLong()
//
//            val (mediaId, mediaType) = when {
//                movieId != null -> {
//                    movieId to MediaType.MOVIE
//                }
//
//                tvShowId != null -> {
//                    tvShowId to MediaType.TV_SHOW
//                }
//
//                else -> null to null
//            }
//
//            navRoutesMain.navigate(
//                destination = MediaDetailRoute(
//                    mediaId = mediaId!!,
//                    mediaType = mediaType!!,
//                    backgroundColor = null
//                )
//            )
//        }
//        composable<MediaDetailRoute> {
//            MediaDetailsNav(navRoutesMain)
//        }
//
//        composable<CastDetailRoute> {
//            CastDetailNav(navRoutesMain)
//        }
//
//        composable<PersonDetailDeepLinkRoute>(
//            deepLinks = listOf(
//                navDeepLink<PersonDetailDeepLinkRoute>(basePath = urlProvider.BASE_DEEP_LINK_PERSON_URL)
//            ),
//        ) { entry ->
//            val route = entry.toRoute<PersonDetailDeepLinkRoute>()
//
//            val personId = route.personId?.substringBefore("-")?.toLong()
//
//            navRoutesMain.navigate(
//                destination = PersonDetailRoute(
//                    personId = personId!!
//                )
//            )
//        }
//
//        composable<PersonDetailRoute> {
//            PersonDetailNav(navRoutesMain)
//        }
//
//        composable<SeasonsDetailRoute> {
//            SeasonsDetailNav(navRoutesMain)
//        }
//
//        composable<EpisodesDetailRoute> {
//            EpisodesDetailNav(navRoutesMain)
//        }
//
//        composable<WatchListFavoritesRoute> {
//            WatchlistFavoritesNav(navRoutesMain)
//        }
//
//        composable<ListsRoute> {
//            ListsNav(navRoutesMain)
//        }
//
//        composable<ListDetailRoute> { entry ->
//            ListDetailNav(navRoutesMain)
//        }
//
//        composable<ManageListsRoute> {
//            ManageListsNav(navRoutesMain)
//        }
//
//        composable<MediaFilterRoute>(typeMap = MediaFilterRoute.typeMap) {
//            MediaFilterNav(navRoutesMain)
//        }
//
//        bottomSheet<RatingRoute>(typeMap = RatingRoute.typeMap) {
//            RatingNav(navRoutesMain)
//        }
//
//        bottomSheet<CreateUpdateListRoute> {
//            CreateUpdateListNav(navRoutesMain)
//        }
//
//        bottomSheet<DeleteListRoute> {
//            DeleteListNav(navRoutesMain)
//        }
//
//        bottomSheet<DeleteItemListRoute>(typeMap = DeleteItemListRoute.typeMap) {
//            DeleteItemListNav(navRoutesMain)
//        }
//    }
}