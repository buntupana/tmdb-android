package com.buntupana.tmdb.feature.discover.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import com.buntupana.tmdb.core.presentation.brush
import com.buntupana.tmdb.core.presentation.theme.PrimaryDark
import com.buntupana.tmdb.core.presentation.theme.TertiaryDark
import com.buntupana.tmdb.core.presentation.theme.TertiaryLight
import com.buntupana.tmdb.feature.discover.R
import com.ramcosta.composedestinations.annotation.Destination

@Destination
@Composable
fun DiscoverScreen(
    viewModel: DiscoverViewModel = hiltViewModel(),
    discoverNavigator: DiscoverNavigator
) {

    val state = viewModel.state

    val popularFilterList by remember {
        mutableStateOf(
            listOf(
                PopularFilter.OnTv(),
                PopularFilter.Streaming(),
                PopularFilter.ForRent(),
                PopularFilter.InTheatres()
            )
        )
    }

    Column(modifier = Modifier.fillMaxSize()) {
        TitleAndFilter<PopularFilter>(
            stringResource(id = R.string.text_whats_popular),
            popularFilterList
        )
        CarouselMediaItem(
            modifier = Modifier.fillMaxWidth(),
            state.popularMediaItemList,
            onItemClicked = {

            }
        )
        Spacer(modifier = Modifier.height(24.dp))
        Selector(popularFilterList, 0)
    }
}

sealed class PopularFilter : DropMenuItem {

    class Streaming(
        override val strRes: Int = R.string.text_streaming
    ) : PopularFilter()

    class OnTv(
        override val strRes: Int = R.string.text_on_tv
    ) : PopularFilter()

    class ForRent(
        override val strRes: Int = R.string.text_for_rent
    ) : PopularFilter()

    class InTheatres(
        override val strRes: Int = R.string.text_in_theatres
    ) : PopularFilter()
}

interface DropMenuItem {
    val strRes: Int
}

@Composable
fun <T : DropMenuItem> TitleAndFilter(
    title: String = "",
    filterList: List<T> = emptyList()
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

        Box {

            var dropMenuExpanded by remember {
                mutableStateOf(false)
            }

//            filterList.firstOrNull { it.selected }?.let {
//                SelectedText(
//                    text = stringResource(id = it.strRes),
//                    modifier = Modifier.clickable {
//                        dropMenuExpanded = true
//                    }
//                )
//            }
//
//            DropdownMenu(
//                modifier = Modifier
//                    .clip(RoundedCornerShape(15.dp))
//                    .background(Brush.horizontalGradient(listOf(TertiaryLight, TertiaryDark))),
//                expanded = dropMenuExpanded,
//                onDismissRequest = {
//                    dropMenuExpanded = false
//                },
//                offset = DpOffset(0.dp, (-38).dp)
//            ) {
//
//                filterList.forEach { filter ->
//
//                    if (filter.selected) {
//                        SelectedText(text = stringResource(id = filter.strRes))
//                    } else {
//                        Text(
//                            text = stringResource(id = filter.strRes),
//                            color = PrimaryDark,
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .padding(horizontal = 16.dp, vertical = 4.dp)
//                                .clickable {
//
//                                }
//                        )
//                    }
//                }
//            }
        }
    }
}

@Composable
fun SelectedText(
    modifier: Modifier = Modifier,
    text: String = "",
    onClick: (() -> Unit)? = null
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(15.dp))
            .background(PrimaryDark)
            .padding(horizontal = 16.dp, vertical = 6.dp)
            .clickable {
                onClick?.invoke()
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text,
            modifier = Modifier
                .brush(Brush.horizontalGradient(listOf(TertiaryLight, TertiaryDark))),
            fontWeight = FontWeight.SemiBold,
            maxLines = 1
        )
//        Spacer(modifier = Modifier.width(8.dp))
//        Icon(
//            modifier = Modifier
//                .rotate(-90f)
//                .size(14.dp)
//                .brush(Brush.horizontalGradient(listOf(TertiaryLight, TertiaryDark))),
//            painter = painterResource(id = R.drawable.ic_arrow_down),
//            contentDescription = null
//        )
    }
}


@Preview(showBackground = true)
@Composable
fun DiscoverScreenPreview() {
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {

        val (mainRef, refTest) = createRefs()

        Text(
            text = "On Tv",
            modifier = Modifier.constrainAs(mainRef){
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
        )
    }
}

