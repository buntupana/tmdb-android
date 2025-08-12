package com.panabuntu.tmdb.core.common.util

import android.util.Base64
import kotlin.reflect.full.memberProperties
import kotlin.reflect.full.primaryConstructor
import kotlin.reflect.jvm.isAccessible


fun String.encode(): String {
    return Base64.encodeToString(this.toByteArray(), Base64.URL_SAFE)
}

fun String.decode(): String {
    return String(Base64.decode(this, Base64.URL_SAFE))
}

// Recursive extension function to encode all strings, including in lists, maps, and nested objects
fun <T : Any> T.encodeAllStrings(): T {
    val params = mutableMapOf<String, Any?>()

    // Process each property in the class
    this::class.memberProperties.forEach { property ->
        property.isAccessible = true // Make private properties accessible
        val value = property.getter.call(this)

        // Determine the encoded value based on the property's type
        val encodedValue = when {
            // Encode URLs in String directly
            property.returnType.classifier == String::class && value is String -> {
                value.encode()
            }
            // Recursively encode each element in a List
            value is List<*> -> {
                value.map { item ->
                    when (item) {
                        is String -> item.encode()
                        is Enum<*> -> item // Keep Enum items as-is
                        is Any -> item.encodeAllStrings() // Recursively encode nested objects in lists
                        else -> item // Keep non-String, non-object items as-is
                    }
                }
            }
            // Recursively encode each element in a Set
            value is Set<*> -> {
                value.map { item ->
                    when (item) {
                        is String -> item.encode()
                        is Enum<*> -> item // Keep Enum items as-is
                        is Any -> item.encodeAllStrings() // Recursively encode nested objects in lists
                        else -> item // Keep non-String, non-object items as-is
                    }
                }
            }
            // Recursively encode each value in a Map
            value is Map<*, *> -> {
                value.mapValues { (_, mapValue) ->
                    when (mapValue) {
                        is String -> mapValue.encode()
                        is Enum<*> -> mapValue // Keep Enum items as-is
                        is Any -> mapValue.encodeAllStrings() // Recursively encode nested objects in maps
                        else -> mapValue // Keep non-String, non-object items as-is
                    }
                }
            }
            // Recursively encode other nested data class objects
            value != null && value::class.isData -> {
                value.encodeAllStrings()
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
                value.decode()
            }
            // Recursively decode each element in a List
            value is List<*> -> {
                value.map { item ->
                    when (item) {
                        is String -> item.decode() // Decode strings in lists
                        is Enum<*> -> item // Keep Enum items as-is
                        is Any -> item.decodeAllStrings() // Recursively decode nested objects in lists
                        else -> item // Keep non-String, non-object items as-is
                    }
                }
            }
            // Recursively decode each element in a Set
            value is Set<*> -> {
                value.map { item ->
                    when (item) {
                        is String -> item.decode() // Decode strings in lists
                        is Enum<*> -> item // Keep Enum items as-is
                        is Any -> item.decodeAllStrings() // Recursively decode nested objects in lists
                        else -> item // Keep non-String, non-object items as-is
                    }
                }
            }
            // Recursively decode each value in a Map
            value is Map<*, *> -> {
                value.mapValues { (_, mapValue) ->
                    when (mapValue) {
                        is String -> mapValue.decode() // Decode strings in maps
                        is Enum<*> -> mapValue // Keep Enum items as-is
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