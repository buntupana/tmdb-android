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
import com.buntupana.tmdb.app.presentation.nav3.RouteNav3


sealed class TabNavigationItem(
    open val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector = selectedIcon,
    val route: RouteNav3
) {
    data class Discover(
        override val title: String
    ) : TabNavigationItem(
        title = title,
        selectedIcon = Icons.Rounded.Explore,
        unselectedIcon = Icons.Outlined.Explore,
        route = RouteNav3.Discover
    )

    data class Movies(
        override val title: String
    ) : TabNavigationItem(
        title = title,
        selectedIcon = Icons.Rounded.Movie,
        unselectedIcon = Icons.Outlined.Movie,
        route = RouteNav3.MediaList.Movie()
    )

    data class TVShows(
        override val title: String
    ) : TabNavigationItem(
        title = title,
        selectedIcon = Icons.Rounded.Tv,
        unselectedIcon = Icons.Outlined.Tv,
        route = RouteNav3.MediaList.TvShow()
    )

    data class Account(
        override val title: String
    ) : TabNavigationItem(
        title = title,
        selectedIcon = Icons.Rounded.Person,
        unselectedIcon = Icons.Outlined.Person,
        route = RouteNav3.Account
    )
}