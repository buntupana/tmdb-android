package com.buntupana.tmdb.feature.discover.presentation.comp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.buntupana.tmdb.core.R
import com.buntupana.tmdb.core.presentation.theme.Dimens
import com.buntupana.tmdb.core.presentation.theme.PrimaryColor
import com.buntupana.tmdb.core.presentation.theme.SecondaryColor
import com.buntupana.tmdb.core.presentation.util.clickableIcon

@Composable
fun TopBar(
    clickOnSearch: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(Dimens.topBarHeight)
            .background(PrimaryColor),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Image(
            modifier = Modifier
                .padding(vertical = Dimens.padding.small, horizontal = Dimens.padding.medium),
            painter = painterResource(id = R.drawable.ic_logo_short),
            contentDescription = null
        )

        Image(
            modifier = Modifier
                .padding(horizontal = Dimens.padding.small)
                .clickable { clickOnSearch() }
                .clickableIcon(),
            painter = painterResource(id = R.drawable.ic_search),
            contentDescription = null,
            alignment = Alignment.CenterEnd,
            colorFilter = ColorFilter.tint(SecondaryColor)
        )
    }
}

@Preview
@Composable
private fun TopBarPreview() {
    TopBar {

    }
}