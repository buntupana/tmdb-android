package com.buntupana.tmdb.feature.seerr.data.provider

import com.buntupana.tmdb.feature.seerr.domain.manager.SeerrSessionManager
import com.buntupana.tmdb.feature.seerr.domain.repository.SeerrRepository
import com.panabuntu.tmdb.core.common.entity.MediaType
import com.panabuntu.tmdb.core.common.entity.Result
import com.panabuntu.tmdb.core.common.model.SeerrStatus
import com.panabuntu.tmdb.core.common.provider.SeerrStatusProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.concurrent.ConcurrentHashMap

class SeerrStatusProviderImpl(
    private val repository: SeerrRepository,
    sessionManager: SeerrSessionManager
) : SeerrStatusProvider {

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    override val isConnected: StateFlow<Boolean> =
        sessionManager.session
            .map { it.isLogged }
            .stateIn(scope, kotlinx.coroutines.flow.SharingStarted.Eagerly, false)

    private val cache = ConcurrentHashMap<Key, MutableStateFlow<SeerrStatus?>>()
    private val jobs = ConcurrentHashMap<Key, Job>()

    init {
        scope.launch {
            sessionManager.session.collect { session ->
                if (!session.isLogged) {
                    cache.values.forEach { it.value = null }
                    jobs.values.forEach { it.cancel() }
                    jobs.clear()
                }
            }
        }
    }

    override fun statusFor(mediaType: MediaType, mediaId: Long): StateFlow<SeerrStatus?> {
        val key = Key(mediaType, mediaId)
        val flow = cache.getOrPut(key) { MutableStateFlow(null) }
        if (isConnected.value && jobs[key]?.isActive != true && flow.value == null) {
            jobs[key] = scope.launch {
                val result = repository.getMediaInfo(mediaType, mediaId)
                if (result is Result.Success) {
                    flow.value = result.data.mediaStatus
                }
            }
        }
        return flow.asStateFlow()
    }

    override fun invalidate(mediaType: MediaType, mediaId: Long) {
        val key = Key(mediaType, mediaId)
        jobs[key]?.cancel()
        jobs.remove(key)
        cache[key]?.value = null
    }

    override fun invalidateAll() {
        jobs.values.forEach { it.cancel() }
        jobs.clear()
        cache.values.forEach { it.value = null }
    }

    private data class Key(val mediaType: MediaType, val mediaId: Long)
}
