package com.buntupana.tmdb.feature.lists.presentation.lists.comp

import android.content.res.Configuration
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.buntupana.tmdb.core.ui.composables.VerticalNumberRoulette
import com.buntupana.tmdb.core.ui.composables.widget.AppTextWithIconButton
import com.buntupana.tmdb.core.ui.theme.AppTheme
import com.buntupana.tmdb.core.ui.theme.Dimens
import com.buntupana.tmdb.core.ui.util.getOnBackgroundColor
import com.buntupana.tmdb.feature.presentation.R

@Composable
fun ListSubBar(
    modifier: Modifier = Modifier,
    listItemTotalCount: Int?,
    onCreateListClick: () -> Unit,
) {

    val textColor = MaterialTheme.colorScheme.primaryContainer.getOnBackgroundColor()

    Row(
        modifier = modifier
            .background(MaterialTheme.colorScheme.primaryContainer)
            .animateContentSize()
            .padding(
                horizontal = Dimens.padding.horizontal,
                vertical = Dimens.padding.verticalItem
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        listItemTotalCount ?: return

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.lists_you_have) + " ",
                color = textColor,
                fontWeight = FontWeight.Bold
            )

            VerticalNumberRoulette(
                value = listItemTotalCount,
                color = textColor,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = " " + stringResource(R.string.lists_lists).lowercase(),
                color = textColor,
                fontWeight = FontWeight.Bold
            )
        }

        AppTextWithIconButton(
            onClick = onCreateListClick,
            text = stringResource(R.string.lists_create_list),
            imageVector = Icons.Rounded.Add
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
private fun ListSubBarPreview() {
    AppTheme {
        ListSubBar(
            modifier = Modifier.fillMaxWidth(),
            listItemTotalCount = 6,
            onCreateListClick = {}
        )
    }
}