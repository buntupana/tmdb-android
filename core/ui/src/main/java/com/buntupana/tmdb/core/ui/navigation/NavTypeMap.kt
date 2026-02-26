package com.buntupana.tmdb.core.ui.navigation

import androidx.navigation.NavType
import kotlin.reflect.KType

interface NavTypeMap {
    val typeMap: Map<KType, NavType<*>>
}