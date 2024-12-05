package com.panabuntu.tmdb.core.common.util

import java.net.URLDecoder
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import kotlin.reflect.full.memberProperties
import kotlin.reflect.full.primaryConstructor
import kotlin.reflect.jvm.isAccessible


private fun String.encodeUrl(): String {
    return URLEncoder.encode(this, StandardCharsets.UTF_8.toString())
}

private fun String.decodeUrl(): String {
    return URLDecoder.decode(this, StandardCharsets.UTF_8.toString())
}

// Regular expression to match URLs
private val navigationRegex = Regex("((?:https?|ftp|file|content|android\\.resource|tel)://\\S+|[a-zA-Z]:\\\\(?:[^\\\\/:*?\"<>|\\r\\n]+\\\\)*[^\\\\/:*?\"<>|\\r\\n]*|/(?:[^\\s/]+/?)+)")

// Recursive extension function to encode all strings, including in lists, maps, and nested objects
fun <T : Any> T.encodeAllUrls(): T {
    val params = mutableMapOf<String, Any?>()

    // Process each property in the class
    this::class.memberProperties.forEach { property ->
        property.isAccessible = true // Make private properties accessible
        val value = property.getter.call(this)

        // Determine the encoded value based on the property's type
        val encodedValue = when {
            // Encode URLs in String directly
            property.returnType.classifier == String::class && value is String -> {
                navigationRegex.replace(value) { matchResult ->
                    matchResult.value.encodeUrl()
                }
            }
            // Recursively encode each element in a List
            value is List<*> -> {
                value.map { item ->
                    when (item) {
                        is String -> navigationRegex.replace(item) { matchResult ->
                            matchResult.value.encodeUrl()
                        }
                        is Any -> item.encodeAllUrls() // Recursively encode nested objects in lists
                        else -> item // Keep non-String, non-object items as-is
                    }
                }
            }
            // Recursively encode each element in a Set
            value is Set<*> -> {
                value.map { item ->
                    when (item) {
                        is String -> navigationRegex.replace(item) { matchResult ->
                            matchResult.value.encodeUrl()
                        }
                        is Any -> item.encodeAllUrls() // Recursively encode nested objects in lists
                        else -> item // Keep non-String, non-object items as-is
                    }
                }
            }
            // Recursively encode each value in a Map
            value is Map<*, *> -> {
                value.mapValues { (_, mapValue) ->
                    when (mapValue) {
                        is String -> navigationRegex.replace(mapValue) { matchResult ->
                            matchResult.value.encodeUrl() // Encode URLs in maps
                        }
                        is Any -> mapValue.encodeAllUrls() // Recursively encode nested objects in maps
                        else -> mapValue // Keep non-String, non-object items as-is
                    }
                }
            }
            // Recursively encode other nested data class objects
            value != null && value::class.isData -> {
                value.encodeAllUrls()
            }
            else -> value // For other types, keep the value unchanged
        }

        params[property.name] = encodedValue
    }

    // Create a new instance using the primary constructor with updated parameters if there is no constructor it will return the same object
    val primaryConstructor = this::class.primaryConstructor ?: return this
    return primaryConstructor.callBy(primaryConstructor.parameters.associateWith { params[it.name] })
}

fun <T : Any> T.decodeAllStrings(): T {
    val params = mutableMapOf<String, Any?>()

    // Process each property in the class
    this::class.memberProperties.forEach { property ->
        property.isAccessible = true // Make private properties accessible
        val value = property.getter.call(this)

        // Determine the decoded value based on the property's type
        val decodedValue = when {
            // Decode String directly
            property.returnType.classifier == String::class && value is String -> {
                value.decodeUrl()
            }
            // Recursively decode each element in a List
            value is List<*> -> {
                value.map { item ->
                    when (item) {
                        is String -> item.decodeUrl() // Decode strings in lists
                        is Any -> item.decodeAllStrings() // Recursively decode nested objects in lists
                        else -> item // Keep non-String, non-object items as-is
                    }
                }
            }
            // Recursively decode each element in a Set
            value is Set<*> -> {
                value.map { item ->
                    when (item) {
                        is String -> item.decodeUrl() // Decode strings in lists
                        is Any -> item.decodeAllStrings() // Recursively decode nested objects in lists
                        else -> item // Keep non-String, non-object items as-is
                    }
                }
            }
            // Recursively decode each value in a Map
            value is Map<*, *> -> {
                value.mapValues { (_, mapValue) ->
                    when (mapValue) {
                        is String -> mapValue.decodeUrl() // Decode strings in maps
                        is Any -> mapValue.decodeAllStrings() // Recursively decode nested objects in maps
                        else -> mapValue // Keep non-String, non-object items as-is
                    }
                }
            }
            // Recursively decode other nested data class objects
            value != null && value::class.isData -> {
                value.decodeAllStrings()
            }
            else -> value // For other types, keep the value unchanged
        }

        params[property.name] = decodedValue
    }

    // Create a new instance using the primary constructor with updated parameters
    val primaryConstructor = this::class.primaryConstructor!!
    return primaryConstructor.callBy(primaryConstructor.parameters.associateWith { params[it.name] })
}