package com.buntupana.tmdb.feature.detail.presentation.person.comp

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import com.buntupana.tmdb.core.ui.composables.DropdownMenuText
import com.buntupana.tmdb.core.ui.theme.Dimens
import com.buntupana.tmdb.core.ui.util.TextButton
import com.buntupana.tmdb.core.ui.util.clickableTextPadding
import com.buntupana.tmdb.core.ui.util.getOnBackgroundColor
import com.buntupana.tmdb.feature.detail.domain.model.CreditPersonItem
import com.buntupana.tmdb.feature.detail.presentation.R
import com.panabuntu.tmdb.core.common.entity.MediaType
import timber.log.Timber
import com.buntupana.tmdb.core.ui.R as RCore

@Composable
fun CreditsFilter(
    mainDepartment: String,
    mediaTypeMap: Map<Int, String>,
    departmentMap: Map<String, String>,
    mediaTypeSelected: Int?,
    departmentSelected: String?,
    onFilterChange: (mediaType: Int?, department: String?) -> Unit,
) {

    val departmentAllValue = stringResource(id = RCore.string.text_department)

    val departmentSelectedValue =
        if (departmentSelected == null) departmentAllValue else departmentMap[departmentSelected].orEmpty()
    val mediaTypeSelectedValue =
        if (mediaTypeSelected == null) stringResource(id = RCore.string.text_all) else mediaTypeMap[mediaTypeSelected].orEmpty()

    Spacer(modifier = Modifier.height(Dimens.padding.big))

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .clickableTextPadding(),
            text = if (departmentSelected == null) mainDepartment else departmentSelectedValue,
            style = MaterialTheme.typography.titleLarge
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            AnimatedVisibility(
                visible = mediaTypeSelected != null || departmentSelected != null,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                TextButton(
                    onClick = { onFilterChange(null, null) },
                    rippleColor = MaterialTheme.colorScheme.background.getOnBackgroundColor()
                ) {
                    Text(
                        modifier = Modifier,
                        text = stringResource(id = RCore.string.text_clear),
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
            }

            DropdownMenuText(
                modifier = Modifier.animateContentSize(),
                text = mediaTypeSelectedValue,
                optionMap = mediaTypeMap,
                onOptionClicked = { id, value ->
                    Timber.d("Credits: selected type $value")
                    onFilterChange(id, departmentSelected)
                }
            )

            DropdownMenuText(
                modifier = Modifier.animateContentSize(),
                text = departmentSelectedValue,
                optionMap = departmentMap,
                onOptionClicked = { id, value ->
                    Timber.d("Credits: selected department $value")
                    onFilterChange(mediaTypeSelected, id)
                }
            )
        }

    }
}

fun LazyListScope.credits(
    personName: String,
    creditMap: Map<String, List<CreditPersonItem>>,
    mainDepartment: String,
    mediaTypeSelected: Int?,
    departmentSelected: String?,
    onItemClick: (id: Long, mediaType: MediaType, dominantColor: Color?) -> Unit
) {

    val creditMapFiltered = filterCreditsByMediaType(creditMap, mediaTypeSelected)

    if (departmentSelected == null) {

        Timber.d("Credits: Showing credits")
        val mainCreditList = creditMapFiltered[mainDepartment].orEmpty()

        if (mainCreditList.isEmpty()) {
            noCreditFound(personName = personName)
        } else {
            creditList(
                creditPersonList = creditMapFiltered[mainDepartment].orEmpty(),
                onItemClick = onItemClick
            )
        }
        creditMapFiltered.filter { it.key != mainDepartment }
            .forEach { (department, creditList) ->

                if (mainCreditList.isEmpty()) {
                    noCreditFound(personName = personName)
                } else {

                    item {

                        Spacer(modifier = Modifier.height(Dimens.padding.medium))

                        Text(
                            modifier = Modifier.padding(horizontal = Dimens.padding.horizontal),
                            text = department,
                            style = MaterialTheme.typography.titleLarge
                        )

                        Spacer(modifier = Modifier.height(Dimens.padding.small))
                    }
                    creditList(
                        creditPersonList = creditList,
                        onItemClick = onItemClick
                    )
                }
            }
    } else {

        val creditList = creditMapFiltered[departmentSelected].orEmpty()

        if (creditList.isEmpty()) {
            noCreditFound(personName = personName)
        } else {
            Timber.d("Credits: Showing credits")
            creditList(
                creditPersonList = creditMapFiltered[departmentSelected].orEmpty(),
                onItemClick = onItemClick
            )
        }
    }

    item {
        Spacer(modifier = Modifier.height(Dimens.padding.medium))
    }
}


fun LazyListScope.noCreditFound(
    personName: String
) {
    item {
        Text(
            modifier = Modifier.padding(
                horizontal = Dimens.padding.horizontal,
                vertical = Dimens.padding.big
            ),
            text = stringResource(id = R.string.text_no_credits, personName)
        )
    }
}

private fun filterCreditsByMediaType(
    creditMap: Map<String, List<CreditPersonItem>>,
    mediaTypeRes: Int?
): Map<String, List<CreditPersonItem>> {

    val mediaType = when (mediaTypeRes) {
        RCore.string.text_movies -> CreditPersonItem.Movie::class.java
        RCore.string.text_tv_shows -> CreditPersonItem.TvShow::class.java
        else -> return creditMap
    }

    return creditMap.mapValues {
        it.value.filterIsInstance(mediaType)
    }
}

private fun LazyListScope.creditList(
    creditPersonList: List<CreditPersonItem>,
    onItemClick: (id: Long, mediaType: MediaType, dominantColor: Color?) -> Unit
) {

    if (creditPersonList.isEmpty()) {
        return
    }

    item {
        Spacer(modifier = Modifier.height(Dimens.padding.medium))
    }

    creditPersonList.groupBy { it.releaseDate?.year }.forEach { (_, creditList) ->

        item {
            HorizontalDivider()
        }

        items(creditList) { credit ->
            Row(
                Modifier
                    .fillMaxWidth()
                    .clickable {
                        when (credit) {
                            is CreditPersonItem.Movie -> MediaType.MOVIE
                            is CreditPersonItem.TvShow -> MediaType.TV_SHOW
                        }.let { mediaType ->
                            onItemClick(credit.id, mediaType, null)
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
    item {
        HorizontalDivider()
    }
}

@Preview
@Composable
fun CreditsFilterPreview() {
    CreditsFilter(
        mainDepartment = "Acting",
        mediaTypeMap = mapOf(
            RCore.string.text_movies to "Movies",
            RCore.string.text_tv_shows to "TV Shows"
        ),
        departmentMap = mapOf(
            "Acting" to "Acting",
            "Directing" to "Directing"
        ),
        mediaTypeSelected = RCore.string.text_movies,
        departmentSelected = "Acting",
        onFilterChange = { _, _ -> }
    )
}