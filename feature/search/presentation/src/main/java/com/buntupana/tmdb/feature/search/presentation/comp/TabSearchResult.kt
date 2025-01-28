package com.buntupana.tmdb.feature.search.presentation.comp

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.buntupana.tmdb.core.ui.composables.OutlinedText
import com.buntupana.tmdb.core.ui.theme.SecondaryColor

@Composable
fun TabSearchResult(
    @StringRes titleResId: Int,
    resultCount: Int,
    isSelected: Boolean
) {

    val textColor: Color
    val outLineColor: Color
    val fontWeight: FontWeight

    if (isSelected) {
        textColor = SecondaryColor
        outLineColor = SecondaryColor
        fontWeight = FontWeight.Bold
    } else {
        textColor = Color.Black
        outLineColor = Color.Gray
        fontWeight = FontWeight.Normal
    }

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(id = titleResId),
            fontWeight = fontWeight,
            color = textColor
        )
        Spacer(modifier = Modifier.width(4.dp))
        OutlinedText(
            modifier = Modifier.alpha(0.6f),
            text = resultCount.toString(),
            cornerRound = 6.dp,
            internalHorizontalPadding = 8.dp,
            outlineColor = outLineColor
        )
    }
}


@Preview
@Composable
private fun TabSearchResultPreview() {
    TabSearchResult(
        titleResId = com.buntupana.tmdb.core.ui.R.string.text_movies,
        resultCount = 3,
        isSelected = true
    )
}