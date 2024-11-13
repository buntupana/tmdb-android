package com.buntupana.tmdb.feature.search.presentation.comp

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.buntupana.tmdb.core.ui.composables.item.MediaItemHorizontal
import com.buntupana.tmdb.core.ui.theme.Dimens
import com.buntupana.tmdb.core.ui.theme.PrimaryColor

@Composable
fun SearchPager(
    modifier: Modifier = Modifier,
    pagingItems: LazyPagingItems<out Any>?,
    noResultMessage: String,
    onMediaClick: (mediaItem: com.panabuntu.tmdb.core.common.model.MediaItem, mainPosterColor: Color) -> Unit,
    onPersonClick: (personId: Long) -> Unit
) {

    if (pagingItems == null) {
        return
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
    ) {

        when (pagingItems.loadState.refresh) {
            LoadState.Loading -> {
                item {
                    Column(
                        Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Spacer(modifier = Modifier.height(Dimens.padding.medium))
                        CircularProgressIndicator(color = PrimaryColor)
                    }
                }
            }

            is LoadState.Error -> {
                // TODO: Error to show when first page of paging fails
            }

            is LoadState.NotLoading -> {
                if (pagingItems.itemCount == 0) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(Dimens.padding.medium)
                        ) {
                            Text(text = noResultMessage)
                        }
                    }
                    return@LazyColumn
                }

                // Setting result items
                item {
                    Spacer(modifier = Modifier.height(Dimens.padding.tiny))
                }

                // Drawing result items
                items(pagingItems.itemCount) { index ->

                    val item = pagingItems[index] ?: return@items

                    when (item) {
                        is com.panabuntu.tmdb.core.common.model.MediaItem -> {
                            MediaItemHorizontal(
                                modifier = modifier.height(Dimens.imageSize.posterHeight),
                                onMediaClick = { _, mainPosterColor ->
                                    onMediaClick(item, mainPosterColor)
                                },
                                mediaId = item.id,
                                title = item.name,
                                posterUrl = item.posterUrl,
                                overview = item.overview.orEmpty(),
                                releaseDate = item.releaseDate
                            )
                        }

                        is com.panabuntu.tmdb.core.common.model.PersonItem -> {

                            val description = if (item.knownForList.firstOrNull() == null) {
                                item.knownForDepartment
                            } else {
                                "${item.knownForDepartment} • ${item.knownForList.first()}"
                            }

                            PersonItemHorizontal(
                                modifier = modifier.height(Dimens.imageSize.personHeightSmall),
                                personId = item.id,
                                name = item.name,
                                profileUrl = item.profilePath,
                                gender = item.gender,
                                description = description,
                                onClick = onPersonClick
                            )
                        }
                    }
                }

                // Appending result strategy
                when (pagingItems.loadState.append) {
                    LoadState.Loading -> {
                        item {
                            Column(
                                Modifier.fillMaxWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Spacer(modifier = Modifier.height(Dimens.padding.medium))
                                CircularProgressIndicator(
                                    color = PrimaryColor
                                )
                            }
                        }
                    }

                    is LoadState.Error -> {
                        // TODO: item to show when append paging fails
                    }

                    else -> {}
                }
                item {
                    Spacer(modifier = Modifier.height(Dimens.padding.tiny))
                }
            }
        }
    }
}