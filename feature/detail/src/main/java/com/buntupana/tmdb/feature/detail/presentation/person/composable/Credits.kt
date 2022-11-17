package com.buntupana.tmdb.feature.detail.presentation.person.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import com.buntupana.tmdb.core.domain.entity.MediaType
import com.buntupana.tmdb.core.presentation.composables.DropdownMenuText
import com.buntupana.tmdb.core.presentation.theme.Dimens
import com.buntupana.tmdb.core.presentation.theme.Secondary
import com.buntupana.tmdb.core.presentation.util.clickableTextPadding
import com.buntupana.tmdb.feature.detail.R
import com.buntupana.tmdb.feature.detail.domain.model.CreditPersonItem
import timber.log.Timber
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

        val departmentAllRes = stringResource(id = RCore.string.text_department)

        var mediaTypeSelected by remember {
            mutableStateOf(RCore.string.text_all)
        }

        Timber.d("Credits: mediaTypeSelected = ${stringResource(id = mediaTypeSelected)}")

        var departmentSelectedRes by remember {
            mutableStateOf(departmentAllRes)
        }

        Timber.d("Credits: departmentSelected = $departmentSelectedRes")

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
                            Timber.d("Credits: click clear")
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

            DropdownMenuText(
                modifier = Modifier,
                text = stringResource(id = mediaTypeSelected),
                optionList = mapOf(
                    RCore.string.text_movies to stringResource(id = RCore.string.text_movies),
                    RCore.string.text_tv_shows to stringResource(id = RCore.string.text_tv_shows)
                ),
                onOptionClicked = { id, value ->
                    Timber.d("Credits: selected type ${id}")
                    mediaTypeSelected = id
                }
            )

            DropdownMenuText(
                modifier = Modifier,
                text = departmentSelectedRes,
                optionList = creditMap.keys.associateWith { it },
                onOptionClicked = { id, value ->
                    Timber.d("Credits: selected department $value")
                    departmentSelectedRes = value
                }
            )
        }

        val creditMapFiltered = if (mediaTypeSelected == RCore.string.text_all) {
            creditMap
        } else {
            creditMap.mapValues {
                when (mediaTypeSelected) {
                    RCore.string.text_movies -> it.value.filterIsInstance<CreditPersonItem.Movie>()
                    RCore.string.text_tv_shows -> it.value.filterIsInstance<CreditPersonItem.TvShow>()
                    else -> it.value
                }
            }
        }

        if (departmentSelectedRes == departmentAllRes) {

            Timber.d("Credits: Showing credits")
            val mainCreditList = creditMapFiltered[mainDepartment].orEmpty()

            if (mainCreditList.isEmpty()) {
                NoCreditFound(personName = personName)
            } else {
                CreditList(
                    creditPersonList = creditMapFiltered[mainDepartment].orEmpty(),
                    onItemClick = onItemClick
                )
            }

            Timber.d("Credits: Showed main deparment credits")
            creditMapFiltered.filter { it.key != mainDepartment }
                .forEach { (department, creditList) ->

                    if (mainCreditList.isEmpty()) {
                        NoCreditFound(personName = personName)
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

            Timber.d("Credits: Showed main rest department credits")
        } else {

            val creditList = creditMapFiltered[departmentSelectedRes].orEmpty()

            if (creditList.isEmpty()) {
                NoCreditFound(personName = personName)
            } else {
                Timber.d("Credits: Showing credits")
                CreditList(
                    creditPersonList = creditMapFiltered[departmentSelectedRes].orEmpty(),
                    onItemClick = onItemClick
                )
            }

        }

        Spacer(modifier = Modifier.height(Dimens.padding.medium))
    }
}

@Composable
fun NoCreditFound(
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