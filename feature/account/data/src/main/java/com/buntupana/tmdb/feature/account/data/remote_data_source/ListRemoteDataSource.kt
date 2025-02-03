package com.buntupana.tmdb.feature.account.data.remote_data_source

import com.buntupana.tmdb.core.data.raw.ResponseListRaw
import com.buntupana.tmdb.core.data.raw.StandardRaw
import com.buntupana.tmdb.core.data.remote_data_source.RemoteDataSource
import com.buntupana.tmdb.feature.account.data.raw.AddRemoveListItemListRaw
import com.buntupana.tmdb.feature.account.data.raw.CreateListRaw
import com.buntupana.tmdb.feature.account.data.raw.ListItemRaw
import com.buntupana.tmdb.feature.account.data.request.AddItem
import com.buntupana.tmdb.feature.account.data.request.AddRemoveListItemListRequest
import com.buntupana.tmdb.feature.account.data.request.CreateUpdateListRequest
import com.buntupana.tmdb.feature.account.domain.model.MediaItemBasic
import com.panabuntu.tmdb.core.common.entity.NetworkError
import com.panabuntu.tmdb.core.common.entity.Result
import io.ktor.client.HttpClient
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import javax.inject.Inject
import javax.inject.Named

class ListRemoteDataSource @Inject constructor(
    @Named("ApiV4") private val httpClient: HttpClient
) : RemoteDataSource() {
    suspend fun getLists(
        accountObjectId: String,
        page: Int = 1
    ): Result<ResponseListRaw<ListItemRaw>, NetworkError> {
        return getResult<ResponseListRaw<ListItemRaw>> {
            httpClient.get(urlString = "account/$accountObjectId/lists") {
                parameter("page", page)
            }
        }
    }

    suspend fun createList(
        name: String,
        description: String,
        isPublic: Boolean
    ): Result<CreateListRaw, NetworkError> {
        return getResult {
            httpClient.post(urlString = "list") {
                setBody(
                    CreateUpdateListRequest(
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
            httpClient.put(urlString = "list/$listId") {
                setBody(
                    CreateUpdateListRequest(
                        name = name,
                        description = description,
                        public = if (isPublic) "1" else "0"
                    )
                )
            }
        }
    }

    suspend fun removeList(listId: Long): Result<StandardRaw, NetworkError> {
        return getResult {
            httpClient.delete(urlString = "list/$listId")
        }
    }

    suspend fun addMediaItemList(
        listId: Long,
        mediaItemList: List<MediaItemBasic>
    ): Result<AddRemoveListItemListRaw, NetworkError> {

        return getResult {
            httpClient.post(urlString = "list/$listId/items") {
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
            httpClient.delete(urlString = "list/$listId/items") {
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
}