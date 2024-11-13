package com.buntupana.tmdb.feature.account.presentation.account

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun AccountScreen(
    viewModel: AccountViewModel = hiltViewModel(),
    onSignInClicked : () -> Unit
) {

    AccountContent(
        viewModel.state,
        onSignInClicked = onSignInClicked
    )
}

@Composable
fun AccountContent(
    state: AccountState,
    onSignInClicked : () -> Unit
) {


    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = {
                onSignInClicked()
            }
        ) {
            Text(text = "Sing In")
        }
    }
}

@Preview
@Composable
fun AccountScreenPreview() {
    AccountContent(
        AccountState(),
        onSignInClicked = {}
    )
}