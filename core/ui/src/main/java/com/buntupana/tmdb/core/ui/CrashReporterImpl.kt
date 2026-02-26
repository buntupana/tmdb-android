package com.buntupana.tmdb.core.ui

import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.panabuntu.tmdb.core.common.CrashReporter

class CrashReporterImpl : CrashReporter {

    override fun log(message: String) {
        FirebaseCrashlytics.getInstance().log(message)
    }

    override fun recordException(throwable: Throwable) {
        FirebaseCrashlytics.getInstance().recordException(throwable)
    }
}