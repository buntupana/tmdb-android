package com.buntupana.tmdb.feature.seer.di

import com.buntupana.tmdb.feature.seer.data.manager.SeerSessionManagerImpl
import com.buntupana.tmdb.feature.seer.data.provider.SeerStatusProviderImpl
import com.buntupana.tmdb.feature.seer.data.remote_data_source.SeerrRemoteDataSource
import com.buntupana.tmdb.feature.seer.data.repository.SeerRepositoryImpl
import com.buntupana.tmdb.feature.seer.domain.manager.SeerSessionManager
import com.buntupana.tmdb.feature.seer.domain.repository.SeerRepository
import com.buntupana.tmdb.feature.seer.domain.usecase.CreateSeerRequestUseCase
import com.buntupana.tmdb.feature.seer.domain.usecase.GetSeerMediaInfoUseCase
import com.buntupana.tmdb.feature.seer.domain.usecase.SignInSeerJellyfinUseCase
import com.buntupana.tmdb.feature.seer.domain.usecase.SignInSeerLocalUseCase
import com.buntupana.tmdb.feature.seer.domain.usecase.SignOutSeerUseCase
import com.buntupana.tmdb.feature.seer.presentation.request.SeerrRequestViewModel
import com.buntupana.tmdb.feature.seer.presentation.sign_in.SeerSignInViewModel
import com.buntupana.tmdb.feature.seer.presentation.status.SeerStatusViewModel
import com.panabuntu.tmdb.core.common.provider.SeerStatusProvider
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
    singleOf(::SeerSessionManagerImpl) bind SeerSessionManager::class
    singleOf(::SeerRepositoryImpl) bind SeerRepository::class
    singleOf(::SeerStatusProviderImpl) bind SeerStatusProvider::class
}

private val domainModule = module {
    factoryOf(::SignInSeerLocalUseCase)
    factoryOf(::SignInSeerJellyfinUseCase)
    factoryOf(::SignOutSeerUseCase)
    factoryOf(::GetSeerMediaInfoUseCase)
    factoryOf(::CreateSeerRequestUseCase)
}

private val presentationModule = module {
    factoryOf(::SeerSignInViewModel)
    factoryOf(::SeerStatusViewModel)
    factoryOf(::SeerrRequestViewModel)
}

val seerModule = module {
    includes(dataModule, domainModule, presentationModule)
}
