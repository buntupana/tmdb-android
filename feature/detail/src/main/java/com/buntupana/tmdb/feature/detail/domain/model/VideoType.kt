package com.buntupana.tmdb.feature.detail.domain.model

enum class VideoType(val value: Int) {
    TRAILER(0),
    TEASER(1),
    FEATURETTE(2),
    BEHIND_THE_SCENES(3),
    OPENING_CREDITS(4),
    BLOOPERS(5),
    CLIP(6),
    UNKNOWN(6)
}