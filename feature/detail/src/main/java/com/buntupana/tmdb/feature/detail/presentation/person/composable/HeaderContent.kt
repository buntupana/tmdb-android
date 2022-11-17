package com.buntupana.tmdb.feature.detail.presentation.person.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.buntupana.tmdb.core.presentation.composables.ImageFromUrl
import com.buntupana.tmdb.core.presentation.theme.Dimens
import com.buntupana.tmdb.feature.detail.R
import com.buntupana.tmdb.feature.detail.domain.model.ExternalLink
import com.buntupana.tmdb.feature.detail.domain.model.PersonFullDetails

@Composable
fun HeaderContent(
    personDetails: PersonFullDetails
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(Dimens.padding.medium),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (personDetails.profileUrl.isNotBlank()) {
            ImageFromUrl(
                modifier = Modifier
                    .size(160.dp)
                    .clip(RoundedCornerShape(Dimens.posterRound)),
                imageUrl = personDetails.profileUrl
            )
        }
        Spacer(modifier = Modifier.height(Dimens.padding.medium))
        Text(
            text = personDetails.name,
            fontWeight = FontWeight.Bold,
            fontSize = 32.sp,
            textAlign = TextAlign.Center
        )
        if (personDetails.externalLinks.isNotEmpty()) {

            Spacer(modifier = Modifier.height(Dimens.padding.small))

            Row {
                val uriHandler = LocalUriHandler.current
                personDetails.externalLinks.forEachIndexed { index, externalLink ->

                    val iconResId = when (externalLink) {
                        is ExternalLink.FacebookLink -> R.drawable.ic_facebook
                        is ExternalLink.HomePage -> com.buntupana.tmdb.core.R.drawable.ic_link
                        is ExternalLink.ImdbLink -> com.buntupana.tmdb.core.R.drawable.ic_link
                        is ExternalLink.InstagramLink -> R.drawable.ic_instagram
                        is ExternalLink.TwitterLink -> R.drawable.ic_twitter
                    }

                    Image(
                        modifier = Modifier
                            .size(32.dp)
                            .clickable {
                                uriHandler.openUri(externalLink.link)
                            },
                        painter = painterResource(id = iconResId),
                        contentDescription = null,
                    )

                    if (index < personDetails.externalLinks.size - 1) {
                        Spacer(modifier = Modifier.width(Dimens.padding.small))
                    }
                }
            }
        }
    }
}