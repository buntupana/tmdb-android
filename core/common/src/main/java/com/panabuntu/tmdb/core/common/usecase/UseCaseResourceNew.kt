package com.panabuntu.tmdb.core.common.usecase

import com.panabuntu.tmdb.core.common.entity.Resource


abstract class UseCaseResourceNew<PARAMS, RESULT> {

    suspend operator fun invoke(
        parameters: PARAMS,
        loading: () -> Unit = {},
        error: (message: String) -> Unit = {},
        success: (result: RESULT) -> Unit = {}
    ) {
        loading()
        when (val resource = getSource(parameters)) {
            is Resource.Error -> error(resource.message)
            is Resource.Success -> success(resource.data)
        }
    }

    abstract suspend fun getSource(params: PARAMS): Resource<RESULT>
}