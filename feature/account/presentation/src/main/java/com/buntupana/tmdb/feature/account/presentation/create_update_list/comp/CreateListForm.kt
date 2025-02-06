package com.buntupana.tmdb.feature.account.presentation.create_update_list.comp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import com.buntupana.tmdb.core.ui.theme.Dimens
import com.buntupana.tmdb.core.ui.theme.PrimaryColor
import com.buntupana.tmdb.core.ui.theme.SecondaryColor
import com.buntupana.tmdb.core.ui.R as RCore

@Composable
fun CreateListForm(
    modifier: Modifier = Modifier,
    listName: String,
    description: String,
    isPublic: Boolean,
    updateForm: (listName: String, listDescription: String, isPublic: Boolean) -> Unit,
    onCreateListClick: () -> Unit
) {

    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = modifier
    ) {

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = SecondaryColor),
            value = listName,
            onValueChange = {
                updateForm(it, description, isPublic)
            },
            label = { Text(text = stringResource(RCore.string.text_name)) },
            maxLines = 1,

            )
        Spacer(modifier = Modifier.height(Dimens.padding.medium))

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = SecondaryColor),
            value = description,
            onValueChange = {
                updateForm(listName, it, isPublic)
            },
            label = { Text(text = stringResource(RCore.string.text_description)) },
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
                text = "Public List?",
                style = MaterialTheme.typography.bodyLarge
            )

            Switch(
                modifier = Modifier,
                checked = isPublic,
                colors = SwitchDefaults.colors(checkedTrackColor = PrimaryColor),
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
            Button(
                onClick = {
                    keyboardController?.hide()
                    onCreateListClick()
                },
                enabled = listName.isNotBlank(),
                colors = ButtonDefaults.buttonColors(containerColor = SecondaryColor),
            ) {
                Text(
                    text = stringResource(RCore.string.text_confirm)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CreateListFormPreview() {
    CreateListForm(
        listName = "The 97th Academy Award nominees for Best Motion Picture of the Year Oscars",
        description = "Description",
        isPublic = true,
        updateForm = { _, _, _ -> },
        onCreateListClick = {}
    )
}