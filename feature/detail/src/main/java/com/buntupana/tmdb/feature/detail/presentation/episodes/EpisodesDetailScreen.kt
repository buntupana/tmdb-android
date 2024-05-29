package com.buntupana.tmdb.feature.detail.presentation.episodes

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.buntupana.tmdb.feature.detail.presentation.DetailNavigator
import com.ramcosta.composedestinations.annotation.Destination

@Destination(
    navArgsDelegate = EpisodesDetailNavArgs::class
)
@Composable
fun EpisodesDetailScreen(
    viewModel: EpisodesDetailViewModel = hiltViewModel(),
    detailNavigator: DetailNavigator
) {

}

@Composable
private fun EpisodesDetailContent(state: EpisodesDetailState) {

}
