package com.buntupana.tmdb.core.data.remote_data_source

import com.buntupana.tmdb.core.data.raw.ResponseErrorRaw
import com.buntupana.tmdb.core.domain.entity.Resource
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import timber.log.Timber

/**
 * Abstract Base Data source class with error handling
 */
abstract class RemoteDataSource {

//    private val networkErrorMessage = resourcesProvider.getString(R.string.message_error_connection)
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
}

