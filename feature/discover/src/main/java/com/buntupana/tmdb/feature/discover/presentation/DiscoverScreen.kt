package com.buntupana.tmdb.feature.discover.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.buntupana.tmdb.core.presentation.theme.Dimens
import com.ramcosta.composedestinations.annotation.Destination

@Destination
@Composable
fun DiscoverScreen(
    viewModel: DiscoverViewModel = hiltViewModel(),
    discoverNavigator: DiscoverNavigator
) {

    val state = viewModel.state

    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            "What's Popular",
            modifier = Modifier.padding(16.dp),
            fontWeight = FontWeight.SemiBold,
            fontSize = 22.sp,
        )
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            items(state.movieItemList.size) { i ->
                if (i == 0) {
                    Spacer(modifier = Modifier.width(12.dp))
                }

                val movieItem = state.movieItemList[i]
                MediaItem(
                    modifier = Modifier
                        .width(Dimens.carouselMediaItemWidth)
                        .clip(RoundedCornerShape(5.dp))
                        .clickable {
                            // Navigate to detail screen
                        },
                    movie = movieItem
                )
                if (i == state.movieItemList.size - 1) {
                    Spacer(modifier = Modifier.width(12.dp))
                }
            }
        }
    }
}

@Preview
@Composable
fun DiscoverScreenPreview() {
    Text("hola")
}

