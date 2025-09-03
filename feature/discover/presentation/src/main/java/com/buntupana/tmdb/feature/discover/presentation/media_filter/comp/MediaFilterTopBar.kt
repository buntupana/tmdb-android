package com.buntupana.tmdb.feature.discover.presentation.media_filter.comp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.buntupana.tmdb.core.ui.theme.Dimens
import com.buntupana.tmdb.core.ui.util.IconButton
import com.buntupana.tmdb.feature.discover.presentation.R
import com.panabuntu.tmdb.core.common.entity.MediaType
import com.buntupana.tmdb.core.ui.R as RCore

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MediaFilterTopBar(
    modifier: Modifier = Modifier,
    mediaType: MediaType,
    onBackClick: () -> Unit,
    onSaveClick: () -> Unit,
    onApplyClick: () -> Unit
) {

    val titleStrResId = when(mediaType) {
        MediaType.MOVIE -> R.string.text_movies_filter
        MediaType.TV_SHOW -> R.string.text_tv_shows_filter
    }

    TopAppBar(
        modifier = modifier,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            scrolledContainerColor = MaterialTheme.colorScheme.primary
        ),
        navigationIcon = {
            IconButton(
                modifier = Modifier,
                onClick = onBackClick,
                rippleColor = MaterialTheme.colorScheme.onPrimary
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                    contentDescription = "Back",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }

        },
        actions = {
//            IconButton(
//                modifier = Modifier,
//                onClick = onSaveClick,
//                rippleColor = MaterialTheme.colorScheme.onPrimary
//            ) {
//                Icon(
//                    modifier = Modifier
//                        .padding(horizontal = Dimens.padding.small),
//                    imageVector = Icons.Rounded.Save,
//                    contentDescription = "Save",
//                    tint = MaterialTheme.colorScheme.onPrimary
//                )
//            }
            Button(
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
                onClick = onApplyClick,
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Check,
                        contentDescription = null
                    )
                    Text(
                        modifier = Modifier.padding(start = Dimens.padding.small),
                        text = stringResource(RCore.string.text_apply)
                    )
                }
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
                    text = stringResource(titleStrResId),
                    color = MaterialTheme.colorScheme.onPrimary
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
        mediaType = MediaType.TV_SHOW,
        onApplyClick = {},
        onSaveClick = {},
        onBackClick = {}
    )
}