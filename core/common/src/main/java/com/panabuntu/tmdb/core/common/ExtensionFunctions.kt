package com.panabuntu.tmdb.core.common

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract


fun LocalDate.toLocalFormat(): String {
    val dateFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT).withLocale(
        java.util.Locale.getDefault()
    )
    return this.format(dateFormatter)
}

fun LocalDate.toFullDate(): String {
    val dateFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG).withLocale(
        java.util.Locale.getDefault()
    )
    return this.format(dateFormatter)
}

@OptIn(ExperimentalContracts::class)
fun CharSequence?.isNotNullOrBlank(): Boolean {
    contract {
        returns(true) implies (this@isNotNullOrBlank != null)
    }
    return this.isNullOrBlank().not()
}

fun <T> T?.ifNull(block: () -> T) = this ?: block()

fun <T> Any?.ifNotNull(block: () -> T?) = if (this != null) block() else null

fun String?.ifNullOrBlank(block: () -> String): String {
    return if (this.isNullOrBlank()) {
        block()
    } else {
        this
    }
}

fun String?.ifNotNullOrBlank(block: () -> String): String? {
    return if (this.isNullOrBlank()) {
        this
    } else {
        block()
    }
}

fun formatTime(time: Int): String {
    var result = ""
    val hours = time / 60
    val minutes = time % 60

    if (hours != 0) {
        result += "${hours}h "
    }
    if (minutes != 0) {
        result += "${minutes}m"
    }

    return result
}

@OptIn(ExperimentalContracts::class)
fun <T> Collection<T>?.isNotNullOrEmpty(): Boolean {
    contract {
        returns(true) implies (this@isNotNullOrEmpty != null)
    }
    return this.isNullOrEmpty().not()
}

@OptIn(ExperimentalContracts::class)
fun <K, V> Map<out K, V>?.isNotNullOrEmpty(): Boolean {
    contract {
        returns(true) implies (this@isNotNullOrEmpty != null)
    }
    return this.isNullOrEmpty().not()
}