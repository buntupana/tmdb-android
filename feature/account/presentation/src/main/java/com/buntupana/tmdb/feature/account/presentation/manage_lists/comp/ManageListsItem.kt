package com.buntupana.tmdb.feature.account.presentation.manage_lists.comp

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Remove
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.buntupana.tmdb.feature.account.domain.model.ListItem

@Composable
fun ManageListsItem(
    modifier: Modifier = Modifier,
    listItem: ListItem,
    isForAdd: Boolean,
    onItemClick: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = "${listItem.name} (${listItem.itemCount})"
        )

        val icon = if (isForAdd) Icons.Rounded.Add else Icons.Rounded.Remove

        IconButton(
            onClick = onItemClick
        ) {
            Icon(
                modifier = Modifier.border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.onBackground,
                    shape = CircleShape
                ),
                imageVector = icon,
                contentDescription = null
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ManageListsItemPreview() {
    ManageListsItem(
        modifier = Modifier.fillMaxWidth(),
        isForAdd = false,
        listItem = ListItem(
            id = 1,
            name = "List 1",
            description = "Description 1",
            itemCount = 1,
            isPublic = true,
            backdropUrl = null,
            revenue = null,
            runtime = null,
            posterUrl = null,
            averageRating = null,
            updatedAt = null
        )
    )
}