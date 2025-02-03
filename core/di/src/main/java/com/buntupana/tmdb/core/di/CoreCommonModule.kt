package com.buntupana.tmdb.core.di

import android.content.Context
import com.buntupana.tmdb.core.data.api.UrlProviderImpl
import com.buntupana.tmdb.core.data.manager.SessionManagerImpl
import com.panabuntu.tmdb.core.common.manager.SessionManager
import com.panabuntu.tmdb.core.common.provider.UrlProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import timber.log.Timber
import javax.inject.Named
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
    fun provideHttpClientV3(urlProvider: UrlProvider): HttpClient {
        return provideHttpClient(
            baseUrl = urlProvider.BASE_URL_API_V3,
            apiKey = urlProvider.API_KEY
        )
    }

    @Singleton
    @Provides
    @Named("ApiV4")
    fun provideHttpClientV4(urlProvider: UrlProvider, sessionManager: SessionManager): HttpClient {
        return provideHttpClient(
            baseUrl = urlProvider.BASE_URL_API_V4,
            apiKey = sessionManager.session.value.accessToken.orEmpty()
        )
    }

    private fun provideHttpClient(baseUrl: String, apiKey: String): HttpClient {
        return HttpClient(OkHttp) {
            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        Timber.d(message)
                    }
                }
                level = LogLevel.INFO
            }
            install(DefaultRequest) {
                url(baseUrl)
                header(HttpHeaders.ContentType, ContentType.Application.Json)
            }
            install(Auth) {
                bearer {
                    loadTokens {
                        BearerTokens(accessToken = apiKey, refreshToken = "")
                    }
                    refreshTokens {
                        BearerTokens(accessToken = apiKey, refreshToken = "")
                    }
                }
            }
            install(ContentNegotiation) {
                json(
                    json = Json {
                        ignoreUnknownKeys = true
                        encodeDefaults = true
                    }
                )
            }
        }
    }

    @Singleton
    @Provides
    fun provideSessionManager(@ApplicationContext context: Context): SessionManager {
        return SessionManagerImpl(context)
    }
}