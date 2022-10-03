package com.buntupana.tmdb.feature.detail.presentation.person.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import com.buntupana.tmdb.core.domain.entity.MediaType
import com.buntupana.tmdb.core.presentation.composables.TextWithIcon
import com.buntupana.tmdb.core.presentation.theme.Dimens
import com.buntupana.tmdb.core.presentation.theme.Secondary
import com.buntupana.tmdb.core.presentation.util.clickableTextPadding
import com.buntupana.tmdb.feature.detail.R
import com.buntupana.tmdb.feature.detail.domain.model.CreditPersonItem
import com.buntupana.tmdb.core.R as RCore

@Composable
fun Credits(
    personName: String,
    mainDepartment: String,
    creditMap: Map<String, List<CreditPersonItem>>,
    onItemClick: (id: Long, mediaType: MediaType) -> Unit
) {

    if (creditMap.isEmpty()) {
        return
    }

    Spacer(modifier = Modifier.height(Dimens.padding.big))

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {

        var creditMapFiltered = remember {
            creditMap
        }

        val departmentAllRes = stringResource(id = RCore.string.text_department)

        var mediaTypeSelected by remember {
            mutableStateOf(RCore.string.text_all)
        }

        var departmentSelectedRes by remember {
            mutableStateOf(departmentAllRes)
        }

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .clickableTextPadding(),
            text = if (departmentSelectedRes == departmentAllRes) mainDepartment else departmentSelectedRes,
            style = MaterialTheme.typography.titleLarge
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {

            if (mediaTypeSelected != RCore.string.text_all || departmentSelectedRes != departmentAllRes) {
                Text(
                    modifier = Modifier
                        .clickable {
                            mediaTypeSelected = RCore.string.text_all
                            departmentSelectedRes = departmentAllRes
                        }
                        .padding(
                            horizontal = Dimens.padding.medium,
                            vertical = Dimens.padding.small
                        ),
                    text = stringResource(id = RCore.string.text_clear),
                    color = Secondary
                )
            }

            Box {

                var mediaTypeDropdownExpanded by remember {
                    mutableStateOf(false)
                }

                TextWithIcon(
                    modifier = Modifier
                        .clickable {
                            mediaTypeDropdownExpanded = true
                        }
                        .clickableTextPadding(),
                    text = stringResource(id = mediaTypeSelected),
                    iconRes = RCore.drawable.ic_arrow_down
                )

                DropdownMenu(expanded = mediaTypeDropdownExpanded, onDismissRequest = {
                    mediaTypeDropdownExpanded = false
                }) {
                    DropdownMenuItem(
                        text = {
                            Text(text = stringResource(id = RCore.string.text_movies))
                        },
                        onClick = {
                            mediaTypeDropdownExpanded = false
                            creditMapFiltered = creditMap.mapValues {
                                it.value.filterIsInstance<CreditPersonItem.Movie>()
                            }
                            mediaTypeSelected = RCore.string.text_movies
                        }
                    )
                    DropdownMenuItem(
                        text = {
                            Text(text = stringResource(id = RCore.string.text_tv_shows))
                        },
                        onClick = {
                            mediaTypeDropdownExpanded = false
                            creditMapFiltered = creditMap.mapValues {
                                it.value.filterIsInstance<CreditPersonItem.TvShow>()
                            }
                            mediaTypeSelected = RCore.string.text_tv_shows
                        }
                    )
                }
            }

            Box {

                var departmentDropdownExpanded by remember {
                    mutableStateOf(false)
                }

                TextWithIcon(
                    modifier = Modifier
                        .clickable {
                            departmentDropdownExpanded = true
                        }
                        .clickableTextPadding(),
                    text = departmentSelectedRes,
                    iconRes = RCore.drawable.ic_arrow_down
                )

                DropdownMenu(expanded = departmentDropdownExpanded, onDismissRequest = {
                    departmentDropdownExpanded = false
                }) {
                    creditMap.keys.forEach { department ->
                        DropdownMenuItem(
                            text = {
                                Text(text = department)
                            },
                            onClick = {
                                departmentDropdownExpanded = false
                                departmentSelectedRes = department
                            }
                        )
                    }
                }
            }
        }

        val creditMapFilteredAux = filterCreditsByMediaType(
            creditMap,
            mediaTypeSelected
        )

        if (departmentSelectedRes == departmentAllRes) {

            val mainCreditList = creditMapFilteredAux[mainDepartment].orEmpty()

            if (mainCreditList.isEmpty()) {
                NoCreditFount(personName = personName)
            } else {
                CreditList(
                    creditPersonList = creditMapFilteredAux[mainDepartment].orEmpty(),
                    onItemClick = onItemClick
                )
            }

            creditMapFilteredAux.filter { it.key != mainDepartment }
                .forEach { (department, creditList) ->

                    if (mainCreditList.isEmpty()) {
                        NoCreditFount(personName = personName)
                    } else {

                        Spacer(modifier = Modifier.height(Dimens.padding.medium))

                        Text(
                            modifier = Modifier.padding(horizontal = Dimens.padding.horizontal),
                            text = department,
                            style = MaterialTheme.typography.titleLarge
                        )

                        Spacer(modifier = Modifier.height(Dimens.padding.small))

                        CreditList(
                            creditPersonList = creditList,
                            onItemClick = onItemClick
                        )
                    }
                }
        } else {

            val creditList = creditMapFilteredAux[departmentSelectedRes].orEmpty()

            if (creditList.isEmpty()) {
                NoCreditFount(personName = personName)
            } else {
                CreditList(
                    creditPersonList = creditMapFilteredAux[departmentSelectedRes].orEmpty(),
                    onItemClick = onItemClick
                )
            }

        }

        Spacer(modifier = Modifier.height(Dimens.padding.medium))
    }
}

@Composable
fun NoCreditFount(
    personName: String
) {
    Text(
        modifier = Modifier.padding(
            horizontal = Dimens.padding.horizontal,
            vertical = Dimens.padding.big
        ),
        text = stringResource(id = R.string.text_no_credits, personName)
    )
}

private fun filterCreditsByMediaType(
    creditMap: Map<String, List<CreditPersonItem>>,
    mediaTypeRes: Int
): Map<String, List<CreditPersonItem>> {

    val mediaType = when (mediaTypeRes) {
        RCore.string.text_movies -> CreditPersonItem.Movie::class.java
        RCore.string.text_tv_shows -> CreditPersonItem.TvShow::class.java
        else -> null
    }

    return creditMap.mapValues {
        if (mediaType == null) {
            it.value
        } else {
            it.value.filterIsInstance(mediaType)
        }
    }
}

@Composable
private fun CreditList(
    creditPersonList: List<CreditPersonItem>,
    onItemClick: (id: Long, mediaType: MediaType) -> Unit
) {

    if (creditPersonList.isEmpty()) {
        return
    }

    Column(modifier = Modifier.fillMaxWidth()) {

        Spacer(modifier = Modifier.height(Dimens.padding.medium))

        creditPersonList.groupBy { it.releaseDate?.year }.forEach { (year, creditList) ->

            Divider()

            creditList.forEach { credit ->
                Row(
                    Modifier
                        .fillMaxWidth()
                        .clickable {
                            when (credit) {
                                is CreditPersonItem.Movie -> MediaType.MOVIE
                                is CreditPersonItem.TvShow -> MediaType.TV_SHOW
                            }.let { mediaType ->
                                onItemClick(credit.id, mediaType)
                            }
                        }
                        .padding(
                            horizontal = Dimens.padding.horizontal,
                            vertical = Dimens.padding.medium
                        )
                ) {
                    Text(
                        text = credit.releaseDate?.year?.toString() ?: "------"
                    )
                    Spacer(modifier = Modifier.width(Dimens.padding.medium))
                    Text(
                        buildAnnotatedString {
                            withStyle(
                                style = SpanStyle(fontWeight = FontWeight.Bold)
                            ) {
                                append(credit.title)
                            }
                            if (credit.role.isNotBlank()) {
                                append(" ")
                                withStyle(
                                    style = SpanStyle(fontWeight = FontWeight.Light)
                                ) {
                                    append(stringResource(id = R.string.text_as))
                                }
                                append(" ")
                                append(credit.role)
                            }
                        }
                    )
                }
            }
        }
        Divider()
    }
}