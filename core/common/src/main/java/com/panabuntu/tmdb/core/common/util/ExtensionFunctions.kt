package com.panabuntu.tmdb.core.common.util

import kotlinx.coroutines.delay
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.StringFormat
import kotlinx.serialization.json.Json.Default.decodeFromString
import kotlinx.serialization.json.Json.Default.serializersModule
import kotlinx.serialization.json.internal.FormatLanguage
import kotlinx.serialization.serializer
import java.text.NumberFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.Locale
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract


fun LocalDate.toLocalFormat(): String {
    val dateFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT).withLocale(
        Locale.getDefault()
    )
    return this.format(dateFormatter)
}

fun LocalDate.toFullDate(): String {
    val dateFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG).withLocale(
        Locale.getDefault()
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

fun <T, R> R?.ifNotNull(block: (R) -> T?) = if (this != null) block(this) else null

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

suspend fun applyDelayFor(initMillis: Long, minDurationDifference: Long = 500) {
    val difference = System.currentTimeMillis() - initMillis
    if (difference < minDurationDifference) {
        delay(minDurationDifference - difference)
    }
}

fun Int.toLocalizedString(locale: Locale = Locale.getDefault()): String {
    return this.toLong().toLocalizedString(locale)
}

fun Long.toLocalizedString(locale: Locale = Locale.getDefault()): String {
    val numberFormat = NumberFormat.getNumberInstance(locale)
    return numberFormat.format(this)
}

fun getLanguageName(languageCode: String?): String {
    return try {
        Locale(languageCode!!).displayLanguage
    } catch (e: Exception) {
        " - "
    }
}

inline fun <reified T> StringFormat.encodeToStringSafe(value: T?): String? {
    value ?: return null
    return encodeToString(serializersModule.serializer(), value)
}

@OptIn(InternalSerializationApi::class)
inline fun <reified T> decodeFromStringSafe(@FormatLanguage("json", "", "") string: String?): T? {
    string ?: return null
    return decodeFromString(serializersModule.serializer(), string)
}
