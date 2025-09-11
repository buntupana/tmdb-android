package com.buntupana.tmdb.feature.lists.presentation.manage_lists.comp

import android.content.res.Configuration
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.LocalMinimumInteractiveComponentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.buntupana.tmdb.core.ui.theme.AppTheme
import com.buntupana.tmdb.core.ui.theme.Dimens
import com.buntupana.tmdb.feature.lists.domain.model.UserListDetails
import com.buntupana.tmdb.feature.lists.presentation.manage_lists.ManageListsState
import com.buntupana.tmdb.feature.presentation.R
import com.panabuntu.tmdb.core.common.entity.MediaType
import com.buntupana.tmdb.core.ui.R as RCore

@Composable
fun ManageListsLists(
    modifier: Modifier = Modifier,
    state: ManageListsState,
    onAddToListClick: (mediaList: UserListDetails) -> Unit = {},
    onDeleteFromListClick: (mediaList: UserListDetails) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier
            .padding(horizontal = Dimens.padding.horizontal)
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
            count = state.userListDetails?.size ?: 0,
            key = { index -> "${state.userListDetails?.get(index)?.id}_media" }
        ) { index ->

            val listItem = state.userListDetails?.get(index)
            listItem ?: return@items

            ManageListsItem(
                modifier = Modifier
                    .fillMaxWidth()
                    .animateItem(),
                mediaList = listItem,
                isForAdd = false,
                onItemClick = { onDeleteFromListClick(listItem) }
            )
        }

        item {
            if (state.userListDetails?.isNotEmpty() == true) return@item

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
                mediaList = listItem,
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

        item {
            Spacer(modifier = Modifier.height(Dimens.padding.medium))
        }
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
private fun ManageListsListsPreview() {
    AppTheme {
        ManageListsLists(
            modifier = Modifier
                .fillMaxSize(),
            state = ManageListsState(
                isContentLoading = true,
                mediaType = MediaType.MOVIE,
                mediaName = "Blue Velvet",
                backgroundColor = null,
                posterUrl = "asdf",
                searchKey = "",
                releaseYear = "1998",
                userListDetails = listOf(
                    UserListDetails(
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
                ),
                listAllLists = listOf(
                    UserListDetails(
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
                        updatedAt = null,
                        shareLink = "test"
                    )
                )
            )
        )
    }
}