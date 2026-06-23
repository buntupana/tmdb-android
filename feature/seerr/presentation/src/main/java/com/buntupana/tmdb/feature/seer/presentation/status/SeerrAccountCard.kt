package com.buntupana.tmdb.feature.seer.presentation.status

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.buntupana.tmdb.core.ui.composables.widget.AppButton
import com.buntupana.tmdb.core.ui.theme.Dimens
import com.buntupana.tmdb.feature.seer.presentation.R
import org.koin.androidx.compose.koinViewModel

@Composable
fun SeerrAccountCard(
    modifier: Modifier = Modifier,
    onConnectClick: () -> Unit,
    viewModel: SeerStatusViewModel = koinViewModel()
) {

    val state = viewModel.state

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = Dimens.padding.horizontal),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(Dimens.padding.medium)
        ) {

            Text(
                text = stringResource(R.string.seerr_account_title),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.padding(vertical = Dimens.padding.tiny))

            Text(
                text = if (state.isConnected) {
                    stringResource(
                        R.string.seerr_account_connected_as,
                        state.userDisplayName ?: state.serverUrl.orEmpty()
                    )
                } else {
                    stringResource(R.string.seerr_account_not_connected)
                },
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(Modifier.padding(vertical = Dimens.padding.small))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (state.isConnected) {
                    AppButton(
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.error
                        ),
                        onClick = { viewModel.signOut() }
                    ) {
                        Text(stringResource(R.string.seerr_account_disconnect))
                    }
                } else {
                    AppButton(onClick = onConnectClick) {
                        Text(stringResource(R.string.seerr_account_connect))
                    }
                }
            }
        }
    }
}
