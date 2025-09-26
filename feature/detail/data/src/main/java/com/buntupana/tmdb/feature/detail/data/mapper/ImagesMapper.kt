package com.buntupana.tmdb.feature.detail.data.mapper

import com.buntupana.tmdb.feature.detail.data.remote_data_source.raw.MediaImagesRaw
import com.buntupana.tmdb.feature.detail.data.remote_data_source.raw.PersonImagesRaw
import com.buntupana.tmdb.feature.detail.domain.model.MediaImages

fun MediaImagesRaw.toModel(
    imageBaseUrl: String,
): MediaImages {
    return MediaImages(
        posterList = posters.map { imageBaseUrl + it.filePath },
        backdropList = backdrops.map { imageBaseUrl + it.filePath }
    )
}

fun PersonImagesRaw.toList(
    imageBaseUrl: String,
): List<String> {
    return profiles.map { imageBaseUrl + it.filePath }
}