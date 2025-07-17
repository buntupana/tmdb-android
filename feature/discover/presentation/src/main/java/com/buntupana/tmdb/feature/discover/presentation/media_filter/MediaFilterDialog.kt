package com.buntupana.tmdb.feature.discover.presentation.media_filter

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun MediaFilterDialog(
    viewModel: MediaFilterViewModel = hiltViewModel()
) {
    MediaFilterContent(
        state = viewModel.state
    )
}

@Composable
fun MediaFilterContent(
    state: MediaFilterState
) {

}

@Preview(showBackground = true)
@Composable
fun MediaFilterScreenPreview() {
    MediaFilterContent(
        MediaFilterState()
    )
}