package com.buntupana.tmdb.feature.account.presentation.account.comp

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Login
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.fromHtml
import androidx.compose.ui.tooling.preview.Preview
import com.buntupana.tmdb.core.ui.composables.ImageFromUrl
import com.buntupana.tmdb.core.ui.composables.widget.AppTextWithIconButton
import com.buntupana.tmdb.core.ui.theme.AppTheme
import com.buntupana.tmdb.core.ui.theme.Dimens
import com.buntupana.tmdb.core.ui.util.getOnBackgroundColor
import com.buntupana.tmdb.feature.account.presentation.R

@Composable
fun SignUp(
    modifier: Modifier = Modifier,
    onSignUpClick: () -> Unit,
) {

    val backgroundColor = MaterialTheme.colorScheme.primaryContainer
    val contentColor = backgroundColor.getOnBackgroundColor()

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {
        ImageFromUrl(
            modifier = Modifier.fillMaxSize(),
            imageUrl = "https://media.themoviedb.org/t/p/w1920_and_h800_multi_faces/lMnoYqPIAVL0YaLP5YjRy7iwaYv.jpg",
            showPlaceHolder = true,
            placeHolderColor = backgroundColor
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundColor.copy(alpha = 0.70f))
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(state = rememberScrollState())
                .windowInsetsPadding(WindowInsets.statusBars)
                .padding(
                    horizontal = Dimens.padding.horizontal
                ),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(R.string.text_join_today),
                style = MaterialTheme.typography.headlineLarge.copy(
                    color = contentColor
                ),
                fontWeight = FontWeight.Bold
            )

            Text(
                modifier = Modifier.padding(top = Dimens.padding.medium),
                text = AnnotatedString.fromHtml(htmlString = stringResource(R.string.message_sign_up_html_description)),
                style = MaterialTheme.typography.titleLarge.copy(
                    color = contentColor
                )
            )

            AppTextWithIconButton(
                modifier = Modifier.padding(top = Dimens.padding.medium),
                text = stringResource(R.string.text_sign_up),
                imageVector = Icons.AutoMirrored.Filled.Login,
                onClick = onSignUpClick
            )

            Text(
                modifier = Modifier.padding(top = Dimens.padding.big),
                text = AnnotatedString.fromHtml(htmlString = stringResource(R.string.list_sign_up_vantages)),
                style = MaterialTheme.typography.titleSmall.copy(
                    color = contentColor
                )
            )
        }
    }
}

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    name = "DefaultPreviewLight",
    showBackground = true
)
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "DefaultPreviewDark",
    showBackground = true
)
@Composable
private fun SignUpPreview() {
    AppTheme {
        SignUp(
            modifier = Modifier
                .fillMaxSize(),
            onSignUpClick = {}
        )
    }
}