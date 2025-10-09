package com.buntupana.tmdb.core.ui.composables

import android.content.res.Configuration
import androidx.annotation.StringRes
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.buntupana.tmdb.core.ui.R
import com.buntupana.tmdb.core.ui.theme.AppTheme
import com.buntupana.tmdb.core.ui.theme.Dimens
import com.buntupana.tmdb.core.ui.util.getOnBackgroundColor

@Composable
fun TabItemCount(
    @StringRes titleResId: Int,
    resultCount: Int,
    isSelected: Boolean
) {

    val textColor: Color
    val outLineColor: Color
    val fontWeight: FontWeight

    if (isSelected) {
        textColor = MaterialTheme.colorScheme.secondaryContainer
        outLineColor = MaterialTheme.colorScheme.secondaryContainer
        fontWeight = FontWeight.Bold
    } else {
        textColor = MaterialTheme.colorScheme.primaryContainer.getOnBackgroundColor()
        outLineColor = MaterialTheme.colorScheme.outline
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
        VerticalNumberRoulette(
            modifier = Modifier
                .border(width = 1.dp, color = outLineColor, shape = RoundedCornerShape(6.dp))
                .padding(horizontal = Dimens.padding.small),
            value = resultCount,
            color = MaterialTheme.colorScheme.primaryContainer.getOnBackgroundColor()
        )
    }
}

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    name = "DefaultPreviewLight",
    showBackground = true
)
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "DefaultPreviewDark",
    showBackground = true
)
@Composable
private fun TabItemCountPreview() {
    AppTheme {
        TabItemCount(
            titleResId = R.string.common_movies,
            resultCount = 3,
            isSelected = true
        )
    }
}