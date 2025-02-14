package com.buntupana.tmdb.feature.lists.presentation.manage_lists.comp

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.buntupana.tmdb.core.ui.theme.SecondaryColor
import com.buntupana.tmdb.core.ui.util.getOnBackgroundColor
import com.buntupana.tmdb.feature.presentation.R

@Composable
fun ManageListsHeader(
    modifier: Modifier = Modifier,
    isLoading: Boolean,
    areListsNull: Boolean,
    onCancelClick: () -> Unit = {},
    onConfirmClick: () -> Unit = {}
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        IconButton(
            onClick = onCancelClick,
            enabled = isLoading.not()
        ) {
            AnimatedVisibility(
                visible = isLoading.not() || areListsNull,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                Icon(
                    imageVector = Icons.Rounded.Clear,
                    contentDescription = null,
                    tint = SecondaryColor
                )
            }
        }

        Text(
            modifier = Modifier.weight(1f),
            maxLines = 1,
            textAlign = TextAlign.Center,
            text = stringResource(R.string.text_manage_lists),
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        IconButton(
            onClick = onConfirmClick,
            enabled = isLoading.not()
        ) {
            AnimatedVisibility(
                visible = isLoading.not(),
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                Icon(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(SecondaryColor),
                    imageVector = Icons.Rounded.Check,
                    contentDescription = null,
                    tint = SecondaryColor.getOnBackgroundColor()
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ManageListsHeaderPreview() {
    ManageListsHeader(
        isLoading = false,
        areListsNull = false
    )
}