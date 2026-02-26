package com.panabuntu.tmdb.core.common.model

data class PersonItem(
    override val id: Long,
    val name: String,
    val profilePath: String?,
    val popularity: Double,
    val adult: Boolean,
    val gender: Gender,
    val knownForDepartment: String,
    val knownForList: List<String>
): DefaultItem
