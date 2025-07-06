package com.buntupana.tmdb.feature.detail.presentation.person.comp

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.buntupana.tmdb.core.ui.composables.ImageFromUrl
import com.buntupana.tmdb.core.ui.theme.Dimens
import com.buntupana.tmdb.core.ui.util.balanced
import com.buntupana.tmdb.feature.detail.domain.model.PersonFullDetails
import com.buntupana.tmdb.feature.detail.presentation.personDetailsSample
import com.panabuntu.tmdb.core.common.util.isNotNullOrBlank

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
        if (personDetails.profileUrl.isNotNullOrBlank()) {
            ImageFromUrl(
                modifier = Modifier
                    .size(Dimens.imageSize.personHeightBig)
                    .clip(RoundedCornerShape(Dimens.posterRound)),
                imageUrl = personDetails.profileUrl
            )
        }
        Spacer(modifier = Modifier.height(Dimens.padding.medium))
        Text(
            text = personDetails.name,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineLarge.balanced()
        )
        if (personDetails.externalLinks.isNotEmpty()) {

            Spacer(modifier = Modifier.height(Dimens.padding.small))

            ExternalLinksRow(
                externalLinkList = personDetails.externalLinks
            )
        }
    }
}

@Preview
@Composable
fun HeaderContentPreview() {
    HeaderContent(personDetailsSample)
}