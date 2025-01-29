package com.panabuntu.tmdb.core.common.model

data class AccountDetails(
    val id: Long,
    val accountObjectId: String,
    val username: String,
    val name: String,
    val avatarUrl: String?
)
