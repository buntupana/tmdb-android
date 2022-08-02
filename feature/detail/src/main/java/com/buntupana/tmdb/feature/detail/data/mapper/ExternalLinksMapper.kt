package com.buntupana.tmdb.feature.detail.data.mapper

import com.buntupana.tmdb.feature.detail.data.raw.ExternalLinksRaw
import com.buntupana.tmdb.feature.detail.domain.model.ExternalLinks

private const val FACEBOOK_BASE_URL = "https://www.facebook.com/"
private const val IMDB_BASE_URL = "https://www.themoviedb.org/person/"
private const val INSTAGRAM_BASE_URL = "https://instagram.com/"
private const val TWITTER_BASE_URL = "https://twitter.com/"

fun ExternalLinksRaw.toModel(): ExternalLinks {

    return ExternalLinks(
        id,
        facebookLink = if (facebookId == null) "" else FACEBOOK_BASE_URL + facebookId,
        imdbLink = if (imdbId == null) "" else IMDB_BASE_URL + imdbId,
        instagramLink = if (instagramId == null) "" else INSTAGRAM_BASE_URL + instagramId,
        twitterLink = if (twitterId == null) "" else TWITTER_BASE_URL + twitterId
    )
}