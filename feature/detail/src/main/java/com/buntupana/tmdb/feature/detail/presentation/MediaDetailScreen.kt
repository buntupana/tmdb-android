package com.buntupana.tmdb.feature.detail.presentation

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.drawable.toBitmap
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.palette.graphics.Palette
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.buntupana.tmdb.core.presentation.CertificationText
import com.buntupana.tmdb.core.presentation.UserScore
import com.buntupana.tmdb.core.presentation.theme.Dimens
import com.buntupana.tmdb.core.presentation.util.getBinaryForegroundColor
import com.buntupana.tmdb.feature.detail.R
import com.buntupana.tmdb.feature.detail.domain.model.MediaDetails
import com.ramcosta.composedestinations.annotation.Destination

@Destination(
    navArgsDelegate = MediaDetailNavArgs::class
)
@Composable
fun MediaDetailScreen(
    viewModel: MediaDetailViewModel = hiltViewModel(),
    detailNavigator: DetailNavigator
) {

    val state = viewModel.state

    val scrollState = rememberScrollState()

    var backgroundColor by remember {
        mutableStateOf(Color.White)
    }
    var textColor by remember {
        mutableStateOf(Color.Black)
    }

    if (state.mediaDetails != null) {
        Column(
            Modifier.verticalScroll(scrollState)
        ) {

            Header(
                state.mediaDetails,
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
                mediaDetails = state.mediaDetails,
                backgroundColor = backgroundColor,
                textColor = textColor
            )
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

            Spacer(modifier = Modifier.width(16.dp))
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

    Column(modifier = Modifier.background(backgroundColor)) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = mediaDetails.title,
                color = textColor,
                fontWeight = FontWeight(600),
                fontSize = 20.sp
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = mediaDetails.releaseDate.year.toString(),
                color = textColor,
                fontWeight = FontWeight(400)
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Row(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                UserScore(
                    modifier = Modifier.size(50.dp),
                    score = mediaDetails.userScore
                )
                Spacer(modifier = Modifier.width(8.dp))
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
                    .padding(8.dp)
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
                    text = "Play Trailer",
                    color = textColor,
                    fontWeight = FontWeight(400),
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0x1A000000))
                .border(BorderStroke(1.dp, Color(0x40000000)))
                .padding(vertical = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {

                CertificationText(
                    text = "U",
                    color = textColor
                )
                Spacer(Modifier.width(6.dp))
                Text(
                    text = "07/10/2015 (FR)  *  1h 19m",
                    color = textColor
                )
            }
            Text(
                text = mediaDetails.genreList.joinToString(", "),
                color = textColor
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {

            Text(
                modifier = Modifier.alpha(0.7f),
                text = mediaDetails.tagLine,
                color = textColor,
                fontStyle = FontStyle.Italic,
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Overview",
                color = textColor,
                fontWeight = FontWeight(600),
                fontSize = 18.sp
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = mediaDetails.overview,
                color = textColor
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}