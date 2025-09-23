package com.buntupana.tmdb.feature.detail.presentation.person.comp

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.buntupana.tmdb.core.ui.composables.ExpandableText
import com.buntupana.tmdb.core.ui.composables.TitleAndSubtitle
import com.buntupana.tmdb.core.ui.theme.AppTheme
import com.buntupana.tmdb.core.ui.theme.Dimens
import com.buntupana.tmdb.core.ui.util.getString
import com.buntupana.tmdb.feature.detail.domain.model.PersonFullDetails
import com.buntupana.tmdb.feature.detail.presentation.R
import com.buntupana.tmdb.feature.detail.presentation.personDetailsSample
import com.panabuntu.tmdb.core.common.util.ifNull
import com.panabuntu.tmdb.core.common.util.toLocalFormat

@Composable
fun PersonalInfo(
    personDetails: PersonFullDetails
) {

    Column(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = Dimens.padding.medium, vertical = Dimens.padding.small)
    ) {

        Spacer(modifier = Modifier.height(Dimens.padding.small))

        Text(
            text = stringResource(id = R.string.detail_personal_info),
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleLarge
        )

        Row {
            if (personDetails.knownForDepartment.isNotBlank()) {
                TitleAndSubtitle(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    title = stringResource(id = R.string.detail_known_for),
                    subtitle = personDetails.knownForDepartment
                )
            }
            TitleAndSubtitle(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                title = stringResource(id = R.string.detail_known_credits),
                subtitle = personDetails.knownCredits.toString()
            )
        }

        TitleAndSubtitle(
            title = stringResource(id = R.string.detail_gender),
            subtitle = personDetails.gender.getString()
        )

        val ageString = if (personDetails.age > 0) {
            "(${stringResource(id = R.string.detail_age, personDetails.age)})"
        } else ""

        val birthdayString = if (personDetails.deathDate == null) {
            (personDetails.birthDate?.toLocalFormat().ifNull { " - " } + (" $ageString"))
        } else {
            personDetails.birthDate?.toLocalFormat() ?: " - "
        }

        TitleAndSubtitle(
            title = stringResource(id = R.string.detail_birthdate),
            subtitle = birthdayString
        )

        if (personDetails.deathDate != null) {
            TitleAndSubtitle(
                title = stringResource(id = R.string.detail_day_of_death),
                subtitle = personDetails.deathDate!!.toLocalFormat() + " $ageString"
            )
        }
        TitleAndSubtitle(
            title = stringResource(id = R.string.detail_place_birth),
            subtitle = personDetails.placeOfBirth.ifBlank { " - " }
        )

        Spacer(modifier = Modifier.height(Dimens.padding.small))

        Text(
            text = stringResource(id = R.string.detail_biography),
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(Dimens.padding.small))

        ExpandableText(text = personDetails.biography.ifBlank {
            stringResource(
                id = R.string.detail_no_biography,
                personDetails.name
            )
        })

        Spacer(Modifier.height(Dimens.padding.small))
    }
}

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    name = "DefaultPreviewLight",
    showBackground = true,
)
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "DefaultPreviewDark",
    showBackground = true,
)
@Composable
private fun PersonalInfoPreview() {
    AppTheme {
        PersonalInfo(personDetails = personDetailsSample)
    }
}