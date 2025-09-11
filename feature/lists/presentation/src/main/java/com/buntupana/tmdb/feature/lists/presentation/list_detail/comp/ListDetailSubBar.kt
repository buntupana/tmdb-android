package com.buntupana.tmdb.feature.lists.presentation.list_detail.comp

import android.content.res.Configuration
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.buntupana.tmdb.core.ui.composables.VerticalTextRoulette
import com.buntupana.tmdb.core.ui.composables.widget.AppIconButton
import com.buntupana.tmdb.core.ui.theme.AppTheme
import com.buntupana.tmdb.core.ui.theme.Dimens
import com.buntupana.tmdb.core.ui.util.getOnBackgroundColor
import com.buntupana.tmdb.feature.presentation.R
import com.panabuntu.tmdb.core.common.util.isNotNullOrBlank
import com.buntupana.tmdb.core.ui.R as RCore

@Composable
fun ListDetailSubBar(
    modifier: Modifier = Modifier,
    listName: String,
    description: String?,
    isPublic: Boolean,
    itemsTotalCount: Int?,
    onEditClick: () -> Unit = {},
    onDeleteClick: () -> Unit = {}
) {

    val textColor = MaterialTheme.colorScheme.primaryContainer.getOnBackgroundColor()

    Box(
        modifier = modifier.animateContentSize()
    ) {

        Box(
            modifier = Modifier.matchParentSize()
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
                    color = textColor,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            }

            if (description.isNotNullOrBlank()) {

                Spacer(modifier = Modifier.height(Dimens.padding.medium))

                Text(
                    text = description,
                    color = MaterialTheme.colorScheme.onSecondaryContainer,
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

                        VerticalTextRoulette(
                            text = " $itemsTotalCount ",
                            color = textColor,
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            text = stringResource(RCore.string.text_items).lowercase(),
                            color = textColor,
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
                                .background(MaterialTheme.colorScheme.onPrimaryContainer)
                                .padding(horizontal = Dimens.padding.small),
                            text = stringResource(listTypeResId).uppercase(),
                            color = MaterialTheme.colorScheme.primaryContainer,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }

                    Row {

                        AppIconButton(
                            onClick = onEditClick,
                            rippleColor = textColor.getOnBackgroundColor()
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.Edit,
                                contentDescription = null,
                                tint = textColor,
                            )
                        }

                        AppIconButton(
                            onClick = onDeleteClick,
                            rippleColor = textColor.getOnBackgroundColor()
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.Delete,
                                contentDescription = null,
                                tint = textColor
                            )
                        }
                    }
                }
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
private fun ListDetailSubBarPreview() {
    AppTheme {
        ListDetailSubBar(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primaryContainer),
            listName = "The 97th Academy Award nominees for Best Motion Picture of the Year Oscars",
            description = "This is a description of the list",
            isPublic = true,
            itemsTotalCount = 3
        )
    }
}