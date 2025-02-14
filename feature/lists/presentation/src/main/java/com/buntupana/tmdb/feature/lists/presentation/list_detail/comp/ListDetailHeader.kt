package com.buntupana.tmdb.feature.lists.presentation.list_detail.comp

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.buntupana.tmdb.core.ui.composables.ImageFromUrl
import com.buntupana.tmdb.core.ui.composables.VerticalNumberRoulette
import com.buntupana.tmdb.core.ui.theme.Dimens
import com.buntupana.tmdb.core.ui.theme.PrimaryColor
import com.buntupana.tmdb.feature.presentation.R
import com.panabuntu.tmdb.core.common.util.isNotNullOrBlank
import com.buntupana.tmdb.core.ui.R as RCore

@Composable
fun ListDetailHeader(
    modifier: Modifier = Modifier,
    listName: String,
    description: String?,
    backdropUrl: String?,
    isPublic: Boolean,
    itemsTotalCount: Int?,
    onEditClick: () -> Unit = {},
    onDeleteClick: () -> Unit = {}
) {
    Box(
        modifier = modifier.animateContentSize()
    ) {

        var backgroundColor = PrimaryColor

        if (backdropUrl.isNotNullOrBlank()) {
            backgroundColor = backgroundColor.copy(alpha = 0.8f)
            ImageFromUrl(
                modifier = Modifier.matchParentSize(),
                imageUrl = backdropUrl,
            )
        }

        Box(
            modifier = Modifier
                .matchParentSize()
                .background(backgroundColor),
        )

        Column(
            modifier = Modifier
                .padding(
                    horizontal = Dimens.padding.horizontal,
                    vertical = Dimens.padding.vertical
                )
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {

                Text(
                    modifier = Modifier
                        .weight(1f)
                        .padding(top = Dimens.padding.small),
                    text = listName,
                    color = Color.White,
                    style = MaterialTheme.typography.titleLarge
                )

            }

            if (description.isNotNullOrBlank()) {

                Spacer(modifier = Modifier.height(Dimens.padding.medium))

                Text(
                    text = description,
                    color = Color.White
                )
            }

            if (itemsTotalCount != null) {

                Spacer(modifier = Modifier.height(Dimens.padding.medium))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        VerticalNumberRoulette(
                            number = itemsTotalCount,
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            text = stringResource(RCore.string.text_items).lowercase(),
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )

                        val listTypeResId = if (isPublic) {
                            R.string.text_public
                        } else {
                            R.string.text_private
                        }

                        Text(
                            modifier = Modifier
                                .animateContentSize()
                                .padding(start = Dimens.padding.medium)
                                .clip(RoundedCornerShape(Dimens.posterRound))
                                .background(Color.Gray)
                                .padding(horizontal = Dimens.padding.small),
                            text = stringResource(listTypeResId).uppercase(),
                            color = Color.White,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }

                    Row {

                        IconButton(
                            onClick = onEditClick
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.Edit,
                                contentDescription = null,
                                tint = Color.White
                            )
                        }

                        IconButton(
                            onClick = onDeleteClick
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.Delete,
                                contentDescription = null,
                                tint = Color.White
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ListDetailHeaderPreview() {
    ListDetailHeader(
        modifier = Modifier.fillMaxWidth(),
//        listName = "The 97th Academy",
        listName = "The 97th Academy Award nominees for Best Motion Picture of the Year Oscars",
        backdropUrl = "",
        description = "",
        isPublic = true,
        itemsTotalCount = 3
    )
}