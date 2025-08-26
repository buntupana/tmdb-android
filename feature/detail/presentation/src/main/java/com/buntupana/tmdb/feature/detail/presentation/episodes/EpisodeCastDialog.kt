package com.buntupana.tmdb.feature.detail.presentation.episodes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.buntupana.tmdb.core.ui.theme.Dimens
import com.buntupana.tmdb.core.ui.util.balanced
import com.buntupana.tmdb.feature.detail.domain.model.Episode
import com.buntupana.tmdb.feature.detail.presentation.cast.comp.castList
import com.buntupana.tmdb.feature.detail.presentation.episodeSample

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EpisodeCastDialog(
    showDialog: Boolean,
    sheetState: SheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
    episode: Episode?,
    onDismiss: () -> Unit,
    onPersonClick: (personId: Long) -> Unit
) {

    if (showDialog.not() || episode == null) return

    EpisodeCastContent(
        sheetState = sheetState,
        episode = episode,
        onDismiss = onDismiss,
        onPersonClick = onPersonClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun EpisodeCastContent(
    sheetState: SheetState,
    episode: Episode,
    onDismiss: () -> Unit,
    onPersonClick: (personId: Long) -> Unit
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
                .padding(top = Dimens.padding.vertical),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Dimens.padding.horizontal),
                textAlign = TextAlign.Center,
                text = episode.name,
                style = MaterialTheme.typography.headlineMedium.balanced(),
                fontWeight = FontWeight.Bold,
            )

            Spacer(modifier = Modifier.height(Dimens.padding.vertical))

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .navigationBarsPadding()
            ) {
                castList(
                    modifier = Modifier
                        .fillMaxWidth(),
                    isEpisode = true,
                    personCastList = episode.castList,
                    personCrewMap = episode.personCrewMap,
                    onPersonClick = onPersonClick
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
private fun EpisodeCastPreview() {
    EpisodeCastContent(
        sheetState = SheetState(
            skipPartiallyExpanded = true,
            positionalThreshold = { 0f },
            initialValue = SheetValue.Expanded,
            velocityThreshold = { 0f }
        ),
        episode = episodeSample,
        onDismiss = {},
        onPersonClick = {}
    )
}