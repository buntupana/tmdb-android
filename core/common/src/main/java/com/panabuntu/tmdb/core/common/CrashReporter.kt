package com.panabuntu.tmdb.core.common

interface CrashReporter {
    fun log(message: String)
    fun recordException(throwable: Throwable)
}
