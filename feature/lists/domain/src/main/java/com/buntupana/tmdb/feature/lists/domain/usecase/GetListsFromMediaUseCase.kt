package com.buntupana.tmdb.feature.lists.domain.usecase

import com.buntupana.tmdb.feature.lists.domain.model.UserListDetails
import com.buntupana.tmdb.feature.lists.domain.repository.ListRepository
import com.panabuntu.tmdb.core.common.entity.MediaType
import com.panabuntu.tmdb.core.common.entity.NetworkError
import com.panabuntu.tmdb.core.common.entity.Result
import com.panabuntu.tmdb.core.common.entity.onError
import com.panabuntu.tmdb.core.common.entity.onSuccess
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetListsFromMediaUseCase(
    private val listRepository: ListRepository
) {
    suspend operator fun invoke(
        mediaId: Long,
        mediaType: MediaType
    ): Flow<Result<GetListsFromMediaUseCaseResult, NetworkError>> {

        var firstRequestDone = false

        val mediaBelongsList = mutableListOf<UserListDetails>()
        var mediaNotBelongsList = mutableListOf<UserListDetails>()

        var networkError: NetworkError? = null

        return listRepository.getLists().map { result ->
            when (result) {
                is Result.Error -> Result.Error(result.error)
                is Result.Success -> {

                    if (firstRequestDone) {
                        mediaNotBelongsList =
                            result.data.subtract(mediaBelongsList.toSet()).toMutableList()
                    } else {
                        run listItemLoop@{

                            result.data.forEach { listItem ->

                                listRepository.checkIfItemInList(
                                    listId = listItem.id,
                                    mediaId = mediaId,
                                    mediaType = mediaType
                                ).onError {
                                    if (it == NetworkError.NOT_FOUND) {
                                        mediaNotBelongsList.add(listItem)
                                    } else {
                                        networkError = it
                                        return@listItemLoop
                                    }
                                }.onSuccess {
                                    mediaBelongsList.add(listItem)
                                }
                            }

                            firstRequestDone = true
                        }
                    }

                    if (networkError != null) {
                        Result.Error(networkError!!)
                    } else {
                        Result.Success(
                            GetListsFromMediaUseCaseResult(
                                mediaBelongsList = mediaBelongsList,
                                mediaNotBelongsList = mediaNotBelongsList,
                                allListsList = result.data
                            )
                        )
                    }
                }
            }
        }
    }
}