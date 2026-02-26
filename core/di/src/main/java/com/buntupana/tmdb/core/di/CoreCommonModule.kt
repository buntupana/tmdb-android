package com.buntupana.tmdb.core.di

import com.buntupana.tmdb.core.data.database.TmdbDataBase
import com.buntupana.tmdb.core.data.manager.SessionManagerImpl
import com.buntupana.tmdb.core.data.provider.UrlProviderImpl
import com.buntupana.tmdb.core.ui.CrashReporterImpl
import com.buntupana.tmdb.core.ui.navigation.NavRoutesMain
import com.buntupana.tmdb.core.ui.navigation.NavRoutesMainImpl
import com.panabuntu.tmdb.core.common.CrashReporter
import com.panabuntu.tmdb.core.common.manager.SessionManager
import com.panabuntu.tmdb.core.common.provider.UrlProvider
import com.panabuntu.tmdb.core.common.util.ifNullOrBlank
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
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import timber.log.Timber


val commonModule = module {

    singleOf(::NavRoutesMainImpl) bind NavRoutesMain::class

    singleOf(::UrlProviderImpl) bind UrlProvider::class

    singleOf(::SessionManagerImpl) bind SessionManager::class

    single<TmdbDataBase> {
        TmdbDataBase.newInstance(context = androidApplication())
    }

    single<HttpClient> {

        val urlProvider: UrlProvider = get()
        val sessionManager: SessionManager = get()

        HttpClient(OkHttp) {
            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        Timber.d(message)
                    }
                }
                level = LogLevel.INFO
            }
            install(DefaultRequest) {
                url(urlProvider.BASE_URL_API)
                header(HttpHeaders.ContentType, ContentType.Application.Json)
            }
            install(Auth) {

                bearer {
                    loadTokens {
                        BearerTokens(
                            accessToken = sessionManager.session.value.accessToken.ifNullOrBlank { urlProvider.API_KEY },
                            refreshToken = ""
                        )
                    }
                    refreshTokens {
                        BearerTokens(
                            accessToken = sessionManager.session.value.accessToken.ifNullOrBlank { urlProvider.API_KEY },
                            refreshToken = ""
                        )
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

    singleOf(::CrashReporterImpl) bind CrashReporter::class
}