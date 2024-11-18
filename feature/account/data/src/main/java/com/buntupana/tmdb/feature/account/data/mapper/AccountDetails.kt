package com.buntupana.tmdb.feature.account.data.mapper

import com.buntupana.tmdb.feature.account.data.raw.AccountDetailsRaw
import com.panabuntu.tmdb.core.common.ifNotNullOrBlank
import com.panabuntu.tmdb.core.common.model.AccountDetails

fun AccountDetailsRaw.toModel(
    baseUrlAvatar: String
): AccountDetails {

    val avatarUrl = avatar.tmdb.avatarPath.ifNotNullOrBlank{ baseUrlAvatar + avatar.tmdb.avatarPath }

    return AccountDetails(
        id = id,
        username = username,
        name = name,
        avatarUrl = avatarUrl
    )
}