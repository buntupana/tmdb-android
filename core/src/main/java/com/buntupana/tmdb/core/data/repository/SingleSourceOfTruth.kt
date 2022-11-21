package com.buntupana.tmdb.core.data

import com.buntupana.tmdb.core.domain.entity.Resource
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
            // TODO: add proper error message
            Resource.Error("Mapping Error")
        }
    } else {
        Resource.Error("Network Error")
    }
}