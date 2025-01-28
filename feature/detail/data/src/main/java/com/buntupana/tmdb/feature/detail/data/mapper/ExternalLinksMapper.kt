package com.buntupana.tmdb.feature.detail.data.mapper

import com.buntupana.tmdb.feature.detail.data.raw.ExternalLinksRaw
import com.buntupana.tmdb.feature.detail.domain.model.ExternalLink


fun ExternalLinksRaw.toModel(
    homepage: String?,
    baseUrlFacebook: String,
    baseUrlInstagram: String,
    baseUrlX: String,
    baseUrlTiktok: String,
    baseUrlImdb: String
): List<ExternalLink> {

    return mutableListOf<ExternalLink>().apply {
        homepage?.let {
            add(ExternalLink.HomePage(homepage))
        }
        facebookId?.let {
            add(ExternalLink.FacebookLink(baseUrlFacebook + facebookId))
        }
        twitterId?.let {
            add(ExternalLink.XLink(baseUrlX + twitterId))
        }
        instagramId?.let {
            add(ExternalLink.InstagramLink(baseUrlInstagram + instagramId))
        }
        tiktokId?.let {
            add(ExternalLink.TiktokLink(baseUrlTiktok + tiktokId))
        }
        imdbId?.let {
            add(ExternalLink.ImdbLink(baseUrlImdb + imdbId))
        }
    }
}