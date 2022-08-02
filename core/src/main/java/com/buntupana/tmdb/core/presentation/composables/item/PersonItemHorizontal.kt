package com.buntupana.tmdb.core.presentation.composables.item

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import com.buntupana.tmdb.core.presentation.composables.DivisorCircle
import com.buntupana.tmdb.core.presentation.composables.ImageFromUrl
import com.buntupana.tmdb.core.presentation.theme.Dimens

@Composable
fun PersonItemHorizontal(
    modifier: Modifier,
    clickable: (() -> Unit)? = null,
    name: String,
    posterUrl: String,
    knownForDepartment: String,
    knownFor: String,
) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(Dimens.posterRound))
            .clickable {
                clickable?.invoke()
            }
    ) {

        ImageFromUrl(
            modifier = Modifier
                .aspectRatio(1f)
                .clip(RoundedCornerShape(Dimens.posterRound)),
            imageUrl = posterUrl
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(Dimens.padding.small),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = name,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = knownForDepartment,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                DivisorCircle()
                Text(
                    text = knownFor,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}