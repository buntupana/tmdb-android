package com.buntupana.tmdb.feature.detail.data.mapper

import com.buntupana.tmdb.feature.detail.data.raw.ExternalLinksRaw
import com.buntupana.tmdb.feature.detail.domain.model.ExternalLink

private const val FACEBOOK_BASE_URL = "https://www.facebook.com/"
private const val INSTAGRAM_BASE_URL = "https://instagram.com/"
private const val TWITTER_BASE_URL = "https://twitter.com/"

fun ExternalLinksRaw.toModel(): List<ExternalLink> {

    return mutableListOf<ExternalLink>().apply {
        facebookId?.let {
            add(ExternalLink.FacebookLink(FACEBOOK_BASE_URL + facebookId))
        }
        instagramId?.let {
            add(ExternalLink.InstagramLink(INSTAGRAM_BASE_URL + instagramId))
        }
        twitterId?.let {
            add(ExternalLink.TwitterLink(TWITTER_BASE_URL + twitterId))
        }
    }
}