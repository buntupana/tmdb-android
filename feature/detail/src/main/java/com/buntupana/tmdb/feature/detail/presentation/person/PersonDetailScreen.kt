package com.buntupana.tmdb.feature.detail.presentation.person

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.buntupana.tmdb.feature.detail.presentation.DetailNavigator
import com.buntupana.tmdb.feature.detail.presentation.PersonDetailNavArgs
import com.ramcosta.composedestinations.annotation.Destination

@Destination(
    navArgsDelegate = PersonDetailNavArgs::class
)
@Composable
fun PersonDetailScreen(
    viewModel: PersonDetailViewModel = hiltViewModel(),
    detailNavigator: DetailNavigator
) {
    PersonDetailContent(
        viewModel.state
    )
}

@Composable
fun PersonDetailContent(
    personState: PersonState
) {

}