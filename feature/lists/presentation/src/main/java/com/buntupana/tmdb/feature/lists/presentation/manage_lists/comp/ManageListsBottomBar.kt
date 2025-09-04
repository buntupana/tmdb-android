package com.buntupana.tmdb.feature.lists.presentation.manage_lists.comp

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.buntupana.tmdb.core.ui.theme.Dimens
import com.buntupana.tmdb.core.ui.util.TextButton
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

    val backgroundButtonColor = if(backgroundColor.getOnBackgroundColor() == Color.White) {
        MaterialTheme.colorScheme.secondary
    } else {
        MaterialTheme.colorScheme.secondary
    }

    Row(
        modifier = modifier
            .background(backgroundColor)
            .padding(horizontal = Dimens.padding.horizontal)
            .padding(vertical = Dimens.padding.small)
            .padding(bottom = bottomPadding),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        TextButton(
            onClick = onCreateListClick,
            rippleColor = backgroundColor.getOnBackgroundColor()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.text_create_list),
                    color = backgroundColor.getOnBackgroundColor()
                )
                Icon(
                    modifier = Modifier.padding(start = Dimens.padding.small),
                    imageVector = Icons.Rounded.Add,
                    contentDescription = null,
                    tint = backgroundColor.getOnBackgroundColor()
                )
            }
        }
        Button(
            border = BorderStroke(2.dp, backgroundColor.getOnBackgroundColor()),
            colors = ButtonDefaults.buttonColors(containerColor = backgroundButtonColor),
            onClick = onConfirmClick,
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(RCore.string.text_apply_changes),
                    color = backgroundButtonColor.getOnBackgroundColor()
                )
                Icon(
                    modifier = Modifier.padding(start = Dimens.padding.small),
                    imageVector = Icons.Rounded.Check,
                    contentDescription = null
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ManageListsBottomBarPreview() {
    ManageListsBottomBar(
        modifier = Modifier.fillMaxWidth(),
        bottomPadding = 0.dp,
        backgroundColor = MaterialTheme.colorScheme.surfaceDim,
        onCreateListClick = {},
        onConfirmClick = {}
    )
}