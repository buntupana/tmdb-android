package com.panabuntu.tmdb.core.common.di

import com.panabuntu.tmdb.core.common.BuildConfig
import com.panabuntu.tmdb.core.common.api.AuthInterceptor
import com.panabuntu.tmdb.core.common.api.CoreApi
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CoreCommonModule {

    @Provides
    @Named("api_key")
    fun provideApiKey(): String = BuildConfig.tmdb_api_key

    @Singleton
    @Provides
    fun provideHttpClient(
        @Named("api_key") apiKey: String,
    ): HttpClient {
        return HttpClient(OkHttp) {
            install(Logging) {
                level = LogLevel.ALL
            }
            install(DefaultRequest) {
                url(CoreApi.BASE_URL_API)
//                header(HttpHeaders.ContentType, ContentType.Application.Json)
//                header("X-Api-Key", apiKey)
            }
            install(Auth) {
                bearer {
                    loadTokens {
                        BearerTokens(
                            accessToken = apiKey,
                            refreshToken = ""
                        )
                    }
                }
            }
            install(ContentNegotiation) {
                json(
                    json = Json { ignoreUnknownKeys = true }
                )
            }
        }
    }

    @Singleton
    @Provides
    fun provideRetrofit(
        @Named("api_key") apiKey: String,
    ): Retrofit {

        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BASIC

        val authInterceptor = AuthInterceptor(apiKey)

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(logging)
            .addInterceptor(authInterceptor)
            .build()

        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        return Retrofit.Builder()
            .baseUrl(CoreApi.BASE_URL_API)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }
}