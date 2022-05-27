package com.buntupana.tmdb.feature.discover.data.remote_data_source

enum class MonetizationType(
    val value: String
) {
    FLAT_RATE("flatrate"),
    FREE("free"),
    ADS("adds"),
    RENT("rent"),
    BUY("buy")
}