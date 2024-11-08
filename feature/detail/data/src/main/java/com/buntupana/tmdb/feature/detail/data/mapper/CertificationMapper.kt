package com.buntupana.tmdb.feature.detail.data.mapper

import com.buntupana.tmdb.feature.detail.data.raw.ContentRating
import com.buntupana.tmdb.feature.detail.domain.model.Certification

fun ContentRating.toModel(): Certification {
    return Certification(
        iso_3166_1,
        rating
    )
}