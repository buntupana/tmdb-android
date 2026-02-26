package com.buntupana.tmdb.app.presentation.navigation

import androidx.compose.runtime.Composable
import com.buntupana.tmdb.core.ui.navigation.NavRoutesMain
import com.buntupana.tmdb.feature.account.presentation.sign_in.SignInRoute
import com.buntupana.tmdb.feature.account.presentation.sign_in.SignInScreen

@Composable
fun SignInNav(
    navRoutesMain: NavRoutesMain
) {
    SignInScreen(
        onNavigateBack = {
            if (navRoutesMain.isCurrentDestination(SignInRoute::class)) {
                navRoutesMain.popBackStack()
            }
        }
    )
}