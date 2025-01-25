package com.buntupana.tmdb.core.data.mapper

import com.panabuntu.tmdb.core.common.model.Order

fun Order.toApi(): String {
    return when(this) {
        Order.ASC -> "asc"
        Order.DESC -> "desc"
    }
}