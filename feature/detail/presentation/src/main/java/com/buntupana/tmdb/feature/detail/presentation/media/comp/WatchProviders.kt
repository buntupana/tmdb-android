package com.buntupana.tmdb.feature.detail.presentation.media.comp

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.util.fastForEachIndexed
import com.buntupana.tmdb.core.ui.composables.ImageFromUrl
import com.buntupana.tmdb.core.ui.theme.AppTheme
import com.buntupana.tmdb.core.ui.theme.Dimens
import com.buntupana.tmdb.core.ui.util.RippleColorContainer
import com.buntupana.tmdb.core.ui.util.getOnBackgroundColor
import com.buntupana.tmdb.feature.detail.domain.model.Providers
import com.buntupana.tmdb.feature.detail.presentation.R

@Composable
fun WatchProviders(
    modifier: Modifier = Modifier,
    backgroundColor: Color,
    providers: Providers?
) {

    if (providers == null || providers.logoUrlList.isEmpty()) return

    val uriHandler = LocalUriHandler.current

    RippleColorContainer(
        rippleColor = backgroundColor.getOnBackgroundColor()
    ) {

        Column(
            modifier = modifier
                .clickable { uriHandler.openUri(providers.justWatchLink) }
                .background(backgroundColor)
        ) {

            HorizontalDivider()

            Spacer(
                modifier = Modifier.height(Dimens.padding.medium)
            )

            Text(
                modifier = Modifier.padding(horizontal = Dimens.padding.horizontal),
                text = stringResource(R.string.detail_available_in),
                color = backgroundColor.getOnBackgroundColor(),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(Dimens.padding.medium)
            )

            FlowRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Dimens.padding.horizontal),
                verticalArrangement = Arrangement.spacedBy(
                    space = Dimens.padding.small,
                    alignment = Alignment.CenterVertically
                ),
            ) {
                providers.logoUrlList.fastForEachIndexed { index, logoUrl ->
                    if (index != 0) {
                        Spacer(modifier = Modifier.width(Dimens.padding.small))
                    }
                    ImageFromUrl(
                        modifier = Modifier
                            .size(Dimens.watchProviderIconSize)
                            .clip(RoundedCornerShape(Dimens.posterRound)),
                        imageUrl = logoUrl
                    )
                }
            }

            Spacer(
                modifier = Modifier.height(Dimens.padding.medium)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Dimens.padding.horizontal),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = stringResource(R.string.detail_powered_by),
                    color = backgroundColor.getOnBackgroundColor(),
                    fontSize = MaterialTheme.typography.bodySmall.fontSize
                )

                Spacer(
                    modifier = Modifier.width(Dimens.padding.small)
                )

                Image(
                    modifier = Modifier.height(Dimens.icon),
                    painter = painterResource(R.drawable.ic_justwatch),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(backgroundColor.getOnBackgroundColor())
                )
            }

            Spacer(
                modifier = Modifier.height(Dimens.padding.medium)
            )
        }
    }
}

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    name = "DefaultPreviewLight",
    showBackground = true,
)
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "DefaultPreviewDark",
    showBackground = true,
)
@Composable
private fun WatchProvidersPreview() {
    AppTheme {
        WatchProviders(
            modifier = Modifier.fillMaxWidth(),
            backgroundColor = MaterialTheme.colorScheme.background,
            providers = Providers(
                justWatchLink = "justWatchLink",
                logoUrlList = listOf("", "", "", "", "", "", "", "", "", "", "", "", "", "")
            )
        )
    }
}