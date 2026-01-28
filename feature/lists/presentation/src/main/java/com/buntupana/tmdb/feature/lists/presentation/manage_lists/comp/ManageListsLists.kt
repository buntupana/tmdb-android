package com.buntupana.tmdb.feature.lists.presentation.manage_lists.comp

import android.content.res.Configuration
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
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
            .animateContentSize()
    ) {

        item {

            Spacer(modifier = Modifier.height(Dimens.padding.medium))

            val mediaName = when (state.mediaType) {
                MediaType.MOVIE -> stringResource(RCore.string.common_movie)
                MediaType.TV_SHOW -> stringResource(RCore.string.common_tv_show)
            }

            Text(
                modifier = Modifier
                    .padding(horizontal = Dimens.padding.horizontal)
                    .fillMaxWidth()
                    .animateItem(),
                text = stringResource(R.string.lists_media_belongs_to, mediaName),
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

            if (index != 0) {
                Spacer(modifier = Modifier.height(Dimens.padding.small))
            }

            ManageListsItem(
                modifier = Modifier
                    .fillMaxWidth()
                    .animateItem()
                    .clickable(
                        onClick = { onDeleteFromListClick(listItem) }
                    )
                    .padding(horizontal = Dimens.padding.horizontal),
                mediaList = listItem,
                isForAdd = false,
            )
        }

        item {
            if (state.userListDetails?.isNotEmpty() == true) return@item

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Dimens.padding.horizontal)
                    .height(LocalMinimumInteractiveComponentSize.current)
                    .animateItem(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.lists_it_does_no_belongs_to_lists)
                )
            }
        }

        items(
            count = 1,
            key = { "all" }
        ) {
            Spacer(modifier = Modifier.height(Dimens.padding.medium))

            HorizontalDivider()

            Spacer(modifier = Modifier.height(Dimens.padding.medium))

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Dimens.padding.horizontal)
                    .animateItem(),
                text = stringResource(R.string.lists_rest_of_the_lists),
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

            if (index != 0) {
                Spacer(modifier = Modifier.height(Dimens.padding.small))
            }

            ManageListsItem(
                modifier = Modifier
                    .fillMaxWidth()
                    .animateItem()
                    .clickable(
                        onClick = { onAddToListClick(listItem) }
                    )
                    .padding(horizontal = Dimens.padding.horizontal),
                mediaList = listItem,
                isForAdd = true,
            )
        }

        item {
            if (state.listAllLists?.isNotEmpty() == true) return@item

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(LocalMinimumInteractiveComponentSize.current)
                    .padding(horizontal = Dimens.padding.horizontal)
                    .animateItem(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.lists_no_list_to_show)
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