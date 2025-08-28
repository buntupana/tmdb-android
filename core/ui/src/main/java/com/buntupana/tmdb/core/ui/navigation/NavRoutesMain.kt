package com.buntupana.tmdb.core.ui.navigation

import android.content.Intent
import androidx.navigation.NavController
import kotlin.reflect.KClass

interface NavRoutesMain {

    fun init(navController: NavController): NavRoutesMain

    fun handleDeepLing(intent: Intent)

    fun navigate(
        destination: Route
    )

    fun <T : Route> navigate(
        destination: Route,
        popUpTo: KClass<T>?,
        popUpToInclusive: Boolean,
    )

    fun popBackStack()

    fun <T : Route> popBackStack(destination: KClass<in T>, inclusive: Boolean = false)

    fun <T : Route> isCurrentDestination(destination: KClass<T>): Boolean

    fun saveResult(value: Any)
}