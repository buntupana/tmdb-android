package com.buntupana.tmdb.core.ui.util

import androidx.lifecycle.ViewModel
import com.buntupana.tmdb.core.ui.navigation.Routes

interface ViewModelFactoryDefault<T: Routes, V: ViewModel>{
    fun create(navArgs: T): V
}