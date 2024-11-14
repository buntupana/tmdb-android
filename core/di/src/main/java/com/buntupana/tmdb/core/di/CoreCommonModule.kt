package com.buntupana.tmdb.core.di

import com.buntupana.tmdb.data.api.AuthInterceptor
import com.buntupana.tmdb.data.api.UrlProviderImpl
import com.panabuntu.tmdb.core.common.UrlProvider
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
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CoreCommonModule {

    @Singleton
    @Provides
    fun provideUrlProvider(): UrlProvider {
        return UrlProviderImpl()
    }
    
    @Singleton
    @Provides
    fun provideHttpClient(urlProvider: UrlProvider): HttpClient {
        return HttpClient(OkHttp) {
            install(Logging) {
                level = LogLevel.ALL
            }
            install(DefaultRequest) {
                url(urlProvider.BASE_URL_API)
//                header(HttpHeaders.ContentType, ContentType.Application.Json)
//                header("X-Api-Key", apiKey)
            }
            install(Auth) {
                bearer {
                    loadTokens {
                        BearerTokens(
                            accessToken = urlProvider.API_KEY,
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
    fun provideRetrofit(urlProvider: UrlProvider): Retrofit {

        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BASIC

        val authInterceptor = AuthInterceptor(urlProvider.API_KEY)

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(logging)
            .addInterceptor(authInterceptor)
            .build()

        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        return Retrofit.Builder()
            .baseUrl(urlProvider.BASE_URL_API)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }
}