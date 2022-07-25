package com.buntupana.tmdb.app.domain.usecase

import com.buntupana.tmdb.core.domain.entity.Resource
import kotlinx.coroutines.*
import timber.log.Timber
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.coroutineContext
import kotlin.reflect.KSuspendFunction1

private typealias SourceResource<R> = Resource<R>

private typealias LoadingListener<T> = suspend (data: T?) -> Unit
private typealias ErrorListener<T> = suspend (data: T?) -> Unit
private typealias SuccessListener<T> = suspend (data: T) -> Unit
typealias UseCaseResourceListenerAlias<R> = KSuspendFunction1<UseCaseResourceListenerHelper<R>.() -> Unit, Unit>

abstract class UseCaseResource<P, R> {

    private val _listenerContainerList: MutableList<ListenerResourceContainer<R>> = mutableListOf()

    suspend operator fun invoke(
        parameters: P,
        dispatcher: CoroutineDispatcher = Dispatchers.IO,
        init: (UseCaseResourceListenerHelper<R>.() -> Unit)? = null
    ) {

        val initialCoroutineContext = coroutineContext

        triggerListenerLoading()

        val listenerHelper = if (init != null) {
            UseCaseResourceListenerHelper<R>().apply {
                init()
                loading()
            }
        } else {
            null
        }

        // executing the source in [dispatcher] context, usually IO
        withContext(dispatcher) {

            val resource = getSource(parameters)

            // returning result in initial coroutine context
            withContext(initialCoroutineContext) {
                executeSource(resource, listenerHelper)
            }
        }
    }

    abstract suspend fun getSource(params: P): Resource<R>

    private suspend fun executeSource(
        source: SourceResource<R>,
        listenerUseCase: UseCaseResourceListener<R>? = null
    ) {
        source.let { resource ->
            when (resource) {
                is Resource.Error -> {
                    listenerUseCase?.error(resource.data)
                    triggerListenerError(resource.data)
                }
                is Resource.Success -> {
                    listenerUseCase?.success(resource.data!!)
                    Timber.d("listener pre main")
                    triggerListenerSuccess(resource.data!!)
                }
            }
        }
    }

    /** Listener to mainly be used in presentation */
    suspend fun listener(init: UseCaseResourceListenerHelper<R>.() -> Unit) {
        val listenerHelper = UseCaseResourceListenerHelper<R>()
        listenerHelper.init()

        val resourceListener = ResourceListener()

        resourceListener(coroutineContext) {
            loading {
                listenerHelper.loading(it)
            }
            error {
                listenerHelper.error(it)
            }
            success {
                listenerHelper.success(it)
            }
        }
        // keeping alive this function in order to use the [CoroutineContext] from where was invoked
        awaitCancellation()
    }

    private suspend fun triggerListenerLoading() {
        _listenerContainerList.forEach { listenerContainer ->
            if (listenerContainer.listenerScopeContext.isActive) {
                withContext(listenerContainer.listenerScopeContext) {
                    listenerContainer.listener.loading()
                }
            }
        }
    }

    private suspend fun triggerListenerError(data: R?) {
        _listenerContainerList.forEach { listenerContainer ->
            if (listenerContainer.listenerScopeContext.isActive) {
                withContext(listenerContainer.listenerScopeContext) {
                    listenerContainer.listener.error(data)
                }
            }
        }
    }

    private suspend fun triggerListenerSuccess(data: R) {
        _listenerContainerList.forEach { listenerContainer ->
            if (listenerContainer.listenerScopeContext.isActive) {
                withContext(listenerContainer.listenerScopeContext) {
                    listenerContainer.listener.success(data)
                }
            }
        }
    }

    inner class ResourceListener {

        operator fun invoke(
            coroutineContext: CoroutineContext,
            init: UseCaseResourceListenerHelper<R>.() -> Unit
        ) {
            val listenerHelper = UseCaseResourceListenerHelper<R>()
            listenerHelper.init()
            _listenerContainerList.add(ListenerResourceContainer(listenerHelper, coroutineContext))
        }
    }
}

class UseCaseResourceListenerHelper<T> : UseCaseResourceListener<T> {

    private var resultLoading: LoadingListener<T>? = null
    private var resultError: ErrorListener<T>? = null
    private var resultSuccess: SuccessListener<T>? = null

    fun loading(onResultLoading: LoadingListener<T>) {
        resultLoading = onResultLoading
    }

    override suspend fun loading(data: T?) {
        resultLoading?.invoke(data)
    }

    fun error(onResultError: ErrorListener<T>) {
        resultError = onResultError
    }

    override suspend fun error(data: T?) {
        resultError?.invoke(data)
    }

    fun success(onResultSuccess: SuccessListener<T>) {
        resultSuccess = onResultSuccess
    }

    override suspend fun success(data: T) {
        resultSuccess?.invoke(data)
    }
}

interface UseCaseResourceListener<T> {
    suspend fun loading(data: T? = null)
    suspend fun error(data: T? = null)
    suspend fun success(data: T)
}

class ListenerResourceContainer<R>(
    val listener: UseCaseResourceListener<R>,
    val listenerScopeContext: CoroutineContext
)