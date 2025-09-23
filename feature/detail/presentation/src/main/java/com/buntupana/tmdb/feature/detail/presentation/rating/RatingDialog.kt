package com.buntupana.tmdb.feature.detail.presentation.rating

import android.content.res.Configuration
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.LifecycleStartEffect
import com.buntupana.tmdb.core.ui.composables.widget.AppButton
import com.buntupana.tmdb.core.ui.composables.widget.AppOutlinedButton
import com.buntupana.tmdb.core.ui.composables.widget.AppTextWithBorder
import com.buntupana.tmdb.core.ui.composables.widget.sliders.RatingSlider
import com.buntupana.tmdb.core.ui.theme.AppTheme
import com.buntupana.tmdb.core.ui.theme.Dimens
import com.buntupana.tmdb.core.ui.util.UiText
import com.buntupana.tmdb.core.ui.util.annotatedStringResource
import com.buntupana.tmdb.core.ui.util.balanced
import com.buntupana.tmdb.core.ui.util.getRatingColor
import com.buntupana.tmdb.core.ui.util.isInvisible
import com.buntupana.tmdb.feature.detail.presentation.R
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import timber.log.Timber
import com.buntupana.tmdb.core.ui.R as RCore

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RatingDialog(
    viewModel: RatingViewModel = koinViewModel(),
    sheetState: SheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
        confirmValueChange = { viewModel.state.isLoading.not() }
    ),
    onDismiss: () -> Unit = {}
) {

    val scope = rememberCoroutineScope()

    LifecycleStartEffect(Unit) {
        scope.launch {
            viewModel.sideEffect.collect { sideEffect ->
                Timber.d("sideEffect = [$sideEffect]")
                when (sideEffect) {
                    is RatingSideEffect.AddRatingSuccess -> {
                        sheetState.hide()
                        onDismiss()
                    }
                }
            }
        }
        onStopOrDispose {}
    }

    RatingContent(
        state = viewModel.state,
        sheetState = sheetState,
        onDismiss = {
            scope.launch {
                sheetState.hide()
                onDismiss()
            }
        },
        onRatingValueChange = {
            viewModel.onEvent(RatingEvent.SetRating(it))
        },
        addRatingClick = {
            viewModel.onEvent(RatingEvent.AddRating)
        },
        clearRatingClick = {
            viewModel.onEvent(RatingEvent.ClearRating)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RatingContent(
    state: RatingState,
    sheetState: SheetState,
    onRatingValueChange: (Int) -> Unit,
    onDismiss: () -> Unit,
    addRatingClick: () -> Unit,
    clearRatingClick: () -> Unit
) {

    ModalBottomSheet(
        onDismissRequest = {
            Timber.d("RatingContent: dismiss")
            onDismiss()
        },
        sheetState = sheetState,
        containerColor = MaterialTheme.colorScheme.background,
        dragHandle = {},
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = Dimens.padding.big)
                .animateContentSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.detail_rating),
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(Dimens.padding.big))

            Text(
                modifier = Modifier
                    .padding(horizontal = Dimens.padding.horizontal),
                textAlign = TextAlign.Center,
                text = annotatedStringResource(
                    R.string.detail_what_do_you_think,
                    state.mediaTitle
                ),
                style = MaterialTheme.typography.titleMedium.balanced(),
                fontWeight = FontWeight.Normal,
            )

            Spacer(modifier = Modifier.height(Dimens.padding.huge))

            AppTextWithBorder(
                text = state.ratingTitle.asString(),
                color = getRatingColor(state.rating),
                borderColor = MaterialTheme.colorScheme.primaryContainer,
                borderWidth = 4f,
                minLines = 1,
                style = MaterialTheme.typography.headlineMedium.balanced(),
            )

            Spacer(modifier = Modifier.height(Dimens.padding.big))

            RatingSlider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Dimens.padding.horizontal),
                value = state.rating,
                enabled = state.isLoading.not(),
                onValueChange = onRatingValueChange
            )

            Spacer(modifier = Modifier.height(Dimens.padding.big))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                AppOutlinedButton(
                    onClick = clearRatingClick,
                    enabled = state.isLoading.not()
                ) {
                    Text(
                        text = stringResource(R.string.detail_clear_rating),
                        style = MaterialTheme.typography.titleMedium,
                    )
                }

                Box(
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.isInvisible(state.isLoading.not())
                    )
                    AppButton(
                        modifier = Modifier.isInvisible(state.isLoading),
                        onClick = addRatingClick,
                        enabled = state.rating > 0,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.secondaryContainer
                        )
                    ) {
                        Text(
                            text = stringResource(RCore.string.common_confirm),
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    }
                }
            }

            Spacer(
                modifier = Modifier.windowInsetsBottomHeight(WindowInsets.systemBars)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    name = "DefaultPreviewLight",
    showBackground = true, heightDp = 500
)
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "DefaultPreviewDark",
    showBackground = true, heightDp = 500
)
@Composable
fun RatingScreenPreview() {
    AppTheme {
        var ratingState by remember {
            mutableStateOf(
                RatingState(
                    isLoading = true,
                    rating = 60,
                    mediaTitle = "The Office",
                    ratingTitle = UiText.DynamicString("Champion")
                )
            )
        }

        RatingContent(
            state = ratingState,
            sheetState = SheetState(
                skipPartiallyExpanded = true,
                positionalThreshold = { 0f },
                initialValue = SheetValue.Expanded,
                velocityThreshold = { 0f }
            ),
            onDismiss = {},
            onRatingValueChange = {
                ratingState = ratingState.copy(rating = it)
            },
            addRatingClick = {},
            clearRatingClick = {}
        )
    }
}