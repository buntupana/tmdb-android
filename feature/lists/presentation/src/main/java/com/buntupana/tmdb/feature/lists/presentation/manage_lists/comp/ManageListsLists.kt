package com.buntupana.tmdb.feature.lists.presentation.manage_lists.comp

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.LocalMinimumInteractiveComponentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.buntupana.tmdb.core.ui.theme.Dimens
import com.buntupana.tmdb.feature.lists.domain.model.ListItem
import com.buntupana.tmdb.feature.lists.presentation.manage_lists.ManageListsState
import com.buntupana.tmdb.feature.presentation.R
import com.panabuntu.tmdb.core.common.entity.MediaType
import com.buntupana.tmdb.core.ui.R as RCore

@Composable
fun ManageListsLists(
    modifier: Modifier = Modifier,
    state: ManageListsState,
    onAddToListClick: (listItem: ListItem) -> Unit = {},
    onDeleteFromListClick: (listItem: ListItem) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier
            .animateContentSize()
    ) {

        item {

            Spacer(modifier = Modifier.height(Dimens.padding.medium))

            val mediaName = when (state.mediaType) {
                MediaType.MOVIE -> stringResource(RCore.string.text_movie)
                MediaType.TV_SHOW -> stringResource(RCore.string.text_tv_show)
            }

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .animateItem(),
                text = stringResource(R.string.text_media_belongs_to, mediaName),
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(Dimens.padding.medium))
        }

        items(
            count = state.listMediaLists?.size ?: 0,
            key = { index -> "${state.listMediaLists?.get(index)?.id}_media" }
        ) { index ->

            val listItem = state.listMediaLists?.get(index)
            listItem ?: return@items

            ManageListsItem(
                modifier = Modifier
                    .fillMaxWidth()
                    .animateItem(),
                listItem = listItem,
                isForAdd = false,
                onItemClick = { onDeleteFromListClick(listItem) }
            )
        }

        item {
            if (state.listMediaLists?.isNotEmpty() == true) return@item

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(LocalMinimumInteractiveComponentSize.current)
                    .animateItem(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.message_it_does_no_belongs_to_lists)
                )
            }
        }

        items(
            count = 1,
            key = { "all" }
        ) {
            Spacer(modifier = Modifier.height(Dimens.padding.big))

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .animateItem(),
                text = stringResource(R.string.text_rest_of_the_lists),
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(Dimens.padding.medium))
        }

        items(
            count = state.listAllLists?.size ?: 0,
            key = { index -> "${state.listAllLists?.get(index)?.id}_all" }
        ) { index ->

            val listItem = state.listAllLists?.get(index)
            listItem ?: return@items

            ManageListsItem(
                modifier = Modifier
                    .fillMaxWidth()
                    .animateItem(),
                listItem = listItem,
                isForAdd = true,
                onItemClick = { onAddToListClick(listItem) }
            )
        }

        item {
            if (state.listAllLists?.isNotEmpty() == true) return@item

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(LocalMinimumInteractiveComponentSize.current)
                    .animateItem(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.message_no_list_to_show)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ManageListsListsPreview() {
    ManageListsLists(
        modifier = Modifier
            .fillMaxSize(),
        state = ManageListsState(
            isLoading = false,
            mediaType = MediaType.MOVIE,
            searchKey = "",
            listMediaLists = listOf(
                ListItem(
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
            ),
            listAllLists = listOf(
                ListItem(
                    id = 2,
                    name = "List 2",
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
        )
    )
}