package com.buntupana.tmdb.feature.account.presentation.lists.comp

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.buntupana.tmdb.core.ui.theme.Dimens
import com.buntupana.tmdb.core.ui.theme.PrimaryColor
import com.buntupana.tmdb.core.ui.theme.SecondaryColor
import com.buntupana.tmdb.core.ui.util.getOnBackgroundColor
import com.buntupana.tmdb.feature.account.presentation.R

@Composable
fun ListSubBar(
    modifier: Modifier = Modifier,
    listItemTotalCount: Int?,
    onCreateListClick: () -> Unit,
) {

    Row(
        modifier = modifier
            .background(PrimaryColor)
            .animateContentSize()
            .padding(
                horizontal = Dimens.padding.horizontal,
                vertical = Dimens.padding.verticalItem
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = stringResource(
                R.string.text_you_have_lists,
                listItemTotalCount ?: 0
            ),
            color = PrimaryColor.getOnBackgroundColor(),
            fontWeight = FontWeight.Bold
        )

        Button(
            colors = ButtonDefaults.buttonColors(containerColor = SecondaryColor),
            onClick = onCreateListClick,
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.text_create_list)
                )
                Icon(
                    modifier = Modifier.padding(start = Dimens.padding.small),
                    imageVector = Icons.Rounded.Add,
                    contentDescription = null
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ListSubBarPreview() {
    ListSubBar(
        modifier = Modifier.fillMaxWidth(),
        listItemTotalCount = 6,
        onCreateListClick = {}
    )
}