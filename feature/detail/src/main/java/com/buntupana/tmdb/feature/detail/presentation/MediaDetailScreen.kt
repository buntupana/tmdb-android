package com.buntupana.tmdb.feature.detail.presentation

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.drawable.toBitmap
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.palette.graphics.Palette
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.buntupana.tmdb.core.presentation.CertificationText
import com.buntupana.tmdb.core.presentation.HoursMinutesText
import com.buntupana.tmdb.core.presentation.NestedVerticalLazyGrid
import com.buntupana.tmdb.core.presentation.UserScore
import com.buntupana.tmdb.core.presentation.theme.Dimens
import com.buntupana.tmdb.core.presentation.util.getBinaryForegroundColor
import com.buntupana.tmdb.feature.detail.R
import com.buntupana.tmdb.feature.detail.domain.model.CastItem
import com.buntupana.tmdb.feature.detail.domain.model.MediaDetails
import com.google.accompanist.flowlayout.FlowCrossAxisAlignment
import com.google.accompanist.flowlayout.FlowMainAxisAlignment
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.flowlayout.SizeMode
import com.ramcosta.composedestinations.annotation.Destination

@Destination(
    navArgsDelegate = MediaDetailNavArgs::class
)
@Composable
fun MediaDetailScreen(
    viewModel: MediaDetailViewModel = hiltViewModel(),
    detailNavigator: DetailNavigator
) {

    MediaDetailContent(
        mediaDetails = viewModel.state.mediaDetails
    )
}

@Composable
fun MediaDetailContent(
    mediaDetails: MediaDetails?
) {

    val scrollState = rememberScrollState()

    var backgroundColor by remember {
        mutableStateOf(Color.White)
    }
    var textColor by remember {
        mutableStateOf(Color.Black)
    }

    if (mediaDetails != null) {
        Column(
            Modifier.verticalScroll(scrollState)
        ) {

            Column(
                Modifier.background(backgroundColor)
            ) {

                Header(
                    mediaDetails,
                    backgroundColor
                ) { palette ->
                    palette.dominantSwatch?.rgb?.let { dominantColor ->
                        if (Color(dominantColor) != backgroundColor) {
                            backgroundColor = Color(dominantColor)
                            textColor = backgroundColor.getBinaryForegroundColor()
                        }
                    }
                }

                MainInfo(
                    mediaDetails = mediaDetails,
                    backgroundColor = backgroundColor,
                    textColor = textColor
                )
            }

            CastHorizontalList(mediaDetails = mediaDetails)
        }
    }
}

@Composable
fun Header(
    mediaDetails: MediaDetails,
    backgroundColor: Color,
    setPalette: (palette: Palette) -> Unit
) {

    Box(
        Modifier
            .fillMaxWidth()
            .height(200.dp),
    ) {

        Row(modifier = Modifier.fillMaxSize()) {
            Image(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(70.dp),
                painter = ColorPainter(backgroundColor),
                contentDescription = null
            )

            Box {

                AsyncImage(
                    modifier = Modifier.fillMaxSize(),
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(mediaDetails.backdropUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = "",
                    contentScale = ContentScale.Crop
                )
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(80.dp)
                        .background(
                            brush = Brush.horizontalGradient(
                                colors = listOf(backgroundColor, Color.Transparent)
                            )
                        ),
                )
            }
        }

        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Spacer(modifier = Modifier.width(Dimens.padding.medium))
            AsyncImage(
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxHeight(0.8f)
                    .clip(RoundedCornerShape(Dimens.posterRound))
                    .aspectRatio(2f / 3f),
                alignment = Alignment.CenterStart,
                model = ImageRequest.Builder(LocalContext.current)
                    .data(mediaDetails.posterUrl)
                    .crossfade(true)
                    .allowHardware(false)
                    .listener { request, result ->
                        Palette.Builder(result.drawable.toBitmap()).generate { palette ->
                            palette?.let {
                                setPalette(it)
                            }
                        }
                    }
                    .build(),
                contentDescription = null
            )
        }
    }
}

@Composable
fun MainInfo(
    mediaDetails: MediaDetails,
    backgroundColor: Color,
    textColor: Color
) {

    Column(
        modifier = Modifier
            .background(backgroundColor)
    ) {

        // Title and release year
        FlowRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = Dimens.padding.medium,
                    bottom = Dimens.padding.small,
                    start = Dimens.padding.medium,
                    end = Dimens.padding.medium
                ),
            mainAxisAlignment = FlowMainAxisAlignment.Center,
            crossAxisAlignment = FlowCrossAxisAlignment.Center,
            mainAxisSize = SizeMode.Expand
        ) {
            Text(
                text = mediaDetails.title,
                color = textColor,
                fontWeight = FontWeight(600),
                fontSize = 24.sp,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                modifier = Modifier.alpha(0.7f),
                text = "(${mediaDetails.releaseDate.year})",
                color = textColor,
                fontWeight = FontWeight(400)
            )
        }

        // User score and trailer
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = Dimens.padding.small),
            verticalAlignment = Alignment.CenterVertically,
        ) {

            Row(
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = Dimens.padding.small, horizontal = Dimens.padding.medium),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                UserScore(
                    modifier = Modifier.size(50.dp),
                    score = mediaDetails.userScore
                )
                Spacer(modifier = Modifier.width(Dimens.padding.small))
                Text(
                    text = "User Score",
                    color = textColor,
                    fontWeight = FontWeight(700)
                )
            }
            Image(
                modifier = Modifier
                    .size(height = 34.dp, width = 1.dp)
                    .alpha(0.5f),
                painter = ColorPainter(textColor),
                contentDescription = null
            )
            Row(
                modifier = Modifier
                    .weight(1f)
                    .padding(Dimens.padding.small)
                    .clickable {

                    },
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Image(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(id = R.drawable.ic_play),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(textColor)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    modifier = Modifier.align(Alignment.CenterVertically),
                    text = stringResource(R.string.text_play_trailer),
                    color = textColor,
                    fontWeight = FontWeight(400),
                )
            }
        }

        // Certification, duration and genres
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0x1A000000))
                .border(BorderStroke(1.dp, Color(0x40000000)))
                .padding(vertical = Dimens.padding.small, horizontal = Dimens.padding.medium),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {

                CertificationText(
                    text = mediaDetails.ageCertification,
                    color = textColor
                )

                Spacer(Modifier.width(Dimens.padding.small))
                if (mediaDetails is MediaDetails.Movie) {
                    Text(
                        text = "${mediaDetails.localReleaseDate.orEmpty()} (${mediaDetails.localCountryCodeRelease})",
                        color = textColor
                    )
                    Spacer(Modifier.width(Dimens.padding.small))
                }
                Canvas(modifier = Modifier.size(4.dp), onDraw = {
                    drawCircle(color = textColor)
                })

                Spacer(Modifier.width(Dimens.padding.small))
                HoursMinutesText(
                    time = mediaDetails.runTime,
                    color = textColor
                )
            }
            Text(
                text = mediaDetails.genreList.joinToString(", "),
                color = textColor
            )
        }

        // Tagline and overview
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Dimens.padding.medium, vertical = Dimens.padding.medium)
        ) {

            if (mediaDetails.tagLine.isNotBlank()) {
                Text(
                    modifier = Modifier.alpha(0.7f),
                    text = mediaDetails.tagLine,
                    color = textColor,
                    fontStyle = FontStyle.Italic,
                )
                Spacer(modifier = Modifier.height(Dimens.padding.small))
            }
            if (mediaDetails.overview.isNotBlank()) {
                Text(
                    text = stringResource(id = R.string.text_overview),
                    color = textColor,
                    fontWeight = FontWeight(600),
                    fontSize = 18.sp
                )
                Spacer(modifier = Modifier.height(Dimens.padding.small))
                Text(
                    text = mediaDetails.overview,
                    color = textColor
                )
            }

            if (mediaDetails.creatorList.isNotEmpty()) {

                Spacer(modifier = Modifier.height(Dimens.padding.medium))

                NestedVerticalLazyGrid(
                    modifier = Modifier
                        .fillMaxWidth(),
                    columns = 2,
                    itemList = mediaDetails.creatorList
                ) { item ->
                    Column(
                        modifier = Modifier.padding(vertical = Dimens.padding.small)
                    ) {
                        Text(
                            text = item.name,
                            color = textColor,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = item.job.ifBlank { stringResource(R.string.text_creator) },
                            color = textColor
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CastHorizontalList(
    mediaDetails: MediaDetails
) {

    val castNumber = 9

    if (mediaDetails.castList.isNotEmpty()) {

        Spacer(modifier = Modifier.height(16.dp))

        val castTitle = when (mediaDetails) {
            is MediaDetails.Movie -> stringResource(id = R.string.text_cast_movie)
            is MediaDetails.TvShow -> stringResource(id = R.string.text_cast_tv_show)
        }

        Text(
            modifier = Modifier.padding(horizontal = Dimens.padding.medium),
            text = castTitle,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyRow(
            modifier = Modifier.fillMaxWidth()
        ) {
            item {
                Spacer(modifier = Modifier.width(Dimens.padding.small))
            }
            items(mediaDetails.castList.take(castNumber)) { item: CastItem ->
                Spacer(modifier = Modifier.width(Dimens.padding.small))
                PersonItemVertical(
                    name = item.name,
                    profileUrl = item.profileUrl,
                    character = item.character
                )
                Spacer(modifier = Modifier.width(Dimens.padding.small))
            }
            item {
                Spacer(modifier = Modifier.width(Dimens.padding.small))
            }
        }

        Spacer(modifier = Modifier.padding(Dimens.padding.medium))

//        Text(
//            modifier = Modifier.padding(horizontal = Dimens.padding.medium),
//            text = stringResource(id = R.string.text_full_cast),
//            fontSize = 20.sp,
//            fontWeight = FontWeight.Bold,
//        )
//
//        Spacer(modifier = Modifier.height(16.dp))
    }
}

//@Preview
//@Composable
//fun MediaDetailScreenPreview() {
//
//    val mediaDetails = MediaDetailsFull.MovieDetails(
//        0L,
//        "Thor: Love and Thunder",
//        "",
//        "",
//        "After his retirement is interrupted by Gorr the God Butcher, a galactic killer who seeks the extinction of the gods, Thor enlists the help of King Valkyrie, Korg, and ex-girlfriend Jane Foster, who now inexplicably wields Mjolnir as the Mighty Thor. Together they embark upon a harrowing cosmic adventure to uncover the mystery of the God Butcher’s vengeance and stop him before it’s too late.",
//        "The one is not the only.",
//        LocalDate.now(),
//        67,
//        120,
//        listOf("Action", "Adventure", "Fantasy"),
//    )
//
//    MediaDetailContent(mediaDetails = mediaDetails)
//}