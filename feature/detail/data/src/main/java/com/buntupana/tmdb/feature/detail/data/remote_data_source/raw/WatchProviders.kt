package com.buntupana.tmdb.feature.detail.data.remote_data_source.raw

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WatchProviders(
    val results: Map<String, CountryWatchProvider>
)

@Serializable
data class CountryWatchProvider(
    val link: String,
    val buy: List<ProviderInfo>? = null,
    val rent: List<ProviderInfo>? = null,
    val flatrate: List<ProviderInfo>? = null,
    val ads: List<ProviderInfo>? = null
)

@Serializable
data class ProviderInfo(
    @SerialName("provider_id")
    val providerId: Long,
    @SerialName("logo_path")
    val logoPath: String,
    @SerialName("provider_name")
    val providerName: String,
    @SerialName("display_priority")
    val displayPriority: Int
)
