package com.buntupana.tmdb.app

import android.app.Application
import com.buntupana.tmdb.app.di.appModule
import com.buntupana.tmdb.core.di.commonModule
import com.buntupana.tmdb.feature.account.di.accountModule
import com.buntupana.tmdb.feature.detail.di.searchModule
import com.buntupana.tmdb.feature.discover.di.discoverModule
import com.buntupana.tmdb.feature.lists.di.listsModule
import com.buntupana.tmdb.feature.search.di.detailModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class TMDBApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())

        startKoin {
            // Log Koin into Android logger
            androidLogger()
            // Reference Android context
            androidContext(this@TMDBApplication)
            // Load modules
            modules(
                appModule,
                commonModule,
                accountModule,
                detailModule,
                discoverModule,
                listsModule,
                searchModule,
            )
        }
    }
}