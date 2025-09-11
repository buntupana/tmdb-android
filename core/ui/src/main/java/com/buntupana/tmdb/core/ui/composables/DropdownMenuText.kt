package com.buntupana.tmdb.core.ui.composables

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.buntupana.tmdb.core.ui.R
import com.buntupana.tmdb.core.ui.composables.widget.AppTextButton
import com.buntupana.tmdb.core.ui.theme.AppTheme

@Composable
fun <OPTION_ID> DropdownMenuText(
    modifier: Modifier = Modifier,
    text: String,
    optionMap: Map<OPTION_ID, String>,
    onOptionClicked: (id: OPTION_ID, value: String) -> Unit
) {
    DropdownMenuCustom(
        modifier = modifier,
        composable = {
            TextWithIcon(
                text = text,
                painter = painterResource(R.drawable.ic_arrow_down)
            )
        },
        optionList = optionMap,
        onOptionClicked = onOptionClicked
    )
}


@Composable
fun <OPTION_ID> DropdownMenuCustom(
    modifier: Modifier = Modifier,
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

        AppTextButton(
            onClick = { dropdownExpanded = true },
            rippleColor = MaterialTheme.colorScheme.onSurface,
        ) {
            composable()
        }

        DropdownMenu(
            containerColor = MaterialTheme.colorScheme.surfaceContainer,
            expanded = dropdownExpanded,
            onDismissRequest = {
                dropdownExpanded = false
            }
        ) {
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
private fun DropdownMenuTextPreview() {
    AppTheme {
        DropdownMenuText(
            modifier = Modifier,
            text = "Text",
            optionMap = mapOf(
                1 to "Option 1",
                2 to "Option 2",
                3 to "Option 3"
            ),
            onOptionClicked = { _, _ -> }
        )
    }
}