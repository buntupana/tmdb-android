package com.buntupana.tmdb.feature.lists.presentation.util

import com.buntupana.tmdb.feature.lists.domain.model.UserListDetails

val listItemMediaLists = listOf(
    UserListDetails(
        id = 1,
        name = "Action Movies",
        backdropUrl = "",
        posterUrl = "",
        averageRating = 30,
        description = "This is a list description",
        itemCount = 7,
        isPublic = false,
        revenue = 1,
        runtime = 10000,
        updatedAt = null,
        shareLink = "test"
    ),
    UserListDetails(
        id = 1,
        name = "Drama TV Shows",
        backdropUrl = "",
        posterUrl = "",
        averageRating = 20,
        description = "",
        itemCount = 7,
        isPublic = false,
        revenue = 1,
        runtime = 20000,
        updatedAt = null,
        shareLink = "test"
    )
)