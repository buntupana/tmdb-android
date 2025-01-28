package com.buntupana.tmdb.feature.detail.presentation.person.comp

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.buntupana.tmdb.core.ui.theme.Dimens
import com.buntupana.tmdb.feature.detail.domain.model.ExternalLink
import com.buntupana.tmdb.feature.detail.presentation.R
import com.buntupana.tmdb.core.ui.R as RCore

@Composable
fun ExternalLinksRow(
    modifier: Modifier = Modifier,
    externalLinkList: List<ExternalLink>
) {
    Row(
        modifier = modifier,
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

            Icon(
                modifier = Modifier
                    .size(32.dp)
                    .clickable { uriHandler.openUri(externalLink.link) },
                painter = painterResource(id = iconResId),
                contentDescription = null,
            )

            if (index < externalLinkList.size - 1) {
                Spacer(modifier = Modifier.width(Dimens.padding.small))
            }
        }

        externalLinkList
            .filterIsInstance<ExternalLink.HomePage>()
            .forEachIndexed { index, externalLink ->

                if (index == 0) {
                    VerticalDivider(
                        modifier = Modifier
                            .height(32.dp)
                            .padding(horizontal = Dimens.padding.small)
                    )
                }

                Icon(
                    modifier = Modifier
                        .size(32.dp)
                        .clickable { uriHandler.openUri(externalLink.link) },
                    painter = painterResource(id = RCore.drawable.ic_link),
                    contentDescription = null,
                )

                if (index < externalLinkList.size - 1) {
                    Spacer(modifier = Modifier.width(Dimens.padding.small))
                }
            }
    }
}

@Preview(showBackground = true)
@Composable
private fun ExternalLinksRowPreview() {
    ExternalLinksRow(
        modifier = Modifier,
        externalLinkList = listOf(
            ExternalLink.HomePage(""),
            ExternalLink.XLink(""),
            ExternalLink.FacebookLink(""),
            ExternalLink.InstagramLink(""),
            ExternalLink.TiktokLink(""),
            ExternalLink.ImdbLink("")
        )
    )
}