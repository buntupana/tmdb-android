package com.buntupana.tmdb.app.presentation.home

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Explore
import androidx.compose.material.icons.outlined.Movie
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Tv
import androidx.compose.material.icons.rounded.Explore
import androidx.compose.material.icons.rounded.Movie
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Tv
import androidx.compose.ui.graphics.vector.ImageVector
import com.buntupana.tmdb.core.ui.navigation.Routes
import com.buntupana.tmdb.feature.account.presentation.account.account.AccountNav
import com.buntupana.tmdb.feature.discover.presentation.discover.DiscoverNav
import com.buntupana.tmdb.feature.discover.presentation.movies.MoviesNav
import com.buntupana.tmdb.feature.discover.presentation.tv_shows.TvShowsNav


sealed class TabNavigationItem(
    open val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector = selectedIcon,
    val route: Routes
) {
    data class Discover(
        override val title: String
    ) : TabNavigationItem(
        title = title,
        selectedIcon = Icons.Rounded.Explore,
        unselectedIcon = Icons.Outlined.Explore,
        route = DiscoverNav
    )

    data class Movies(
        override val title: String
    ) : TabNavigationItem(
        title = title,
        selectedIcon = Icons.Rounded.Movie,
        unselectedIcon = Icons.Outlined.Movie,
        route = MoviesNav
    )

    data class TVShows(
        override val title: String
    ) : TabNavigationItem(
        title = title,
        selectedIcon = Icons.Rounded.Tv,
        unselectedIcon = Icons.Outlined.Tv,
        route = TvShowsNav
    )

    data class Account(
        override val title: String
    ) : TabNavigationItem(
        title = title,
        selectedIcon = Icons.Rounded.Person,
        unselectedIcon = Icons.Outlined.Person,
        route = AccountNav
    )
}