package com.buntupana.tmdb.feature.account.presentation.account.comp

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.buntupana.tmdb.core.ui.composables.ErrorAndRetry
import com.buntupana.tmdb.core.ui.composables.ShowMoreButton
import com.buntupana.tmdb.core.ui.theme.AppTheme
import com.buntupana.tmdb.core.ui.theme.Dimens
import com.buntupana.tmdb.feature.account.presentation.R
import com.buntupana.tmdb.feature.lists.domain.model.UserListDetails
import com.panabuntu.tmdb.core.common.util.Const.PLACE_HOLDER_ITEM_NUMBER
import com.buntupana.tmdb.core.ui.R as RCore

@Composable
fun ListItemsSection(
    modifier: Modifier = Modifier,
    userListDetailsList: List<UserListDetails>?,
    isLoadingError: Boolean,
    onItemClicked: (listId: Long, listName: String, description: String?, backdropUrl: String?) -> Unit,
    titleClicked: () -> Unit,
    onRetryClicked: () -> Unit,
    onShowMoreClick: (() -> Unit)? = null
) {
    Column(
        modifier = modifier
    ) {

        TextButton(
            modifier = Modifier
                .padding(start = Dimens.padding.tiny),
            onClick = titleClicked
        ) {
            Text(
                text = stringResource(R.string.text_lists),
                fontWeight = FontWeight.SemiBold,
                fontSize = 22.sp,
                color = MaterialTheme.colorScheme.onBackground
            )
            Icon(
                modifier = Modifier.size(22.dp),
                painter = painterResource(RCore.drawable.ic_arrow_right),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onBackground
            )
        }

        Box {

            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = Dimens.padding.vertical),
                verticalAlignment = Alignment.CenterVertically
            ) {

                item {
                    Spacer(modifier = Modifier.padding(horizontal = Dimens.padding.small))
                }

                if (userListDetailsList.isNullOrEmpty() || isLoadingError) {

                    items(PLACE_HOLDER_ITEM_NUMBER) {
                        ListItemHorizontalPlaceHolder()
                        Spacer(modifier = Modifier.padding(horizontal = Dimens.padding.tiny))
                    }
                    return@LazyRow
                }

                items(
                    count = userListDetailsList.count(),
                    key = { index -> userListDetailsList[index].id }
                ) { index ->

                    val listItem = userListDetailsList[index]

                    ListItemHorizontal(
                        modifier = Modifier.animateItem(),
                        userListDetails = listItem,
                        onListClick = onItemClicked
                    )

                    if (index != userListDetailsList.lastIndex) {
                        Spacer(modifier = Modifier.padding(horizontal = Dimens.padding.tiny))
                    }
                }

                if (userListDetailsList.count() > 2 && onShowMoreClick != null) {

                    item {
                        Spacer(modifier = Modifier.padding(horizontal = Dimens.padding.tiny))

                        ShowMoreButton(onClick = onShowMoreClick)
                    }
                }

                item {
                    Spacer(modifier = Modifier.padding(horizontal = Dimens.padding.small))
                }
            }

            if (userListDetailsList?.isEmpty() == true) {
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .background(MaterialTheme.colorScheme.background.copy(alpha = 0.8f))
                ) {
                    Text(
                        modifier = Modifier
                            .align(Alignment.TopCenter)
                            .padding(top = Dimens.padding.huge),
                        text = stringResource(RCore.string.message_no_results_found),
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            if (isLoadingError) {
                ErrorAndRetry(
                    modifier = Modifier
                        .matchParentSize()
                        .background(MaterialTheme.colorScheme.background.copy(alpha = 0.8f)),
                    contentColor = MaterialTheme.colorScheme.onBackground,
                    errorMessage = stringResource(RCore.string.message_loading_content_error),
                    onRetryClick = onRetryClicked
                )
            }
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
private fun ListItemsSectionPreview() {
    AppTheme {
        ListItemsSection(
            userListDetailsList = null,
            isLoadingError = false,
            onItemClicked = { _, _, _, _ -> },
            titleClicked = {},
            onRetryClicked = {}
        )
    }
}