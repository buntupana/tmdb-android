package com.buntupana.tmdb.feature.lists.data.remote_data_source

import com.buntupana.tmdb.core.data.raw.MediaItemRaw
import com.buntupana.tmdb.core.data.raw.ResponseListRaw
import com.buntupana.tmdb.core.data.raw.StandardRaw
import com.buntupana.tmdb.core.data.remote_data_source.RemoteDataSource
import com.buntupana.tmdb.feature.account.data.request.AddItem
import com.buntupana.tmdb.feature.account.data.request.AddRemoveListItemListRequest
import com.buntupana.tmdb.feature.lists.data.remote_data_source.raw.AddRemoveListItemListRaw
import com.buntupana.tmdb.feature.lists.data.remote_data_source.raw.CreateListRaw
import com.buntupana.tmdb.feature.lists.data.remote_data_source.raw.ListDetailRaw
import com.buntupana.tmdb.feature.lists.data.remote_data_source.raw.ListItemRaw
import com.buntupana.tmdb.feature.lists.data.remote_data_source.request.CreateListRequest
import com.buntupana.tmdb.feature.lists.data.remote_data_source.request.UpdateListRequest
import com.buntupana.tmdb.feature.lists.domain.model.MediaItemBasic
import com.panabuntu.tmdb.core.common.entity.MediaType
import com.panabuntu.tmdb.core.common.entity.NetworkError
import com.panabuntu.tmdb.core.common.entity.Result
import io.ktor.client.HttpClient
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody

class ListRemoteDataSource(
    private val httpClient: HttpClient
) : RemoteDataSource() {

    suspend fun getLists(
        accountObjectId: String,
        page: Int = 1
    ): Result<ResponseListRaw<ListItemRaw>, NetworkError> {
        return getResult<ResponseListRaw<ListItemRaw>> {
            httpClient.get(urlString = "4/account/$accountObjectId/lists") {
                parameter("page", page)
            }
        }
    }

    suspend fun checkItemInList(
        listId: Long,
        mediaId: Long,
        mediaType: MediaType
    ): Result<Unit, NetworkError> {

        return getResult {
            httpClient.get(urlString = "/4/list/$listId/item_status") {
                parameter("media_id", mediaId)
                parameter("media_type", mediaType.value)
            }
        }
    }

    suspend fun createList(
        name: String,
        description: String,
        isPublic: Boolean
    ): Result<CreateListRaw, NetworkError> {
        return getResult {
            httpClient.post(urlString = "4/list") {
                setBody(
                    CreateListRequest(
                        name = name,
                        description = description,
                        public = if (isPublic) "1" else "0"
                    )
                )
            }
        }
    }

    suspend fun updateList(
        listId: Long,
        name: String,
        description: String,
        isPublic: Boolean,
    ): Result<StandardRaw, NetworkError> {
        return getResult {
            httpClient.put(urlString = "4/list/$listId") {
                setBody(
                    UpdateListRequest(
                        name = name,
                        description = description,
                        public = isPublic
                    )
                )
            }
        }
    }

    suspend fun deleteList(listId: Long): Result<StandardRaw, NetworkError> {
        return getResult {
            httpClient.delete(urlString = "4/list/$listId")
        }
    }

    suspend fun addMediaItemList(
        listId: Long,
        mediaItemList: List<MediaItemBasic>
    ): Result<AddRemoveListItemListRaw, NetworkError> {

        return getResult {
            httpClient.post(urlString = "4/list/$listId/items") {
                setBody(
                    AddRemoveListItemListRequest(
                        addItemList = mediaItemList.map {
                            AddItem(
                                mediaId = it.id,
                                mediaType = it.mediaType.value
                            )
                        }
                    )
                )
            }
        }
    }

    suspend fun removeMediaItemList(
        listId: Long,
        mediaItemList: List<MediaItemBasic>
    ): Result<AddRemoveListItemListRaw, NetworkError> {

        return getResult {
            httpClient.delete(urlString = "4/list/$listId/items") {
                setBody(
                    AddRemoveListItemListRequest(
                        addItemList = mediaItemList.map {
                            AddItem(
                                mediaId = it.id,
                                mediaType = it.mediaType.value
                            )
                        }
                    )
                )
            }
        }
    }

    suspend fun getListDetail(
        listId: Long,
    ): Result<ListDetailRaw, NetworkError> {
        return getResult {
            httpClient.get(urlString = "/4/list/$listId")
        }
    }

    suspend fun getListItems(
        listId: Long,
        page: Int = 1
    ): Result<ResponseListRaw<MediaItemRaw>, NetworkError> {
        return getResult {
            httpClient.get(urlString = "/4/list/$listId") {
                parameter("page", page)
            }
        }
    }
}