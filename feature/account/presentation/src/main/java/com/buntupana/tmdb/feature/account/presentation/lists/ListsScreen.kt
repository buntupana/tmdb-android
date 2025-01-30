package com.buntupana.tmdb.feature.account.presentation.lists

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun ListsScreen(
    viewModel: ListsViewModel = hiltViewModel()
) {
    ListsContent(viewModel.state)
}

@Composable
fun ListsContent(
    state: ListsState
) {

}

@Preview(showBackground = true)
@Composable
fun ListsScreenPreview() {
    ListsContent(
        ListsState()
    )
}