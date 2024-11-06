package com.buntupana.tmdb.app.presentation.navigation

import android.view.View
import androidx.navigation.NavController
import com.buntupana.tmdb.core.presentation.util.encodeAllUrls
import timber.log.Timber
import kotlin.reflect.KClass

class NavRoutesMainImpl : NavRoutesMain {

    private var navController: NavController? = null

    override fun init(navController: NavController): NavRoutesMainImpl {
        this.navController = navController
        return this
    }

    override fun navigate(
        destination: Routes,
        sharedElements: MutableList<Pair<View, String>>.() -> Unit
    ) {
        navigate<Routes>(
            destination = destination,
            popUpTo = null,
            popUpToInclusive = false,
            sharedElements = sharedElements,
        )
    }

    override fun <T : Routes> navigate(
        destination: Routes,
        popUpTo: KClass<T>?,
        popUpToInclusive: Boolean,
        sharedElements: MutableList<Pair<View, String>>.() -> Unit
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
        getDestinationId(destination)?.let { popUpToId ->
            navController?.popBackStack(popUpToId, inclusive)
        }
    }

    override fun getCurrentDestinationId(): Int? {
        return navController?.currentDestination?.id
    }

    override fun <T : Routes> getDestinationId(
        destination: KClass<in T>
    ): Int? {

        return try {
            navController?.getBackStackEntry(
                route = destination.java.canonicalName!!
            )?.destination?.id
        } catch (e: Exception) {
            try {
                navController?.getBackStackEntry(
                    route = destination.java.canonicalName!! + "/{args}"
                )?.destination?.id
            } catch (e: Exception) {
                Timber.e(e)
                null
            }
        }
    }
}