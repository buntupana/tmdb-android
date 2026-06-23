package com.buntupana.tmdb.feature.seerr.domain.model

data class SeerrSession(
    val serverUrl: String?,
    val cookie: String?,
    val user: SeerrUser?
) {
    val isLogged: Boolean
        get() = !serverUrl.isNullOrBlank() && !cookie.isNullOrBlank()

    companion object {
        val EMPTY = SeerrSession(serverUrl = null, cookie = null, user = null)
    }
}

data class SeerrUser(
    val id: Long,
    val email: String?,
    val displayName: String?,
    val avatar: String?
)
