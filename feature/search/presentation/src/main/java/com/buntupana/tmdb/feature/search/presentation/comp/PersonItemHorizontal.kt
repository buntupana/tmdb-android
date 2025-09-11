package com.buntupana.tmdb.feature.search.presentation.comp

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.buntupana.tmdb.core.ui.composables.ImagePersonFromUrl
import com.buntupana.tmdb.core.ui.theme.AppTheme
import com.buntupana.tmdb.core.ui.theme.Dimens

@Composable
fun PersonItemHorizontal(
    modifier: Modifier = Modifier,
    personId: Long,
    name: String,
    gender: com.panabuntu.tmdb.core.common.model.Gender,
    profileUrl: String?,
    description: String,
    onClick: (personId: Long) -> Unit
) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(Dimens.posterRound))
            .clickable { onClick(personId) }
            .padding(horizontal = Dimens.padding.horizontal, vertical = Dimens.padding.verticalItem)
    ) {

        ImagePersonFromUrl(
            modifier = Modifier
                .aspectRatio(1f)
                .clip(RoundedCornerShape(Dimens.posterRound)),
            imageUrl = profileUrl,
            gender = gender
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = Dimens.padding.small),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = name,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = description,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
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
fun PersonItemHorizontalPreview() {
    AppTheme {
        PersonItemHorizontal(
            modifier = Modifier
                .fillMaxWidth()
                .height(Dimens.imageSize.personHeightSmall),
            personId = 0L,
            name = "Jason Momoa",
            profileUrl = "",
            description = "Acting • Aquaman",
            gender = com.panabuntu.tmdb.core.common.model.Gender.MALE,
            onClick = {}
        )
    }
}