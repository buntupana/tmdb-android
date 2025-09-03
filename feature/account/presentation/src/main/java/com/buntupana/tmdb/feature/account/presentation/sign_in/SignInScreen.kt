package com.buntupana.tmdb.feature.account.presentation.sign_in

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.buntupana.tmdb.core.ui.composables.ErrorAndRetry
import com.buntupana.tmdb.core.ui.theme.AppTheme
import com.buntupana.tmdb.core.ui.util.SetSystemBarsColors
import com.buntupana.tmdb.core.ui.util.getCustomTabIntent
import com.buntupana.tmdb.feature.account.presentation.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import timber.log.Timber
import com.buntupana.tmdb.core.ui.R as RCore

@Composable
fun SignInScreen(
    viewModel: SignInViewModel = koinViewModel(),
    onNavigateBack: () -> Unit,
) {

    val scope = rememberCoroutineScope()

    val customTabLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        scope.launch {
            // delaying this to let deeplink handle the url
            delay(200)
            onNavigateBack()
        }
    }

    val customTabToolbarColor = MaterialTheme.colorScheme.primary

    LaunchedEffect(viewModel.sideEffect) {
        viewModel.sideEffect.collect { sideEffect ->
            Timber.d("sideEffect = [$sideEffect]")
            when (sideEffect) {
                is SignInSideEffect.CreatedRequestTokenSuccess -> {
                    customTabLauncher.launch(
                        getCustomTabIntent(
                            url = sideEffect.authenticationUrl,
                            toolbarColor = customTabToolbarColor
                        )
                    )
                }

                SignInSideEffect.LoginSuccess -> {
                    onNavigateBack()
                }
            }
        }
    }

    SignInContent(
        viewModel.state,
        onRetryClicked = {
            viewModel.onEvent(SignInEvent.CreateRequestToken)
        }
    )
}

@Composable
fun SignInContent(
    state: SignInState,
    onRetryClicked: () -> Unit
) {

    SetSystemBarsColors(
        statusBarColor = MaterialTheme.colorScheme.background,
        navigationBarColor = MaterialTheme.colorScheme.background,
        translucentNavigationBar = false
    )

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.background)
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround
        ) {
            Image(
                painter = painterResource(RCore.drawable.img_logo),
                contentDescription = "Logo"
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom
            ) {
                if (state.isLoading) {
                    Text(
                        text = stringResource(R.string.text_signing_in),
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Spacer(
                        modifier = Modifier.padding(vertical = 20.dp)
                    )
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.onBackground
                    )
                } else if (state.isSignInError) {
                    ErrorAndRetry(
                        textColor = MaterialTheme.colorScheme.onBackground,
                        errorMessage = stringResource(R.string.text_signing_in_error),
                        onRetryClick = onRetryClicked
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun SignInScreenPreview() {
    AppTheme(darkTheme = true) {
        SignInContent(
            SignInState(
                isSignInError = true,
                isLoading = true
            ),
            onRetryClicked = {}
        )
    }
}