package com.buntupana.tmdb.feature.lists.domain.usecase

import com.buntupana.tmdb.feature.lists.domain.model.UserListDetails
import com.buntupana.tmdb.feature.lists.domain.repository.ListRepository
import com.panabuntu.tmdb.core.common.entity.MediaType
import com.panabuntu.tmdb.core.common.entity.NetworkError
import com.panabuntu.tmdb.core.common.entity.Result
import com.panabuntu.tmdb.core.common.entity.onError

class SetListsForMediaUseCase(
    private val listRepository: ListRepository
) {

    suspend operator fun invoke(
        mediaId: Long,
        mediaType: MediaType,
        originalList: List<UserListDetails>,
        newList: List<UserListDetails>
    ): Result<Unit, NetworkError> {

        val listsToDelete = originalList.filter { id -> newList.contains(id).not() }

        run deleteLoop@{
            listsToDelete.forEach { list ->
                listRepository.deleteMediaItemList(
                    listId = list.id,
                    mediaId = mediaId,
                    mediaType = mediaType
                ).onError {
                    return@deleteLoop Result.Error(it)
                }
            }
        }


        val listToAdd = newList.filter { id -> originalList.contains(id).not() }

        run addLoop@{
            listToAdd.forEach { list ->
                listRepository.addMediaItemList(
                    listId = list.id,
                    mediaId = mediaId,
                    mediaType = mediaType
                ).onError {
                    return@addLoop Result.Error(it)
                }
            }
        }

        return Result.Success(Unit)
    }
}