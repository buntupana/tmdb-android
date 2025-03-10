package com.buntupana.tmdb.feature.lists.domain.usecase

import com.buntupana.tmdb.feature.lists.domain.repository.ListRepository
import com.panabuntu.tmdb.core.common.entity.MediaType
import com.panabuntu.tmdb.core.common.entity.NetworkError
import com.panabuntu.tmdb.core.common.entity.Result
import com.panabuntu.tmdb.core.common.entity.onError
import com.panabuntu.tmdb.core.common.entity.onSuccess
import javax.inject.Inject

class GetListsFromMediaUseCase @Inject constructor(
    private val listRepository: ListRepository
) {
    suspend operator fun invoke(
        mediaId: Long,
        mediaType: MediaType
    ): Result<GetListsFromMediaUseCaseResult, NetworkError> {

        val getListsResult = listRepository.getLists()

        val mediaBelongsList = mutableListOf<com.buntupana.tmdb.feature.lists.domain.model.MediaList>()
        val mediaNotBelongsList = mutableListOf<com.buntupana.tmdb.feature.lists.domain.model.MediaList>()

        var networkError: NetworkError? = null

        return when (getListsResult) {

            is Result.Error -> Result.Error(getListsResult.error)

            is Result.Success -> {

                run listItemLoop@{

                    getListsResult.data.forEach { listItem ->

                        listRepository.checkIfitemInList(
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
                }

                if (networkError != null) {
                    Result.Error(networkError)
                } else {
                    Result.Success(
                        GetListsFromMediaUseCaseResult(
                            mediaBelongsList = mediaBelongsList,
                            mediaNotBelongsList = mediaNotBelongsList,
                            allListsList = getListsResult.data
                        )
                    )
                }
            }
        }
    }
}


data class GetListsFromMediaUseCaseResult(
    val mediaBelongsList: List<com.buntupana.tmdb.feature.lists.domain.model.MediaList>,
    val mediaNotBelongsList: List<com.buntupana.tmdb.feature.lists.domain.model.MediaList>,
    val allListsList: List<com.buntupana.tmdb.feature.lists.domain.model.MediaList>,
)