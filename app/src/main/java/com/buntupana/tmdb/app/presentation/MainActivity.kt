package com.buntupana.tmdb.app.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.buntupana.tmdb.app.presentation.navigation.CommonNavigation
import com.buntupana.tmdb.app.presentation.navigation.CustomNavType
import com.buntupana.tmdb.app.presentation.navigation.Routes
import com.buntupana.tmdb.core.presentation.theme.TMDBTheme
import com.buntupana.tmdb.feature.detail.presentation.cast.CastDetailNavArgs
import com.buntupana.tmdb.feature.detail.presentation.cast.CastDetailScreen
import com.buntupana.tmdb.feature.detail.presentation.media.MediaDetailNavArgs
import com.buntupana.tmdb.feature.detail.presentation.media.MediaDetailScreen
import com.buntupana.tmdb.feature.detail.presentation.person.PersonDetailNavArgs
import com.buntupana.tmdb.feature.detail.presentation.person.PersonDetailScreen
import com.buntupana.tmdb.feature.detail.presentation.seasons.SeasonsDetailNavArgs
import com.buntupana.tmdb.feature.detail.presentation.seasons.SeasonsDetailScreen
import com.buntupana.tmdb.feature.discover.presentation.DiscoverScreen
import com.buntupana.tmdb.feature.search.presentation.SearchScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlin.reflect.typeOf

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContent {
            TMDBTheme {
                val navController = rememberNavController()
                val commonNavigation = CommonNavigation(navController)
                NavHost(navController = navController, startDestination = Routes.Discover) {

                    composable<Routes.Discover> {
                        DiscoverScreen(discoverNavigator = commonNavigation)
                    }
                    composable<Routes.Search> {
                        SearchScreen(searchNavigator = commonNavigation)
                    }
                    composable<Routes.MediaDetail>(
                        typeMap = mapOf(
                            typeOf<MediaDetailNavArgs>() to CustomNavType(
                                MediaDetailNavArgs::class.java,
                                MediaDetailNavArgs.serializer()
                            )
                        )
                    ) {
                        MediaDetailScreen(detailNavigator = commonNavigation)
                    }
                    composable<Routes.CastDetail>(
                        typeMap = mapOf(
                            typeOf<CastDetailNavArgs>() to CustomNavType(
                                CastDetailNavArgs::class.java,
                                CastDetailNavArgs.serializer()
                            )
                        )
                    ) {
                        CastDetailScreen(detailNavigator = commonNavigation)
                    }
                    composable<Routes.PersonDetail>(
                        typeMap = mapOf(
                            typeOf<PersonDetailNavArgs>() to CustomNavType(
                                PersonDetailNavArgs::class.java,
                                PersonDetailNavArgs.serializer()
                            )
                        )
                    ) {
                        PersonDetailScreen(detailNavigator = commonNavigation)
                    }
                    composable<Routes.SeasonDetail>(
                        typeMap = mapOf(
                            typeOf<SeasonsDetailNavArgs>() to CustomNavType(
                                SeasonsDetailNavArgs::class.java,
                                SeasonsDetailNavArgs.serializer()
                            )
                        )
                    ) {
                        SeasonsDetailScreen(detailNavigator = commonNavigation)
                    }
                }
            }
        }
    }
}
