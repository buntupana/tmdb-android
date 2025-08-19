package com.buntupana.tmdb.feature.discover.presentation.media_filter.comp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Save
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.buntupana.tmdb.core.ui.theme.Dimens
import com.buntupana.tmdb.core.ui.theme.PrimaryColor
import com.buntupana.tmdb.core.ui.util.IconButton
import com.buntupana.tmdb.core.ui.util.getOnBackgroundColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MediaFilterTopBar(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    onSaveClick: () -> Unit
) {
    TopAppBar(
        modifier = modifier,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = PrimaryColor,
            scrolledContainerColor = PrimaryColor
        ),
        navigationIcon = {
            IconButton(
                modifier = Modifier,
                onClick = onBackClick,
                rippleColor = PrimaryColor.getOnBackgroundColor()
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                    contentDescription = "Back",
                    tint = PrimaryColor.getOnBackgroundColor()
                )
            }

        },
        actions = {
            IconButton(
                modifier = Modifier,
                onClick = onSaveClick,
                rippleColor = PrimaryColor.getOnBackgroundColor()
            ) {
                Icon(
                    modifier = Modifier
                        .padding(horizontal = Dimens.padding.small),
                    imageVector = Icons.Rounded.Save,
                    contentDescription = "Save",
                    tint = PrimaryColor.getOnBackgroundColor()
                )
            }
        },
        title = {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {

                Text(
                    text = "Filter",
                    color = PrimaryColor.getOnBackgroundColor()
                )
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun MediaFilterTopBarPreview() {
    MediaFilterTopBar(
        modifier = Modifier,
        onSaveClick = {},
        onBackClick = {}
    )
}