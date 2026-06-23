package com.buntupana.tmdb.feature.seer.domain.model

data class SeerSession(
    val serverUrl: String?,
    val cookie: String?,
    val user: SeerUser?
) {
    val isLogged: Boolean
        get() = !serverUrl.isNullOrBlank() && !cookie.isNullOrBlank()

    companion object {
        val EMPTY = SeerSession(serverUrl = null, cookie = null, user = null)
    }
}

data class SeerUser(
    val id: Long,
    val email: String?,
    val displayName: String?,
    val avatar: String?
)
