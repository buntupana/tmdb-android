package com.buntupana.tmdb.feature.discover.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import com.buntupana.tmdb.core.presentation.widget.menu_selector.MenuSelector
import com.buntupana.tmdb.core.presentation.widget.menu_selector.MenuSelectorItem
import com.buntupana.tmdb.feature.discover.R
import com.buntupana.tmdb.feature.discover.domain.entity.FreeToWatchType
import com.buntupana.tmdb.feature.discover.domain.entity.PopularType
import com.buntupana.tmdb.feature.discover.domain.entity.TrendingType
import com.buntupana.tmdb.feature.discover.presentation.filter_type.FreeToWatchFilter
import com.buntupana.tmdb.feature.discover.presentation.filter_type.PopularFilter
import com.buntupana.tmdb.feature.discover.presentation.filter_type.TrendingFilter
import com.ramcosta.composedestinations.annotation.Destination

@Destination
@Composable
fun DiscoverScreen(
    viewModel: DiscoverViewModel = hiltViewModel(),
    discoverNavigator: DiscoverNavigator
) {

    val state = viewModel.state

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(
                state = scrollState
            )
    ) {
        TitleAndFilter(
            title = stringResource(id = R.string.text_whats_popular),
            filterSet = viewModel.popularFilterSet,
            indexSelected = viewModel.popularFilterSelected,
            filterClicked = { item, index ->
                val popularType = when (item) {
                    PopularFilter.ForRent -> PopularType.FOR_RENT
                    PopularFilter.InTheatres -> PopularType.IN_THEATRES
                    PopularFilter.OnTv -> PopularType.ON_TV
                    PopularFilter.Streaming -> PopularType.STREAMING
                }
                viewModel.popularFilterSelected = index
                viewModel.onEvent(DiscoverEvent.ChangePopularType(popularType))
            }
        )
        CarouselMediaItem(
            modifier = Modifier.fillMaxWidth(),
            state.popularMediaItemList,
            onItemClicked = {

            }
        )
        TitleAndFilter(
            title = stringResource(id = R.string.text_free_to_watch),
            filterSet = viewModel.freeToWatchFilterSet,
            indexSelected = viewModel.freeToWatchFilterSelected,
            filterClicked = { item, index ->
                val freeToWatchType = when (item) {
                    FreeToWatchFilter.Movies -> FreeToWatchType.MOVIES
                    FreeToWatchFilter.TvShows -> FreeToWatchType.TV_SHOWS
                }
                viewModel.freeToWatchFilterSelected = index
                viewModel.onEvent(DiscoverEvent.ChangeFreeToWatchType(freeToWatchType))
            }
        )
        CarouselMediaItem(
            modifier = Modifier.fillMaxWidth(),
            state.freeToWatchMediaItemList,
            onItemClicked = {

            }
        )
        TitleAndFilter(
            title = stringResource(id = R.string.text_trending),
            filterSet = viewModel.trendingFilterSet,
            indexSelected = viewModel.trendingFilterSelected,
            filterClicked = { item, index ->
                val trendingType = when (item) {
                    TrendingFilter.ThisWeek -> TrendingType.THIS_WEEK
                    TrendingFilter.Today -> TrendingType.TODAY
                }
                viewModel.trendingFilterSelected = index
                viewModel.onEvent(DiscoverEvent.ChangeTrendingType(trendingType))
            }
        )
        Box(modifier = Modifier.fillMaxWidth()) {

            Image(
                painter = painterResource(id = R.drawable.img_trending),
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier.fillMaxWidth().align(Alignment.Center)
            )

            CarouselMediaItem(
                modifier = Modifier.fillMaxWidth(),
                state.trendingMediaItemList,
                onItemClicked = {

                }
            )
        }
    }
}

@Composable
fun <T : MenuSelectorItem> TitleAndFilter(
    title: String = "",
    filterSet: Set<T> = emptySet(),
    indexSelected: Int = 0,
    filterClicked: ((item: T, index: Int) -> Unit)? = null
) {
    Box(
        modifier = Modifier.padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = title,
            fontWeight = FontWeight.SemiBold,
            fontSize = 22.sp,
            modifier = Modifier.align(Alignment.CenterStart)
        )
        MenuSelector(
            menuItemSet = filterSet,
            indexSelected = indexSelected,
            onItemClick = { item, index ->
                filterClicked?.invoke(item, index)
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DiscoverScreenPreview() {

    Column {

        Text(
            text = "Hola estoy sola",
            modifier = Modifier.width(60.dp),
            maxLines = 1,
            overflow = TextOverflow.Visible
        )

        ConstraintLayout(modifier = Modifier.fillMaxSize()) {

            val (mainRef, refTest) = createRefs()

            Text(
                text = "On Tv",
                modifier = Modifier.constrainAs(mainRef) {
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                }
            )

            Text(
                text = "S hola",
                modifier = Modifier
                    .constrainAs(
                        refTest
                    ) {
                        top.linkTo(mainRef.bottom)
                        start.linkTo(mainRef.start)
                        end.linkTo(parent.end)
                        width = Dimension.fillToConstraints
                    },
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }

}

