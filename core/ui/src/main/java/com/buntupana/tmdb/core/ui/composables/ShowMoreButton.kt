package com.buntupana.tmdb.core.ui.composables

import android.content.res.Configuration
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.buntupana.tmdb.core.ui.R
import com.buntupana.tmdb.core.ui.composables.widget.AppTextButton
import com.buntupana.tmdb.core.ui.theme.AppTheme
import com.buntupana.tmdb.core.ui.theme.Dimens

@Composable
fun ShowMoreButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    AppTextButton(
        modifier = modifier,
        onClick = onClick,
        rippleColor = MaterialTheme.colorScheme.onBackground
    ) {
        Text(
            text = stringResource(R.string.text_show_more),
            color = MaterialTheme.colorScheme.onBackground
        )

        Icon(
            modifier = Modifier.padding(start = Dimens.padding.tiny),
            imageVector = Icons.AutoMirrored.Rounded.ArrowForward,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onBackground
        )
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
private fun ShowMoreButtonPreview() {
    AppTheme {
        ShowMoreButton(
            onClick = {}
        )
    }
}