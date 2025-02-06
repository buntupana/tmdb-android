package com.buntupana.tmdb.feature.account.data.remote_data_source.request


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateListRequest(
    @SerialName("name")
    val name: String,
    @SerialName("description")
    val description: String,
    @SerialName("public")
    val public: String,
    @SerialName("iso_3166_1")
    val iso31661: String? = null,
    @SerialName("iso_639_1")
    val iso6391: String = "en"
)