package com.buntupana.tmdb.app.presentation.navigation

import android.view.View
import androidx.navigation.NavController
import kotlin.reflect.KClass

interface NavRoutesMain {

    fun init(navController: NavController): NavRoutesMain

    fun navigate(
        destination: Routes,
        sharedElements: (MutableList<Pair<View, String>>.() -> Unit) = {}
    )

    fun <T : Routes> navigate(
        destination: Routes,
        popUpTo: KClass<T>?,
        popUpToInclusive: Boolean,
        sharedElements: (MutableList<Pair<View, String>>.() -> Unit)
    )

    fun popBackStack()

    fun <T : Routes> popBackStack(destination: KClass<in T>, inclusive: Boolean = false)

    fun getCurrentDestinationId(): Int?

    fun <T : Routes> getDestinationId(destination: KClass<in T>): Int?
}