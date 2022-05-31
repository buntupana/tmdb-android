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
                .height(288.dp)
        ) {
            items(state.movieItemList.size) { i ->
                if (i == 0) {
                    Spacer(modifier = Modifier.width(12.dp))
                }

                val movieItem = state.movieItemList[i]
                MovieItem(
                    movie = movieItem,
                    modifier = Modifier
                        .clip(RoundedCornerShape(5.dp))
                        .clickable {
                            // Navigate to detail screen
                        }
                        .width((200 * 2 / 3).dp)
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

