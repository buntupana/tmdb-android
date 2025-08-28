package com.buntupana.tmdb.feature.detail.presentation.rating

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LifecycleStartEffect
import com.buntupana.tmdb.core.ui.composables.widget.sliders.RatingSlider
import com.buntupana.tmdb.core.ui.theme.Dimens
import com.buntupana.tmdb.core.ui.theme.PrimaryColor
import com.buntupana.tmdb.core.ui.theme.SecondaryColor
import com.buntupana.tmdb.core.ui.util.UiText
import com.buntupana.tmdb.core.ui.util.annotatedStringResource
import com.buntupana.tmdb.core.ui.util.balanced
import com.buntupana.tmdb.core.ui.util.getRatingColor
import com.buntupana.tmdb.core.ui.util.isInvisible
import com.buntupana.tmdb.feature.detail.presentation.R
import kotlinx.coroutines.launch
import timber.log.Timber
import com.buntupana.tmdb.core.ui.R as RCore

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RatingDialog(
    viewModel: RatingViewModel = hiltViewModel(),
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
            if (state.isLoading.not()) {
                onDismiss()
            }
        },
        sheetState = sheetState,
        containerColor = MaterialTheme.colorScheme.background,
        dragHandle = {},
        properties = ModalBottomSheetProperties(
            shouldDismissOnBackPress = false
        )
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
                text = stringResource(R.string.text_rating),
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(Dimens.padding.big))

            Text(
                textAlign = TextAlign.Center,
                text = annotatedStringResource(R.string.message_what_do_you_think, state.mediaTitle),
                style = MaterialTheme.typography.titleMedium.balanced(),
                fontWeight = FontWeight.Normal
            )

            Spacer(modifier = Modifier.height(Dimens.padding.huge))

            if (state.isLoading.not()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .isInvisible(state.rating == 0)
                ) {
                    Text(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .clip(RoundedCornerShape(100.dp))
                            .background(PrimaryColor)
                            .padding(horizontal = 12.dp, vertical = 8.dp),
                        text = state.ratingTitle.asString(),
                        fontWeight = FontWeight.Bold,
                        color = getRatingColor(state.rating)
                    )
                }
            }

            Spacer(modifier = Modifier.height(Dimens.padding.big))

            if (state.isLoading) {
                CircularProgressIndicator()
            } else {
                RatingSlider(
                    modifier = Modifier.fillMaxWidth(),
                    value = state.rating,
                    onValueChange = onRatingValueChange
                )

                Spacer(modifier = Modifier.height(Dimens.padding.big))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Button(
                        onClick = clearRatingClick
                    ) {
                        Text(
                            text = stringResource(R.string.text_clear_rating),
                            style = MaterialTheme.typography.titleMedium,
                        )
                    }
                    Button(
                        onClick = addRatingClick,
                        enabled = state.rating > 0,
                        colors = ButtonDefaults.buttonColors(containerColor = SecondaryColor)
                    ) {
                        Text(
                            text = stringResource(RCore.string.text_confirm),
                            style = MaterialTheme.typography.titleMedium,
                        )
                    }
                }
            }

            Spacer(
                modifier = Modifier
                    .windowInsetsBottomHeight(
                        WindowInsets.systemBars
                    )
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun RatingScreenPreview() {

    var ratingState by remember {
        mutableStateOf(
            RatingState(
                isLoading = false,
                rating = 20,
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