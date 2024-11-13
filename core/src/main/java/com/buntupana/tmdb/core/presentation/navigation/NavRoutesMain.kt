package com.buntupana.tmdb.core.presentation.navigation

import android.content.Intent
import androidx.navigation.NavController
import kotlin.reflect.KClass

interface NavRoutesMain {

    fun init(navController: NavController): NavRoutesMain

    fun handleDeepLing(intent: Intent)

    fun navigate(
        destination: Routes
    )

    fun <T : Routes> navigate(
        destination: Routes,
        popUpTo: KClass<T>?,
        popUpToInclusive: Boolean,
    )

    fun popBackStack()

    fun <T : Routes> popBackStack(destination: KClass<in T>, inclusive: Boolean = false)

    fun getCurrentDestinationId(): Int?

    fun <T : Routes> getDestinationId(destination: KClass<in T>): Int?

    fun <T : Routes> isCurrentDestination(destination: KClass<T>): Boolean
}