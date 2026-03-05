package com.buntupana.tmdb.app.presentation

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
inline fun <reified V : ViewModel> getViewModel(navArgs: Any? = null): V {
    return koinViewModel<V>(
        parameters = {
            parametersOf(navArgs)
        }
    )
}