package com.buntupana.tmdb.core.ui.navigation

import android.content.Intent
import androidx.navigation.NavController
import com.buntupana.tmdb.core.ui.navigation.NavRoutesMain.Companion.RESULT_DEFAULT_KEY
import kotlinx.coroutines.flow.StateFlow
import kotlin.reflect.KClass

/**
 * Interface defining the navigation routes for the main application flow.
 *
 * This interface provides methods for initializing the navigation controller,
 * handling deep links, navigating to different destinations, managing the back stack,
 * and exchanging data between destinations.
 */
interface NavRoutesMain {

    companion object {
        private const val RESULT_DEFAULT_KEY = "RESULT_DEFAULT_KEY"
    }

    fun init(navController: NavController): NavRoutesMain

    fun handleDeepLink(intent: Intent)

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

    fun saveResult(value: Any, key: String = RESULT_DEFAULT_KEY)

    /**
     * Retrieves a [StateFlow] that emits the result passed from a previous screen.
     *
     * This function allows observing results passed back from other destinations using the Navigation component.
     * The result is associated with a specific key. If no result is found for the given key,
     * the [StateFlow] will emit `null`. This function is mostly used to retrieve results from dialogs and bottomsheets
     *
     * @param T The type of the expected result.
     * @param key The key used to store and retrieve the result. Defaults to [RESULT_DEFAULT_KEY].
     * @return A [StateFlow] emitting the result of type [T] or `null` if no result is available for the given key.
     *         Returns `null` if the underlying mechanism to retrieve the result is not available (e.g., if the
     *         NavController's current back stack entry is not found).
     */
    fun <T>getStateFlowResult(key: String = RESULT_DEFAULT_KEY): StateFlow<T?>?

    fun <T> getResult(key: String = RESULT_DEFAULT_KEY): T?
}