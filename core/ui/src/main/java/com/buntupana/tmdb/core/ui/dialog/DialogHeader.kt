package com.buntupana.tmdb.core.ui.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.buntupana.tmdb.core.ui.theme.Dimens
import com.buntupana.tmdb.core.ui.util.IconButton
import com.buntupana.tmdb.core.ui.util.getOnBackgroundColor

@Composable
fun DialogHeader(
    modifier: Modifier = Modifier,
    title: String,
    onCloseClick: (() -> Unit)? = null,
    onAcceptClick: (() -> Unit)? = null
) {


    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = Dimens.padding.big)
        ,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        IconButton(
            modifier = Modifier
                .padding(horizontal = Dimens.padding.medium)
                .alpha(if (onCloseClick != null) 1f else 0f)
                .background(shape = CircleShape, color = MaterialTheme.colorScheme.error),
            onClick = { onCloseClick?.invoke() },
            enabled = onCloseClick != null,
            rippleColor = MaterialTheme.colorScheme.error.getOnBackgroundColor()
        ) {
            Icon(
                modifier = Modifier,
                imageVector = Icons.Rounded.Close,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.background
            )
        }

        Text(
            text = title,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        IconButton(
            modifier = Modifier
                .padding(horizontal = Dimens.padding.medium)
                .alpha(if (onAcceptClick != null) 1f else 0f)
                .background(shape = CircleShape, color = MaterialTheme.colorScheme.secondary),
            onClick = { onAcceptClick?.invoke() },
            enabled = onAcceptClick != null,
            rippleColor = MaterialTheme.colorScheme.onSecondary
        ) {
            Icon(
                modifier = Modifier,
                imageVector = Icons.Rounded.Check,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.background
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DialogHeaderPreview() {
    DialogHeader(
        title = "Dialog Title",
        onCloseClick = {},
        onAcceptClick = {}
    )
}