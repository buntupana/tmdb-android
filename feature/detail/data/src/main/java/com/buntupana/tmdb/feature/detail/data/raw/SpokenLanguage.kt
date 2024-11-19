package com.buntupana.tmdb.feature.detail.data.raw

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SpokenLanguage(
    val name: String,
    @SerialName("english_name")
    val englishName: String,
    @SerialName("iso_639_1")
    val iso_639_1: String
)