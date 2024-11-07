package com.buntupana.tmdb.feature.discover.presentation.tv_shows

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun TvShowsScreen(
    viewModel: TvShowsViewModel = hiltViewModel()
) {
    TvShowsContent(viewModel.state)
}

@Composable
fun TvShowsContent(
    state: TvShowsState
) {
    Text(text = "TV Shows")
}

@Preview
@Composable
fun TvShowsScreenPreview() {
    TvShowsContent(
        TvShowsState()
    )
}