package com.buntupana.tmdb.feature.detail.presentation.cast.comp

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.buntupana.tmdb.core.ui.R
import com.buntupana.tmdb.core.ui.theme.Dimens
import com.buntupana.tmdb.core.ui.theme.Typography
import com.buntupana.tmdb.feature.detail.domain.model.Person
import com.buntupana.tmdb.feature.detail.presentation.mediaDetailsMovieSample
import com.panabuntu.tmdb.core.common.isNotNullOrEmpty

fun LazyListScope.castList(
    modifier: Modifier = Modifier,
    personCastList: List<Person.Cast>?,
    personCrewMap: Map<String, List<Person.Crew>>?,
    onPersonClick: (personId: Long) -> Unit
) {

    if (personCastList.isNotNullOrEmpty()) {

        item {
            Row(
                modifier = modifier
                    .padding(horizontal = Dimens.padding.horizontal)
                    .padding(top = Dimens.padding.vertical, bottom = Dimens.padding.verticalItem)
            ) {
                Text(
                    text = stringResource(id = R.string.text_cast),
                    fontSize = Typography.titleMedium.fontSize,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.padding(horizontal = Dimens.padding.tiny))

                Text(
                    text = personCastList.size.toString(),
                    fontSize = Typography.titleMedium.fontSize
                )
            }
        }
        personCastList.forEach { person ->
            item {

                PersonItemCastHorizontal(
                    modifier = modifier.fillMaxWidth(),
                    person = person,
                    onClick = onPersonClick
                )
            }
        }
    }

    if (personCrewMap.isNotNullOrEmpty()) {
        item {
            HorizontalDivider(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(vertical = Dimens.padding.verticalItem)
            )
            Row(
                modifier = modifier
                    .padding(
                        horizontal = Dimens.padding.horizontal,
                        vertical = Dimens.padding.verticalItem
                    )
            ) {
                Text(
                    text = stringResource(id = R.string.text_crew),
                    fontSize = Typography.titleMedium.fontSize,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.padding(horizontal = Dimens.padding.tiny))

                Text(
                    text = personCrewMap.values.flatten().size.toString(),
                    fontSize = Typography.titleMedium.fontSize
                )
            }
        }
        personCrewMap.forEach { (department, personList) ->
            item {
                Text(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(horizontal = Dimens.padding.horizontal)
                        .padding(
                            top = Dimens.padding.vertical,
                            bottom = Dimens.padding.verticalItem
                        ),
                    text = department,
                    fontWeight = FontWeight.Bold
                )
            }
            personList.forEach { person ->
                item {
                    PersonItemCastHorizontal(
                        modifier = modifier.fillMaxWidth(),
                        person = person,
                        onClick = onPersonClick
                    )
                }
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun CastListPreview() {
    LazyColumn {
        castList(
            personCastList = mediaDetailsMovieSample.castList,
            personCrewMap = mediaDetailsMovieSample.crewList.groupBy { it.department },
            onPersonClick = {}
        )
    }
}

