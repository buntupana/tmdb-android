package com.buntupana.tmdb.feature.discover.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.ramcosta.composedestinations.annotation.Destination
import timber.log.Timber

@Destination
@Composable
fun DiscoverScreen(
    viewModel: DiscoverViewModel = hiltViewModel(),
    discoverNavigator: DiscoverNavigator
) {

    val state = viewModel.state

    Column(modifier = Modifier.fillMaxSize()) {
        TitleAndFilter(
            stringResource(id = R.string.text_whats_popular),
            viewModel.popularFilterSet
        )
        CarouselMediaItem(
            modifier = Modifier.fillMaxWidth(),
            state.popularMediaItemList,
            onItemClicked = {

            }
        )
        Spacer(modifier = Modifier.height(24.dp))
        MenuSelector(
            menuItemSet = viewModel.popularFilterSet,
            indexSelected = viewModel.popularFilterSelected,
            onItemClick = { item, index ->
                viewModel.popularFilterSelected = index
                Timber.d("menu clicked = $item")
            }
        )
    }
}

@Composable
fun <T : MenuSelectorItem> TitleAndFilter(
    title: String = "",
    filterSet: Set<T> = emptySet()
) {
    Row(
        modifier = Modifier.padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            fontWeight = FontWeight.SemiBold,
            fontSize = 22.sp,
            modifier = Modifier.weight(1f)
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

