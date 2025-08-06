package com.buntupana.tmdb.core.ui.composables.widget

import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.buntupana.tmdb.core.ui.R
import com.buntupana.tmdb.core.ui.util.TextButton
import java.time.Instant
import java.time.LocalDate
import java.util.TimeZone

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerModal(
    onDateSelected: (LocalDate?) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState()

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                if (datePickerState.selectedDateMillis == null) {
                    onDateSelected(null)
                } else {
                    val result = Instant.ofEpochMilli(datePickerState.selectedDateMillis!!)
                        .atZone(TimeZone.getDefault().toZoneId())
                        .toLocalDate()
                    onDateSelected(result)
                }
                onDismiss()
            }) {
                Text(stringResource(R.string.text_confirm))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(R.string.text_cancel))
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}