package com.buntupana.tmdb.feature.detail.domain.model

sealed class ExternalLink(val link: String) {
    class FacebookLink(link: String) : ExternalLink(link)
    class ImdbLink(link: String) : ExternalLink(link)
    class InstagramLink(link: String) : ExternalLink(link)
    class XLink(link: String) : ExternalLink(link)
    class HomePage(link: String) : ExternalLink(link)
    class TiktokLink(link: String) : ExternalLink(link)
}
