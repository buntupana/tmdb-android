package com.buntupana.tmdb.feature.discover.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.buntupana.tmdb.feature.discover.R
import com.ramcosta.composedestinations.annotation.Destination

@Destination
@Composable
fun DiscoverScreen(
    viewModel: DiscoverViewModel = hiltViewModel(),
    discoverNavigator: DiscoverNavigator
) {

    val state = viewModel.state

    Column(modifier = Modifier.fillMaxSize()) {
        Row {
            Text(
                stringResource(id = R.string.text_whats_popular),
                modifier = Modifier.padding(16.dp),
                fontWeight = FontWeight.SemiBold,
                fontSize = 22.sp,
            )
        }
        CarouselMediaItem(
            modifier = Modifier.fillMaxWidth(),
            state.popularMediaItemList,
            onItemClicked = {

            }
        )
        Text(
            stringResource(id = R.string.text_free_to_watch),
            modifier = Modifier.padding(16.dp),
            fontWeight = FontWeight.SemiBold,
            fontSize = 22.sp,
        )
        CarouselMediaItem(
            modifier = Modifier.fillMaxWidth(),
            state.popularMediaItemList,
            onItemClicked = {

            }
        )
    }
}

@Preview
@Composable
fun DiscoverScreenPreview() {
    Text("hola")
}

