package com.buntupana.tmdb.feature.seerr.di

import com.buntupana.tmdb.feature.seerr.data.manager.SeerrSessionManagerImpl
import com.buntupana.tmdb.feature.seerr.data.provider.SeerrStatusProviderImpl
import com.buntupana.tmdb.feature.seerr.data.remote_data_source.SeerrRemoteDataSource
import com.buntupana.tmdb.feature.seerr.data.repository.SeerrRepositoryImpl
import com.buntupana.tmdb.feature.seerr.domain.manager.SeerrSessionManager
import com.buntupana.tmdb.feature.seerr.domain.repository.SeerrRepository
import com.buntupana.tmdb.feature.seerr.domain.usecase.CreateSeerrRequestUseCase
import com.buntupana.tmdb.feature.seerr.domain.usecase.GetSeerrMediaInfoUseCase
import com.buntupana.tmdb.feature.seerr.domain.usecase.SignInSeerLocalUseCase
import com.buntupana.tmdb.feature.seerr.domain.usecase.SignInSeerrJellyfinUseCase
import com.buntupana.tmdb.feature.seerr.domain.usecase.SignOutSeerrUseCase
import com.buntupana.tmdb.feature.seerr.presentation.request.SeerrRequestViewModel
import com.buntupana.tmdb.feature.seerr.presentation.sign_in.SeerrSignInViewModel
import com.buntupana.tmdb.feature.seerr.presentation.status.SeerStatusViewModel
import com.panabuntu.tmdb.core.common.provider.SeerrStatusProvider
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module
import timber.log.Timber

private val SEER_HTTP_CLIENT = named("seerHttpClient")

private val dataModule = module {

    single(SEER_HTTP_CLIENT) {
        HttpClient(OkHttp) {
            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        Timber.tag("SeerHttp").d(message)
                    }
                }
                level = LogLevel.INFO
            }
            install(ContentNegotiation) {
                json(
                    json = Json {
                        ignoreUnknownKeys = true
                        encodeDefaults = true
                    }
                )
            }
            expectSuccess = false
        }
    }

    single { SeerrRemoteDataSource(httpClient = get(SEER_HTTP_CLIENT)) }
    singleOf(::SeerrSessionManagerImpl) bind SeerrSessionManager::class
    singleOf(::SeerrRepositoryImpl) bind SeerrRepository::class
    singleOf(::SeerrStatusProviderImpl) bind SeerrStatusProvider::class
}

private val domainModule = module {
    factoryOf(::SignInSeerLocalUseCase)
    factoryOf(::SignInSeerrJellyfinUseCase)
    factoryOf(::SignOutSeerrUseCase)
    factoryOf(::GetSeerrMediaInfoUseCase)
    factoryOf(::CreateSeerrRequestUseCase)
}

private val presentationModule = module {
    factoryOf(::SeerrSignInViewModel)
    factoryOf(::SeerStatusViewModel)
    factoryOf(::SeerrRequestViewModel)
}

val seerrModule = module {
    includes(dataModule, domainModule, presentationModule)
}
