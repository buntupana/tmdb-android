package com.buntupana.tmdb.feature.discover.presentation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@RootNavGraph(start = true)
@Destination
@Composable
fun DiscoverScreen(
    navigator: DestinationsNavigator,
    viewModel: DiscoverViewModel = hiltViewModel()
) {
    Text(text = "Hello Discover Module")
}