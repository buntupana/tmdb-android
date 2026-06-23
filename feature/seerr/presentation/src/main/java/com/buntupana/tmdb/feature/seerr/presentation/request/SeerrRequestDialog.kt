package com.buntupana.tmdb.feature.seerr.presentation.request

import android.content.res.Configuration
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.buntupana.tmdb.core.ui.composables.widget.AppButton
import com.buntupana.tmdb.core.ui.theme.AppTheme
import com.buntupana.tmdb.core.ui.theme.Dimens
import com.buntupana.tmdb.core.ui.util.ObserveAsEvents
import com.buntupana.tmdb.feature.seerr.domain.model.SeerrMediaInfo
import com.buntupana.tmdb.feature.seerr.domain.model.SeerrSeasonInfo
import com.buntupana.tmdb.feature.seerr.presentation.R
import com.buntupana.tmdb.feature.seerr.presentation.request.comp.TvShowSeasonsRequest
import com.panabuntu.tmdb.core.common.entity.MediaType
import com.panabuntu.tmdb.core.common.model.SeerrStatus
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SeerrRequestDialog(
    viewModel: SeerrRequestViewModel = koinViewModel(),
    sheetState: SheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
//        confirmValueChange = { viewModel.state.isLoading.not() }
    ),
    onDismiss: () -> Unit
) {

    val scope = rememberCoroutineScope()

    ObserveAsEvents(viewModel.sideEffect) { sideEffect ->
        when (sideEffect) {
            SeerrRequestSideEffect.Dismiss -> {
                scope.launch {
                    sheetState.hide()
                    onDismiss()
                }
            }
        }
    }

    SeerrRequestContent(
        state = viewModel.state,
        sheetState = sheetState,
        onSeasonToggle = { sessionNumber ->
            viewModel.onEvent(SeerrRequestEvent.OnSeasonToggle(sessionNumber))
        },
        onAllSeasonsToggle = {
            viewModel.onEvent(SeerrRequestEvent.OnAllSeasonsToggle)
        },
        onSubmitClick = { viewModel.onEvent(SeerrRequestEvent.Submit) },
        onDismiss = {
            if (!viewModel.state.isSubmitting) {
                scope.launch {
                    sheetState.hide()
                    onDismiss()
                }
            }
        },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SeerrRequestContent(
    state: SeerrRequestState,
    sheetState: SheetState,
    onSeasonToggle: (Int) -> Unit = {},
    onAllSeasonsToggle: () -> Unit = {},
    onSubmitClick: () -> Unit = {},
    onDismiss: () -> Unit
) {

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = MaterialTheme.colorScheme.background,
        dragHandle = {},
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimens.padding.big)
                .animateContentSize(),
        ) {

            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                text = stringResource(R.string.seerr_request_media),
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(Dimens.padding.big))

            if (state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(vertical = Dimens.padding.big)
                )
                return@Column
            }

            state.mediaInfo ?: return@Column

            Column(
                modifier = Modifier.weight(1f, fill = false)
            ) {
                when (state.mediaType) {
                    MediaType.MOVIE -> {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = Dimens.padding.horizontal),
                            text = stringResource(R.string.seerr_request_movie_confirmation)
                        )
                    }

                    MediaType.TV_SHOW -> TvShowSeasonsRequest(
                        modifier = Modifier.weight(1f, fill = false),
                        enable = state.isSubmitting.not(),
                        mediaInfo = state.mediaInfo,
                        selectedSeasons = state.selectedSeasons,
                        onSeasonToggle = onSeasonToggle,
                        onAllSeasonsToggle = onAllSeasonsToggle,
                    )
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = Dimens.padding.big),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                AppButton(
                    onClick = onDismiss,
                    enabled = state.isSubmitting.not(),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                ) {
                    Text(stringResource(com.buntupana.tmdb.core.ui.R.string.common_cancel))
                }
                AppButton(
                    onClick = onSubmitClick,
                    enabled = state.ableToSubmit
                ) {
                    Text(stringResource(R.string.seerr_request_submit))
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
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
private fun SeerrRequestContentPreview() {
    AppTheme {
        SeerrRequestContent(
            state = SeerrRequestState(
                isLoading = true,
                mediaId = 0L,
                mediaType = MediaType.TV_SHOW,
                mediaInfo = SeerrMediaInfo(
                    mediaStatus = SeerrStatus.PARTIALLY_AVAILABLE,
                    seasons = listOf(
                        SeerrSeasonInfo(
                            seasonNumber = 1,
                            seasonName = "Season 1",
                            status = SeerrStatus.AVAILABLE
                        ),
                        SeerrSeasonInfo(
                            seasonNumber = 2,
                            seasonName = "Season 2",
                            status = SeerrStatus.REQUESTED
                        ),
                        SeerrSeasonInfo(
                            seasonNumber = 3,
                            seasonName = "Season 3",
                            status = SeerrStatus.UNKNOWN
                        ),
                        SeerrSeasonInfo(
                            seasonNumber = 4,
                            seasonName = "Season 4",
                            status = SeerrStatus.UNKNOWN
                        ),
                    )
                ),
                selectedSeasons = setOf(3)
            ),
            sheetState = SheetState(
                skipPartiallyExpanded = true,
                positionalThreshold = { 0f },
                initialValue = SheetValue.Expanded,
                velocityThreshold = { 0f }
            ),
            onDismiss = {}
        )
    }
}
