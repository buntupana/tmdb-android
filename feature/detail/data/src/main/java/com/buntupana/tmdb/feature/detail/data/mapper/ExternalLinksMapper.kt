package com.buntupana.tmdb.feature.detail.data.mapper

import com.buntupana.tmdb.feature.detail.data.raw.ExternalLinksRaw
import com.buntupana.tmdb.feature.detail.domain.model.ExternalLink


fun ExternalLinksRaw.toModel(
    baseUrlFacebook: String,
    baseUrlInstagram: String,
    baseUrlX: String
): List<ExternalLink> {

    return mutableListOf<ExternalLink>().apply {
        facebookId?.let {
            add(ExternalLink.FacebookLink(baseUrlFacebook + facebookId))
        }
        instagramId?.let {
            add(ExternalLink.InstagramLink(baseUrlInstagram + instagramId))
        }
        twitterId?.let {
            add(ExternalLink.TwitterLink(baseUrlX + twitterId))
        }
    }
}