package com.buntupana.tmdb.feature.lists.presentation.manage_lists.comp

import android.content.res.Configuration
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Remove
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.buntupana.tmdb.core.ui.composables.ImageFromUrl
import com.buntupana.tmdb.core.ui.theme.AppTheme
import com.buntupana.tmdb.core.ui.theme.Dimens
import com.buntupana.tmdb.feature.lists.domain.model.UserListDetails
import com.buntupana.tmdb.feature.presentation.R

@Composable
fun ManageListsItem(
    modifier: Modifier = Modifier,
    mediaList: UserListDetails,
    isForAdd: Boolean,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {

        ImageFromUrl(
            modifier = Modifier
                .height(80.dp)
                .aspectRatio(16f / 12f)
                .clip(RoundedCornerShape(Dimens.posterRound)),
            imageUrl = mediaList.backdropUrl
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = Dimens.padding.medium)
        ) {

            Text(
                text = mediaList.name,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = stringResource(R.string.lists_num_items, mediaList.itemCount),
                style = MaterialTheme.typography.titleSmall
            )
        }


        val icon = if (isForAdd) Icons.Rounded.Add else Icons.Rounded.Remove

        Icon(
            modifier = Modifier
                .padding(end = Dimens.padding.medium)
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.onBackground,
                    shape = CircleShape
                ),
            imageVector = icon,
            contentDescription = null
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
private fun ManageListsItemPreview() {
    AppTheme {
        ManageListsItem(
            modifier = Modifier.fillMaxWidth(),
            isForAdd = false,
            mediaList = com.buntupana.tmdb.feature.lists.domain.model.UserListDetails(
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
                updatedAt = null,
                shareLink = "test"
            )
        )
    }
}