package com.buntupana.tmdb.core.ui.composables.dialog

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalBottomSheetProperties
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.fromHtml
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.LifecycleStartEffect
import com.buntupana.tmdb.core.ui.theme.Dimens
import com.buntupana.tmdb.core.ui.util.isInvisible

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConfirmationDialog(
    sheetState: SheetState,
    title: String,
    description: String,
    isLoading: Boolean,
    confirmButtonColor: Color = MaterialTheme.colorScheme.secondary,
    onCancelClick: () -> Unit = {},
    onConfirmClick: () -> Unit,
    onDismiss: () -> Unit,
) {

    ConfirmationDialog(
        sheetState = sheetState,
        title = title,
        description = AnnotatedString.fromHtml(description),
        isLoading = isLoading,
        confirmButtonColor = confirmButtonColor,
        onCancelClick = onCancelClick,
        onConfirmClick = onConfirmClick,
        onDismiss = onDismiss
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConfirmationDialog(
    sheetState: SheetState,
    title: String,
    description: AnnotatedString,
    isLoading: Boolean,
    confirmButtonColor: Color = MaterialTheme.colorScheme.secondary,
    onConfirmClick: () -> Unit,
    onCancelClick: () -> Unit = {},
    onDismiss: () -> Unit,
) {

    LifecycleStartEffect(Unit) {
        onStopOrDispose {
            onCancelClick()
            onDismiss()
        }
    }

    ModalBottomSheet(
        onDismissRequest = {
            if (isLoading.not()) {
                onCancelClick()
                onDismiss()
            }
        },
        sheetState = sheetState,
        containerColor = MaterialTheme.colorScheme.background,
        dragHandle = {},
        properties = ModalBottomSheetProperties(shouldDismissOnBackPress = false)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = Dimens.padding.horizontal,
                    vertical = Dimens.padding.big
                )
                .animateContentSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = title,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(Dimens.padding.big))

            Box {

                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                Column(
                    modifier = Modifier.isInvisible(isLoading),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(
                        text = description,
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(Dimens.padding.huge))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        Button(
                            onClick = {
                                onCancelClick()
                                onDismiss()
                            }
                        ) {
                            Text(
                                text = stringResource(com.buntupana.tmdb.core.ui.R.string.text_cancel),
                                style = MaterialTheme.typography.titleMedium,
                            )
                        }
                        Button(
                            onClick = onConfirmClick,
                            colors = ButtonDefaults.buttonColors(containerColor = confirmButtonColor)
                        ) {
                            Text(
                                text = stringResource(com.buntupana.tmdb.core.ui.R.string.text_confirm),
                                style = MaterialTheme.typography.titleMedium
                            )
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
private fun ConfirmationDialogPreview() {
    ConfirmationDialog(
        sheetState = SheetState(
            skipPartiallyExpanded = true,
            positionalThreshold = { 0f },
            initialValue = SheetValue.Expanded,
            velocityThreshold = { 0f }
        ),
        title = "Dialog Title",
        description = "Dialog Description",
        isLoading = false,
        onCancelClick = {},
        onConfirmClick = {},
        onDismiss = {}
    )
}