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
import androidx.hilt.navigation.compose.hiltViewModel
import com.buntupana.tmdb.core.ui.composables.ErrorAndRetry
import com.buntupana.tmdb.core.ui.theme.PrimaryColor
import com.buntupana.tmdb.core.ui.util.getCustomTabIntent
import com.buntupana.tmdb.core.ui.util.getOnBackgroundColor
import com.buntupana.tmdb.core.ui.util.setStatusNavigationBarColor
import com.buntupana.tmdb.feature.account.presentation.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber
import com.buntupana.tmdb.core.ui.R as RCore

@Composable
fun SignInScreen(
    viewModel: SignInViewModel = hiltViewModel(),
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

    LaunchedEffect(viewModel.sideEffect) {
        viewModel.sideEffect.collect { sideEffect ->
            Timber.d("sideEffect = [$sideEffect]")
            when (sideEffect) {
                is SignInSideEffect.CreatedRequestTokenSuccess -> {
                    customTabLauncher.launch(getCustomTabIntent(sideEffect.authenticationUrl))
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
    Column(
        modifier = Modifier
            .fillMaxSize()
            .setStatusNavigationBarColor(backgroundColor = PrimaryColor)
            .background(color = PrimaryColor),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            modifier = Modifier.padding(top = 100.dp),
            painter = painterResource(RCore.drawable.img_logo),
            contentDescription = "Logo"
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 100.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {
            if (state.isLoading) {
                Text(
                    text = stringResource(R.string.text_signing_in),
                    color =  MaterialTheme.colorScheme.onPrimaryContainer
                )
                Spacer(
                    modifier = Modifier.padding(vertical = 20.dp)
                )
                CircularProgressIndicator(
                    color = PrimaryColor.getOnBackgroundColor()
                )
            } else if (state.isSignInError) {
                ErrorAndRetry(
                    textColor = PrimaryColor.getOnBackgroundColor(),
                    errorMessage = stringResource(R.string.text_signing_in_error),
                    onRetryClick = onRetryClicked
                )
            }
        }
    }
}

@Preview
@Composable
fun SignInScreenPreview() {
    SignInContent(
        SignInState(isSignInError = true, isLoading = false),
        onRetryClicked = {}
    )
}