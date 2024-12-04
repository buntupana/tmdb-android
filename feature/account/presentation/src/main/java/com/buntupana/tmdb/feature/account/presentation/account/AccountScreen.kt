package com.buntupana.tmdb.feature.account.presentation.account

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Login
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.buntupana.tmdb.core.ui.theme.Dimens
import com.buntupana.tmdb.feature.account.presentation.R
import com.buntupana.tmdb.feature.account.presentation.account.composables.AccountInfoTop
import com.buntupana.tmdb.feature.account.presentation.sign_out.SignOutDialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountScreen(
    viewModel: AccountViewModel = hiltViewModel(),
    onSignInClicked: () -> Unit
) {

    var showBottomSheet by remember { mutableStateOf(false) }

    AccountContent(
        viewModel.state,
        onSignInClick = onSignInClicked,
        onSignOutClick = {
            showBottomSheet = true
        }
    )

    SignOutDialog(
        showDialog = showBottomSheet,
        onDismiss = { showBottomSheet = false }
    )
}

@Composable
fun AccountContent(
    state: AccountState,
    onSignInClick: () -> Unit,
    onSignOutClick: () -> Unit
) {

    if (state.isUserLogged) {

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AccountInfoTop(
                modifier = Modifier.fillMaxWidth(),
                avatarUrl = state.avatarUrl,
                username = state.username
            )
            Spacer(Modifier.padding(vertical = Dimens.padding.vertical))
            Button(
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                onClick = onSignOutClick
            ) {
                Icon(Icons.AutoMirrored.Filled.Login, contentDescription = null)
                Spacer(modifier = Modifier.padding(horizontal = Dimens.padding.tiny))
                Text(text = stringResource(R.string.text_sign_out))
            }
        }
    } else {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = onSignInClick,
            ) {
                Icon(Icons.AutoMirrored.Filled.Logout, contentDescription = null)
                Spacer(modifier = Modifier.padding(horizontal = Dimens.padding.tiny))
                Text(text = stringResource(R.string.text_sign_in))
            }
        }
    }
}

@Preview
@Composable
fun AccountScreenPreview() {
    AccountContent(
        AccountState(
            isUserLogged = true,
            username = "Alvaro"
        ),
        onSignInClick = {},
        onSignOutClick = {}
    )
}