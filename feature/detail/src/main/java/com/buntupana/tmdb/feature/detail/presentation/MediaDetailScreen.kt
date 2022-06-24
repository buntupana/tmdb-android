package com.buntupana.tmdb.feature.detail.presentation

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.buntupana.tmdb.core.domain.entity.MediaType
import com.buntupana.tmdb.core.presentation.UserScore
import com.buntupana.tmdb.core.presentation.theme.Dimens
import com.buntupana.tmdb.feature.detail.R
import com.ramcosta.composedestinations.annotation.Destination

@Destination
@Composable
fun MediaDetailScreen(
    viewModel: DetailViewModel = hiltViewModel(),
    detailNavigator: DetailNavigator,
    id: Long,
    mediaType: MediaType
) {

    val scrollState = rememberScrollState()

    val backGroundColor = Color(0xFF35B3DD)
    val textColor = Color.White

    Column(
        Modifier.verticalScroll(scrollState)
    ) {

        Header(backGroundColor)

        Column(modifier = Modifier.background(backGroundColor)) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Hotel Transylvania 2",
                    color = textColor,
                    fontWeight = FontWeight(600)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "(2015)",
                    color = textColor,
                    fontWeight = FontWeight(400)
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Row(
                    modifier = Modifier.weight(1f),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    UserScore(
                        modifier = Modifier.size(50.dp),
                        score = 68
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
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    
                }
            }
        }
    }
}

@Composable
fun Header(
    backgroundColor: Color
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
                painter = ColorPainter(backgroundColor), contentDescription = null
            )

            Box() {

                AsyncImage(
                    modifier = Modifier.fillMaxSize(),
                    model = "https://www.themoviedb.org/t/p/w1000_and_h450_multi_faces/2OQYAP64ybQZBiOcjthMVFcQRE.jpg",
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
                model = "https://www.themoviedb.org/t/p/w1280/kKFgwQnR5q08UFsAvtoYyTIiHyj.jpg",
                contentDescription = null
            )
        }
    }
}