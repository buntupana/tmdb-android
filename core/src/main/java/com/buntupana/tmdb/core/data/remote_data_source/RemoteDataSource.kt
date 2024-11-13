package com.buntupana.tmdb.core.data.remote_data_source

import com.buntupana.tmdb.core.data.raw.ResponseErrorRaw
import com.buntupana.tmdb.core.domain.entity.NetworkError
import com.buntupana.tmdb.core.domain.entity.Resource
import com.buntupana.tmdb.core.domain.entity.Result
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import kotlinx.serialization.SerializationException
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import timber.log.Timber
import java.nio.channels.UnresolvedAddressException

/**
 * Abstract Base Data source class with error handling
 */
abstract class RemoteDataSource {

    private val networkErrorMessage = "Network Error"

    protected suspend fun <T> getResourceResult(call: suspend () -> Response<T>): Resource<T> {
        try {
            val response = call()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) return Resource.Success(body)
            }
            return Resource.Error(getErrorMessage(response))
        } catch (e: Exception) {
            Timber.d(e)
            return Resource.Error(networkErrorMessage)
        }
    }

    private fun <T> getErrorMessage(response: Response<T>): String {

        val errorMessage = try {
            // Creating a dummy retrofit object in order to create a converter
            val errorConverter: Converter<ResponseBody, ResponseErrorRaw> =
                Retrofit.Builder()
                    .baseUrl("http://www.dummy.com")
                    .addConverterFactory(MoshiConverterFactory.create())
                    .build().responseBodyConverter(ResponseErrorRaw::class.java, arrayOf())

            val errorResponse = (errorConverter.convert(response.errorBody()!!) as ResponseErrorRaw)
            errorResponse.statusMessage
        } catch (e: Exception) {
            networkErrorMessage
        }

        return errorMessage
//        return when (response.code()) {
//            401 -> InvalidCredentialException(errorMessage)
//            422 -> UnprocessableEntityException(errorMessage)
//            else -> Exception(errorMessage)
//        }
    }

    suspend inline fun <reified D> getResult(request: () -> HttpResponse): Result<D, NetworkError> {
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
                Result.Success(response.body<D>())
            }

            401 -> {
                Result.Error(NetworkError.UNAUTHORIZED)
            }

            409 -> {
                Result.Error(NetworkError.CONFLICT)
            }

            408 -> {
                Result.Error(NetworkError.REQUEST_TIMEOUT)
            }

            413 -> {
                Result.Error(NetworkError.PAYLOAD_TOO_LARGE)
            }

            in 500..599 -> {
                Result.Error(NetworkError.SERVER_ERROR)
            }

            else -> {
                Result.Error(NetworkError.UNKNOWN)
            }
        }
    }
}

