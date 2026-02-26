package com.buntupana.tmdb.feature.detail.data.mapper

import com.buntupana.tmdb.feature.detail.data.remote_data_source.raw.MediaVideosRaw
import com.buntupana.tmdb.feature.detail.domain.model.Video
import com.buntupana.tmdb.feature.detail.domain.model.VideoType
import com.panabuntu.tmdb.core.common.util.ifNullOrBlank
import java.time.LocalDate
import java.time.format.DateTimeParseException

private const val YOUTUBE_BASE_URL = "https://www.youtube.com/watch?v="
private const val VIMEO_BASE_URL = "https://vimeo.com/"

fun MediaVideosRaw.toModel(): List<Video> {

    return results.map { videoRaw ->

        val videoUrl = when (videoRaw.site?.lowercase()) {
            "youtube" -> YOUTUBE_BASE_URL + videoRaw.key
            "vimeo" -> VIMEO_BASE_URL + videoRaw.key
            else -> ""
        }

        val videoType = when (videoRaw.type?.lowercase()) {
            "trailer" -> VideoType.TRAILER
            "teaser" -> VideoType.TEASER
            "featurette" -> VideoType.FEATURETTE
            "behind the scenes" -> VideoType.BEHIND_THE_SCENES
            "opening credits" -> VideoType.OPENING_CREDITS
            "bloopers" -> VideoType.BLOOPERS
            "clip" -> VideoType.CLIP
            else -> VideoType.UNKNOWN
        }

        val publishedAt = try {
            LocalDate.parse(videoRaw.publishedAt)
        } catch (exc: DateTimeParseException) {
            null
        }

        Video(
            videoRaw.id,
            videoRaw.name.orEmpty(),
            videoUrl,
            videoType,
            publishedAt,
            videoRaw.official ?: false
        )
    }
}

fun getVideoTrailerUrl(videoList: List<Video>): String {

    val videoTrailerList =
        videoList
            .filter { it.videoType == VideoType.TRAILER || it.videoType == VideoType.TEASER }
            .sortedWith(
                compareBy<Video> { it.videoType }
                    .thenBy { it.official }
                    .thenByDescending { it.publishedAt }
            )

    return videoList.sortedBy { it.publishedAt }.sortedBy { it.official }.firstOrNull { video ->
        when {
            listOf("final", "trailer").all { video.name.contains(it, true) } -> true
            listOf("official", "trailer").all { video.name.contains(it, true) } -> true
            else -> false
        }
    }?.url.ifNullOrBlank { videoTrailerList.firstOrNull()?.url.orEmpty() }
}