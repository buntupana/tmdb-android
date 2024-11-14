package com.buntupana.tmdb.feature.account.data.mapper

import com.buntupana.tmdb.feature.account.data.raw.AccountDetailsRaw
import com.buntupana.tmdb.feature.account.domain.model.AccountDetails
import com.panabuntu.tmdb.core.common.ifNotNullOrBlank

fun AccountDetailsRaw.toModel(
    baseUrlAvatar: String
): AccountDetails {

    val avatarUrl = avatar.tmdb.avatarPath.ifNotNullOrBlank{ baseUrlAvatar + avatar.tmdb.avatarPath }

    return AccountDetails(
        id = id,
        username = username,
        avatarUrl = avatarUrl
    )
}