package com.buntupana.tmdb.core.domain.entity

// A generic class that contains data and status about loading this data.
sealed class Resource<T> {
    class Success<T>(val data: T) : Resource<T>()
    class Error<T>(val message: String, val data: T? = null) : Resource<T>()
}