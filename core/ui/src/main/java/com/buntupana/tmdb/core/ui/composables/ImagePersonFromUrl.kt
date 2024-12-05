package com.buntupana.tmdb.core.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.buntupana.tmdb.core.ui.R
import com.buntupana.tmdb.core.ui.theme.PlaceHolderColor
import com.panabuntu.tmdb.core.common.model.Gender
import com.panabuntu.tmdb.core.common.util.isNotNullOrBlank

@Composable
fun ImagePersonFromUrl(
    modifier: Modifier,
    imageUrl: String?,
    gender: Gender = Gender.NOT_SPECIFIED,
    contentDescription: String? = null,
    crossFade: Boolean = true,
    contentScale: ContentScale = ContentScale.Crop,
) {
    if (imageUrl.isNotNullOrBlank()) {
        ImageFromUrl(
            modifier = modifier,
            imageUrl = imageUrl,
            crossFade = crossFade,
            contentScale = contentScale
        )

    } else {

        val imageRes = when (gender) {
            Gender.NOT_SPECIFIED -> R.drawable.ic_profile_man
            Gender.FEMALE -> R.drawable.ic_profile_woman
            Gender.MALE -> R.drawable.ic_profile_man
            Gender.NON_BINARY -> R.drawable.ic_profile_man
        }

        Surface(
            modifier = modifier
        ) {
            Box(
                Modifier
                    .fillMaxSize()
                    .background(PlaceHolderColor),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    modifier = Modifier
                        .fillMaxSize(0.8f),
                    painter = painterResource(id = imageRes),
                    contentDescription = contentDescription
                )
            }
        }
    }
}

@Preview
@Composable
private fun ImagePersonFromUrlPreview() {
    ImagePersonFromUrl(
        modifier = Modifier.size(100.dp),
        imageUrl = "",
        gender = Gender.FEMALE
    )
}