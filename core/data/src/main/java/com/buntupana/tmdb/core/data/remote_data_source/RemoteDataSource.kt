package com.buntupana.tmdb.core.data.remote_data_source

import com.panabuntu.tmdb.core.common.entity.NetworkError
import com.panabuntu.tmdb.core.common.entity.Result
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import kotlinx.serialization.SerializationException
import timber.log.Timber
import java.nio.channels.UnresolvedAddressException

/**
 * Abstract Base Data source class with error handling
 */
abstract class RemoteDataSource {

    protected suspend inline fun <reified D> getResult(request: () -> HttpResponse): Result<D, NetworkError> {
        val response = try {
            request()
        } catch (e: UnresolvedAddressException) {
            Timber.e(e)
            return Result.Error(NetworkError.NO_INTERNET)
        } catch (e: SerializationException) {
            Timber.e(e)
            return Result.Error(NetworkError.SERIALIZATION)
        } catch (e: Exception) {
            Timber.e(e)
            return Result.Error(NetworkError.UNKNOWN)
        }

        return when (response.status.value) {

            in 200..299 -> {
                try {
                    Result.Success(response.body<D>())
                } catch (e: Exception) {
                    Timber.d(e)
                    Result.Error(NetworkError.SERIALIZATION)
                }
            }

            401 -> Result.Error(NetworkError.UNAUTHORIZED)

            404 -> Result.Error(NetworkError.NOT_FOUND)

            409 -> Result.Error(NetworkError.CONFLICT)

            408 -> Result.Error(NetworkError.REQUEST_TIMEOUT)

            413 -> Result.Error(NetworkError.PAYLOAD_TOO_LARGE)

            in 500..599 -> Result.Error(NetworkError.SERVER_ERROR)

            else -> Result.Error(NetworkError.UNKNOWN)

        }
    }
}

