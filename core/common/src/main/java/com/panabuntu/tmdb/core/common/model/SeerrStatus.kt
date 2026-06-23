package com.panabuntu.tmdb.core.common.model

enum class SeerrStatus(val code: Int) {
    UNKNOWN(1),
    PENDING(2),
    REQUESTED(3),
    PARTIALLY_AVAILABLE(4),
    AVAILABLE(5),
    DELETED(6);

    companion object {
        fun fromCode(code: Int?): SeerrStatus? = entries.firstOrNull { it.code == code }
    }
}

fun SeerrStatus?.canBeRequested(): Boolean {
    return this == null ||
            this == SeerrStatus.UNKNOWN ||
            this == SeerrStatus.PARTIALLY_AVAILABLE ||
            this == SeerrStatus.DELETED
}
