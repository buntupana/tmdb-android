package com.buntupana.tmdb.app.presentation.navigation

import androidx.compose.runtime.Composable
import com.buntupana.tmdb.core.ui.navigation.NavRoutesMain
import com.buntupana.tmdb.feature.seerr.presentation.sign_in.SeerrSignInRoute
import com.buntupana.tmdb.feature.seerr.presentation.sign_in.SeerrSignInScreen

@Composable
fun SeerrSignInNav(
    navRoutesMain: NavRoutesMain
) {
    SeerrSignInScreen(
        onNavigateBack = {
            if (navRoutesMain.isCurrentDestination(SeerrSignInRoute::class)) {
                navRoutesMain.popBackStack()
            }
        }
    )
}
