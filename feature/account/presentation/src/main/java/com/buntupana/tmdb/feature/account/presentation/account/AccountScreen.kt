package com.buntupana.tmdb.feature.account.presentation.account

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.buntupana.tmdb.core.ui.composables.ImageFromUrl
import com.buntupana.tmdb.core.ui.theme.Dimens
import com.buntupana.tmdb.feature.account.presentation.sign_out.SignOutDialog

@Composable
fun AccountScreen(
    viewModel: AccountViewModel = hiltViewModel(),
    onSignInClicked: () -> Unit
) {

    AccountContent(
        viewModel.state,
        onSignInClick = onSignInClicked
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountContent(
    state: AccountState,
    onSignInClick: () -> Unit
) {

    var showBottomSheet by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (state.isUserLogged) {
            ImageFromUrl(
                modifier = Modifier.size(100.dp),
                imageUrl = state.avatarUrl
            )
            Spacer(Modifier.padding(vertical = Dimens.padding.vertical))
            Text(
                text = state.username.orEmpty()
            )
            Spacer(Modifier.padding(vertical = Dimens.padding.vertical))
            Button(
                onClick = {
                    showBottomSheet = true
                }
            ) {
                Text(text = "Sing Out")
            }
        } else {
            Button(
                onClick = onSignInClick
            ) {
                Text(text = "Sing In")
            }
        }
        SignOutDialog(
            showDialog = showBottomSheet,
            onDismiss = { showBottomSheet = false }
        )
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
    )
}