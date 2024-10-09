package com.buntupana.tmdb.app.presentation.navigation

import com.buntupana.tmdb.feature.detail.presentation.DetailNavigator
import com.buntupana.tmdb.feature.discover.presentation.DiscoverNavigator
import com.buntupana.tmdb.feature.search.presentation.SearchNavigator

interface CommonNavigationBase: DiscoverNavigator, DetailNavigator, SearchNavigator