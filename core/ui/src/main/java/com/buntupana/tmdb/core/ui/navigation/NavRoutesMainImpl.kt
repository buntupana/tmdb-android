package com.buntupana.tmdb.core.ui.navigation

import android.content.Intent
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import com.panabuntu.tmdb.core.common.encodeAllUrls
import kotlin.reflect.KClass

class NavRoutesMainImpl : NavRoutesMain {

    private var navController: NavController? = null

    override fun init(navController: NavController): NavRoutesMainImpl {
        this.navController = navController
        return this
    }

    override fun handleDeepLing(intent: Intent) {
        navController?.handleDeepLink(intent)
    }

    override fun navigate(
        destination: Routes
    ) {
        navigate<Routes>(
            destination = destination,
            popUpTo = null,
            popUpToInclusive = false
        )
    }

    override fun <T : Routes> navigate(
        destination: Routes,
        popUpTo: KClass<T>?,
        popUpToInclusive: Boolean
    ) {
        navController?.navigate(destination.encodeAllUrls())
    }

    override fun popBackStack() {
        navController?.popBackStack()
    }

    override fun <T : Routes> popBackStack(
        destination: KClass<in T>,
        inclusive: Boolean
    ) {
        navController?.popBackStack(destination, inclusive)
    }

    override fun <T : Routes> isCurrentDestination(destination: KClass<T>): Boolean {
        return navController?.currentDestination?.hasRoute(destination) ?: false
    }
}