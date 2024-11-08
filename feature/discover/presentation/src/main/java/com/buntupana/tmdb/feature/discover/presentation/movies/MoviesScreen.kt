package com.buntupana.tmdb.feature.discover.presentation.movies

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun MoviesScreen(
    viewModel: MoviesViewModel = hiltViewModel()
) {
    MoviesContent(viewModel.state)
}

@Composable
fun MoviesContent(
    state: MoviesState
) {
    Text(text = "Movies")
}

@Preview
@Composable
fun MoviesScreenPreview() {
    MoviesContent(
        MoviesState()
    )
}