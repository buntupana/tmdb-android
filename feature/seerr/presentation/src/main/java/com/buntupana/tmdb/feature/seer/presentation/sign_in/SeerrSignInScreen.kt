package com.buntupana.tmdb.feature.seer.presentation.sign_in

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import com.buntupana.tmdb.core.ui.composables.widget.AppButton
import com.buntupana.tmdb.core.ui.theme.AppTheme
import com.buntupana.tmdb.core.ui.theme.Dimens
import com.buntupana.tmdb.feature.seer.domain.model.SeerAuthType
import com.buntupana.tmdb.feature.seer.presentation.R
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SeerrSignInScreen(
    viewModel: SeerSignInViewModel = koinViewModel(),
    onNavigateBack: () -> Unit
) {

    LaunchedEffect(viewModel.sideEffect) {
        viewModel.sideEffect.collect { effect ->
            when (effect) {
                SeerSignInSideEffect.LoginSuccess -> onNavigateBack()
            }
        }
    }

    SeerSignInContent(
        state = viewModel.state,
        onNavigateBack = onNavigateBack,
        onServerUrlChanged = { serverUrl ->
            viewModel.onEvent(SeerSignInEvent.ServerUrlChanged(serverUrl))
        },
        onUsernameChanged = { username ->
            viewModel.onEvent(SeerSignInEvent.UsernameChanged(username))
        },
        onPasswordChanged = { password ->
            viewModel.onEvent(SeerSignInEvent.PasswordChanged(password))
        },
        onAuthChanged = { authType ->
            viewModel.onEvent(SeerSignInEvent.AuthTypeChanged(authType))
        },
        onSubmitClick = {
            viewModel.onEvent(SeerSignInEvent.Submit)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SeerSignInContent(
    state: SeerSignInState,
    onNavigateBack: () -> Unit,
    onServerUrlChanged: (serverUrl: String) -> Unit,
    onUsernameChanged: (username: String) -> Unit,
    onPasswordChanged: (password: String) -> Unit,
    onAuthChanged: (authType: SeerAuthType) -> Unit,
    onSubmitClick: () -> Unit
) {

    var passwordVisible by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(stringResource(R.string.seerr_sign_in_title)) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = Dimens.padding.horizontal),
            verticalArrangement = Arrangement.spacedBy(Dimens.padding.medium)
        ) {

            Spacer(Modifier.height(Dimens.padding.medium))

            TabRow(selectedTabIndex = state.authType.ordinal) {
                SeerAuthType.entries.forEachIndexed { index, type ->
                    Tab(
                        selected = state.authType.ordinal == index,
                        onClick = { onAuthChanged(type) },
                        text = {
                            Text(
                                when (type) {
                                    SeerAuthType.LOCAL -> stringResource(R.string.seerr_auth_local)
                                    SeerAuthType.JELLYFIN -> stringResource(R.string.seerr_auth_jellyfin)
                                }
                            )
                        }
                    )
                }
            }

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = state.serverUrl,
                onValueChange = onServerUrlChanged,
                label = { Text(stringResource(R.string.seerr_server_url_label)) },
                placeholder = { Text(stringResource(R.string.seerr_server_url_placeholder)) },
                singleLine = true,
                enabled = !state.isLoading
            )

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = state.username,
                onValueChange = onUsernameChanged,
                label = {
                    Text(
                        when (state.authType) {
                            SeerAuthType.LOCAL -> stringResource(R.string.seerr_email_label)
                            SeerAuthType.JELLYFIN -> stringResource(R.string.seerr_username_label)
                        }
                    )
                },
                singleLine = true,
                enabled = !state.isLoading
            )

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = state.password,
                onValueChange = onPasswordChanged,
                label = { Text(stringResource(R.string.seerr_password_label)) },
                singleLine = true,
                enabled = !state.isLoading,
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            imageVector = if (passwordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                            contentDescription = null
                        )
                    }
                }
            )

            if (state.isError) {
                Text(
                    text = stringResource(R.string.seerr_sign_in_error) +
                            (state.errorMessage?.let { ": $it" } ?: ""),
                    color = MaterialTheme.colorScheme.error
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (state.isLoading) {
                    CircularProgressIndicator()
                } else {
                    AppButton(
                        onClick = onSubmitClick,
                        enabled = state.isFormValid
                    ) {
                        Text(stringResource(R.string.seerr_sign_in_button))
                    }
                }
            }

            Spacer(Modifier.height(Dimens.padding.big))
        }
    }
}

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    name = "DefaultPreviewLight",
    showBackground = true,
    heightDp = 1150
)
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "DefaultPreviewDark",
    showBackground = true,
    heightDp = 1150
)
@Composable
private fun SeerSignInScreenPreview() {
    AppTheme {
        SeerSignInContent(
            state = SeerSignInState(),
            onNavigateBack = {},
            onServerUrlChanged = { },
            onUsernameChanged = { },
            onPasswordChanged = {},
            onAuthChanged = {},
            onSubmitClick = {}
        )
    }
}
