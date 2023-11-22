package com.buntupana.tmdb.feature.detail.presentation.cast.comp

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.buntupana.tmdb.core.presentation.composables.ImageFromUrl
import com.buntupana.tmdb.core.presentation.theme.DetailBackgroundColor
import com.buntupana.tmdb.core.presentation.theme.Dimens
import com.buntupana.tmdb.feature.detail.domain.model.Person
import com.buntupana.tmdb.feature.detail.presentation.castPersonSample

@Composable
fun PersonItem(
    modifier: Modifier = Modifier,
    person: Person,
    onClick: (personId: Long) -> Unit
) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(100.dp)
            .clickable { onClick(person.id) }
            .padding(vertical = Dimens.padding.verticalItem, horizontal = Dimens.padding.medium),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ImageFromUrl(
            modifier = Modifier
                .fillMaxHeight()
                .clip(RoundedCornerShape(Dimens.posterRound))
                .aspectRatio(1F),
            imageUrl = person.profileUrl
        )

        Spacer(modifier = Modifier.padding(horizontal = Dimens.padding.small))

        Column(
            modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = person.name,
                fontWeight = FontWeight.Bold
            )
            val subtitle = when (person) {
                is Person.Cast -> person.character
                is Person.Crew -> person.job
            }
            Text(text = subtitle)
        }
    }
}

@Preview
@Composable
fun PersonItemPreview() {
    PersonItem(
        modifier = Modifier.background(DetailBackgroundColor),
        person = castPersonSample,
        onClick = {}
    )
}