package com.buntupana.tmdb.feature.detail.presentation.person.comp

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.buntupana.tmdb.core.ui.theme.Dimens
import com.buntupana.tmdb.feature.detail.domain.model.ExternalLink
import com.buntupana.tmdb.feature.detail.presentation.R
import com.buntupana.tmdb.feature.detail.presentation.personDetailsSample
import com.buntupana.tmdb.core.ui.R as RCore

@Composable
fun ExternalLinksRow(
    modifier: Modifier = Modifier,
    iconSize: Dp = 40.dp,
    externalLinkList: List<ExternalLink>
) {
    Row(
        modifier = modifier.height(iconSize),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val uriHandler = LocalUriHandler.current
        externalLinkList.forEachIndexed { index, externalLink ->

            val iconResId = when (externalLink) {
                is ExternalLink.HomePage -> return@forEachIndexed
                is ExternalLink.FacebookLink -> R.drawable.ic_facebook
                is ExternalLink.ImdbLink -> R.drawable.ic_imdb
                is ExternalLink.InstagramLink -> R.drawable.ic_instagram
                is ExternalLink.XLink -> R.drawable.ic_x
                is ExternalLink.TiktokLink -> R.drawable.ic_tiktok
            }

            IconButton(
                onClick = { uriHandler.openUri(externalLink.link) }
            ) {

                Icon(
                    modifier = Modifier
                        .size(iconSize)
                        .then(if (iconResId == R.drawable.ic_x) Modifier.padding(4.dp) else Modifier),
                    painter = painterResource(id = iconResId),
                    contentDescription = null,
                )
            }
        }

        externalLinkList
            .filterIsInstance<ExternalLink.HomePage>()
            .forEachIndexed { index, externalLink ->

                if (index == 0 && externalLinkList.any { it !is ExternalLink.HomePage }) {
                    VerticalDivider(
                        modifier = Modifier
                            .padding(
                                horizontal = Dimens.padding.small,
                                vertical = Dimens.padding.tiny
                            )
                    )
                }
                IconButton(
                    onClick = { uriHandler.openUri(externalLink.link) }
                ) {
                    Icon(
                        modifier = Modifier,
                        painter = painterResource(id = RCore.drawable.ic_link),
                        contentDescription = null,
                    )
                }
            }
    }
}

@Preview(showBackground = true)
@Composable
private fun ExternalLinksRowPreview() {
    ExternalLinksRow(
        externalLinkList = personDetailsSample.externalLinks
    )
}