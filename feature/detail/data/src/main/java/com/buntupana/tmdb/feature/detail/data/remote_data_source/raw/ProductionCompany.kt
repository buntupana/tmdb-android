package com.buntupana.tmdb.feature.detail.data.remote_data_source.raw

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProductionCompany(
    val id: Int,
    val name: String,
    @SerialName("logo_path")
    val logoPath: String? = null,
    @SerialName("origin_country")
    val originCountry: String? = null
)