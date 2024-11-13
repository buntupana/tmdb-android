package com.buntupana.tmdb.core.ui.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.buntupana.tmdb.core.ui.R
import com.buntupana.tmdb.core.ui.util.clickableTextPadding

@Composable
fun <OPTION_ID> DropdownMenuText(
    modifier: Modifier,
    text: String,
    optionList: Map<OPTION_ID, String>,
    onOptionClicked: (id: OPTION_ID, value: String) -> Unit
) {
    DropdownMenuCustom(
        modifier = modifier,
        composable = {
            TextWithIcon(
                modifier.clickableTextPadding(),
                text = text,
                iconRes = R.drawable.ic_arrow_down
            )
        },
        optionList = optionList,
        onOptionClicked = onOptionClicked
    )
}


@Composable
fun <OPTION_ID> DropdownMenuCustom(
    modifier: Modifier,
    composable: @Composable() () -> Unit,
    optionList: Map<OPTION_ID, String>,
    onOptionClicked: (id: OPTION_ID, value: String) -> Unit
) {
    Box(
        modifier = modifier
    ) {

        var dropdownExpanded by remember {
            mutableStateOf(false)
        }

        Box(
            modifier = Modifier.clickable {
                dropdownExpanded = true
            }) {
            composable()
        }

        DropdownMenu(expanded = dropdownExpanded, onDismissRequest = {
            dropdownExpanded = false
        }) {
            optionList.forEach { (id, value) ->
                DropdownMenuItem(
                    text = {
                        Text(text = value)
                    },
                    onClick = {
                        dropdownExpanded = false
                        onOptionClicked(id, value)
                    }
                )
            }
        }
    }
}