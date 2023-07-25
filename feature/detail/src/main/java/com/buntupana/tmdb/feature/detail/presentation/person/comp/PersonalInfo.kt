package com.buntupana.tmdb.feature.detail.presentation.person.comp

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
import com.buntupana.tmdb.core.presentation.composables.ExpandableText
import com.buntupana.tmdb.core.presentation.composables.TitleAndSubtitle
import com.buntupana.tmdb.core.presentation.theme.Dimens
import com.buntupana.tmdb.core.presentation.util.getString
import com.buntupana.tmdb.core.presentation.util.ifNull
import com.buntupana.tmdb.core.presentation.util.toLocalFormat
import com.buntupana.tmdb.feature.detail.R
import com.buntupana.tmdb.feature.detail.domain.model.PersonFullDetails

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
            text = stringResource(id = R.string.text_personal_info),
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleLarge
        )

        Row {
            if (personDetails.knownForDepartment.isNotBlank()) {
                TitleAndSubtitle(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    title = stringResource(id = R.string.text_known_for),
                    subtitle = personDetails.knownForDepartment
                )
            }
            TitleAndSubtitle(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                title = stringResource(id = R.string.text_known_credits),
                subtitle = personDetails.knownCredits.toString()
            )
        }

        TitleAndSubtitle(
            title = stringResource(id = R.string.text_gender),
            subtitle = personDetails.gender.getString()
        )

        val ageString = if (personDetails.age > 0) {
            "(${stringResource(id = R.string.text_age, personDetails.age)})"
        } else ""

        val birthdayString = if (personDetails.deathDate == null) {
            (personDetails.birthDate?.toLocalFormat().ifNull { " - " } + (" $ageString"))
        } else {
            personDetails.birthDate?.toLocalFormat() ?: " - "
        }

        TitleAndSubtitle(
            title = stringResource(id = R.string.text_birthdate),
            subtitle = birthdayString
        )

        if (personDetails.deathDate != null) {
            TitleAndSubtitle(
                title = stringResource(id = R.string.text_day_of_death),
                subtitle = personDetails.deathDate.toLocalFormat() + " $ageString"
            )
        }
        TitleAndSubtitle(
            title = stringResource(id = R.string.text_place_birth),
            subtitle = personDetails.placeOfBirth.ifBlank { " - " }
        )

        Spacer(modifier = Modifier.height(Dimens.padding.small))

        Text(
            text = stringResource(id = R.string.text_biography),
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(Dimens.padding.small))

        ExpandableText(text = personDetails.biography.ifBlank {
            stringResource(
                id = R.string.text_no_biography,
                personDetails.name
            )
        })

        Spacer(Modifier.height(Dimens.padding.small))
    }
}