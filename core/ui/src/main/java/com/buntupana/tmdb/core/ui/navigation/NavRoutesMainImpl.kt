package com.buntupana.tmdb.core.ui.navigation

import android.annotation.SuppressLint
import android.content.Intent
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.serialization.generateHashCode
import com.panabuntu.tmdb.core.common.encodeAllUrls
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.serializer
import timber.log.Timber
import kotlin.reflect.KClass

class NavRoutesMainImpl : NavRoutesMain {

    private var navController: NavController? = null

    override fun init(navController: NavController): NavRoutesMainImpl {
        this.navController = navController
        return this
    }

    override fun handleDeepLing(intent: Intent) {
        Timber.d("handleDeepLing() called with: intent = [$intent]")
        navController?.handleDeepLink(intent)
    }

    override fun navigate(
        destination: Routes
    ) {
        Timber.d("navigate() called with: destination = [$destination]")
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
        Timber.d("navigate() called with: destination = [$destination], popUpTo = [$popUpTo], popUpToInclusive = [$popUpToInclusive]")
        navController?.navigate(destination.encodeAllUrls())
    }

    override fun popBackStack() {
        Timber.d("popBackStack() called")
        navController?.popBackStack()
    }

    @SuppressLint("RestrictedApi")
    @OptIn(InternalSerializationApi::class)
    override fun <T : Routes> popBackStack(
        destination: KClass<in T>,
        inclusive: Boolean
    ) {
        navController?.popBackStack(destination.serializer().generateHashCode(), inclusive)
    }

    override fun <T : Routes> isCurrentDestination(destination: KClass<T>): Boolean {
        Timber.d("isCurrentDestination() called with: destination = [$destination]")
        return navController?.currentDestination?.hasRoute(destination) ?: false
    }
}