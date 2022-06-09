package com.buntupana.tmdb.feature.detail.presentation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.buntupana.tmdb.core.domain.entity.MediaType
import com.ramcosta.composedestinations.annotation.Destination

@Destination
@Composable
fun MediaDetailScreen(
    viewModel: DetailViewModel = hiltViewModel(),
    detailNavigator: DetailNavigator,
    id: Long,
    mediaType: MediaType
) {
    Text(text = "Detail Screen")
}