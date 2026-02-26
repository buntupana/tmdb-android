package com.buntupana.tmdb.feature.lists.data.remote_data_source.request


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UpdateListRequest(
    @SerialName("name")
    val name: String,
    @SerialName("description")
    val description: String,
    @SerialName("public")
    val public: Boolean,
    @SerialName("iso_3166_1")
    val iso31661: String? = null,
    @SerialName("iso_639_1")
    val iso6391: String = "en"
)