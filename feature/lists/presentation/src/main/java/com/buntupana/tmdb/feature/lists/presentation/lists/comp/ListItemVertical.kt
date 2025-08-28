package com.buntupana.tmdb.feature.lists.presentation.lists.comp

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.buntupana.tmdb.core.ui.composables.ImageFromUrl
import com.buntupana.tmdb.core.ui.theme.Dimens
import com.buntupana.tmdb.core.ui.theme.PrimaryColor
import com.buntupana.tmdb.feature.lists.domain.model.UserListDetails
import com.buntupana.tmdb.feature.lists.presentation.util.listItemMediaLists
import com.buntupana.tmdb.feature.presentation.R
import com.panabuntu.tmdb.core.common.util.countWordsBySpace
import com.panabuntu.tmdb.core.common.util.isNotNullOrBlank
import com.buntupana.tmdb.core.ui.R as RCore

@Composable
fun ListItemVertical(
    modifier: Modifier,
    userListDetails: UserListDetails,
    onItemClick: (listId: Long, listName: String, description: String?, backdropUrl: String?) -> Unit,
) {

    Surface(
        modifier = modifier
            .padding(
                horizontal = Dimens.padding.horizontal,
                vertical = Dimens.padding.verticalItem
            )
            .clickable {
                onItemClick(
                    userListDetails.id,
                    userListDetails.name,
                    userListDetails.description,
                    userListDetails.backdropUrl
                )
            },
        shape = RoundedCornerShape(Dimens.posterRound),
        shadowElevation = Dimens.cardElevation
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16f / 10f)
            ) {

                var backgroundColor = PrimaryColor

                if (userListDetails.backdropUrl.isNotNullOrBlank()) {
                    backgroundColor = backgroundColor.copy(alpha = 0.8f)
                    ImageFromUrl(
                        modifier = Modifier.fillMaxSize(),
                        imageUrl = userListDetails.backdropUrl,
                        showPlaceHolder = true
                    )
                }

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(backgroundColor)
                )

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(
                            horizontal = Dimens.padding.horizontal,
                            vertical = Dimens.padding.vertical
                        ),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {

                    Box(
                        modifier = Modifier.weight(1f),
                        contentAlignment = Alignment.Center,
                    ) {

                        var maxLines = userListDetails.name.countWordsBySpace()

                        if (maxLines > 2) {
                            maxLines = 3
                        }

                        Text(
                            text  = userListDetails.name,
                            style = TextStyle.Default.copy(textAlign = TextAlign.Center),
                            autoSize = TextAutoSize.StepBased(minFontSize = 18.sp),
                            maxLines = maxLines,
                            overflow = TextOverflow.Ellipsis,
                            color =  Color.White
                        )
                    }

                    Row(
                        modifier = Modifier.padding(top = Dimens.padding.medium)
                    ) {
                        Text(
                            text = stringResource(
                                RCore.string.text_num_items,
                                userListDetails.itemCount
                            ),
                            color = Color.White,
                            style = MaterialTheme.typography.bodyLarge
                        )

                        val listTypeResId = if (userListDetails.isPublic) {
                            R.string.text_public
                        } else {
                            R.string.text_private
                        }

                        Text(
                            modifier = Modifier
                                .padding(start = Dimens.padding.medium)
                                .clip(RoundedCornerShape(Dimens.posterRound))
                                .background(Color.Gray)
                                .padding(horizontal = Dimens.padding.small),
                            text = stringResource(listTypeResId).uppercase(),
                            color = Color.White,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }

            if (userListDetails.description.isNullOrBlank()) return@Column

            Text(
                modifier = Modifier.padding(
                    horizontal = Dimens.padding.horizontal,
                    Dimens.padding.vertical
                ),
                text = userListDetails.description!!
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ListItemVerticalPreview() {
    ListItemVertical(
        modifier = Modifier,
        userListDetails = listItemMediaLists.first().copy(name = "Body Transformation"),
        onItemClick = { _, _, _, _ -> }
    )
}