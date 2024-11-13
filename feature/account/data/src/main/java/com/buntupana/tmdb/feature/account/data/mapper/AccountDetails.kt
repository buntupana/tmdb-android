package com.buntupana.tmdb.feature.account.data.mapper

import com.buntupana.tmdb.core.data.api.CoreApi
import com.buntupana.tmdb.core.presentation.util.ifNotNullOrBlank
import com.buntupana.tmdb.feature.account.data.raw.AccountDetailsRaw
import com.buntupana.tmdb.feature.account.domain.model.AccountDetails

fun AccountDetailsRaw.toModel(): AccountDetails {

    val avatarUrl = avatar.tmdb.avatarPath.ifNotNullOrBlank{ CoreApi.BASE_URL_AVATAR + avatar.tmdb.avatarPath }

    return AccountDetails(
        id = id,
        username = username,
        avatarUrl = avatarUrl
    )
}