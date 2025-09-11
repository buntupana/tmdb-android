package com.buntupana.tmdb.feature.lists.presentation.manage_lists.comp

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.buntupana.tmdb.core.ui.composables.widget.AppTextWithIconButton
import com.buntupana.tmdb.core.ui.theme.AppTheme
import com.buntupana.tmdb.core.ui.theme.Dimens
import com.buntupana.tmdb.core.ui.util.getOnBackgroundColor
import com.buntupana.tmdb.feature.presentation.R
import com.buntupana.tmdb.core.ui.R as RCore

@Composable
fun ManageListsBottomBar(
    modifier: Modifier = Modifier,
    bottomPadding: Dp,
    backgroundColor: Color,
    onCreateListClick: () -> Unit,
    onConfirmClick: () -> Unit,
) {

    Row(
        modifier = modifier
            .background(backgroundColor)
            .padding(horizontal = Dimens.padding.horizontal)
            .padding(vertical = Dimens.padding.small)
            .padding(bottom = bottomPadding),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        AppTextWithIconButton(
            colors = ButtonDefaults.buttonColors(containerColor = backgroundColor, contentColor = backgroundColor.getOnBackgroundColor()),
            onClick = onCreateListClick,
            border = BorderStroke(ButtonDefaults.outlinedButtonBorder(true).width, backgroundColor.getOnBackgroundColor()),
            text = stringResource(R.string.text_create_list),
            imageVector = Icons.Rounded.Add,
        )

        AppTextWithIconButton(
            border = BorderStroke(2.dp, backgroundColor.getOnBackgroundColor()),
            onClick = onConfirmClick,
            imageVector = Icons.Rounded.Check,
            text = stringResource(RCore.string.text_confirm)
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
private fun ManageListsBottomBarPreview() {
    AppTheme {
        ManageListsBottomBar(
            modifier = Modifier.fillMaxWidth(),
            bottomPadding = 0.dp,
            backgroundColor = MaterialTheme.colorScheme.surfaceDim,
            onCreateListClick = {},
            onConfirmClick = {}
        )
    }
}