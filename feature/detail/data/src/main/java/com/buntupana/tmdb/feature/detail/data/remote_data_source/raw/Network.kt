package com.buntupana.tmdb.feature.detail.data.remote_data_source.raw

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Network(
    val id: Long,
    @SerialName("logo_path")
    val logoPath: String? = null,
    val name: String? = null,
    @SerialName("origin_country")
    val originCountry: String? = null
)