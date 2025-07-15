package com.buntupana.tmdb.feature.account.presentation.account.comp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Login
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.fromHtml
import androidx.compose.ui.tooling.preview.Preview
import com.buntupana.tmdb.core.ui.composables.ImageFromUrl
import com.buntupana.tmdb.core.ui.theme.Dimens
import com.buntupana.tmdb.core.ui.theme.SecondaryColor
import com.buntupana.tmdb.core.ui.theme.SignUpBackgroundColor
import com.buntupana.tmdb.feature.account.presentation.R

@Composable
fun SignUp(
    modifier: Modifier = Modifier,
    onSignUpClick: () -> Unit,
) {

    val backgroundColor = SignUpBackgroundColor

    Column(
        modifier = modifier
            .fillMaxSize()
    ) {

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) { }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(backgroundColor)
        ) {

            ImageFromUrl(
                modifier = Modifier.matchParentSize(),
                imageUrl = "https://media.themoviedb.org/t/p/w1920_and_h800_multi_faces/lMnoYqPIAVL0YaLP5YjRy7iwaYv.jpg",
                showPlaceHolder = true,
                placeHolderColor = backgroundColor
            )

            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(backgroundColor.copy(alpha = 0.70f))
            )

            Column(
                modifier = Modifier
                    .padding(
                        horizontal = Dimens.padding.horizontal,
                        vertical = Dimens.padding.big
                    )
            ) {
                Text(
                    text = stringResource(R.string.text_join_today),
                    style = MaterialTheme.typography.headlineLarge.copy(
                        color = Color.White
                    ),
                    fontWeight = FontWeight.Bold
                )

                Text(
                    modifier = Modifier.padding(top = Dimens.padding.medium),
                    text = AnnotatedString.fromHtml(htmlString = stringResource(R.string.message_sign_up_html_description)),
                    style = MaterialTheme.typography.titleLarge.copy(
                        color = Color.White
                    )
                )

                Button(
                    modifier = Modifier.padding(top = Dimens.padding.medium),
                    colors = ButtonDefaults.buttonColors(containerColor = SecondaryColor),
                    onClick = onSignUpClick,
                ) {
                    Icon(Icons.AutoMirrored.Filled.Login, contentDescription = null)
                    Spacer(modifier = Modifier.padding(horizontal = Dimens.padding.tiny))
                    Text(text = stringResource(R.string.text_sign_up))
                }

                Text(
                    modifier = Modifier.padding(top = Dimens.padding.big),
                    text = AnnotatedString.fromHtml(htmlString = stringResource(R.string.list_sign_up_vantages)),
                    style = MaterialTheme.typography.titleSmall.copy(
                        color = Color.White
                    )
                )
            }
        }
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
//                .background(backgroundColor)
        ) { }
    }
}

@Preview(showBackground = true)
@Composable
private fun JoinPreview() {
    SignUp(
        modifier = Modifier
            .fillMaxSize(),
        onSignUpClick = {}
    )
}
