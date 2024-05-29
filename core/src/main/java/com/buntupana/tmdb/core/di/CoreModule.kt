package com.buntupana.tmdb.core.di

import com.buntupana.tmdb.core.BuildConfig
import com.buntupana.tmdb.core.data.api.AuthInterceptor
import com.buntupana.tmdb.core.data.api.CoreApi
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CoreModule {


    @Provides
    @Named("api_key")
    fun provideApiKey(): String = BuildConfig.access_token

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