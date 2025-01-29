package com.buntupana.tmdb.feature.account.data.mapper

import com.buntupana.tmdb.feature.account.data.raw.AccountDetailsRaw
import com.panabuntu.tmdb.core.common.model.AccountDetails
import com.panabuntu.tmdb.core.common.util.ifNotNullOrBlank

fun AccountDetailsRaw.toModel(
    baseUrlAvatar: String
): AccountDetails {

    val avatarUrl = avatar.tmdb.avatarPath.ifNotNullOrBlank{ baseUrlAvatar + avatar.tmdb.avatarPath }

    return AccountDetails(
        id = id,
        accountObjectId = "",
        username = username,
        name = name,
        avatarUrl = avatarUrl
    )
}