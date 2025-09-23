package com.buntupana.tmdb.feature.lists.presentation.create_update_list.comp

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.buntupana.tmdb.core.ui.composables.widget.AppButton
import com.buntupana.tmdb.core.ui.theme.AppTheme
import com.buntupana.tmdb.core.ui.theme.Dimens
import com.buntupana.tmdb.core.ui.util.isInvisible
import com.buntupana.tmdb.feature.presentation.R
import com.buntupana.tmdb.core.ui.R as RCore

@Composable
fun CreateListForm(
    modifier: Modifier = Modifier,
    listName: String,
    description: String,
    isPublic: Boolean,
    isLoading: Boolean = false,
    updateForm: (listName: String, listDescription: String, isPublic: Boolean) -> Unit,
    onCreateListClick: () -> Unit
) {

    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = modifier
    ) {

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = MaterialTheme.colorScheme.secondary),
            value = listName,
            enabled = isLoading.not(),
            onValueChange = {
                updateForm(it, description, isPublic)
            },
            label = { Text(text = stringResource(RCore.string.common_name)) },
            maxLines = 1,

            )
        Spacer(modifier = Modifier.height(Dimens.padding.medium))

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = MaterialTheme.colorScheme.secondary),
            value = description,
            enabled = isLoading.not(),
            onValueChange = {
                updateForm(listName, it, isPublic)
            },
            label = { Text(text = stringResource(RCore.string.common_description)) },
            maxLines = 5,
            minLines = 5
        )

        Spacer(modifier = Modifier.height(Dimens.padding.medium))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(R.string.lists_public_list),
                style = MaterialTheme.typography.bodyLarge
            )

            Switch(
                modifier = Modifier,
                checked = isPublic,
                enabled = isLoading.not(),
                colors = SwitchDefaults.colors(checkedTrackColor = MaterialTheme.colorScheme.primary),
                onCheckedChange = {
                    updateForm(listName, description, it)
                }
            )
        }

        Spacer(modifier = Modifier.height(Dimens.padding.medium))

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.CenterEnd
        ) {
            Box(
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.isInvisible(isLoading.not())
                )
                AppButton(
                    modifier = Modifier.isInvisible(isLoading),
                    onClick = {
                        keyboardController?.hide()
                        onCreateListClick()
                    },
                    enabled = listName.isNotBlank(),
                ) {
                    Text(
                        text = stringResource(RCore.string.common_confirm)
                    )
                }
            }
        }
    }
}

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    name = "DefaultPreviewLight",
    showBackground = true
)
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "DefaultPreviewDark",
    showBackground = true
)
@Composable
private fun CreateListFormPreview() {
    AppTheme {
        CreateListForm(
            listName = "The 97th Academy Award nominees for Best Motion Picture of the Year Oscars",
            description = "Description",
            isPublic = true,
            isLoading = false,
            updateForm = { _, _, _ -> },
            onCreateListClick = {}
        )
    }
}