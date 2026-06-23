package com.panabuntu.tmdb.core.common.provider

import com.panabuntu.tmdb.core.common.entity.MediaType
import com.panabuntu.tmdb.core.common.model.SeerrStatus
import kotlinx.coroutines.flow.StateFlow

/**
 * Lookup-cache for Seer media status keyed by (mediaType, tmdbId).
 *
 * Implementations should be cheap to call repeatedly from composables, lazily
 * fetching status the first time a media id is requested and caching the result.
 * When the user is not connected to a Seer server the flow should keep emitting
 * null for every key.
 */
interface SeerStatusProvider {

    val isConnected: StateFlow<Boolean>

    fun statusFor(mediaType: MediaType, tmdbId: Long): StateFlow<SeerrStatus?>

    fun invalidate(mediaType: MediaType, tmdbId: Long)

    fun invalidateAll()
}
