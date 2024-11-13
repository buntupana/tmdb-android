package com.panabuntu.tmdb.core.common

import com.panabuntu.tmdb.core.common.entity.Resource
import kotlinx.coroutines.CancellationException
import timber.log.Timber

suspend fun <T, A> networkResult(
    networkCall: suspend () -> Resource<T>,
    mapResponse: suspend (response: T) -> A
): Resource<A> {

    val network = networkCall.invoke()

    return if (network is Resource.Success) {
        try {
            Resource.Success(mapResponse(network.data))
        } catch (exc: Exception) {

            if (exc is CancellationException) {
                throw exc
            }
//            val networkException = NetworkException(cause = e)
            Timber.e(exc, "Mapping Error: ")
            Resource.Error("Mapping Error")
        }
    } else {
        Resource.Error("Network Error")
    }
}