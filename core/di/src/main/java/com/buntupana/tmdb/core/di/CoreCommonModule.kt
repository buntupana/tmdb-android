package com.buntupana.tmdb.core.di

import android.content.Context
import com.buntupana.tmdb.core.data.database.TmdbDataBase
import com.buntupana.tmdb.core.data.manager.SessionManagerImpl
import com.buntupana.tmdb.core.data.provider.UrlProviderImpl
import com.panabuntu.tmdb.core.common.manager.SessionManager
import com.panabuntu.tmdb.core.common.provider.UrlProvider
import com.panabuntu.tmdb.core.common.util.ifNullOrBlank
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
    fun provideHttpClient(sessionManager: SessionManager, urlProvider: UrlProvider): HttpClient {
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

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext app: Context): TmdbDataBase {
        return TmdbDataBase.newInstance(context = app)
    }

    @Singleton
    @Provides
    fun provideRemoteKeyDao(db: TmdbDataBase) = db.remoteKeyDao

    @Singleton
    @Provides
    fun provideEpisodesDao(db: TmdbDataBase) = db.episodesDao

    @Singleton
    @Provides
    fun provideAnyMediaDao(db: TmdbDataBase) = db.mediaDao

    @Singleton
    @Provides
    fun provideWatchlistDao(db: TmdbDataBase) = db.watchlistDao

    @Singleton
    @Provides
    fun provideFavoriteDao(db: TmdbDataBase) = db.favoriteDao

    @Singleton
    @Provides
    fun provideListDao(db: TmdbDataBase) = db.userListDetailsDao

    @Singleton
    @Provides
    fun provideUserListItemDao(db: TmdbDataBase) = db.userListItemDao

    @Singleton
    @Provides
    fun provideSessionManager(@ApplicationContext context: Context): SessionManager {
        return SessionManagerImpl(context)
    }
}