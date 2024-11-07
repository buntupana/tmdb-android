package com.buntupana.tmdb.feature.account.presentation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun AccountScreen(
    viewModel: AccountViewModel = hiltViewModel()
) {
    AccountContent(viewModel.state)
}

@Composable
fun AccountContent(
    state: AccountState
) {
    Text(text = "Account")
}

@Preview
@Composable
fun AccountScreenPreview() {
    AccountContent(
        AccountState()
    )
}