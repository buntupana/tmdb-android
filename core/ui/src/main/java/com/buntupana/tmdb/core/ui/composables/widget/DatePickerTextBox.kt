package com.buntupana.tmdb.core.ui.composables.widget

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Cancel
import androidx.compose.material.icons.rounded.DateRange
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import com.buntupana.tmdb.core.ui.theme.AppTheme
import com.panabuntu.tmdb.core.common.util.toLocalFormat
import java.time.LocalDate
import java.time.format.FormatStyle

@Composable
fun DatePickerTextBox(
    modifier: Modifier = Modifier,
    label: String,
    localDate: LocalDate? = null,
    onValueChange: (selectedLocalDate: LocalDate?) -> Unit,
) {

    var showDatePicker by remember { mutableStateOf(false) }
    // Create an InteractionSource
    val interactionSource = remember { MutableInteractionSource() }
    // Observe press events on the InteractionSource
    val isPressed by interactionSource.collectIsPressedAsState()

    val focusManager = LocalFocusManager.current

    if (isPressed) {
        // Execute your function when the TextField is pressed
        showDatePicker = true
    }

    if (showDatePicker) {
        DatePickerModal(
            onDateSelected = {
                onValueChange(it)
            },
            onDismiss = {
                focusManager.clearFocus()
                showDatePicker = false
            }
        )
    }

    FormatStyle.SHORT

    OutlinedTextField(
        modifier = modifier,
        value = localDate?.toLocalFormat().orEmpty(),
        enabled = true,
        interactionSource = interactionSource,
        onValueChange = { },
        label = { Text(label) },
        readOnly = true,
        trailingIcon = {
            Row {
                AnimatedVisibility(
                    visible = localDate != null,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    AppIconButton(
                        onClick = {
                            onValueChange(null)
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Cancel,
                            contentDescription = "Remove date"
                        )
                    }
                }
                AppIconButton(onClick = { showDatePicker = !showDatePicker }) {
                    Icon(
                        imageVector = Icons.Rounded.DateRange,
                        contentDescription = "Select date"
                    )
                }
            }
        }
    )
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
private fun DatePickerTextBoxPreview() {
    AppTheme {
        DatePickerTextBox(
            label = "From",
            localDate = LocalDate.now(),
            onValueChange = {}
        )
    }
}