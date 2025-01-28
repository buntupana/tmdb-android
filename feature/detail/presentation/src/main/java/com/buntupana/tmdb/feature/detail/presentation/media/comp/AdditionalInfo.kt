package com.buntupana.tmdb.feature.detail.presentation.media.comp

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import com.buntupana.tmdb.core.ui.theme.Dimens
import com.buntupana.tmdb.feature.detail.domain.model.MediaDetails
import com.buntupana.tmdb.feature.detail.presentation.R
import com.buntupana.tmdb.feature.detail.presentation.mediaDetailsTvShowSample
import com.buntupana.tmdb.feature.detail.presentation.person.comp.ExternalLinksRow
import com.panabuntu.tmdb.core.common.util.toLocalizedString

@Composable
fun AdditionalInfo(
    modifier: Modifier = Modifier,
    mediaDetails: MediaDetails
) {

    Column(modifier = modifier) {

        ExternalLinksRow(
            externalLinkList = mediaDetails.externalLinkList
        )

        if (mediaDetails.externalLinkList.isNotEmpty()) {
            Spacer(modifier = Modifier.padding(vertical = Dimens.padding.medium))
        }

        val verticalItemPadding = Dimens.padding.small

        when (mediaDetails) {
            is MediaDetails.Movie -> MovieAdditionalInfo(
                movieDetails = mediaDetails,
                verticalItemPadding = verticalItemPadding
            )

            is MediaDetails.TvShow -> TvShowAdditionalInfo(
                tvShowDetails = mediaDetails,
                verticalItemPadding = verticalItemPadding
            )
        }
    }
}

@Composable
private fun MovieAdditionalInfo(
    modifier: Modifier = Modifier,
    movieDetails: MediaDetails.Movie,
    verticalItemPadding: Dp
) {
    Column(
        modifier = modifier
    ) {
        AdditionalInfoItem(
            title = stringResource(R.string.text_status),
            value = movieDetails.status
        )

        Spacer(modifier = Modifier.padding(vertical = verticalItemPadding))

        AdditionalInfoItem(
            title = stringResource(R.string.text_original_language),
            value = movieDetails.originalLanguage
        )

        Spacer(modifier = Modifier.padding(vertical = verticalItemPadding))

        AdditionalInfoItem(
            title = stringResource(R.string.text_budget),
            value = if (movieDetails.budget == 0) " - " else "$${movieDetails.budget.toLocalizedString()}"
        )

        Spacer(modifier = Modifier.padding(vertical = verticalItemPadding))

        AdditionalInfoItem(
            title = stringResource(R.string.text_revenue),
            value = if (movieDetails.revenue == 0) " - " else "$${movieDetails.revenue.toLocalizedString()}"
        )
    }
}

@Composable
private fun TvShowAdditionalInfo(
    modifier: Modifier = Modifier,
    tvShowDetails: MediaDetails.TvShow,
    verticalItemPadding: Dp
) {
    Column(
        modifier = modifier
    ) {
        AdditionalInfoItem(
            title = stringResource(R.string.text_status),
            value = tvShowDetails.status
        )

        Spacer(modifier = Modifier.padding(vertical = verticalItemPadding))

        AdditionalInfoItem(
            title = stringResource(R.string.text_type),
            value = tvShowDetails.type
        )

        Spacer(modifier = Modifier.padding(vertical = verticalItemPadding))

        AdditionalInfoItem(
            title = stringResource(R.string.text_original_language),
            value = tvShowDetails.originalLanguage
        )
    }
}

@Composable
private fun AdditionalInfoItem(
    title: String,
    value: String?
) {

    Text(
        text = title,
        fontWeight = FontWeight.Bold
    )
    Text(
        modifier = Modifier.padding(top = Dimens.padding.tiny),
        text = value ?: " - "
    )
}

@Preview(showBackground = true)
@Composable
private fun AdditionalInfoPreview() {
    AdditionalInfo(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = Dimens.padding.vertical, horizontal = Dimens.padding.vertical),
        mediaDetails = mediaDetailsTvShowSample
    )
}