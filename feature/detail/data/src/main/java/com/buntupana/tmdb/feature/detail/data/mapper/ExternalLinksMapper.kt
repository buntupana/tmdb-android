package com.buntupana.tmdb.feature.detail.data.mapper

import com.buntupana.tmdb.feature.detail.data.remote_data_source.raw.ExternalLinksRaw
import com.buntupana.tmdb.feature.detail.domain.model.ExternalLink
import com.panabuntu.tmdb.core.common.util.isNotNullOrBlank


fun ExternalLinksRaw.toModel(
    homepage: String?,
    baseUrlFacebook: String,
    baseUrlInstagram: String,
    baseUrlX: String,
    baseUrlTiktok: String,
    baseUrlImdb: String
): List<ExternalLink> {

    return mutableListOf<ExternalLink>().apply {
        if (homepage.isNotNullOrBlank()) {
            add(ExternalLink.HomePage(homepage))
        }
        if (facebookId.isNotNullOrBlank()) {
            add(ExternalLink.FacebookLink(baseUrlFacebook + facebookId))
        }
        if (twitterId.isNotNullOrBlank()) {
            add(ExternalLink.XLink(baseUrlX + twitterId))
        }
        if (instagramId.isNotNullOrBlank()) {
            add(ExternalLink.InstagramLink(baseUrlInstagram + instagramId))
        }
        if (tiktokId.isNotNullOrBlank()) {
            add(ExternalLink.TiktokLink(baseUrlTiktok + tiktokId))
        }
        if (imdbId.isNotNullOrBlank()) {
            add(ExternalLink.ImdbLink(baseUrlImdb + imdbId))
        }
    }
}