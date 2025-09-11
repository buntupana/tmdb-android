package com.buntupana.tmdb.core.ui.composables

import android.content.res.Configuration
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.buntupana.tmdb.core.ui.R
import com.buntupana.tmdb.core.ui.composables.widget.AppTextButton
import com.buntupana.tmdb.core.ui.theme.AppTheme
import com.buntupana.tmdb.core.ui.theme.Dimens
import com.buntupana.tmdb.core.ui.util.toPx

@Composable
fun OutlinedText(
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color.Unspecified,
    text: String = "",
    fontSize: TextUnit = TextUnit.Unspecified,
    fontWeight: FontWeight = FontWeight.Normal,
    color: Color = Color.Unspecified,
    outlineColor: Color = color,
    cornerRound: Dp = 100.dp,
    padding: PaddingValues = PaddingValues(0.dp),
    internalHorizontalPadding: Dp = 4.dp,
    internalVerticalPadding: Dp = 0.dp
) {

    if (text.isNotEmpty()) {
        Text(
            modifier = modifier
                .padding(padding)
                .clip(RoundedCornerShape(cornerRound))
                .background(backgroundColor)
                .border(BorderStroke(1.dp, outlineColor), RoundedCornerShape(cornerRound))
                .padding(
                    horizontal = internalHorizontalPadding,
                    vertical = internalVerticalPadding
                ),
            text = text,
            color = color,
            fontWeight = fontWeight,
            fontSize = fontSize
        )
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
fun OutlinedTextPreview() {
    AppTheme {
        OutlinedText(
            text = "Hola",
            fontWeight = FontWeight.Bold,
            backgroundColor = Color.Yellow,
            cornerRound = 100.dp,
            outlineColor = Color.Red,
            internalVerticalPadding = 4.dp,
            internalHorizontalPadding = 8.dp,
            padding = PaddingValues(20.dp)
        )
    }
}

@Composable
fun HoursMinutesText(
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified,
    time: Long
) {

    val hours = time / 60
    val minutes = time - (hours * 60)

    val timeText = if (hours == 0L) {
        stringResource(id = R.string.text_minutes, minutes)
    } else {
        stringResource(id = R.string.text_hours_minutes, hours, minutes)
    }
    Text(
        modifier = modifier,
        text = timeText,
        color = color
    )
}

@Composable
fun TextFieldSearch(
    modifier: Modifier = Modifier,
    fontColor: Color = Color.Gray,
    onValueChange: (value: String) -> Unit,
    onSearch: (searchKey: String) -> Unit,
    value: String,
    isEnabled: Boolean = true,
    fontSize: TextUnit = MaterialTheme.typography.titleLarge.fontSize,
    requestFocus: Boolean = false,
    cursorColor: Color = MaterialTheme.colorScheme.primary
) {
    Box(
        modifier = modifier.padding(
            horizontal = Dimens.padding.medium,
            vertical = Dimens.padding.small
        ),
        contentAlignment = Alignment.CenterStart
    ) {

        val focus = remember { FocusRequester() }
        val focusManager = LocalFocusManager.current

        var triggerFocus by rememberSaveable {
            mutableStateOf(requestFocus)
        }

        LaunchedEffect(key1 = true) {
            if (triggerFocus) {
                focus.requestFocus()
                triggerFocus = false
            }
        }

        val customTextSelectionColors = TextSelectionColors(
            handleColor = cursorColor,
            backgroundColor = cursorColor.copy(alpha = 0.4f)
        )
        CompositionLocalProvider(LocalTextSelectionColors provides customTextSelectionColors) {
            BasicTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focus),
                value = value,
                onValueChange = {
                    onValueChange(it)
                },
                textStyle = TextStyle(
                    color = fontColor,
                    fontSize = fontSize
                ),
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(
                    onDone = {
                        onSearch(value)
                        focusManager.clearFocus()
                    }
                ),
                enabled = isEnabled,
                cursorBrush = SolidColor(cursorColor)
            )
        }
        if (value.isBlank()) {
            Text(
                text = stringResource(id = R.string.text_search),
                color = Color.Gray,
                fontSize = fontSize
            )
        }
    }
}

@Composable
fun TitleAndSubtitle(
    modifier: Modifier = Modifier,
    title: String,
    subtitle: String,
    fontColor: Color = MaterialTheme.colorScheme.onBackground
) {
    Box(modifier = modifier) {
        Column(
            modifier = Modifier
                .padding(vertical = Dimens.padding.small)
        ) {
            Text(
                text = title,
                color = fontColor,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = subtitle,
                color = fontColor
            )
        }
    }
}

@Composable
fun ExpandableText(
    modifier: Modifier = Modifier,
    text: String,
    fontSize: TextUnit = TextUnit.Unspecified,
    fontWeight: FontWeight = FontWeight.Normal,
    collapsedVisibleLines: Int = 12
) {

    var textExpanded by remember {
        mutableStateOf(false)
    }

    Box(modifier = modifier) {

        Text(
            modifier = Modifier
                .animateContentSize(),
            text = text,
            fontSize = fontSize,
            fontWeight = fontWeight,
            maxLines = if (textExpanded) Int.MAX_VALUE else collapsedVisibleLines,
            onTextLayout = {
                if (!textExpanded) {
                    textExpanded = it.lineCount < collapsedVisibleLines && textExpanded == false
                }
            }
        )

        if (textExpanded) return@Box

        Box(
            Modifier
                .align(Alignment.BottomEnd),
            contentAlignment = Alignment.BottomEnd
        ) {

            val gradientMargin = 32.dp
            var buttonHeight by remember { mutableStateOf(0.dp) }
            var buttonWidth by remember { mutableStateOf(0.dp) }

            Row(
                modifier = Modifier.size(
                    width = buttonWidth + buttonHeight,
                    height = buttonHeight + gradientMargin
                )
            ) {

                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(buttonHeight + gradientMargin)
                        .background(
                            Brush.radialGradient(
                                colorStops = arrayOf(
                                    0f to MaterialTheme.colorScheme.background,
                                    0.5f to MaterialTheme.colorScheme.background,
                                    1f to Color.Transparent
                                ),
                                center = Offset(
                                    x = (buttonHeight + gradientMargin).toPx(),
                                    y = (buttonHeight + gradientMargin).toPx()
                                ),
                                radius = (buttonHeight + gradientMargin).toPx()
                            )
                        )

                ) {}

                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f)
                        .background(
                            Brush.verticalGradient(
                                colorStops = arrayOf(
                                    0f to Color.Transparent,
                                    0.5f to MaterialTheme.colorScheme.background,
                                    1f to MaterialTheme.colorScheme.background
                                ),
                            )
                        )
                ) {}
            }

            val density = LocalDensity.current

            AppTextButton(
                modifier = Modifier
                    .onGloballyPositioned {
                        with(density) {
                            buttonHeight = it.size.height.toDp()
                            buttonWidth = it.size.width.toDp()
                        }
                    },
                onClick = { textExpanded = true },
                rippleColor = MaterialTheme.colorScheme.onBackground,
            ) {
                Text(
                    text = stringResource(id = R.string.text_read_more),
                    color = MaterialTheme.colorScheme.secondary,
                    fontWeight = FontWeight.Bold
                )
                Image(
                    modifier = Modifier.size(16.dp),
                    painter = painterResource(id = R.drawable.ic_arrow_right),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.secondary)
                )
            }
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
fun ExpandableTextPreview() {
    AppTheme {
        ExpandableText(
            text = """
            111111111111111111111111111111111111111111111111111
            222222222222222222222222222222222222222222222222222
            333333333333333333333333333333333333333333333333333
            444444444444444444444444444444444444444444444444444
            555555555555555555555555555555555555555555555555555
            666666666666666666666666666666666666666666666666666
            777777777777777777777777777777777777777777777777777
            888888888888888888888888888888888888888888888888888
            999999999999999999999999999999999999999999999999999
            """.trimIndent(),
            collapsedVisibleLines = 8
        )
    }
}

@Composable
fun TextWithIcon(
    modifier: Modifier = Modifier,
    text: String,
    painter: Painter,
    color: Color = MaterialTheme.colorScheme.onBackground
) {

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text,
            color = color
        )
        Spacer(modifier = Modifier.width(Dimens.padding.tiny))
        Image(
            modifier = Modifier.size(16.dp),
            painter = painter,
            contentDescription = null,
            colorFilter = ColorFilter.tint(color)
        )
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
fun TextFieldSearchPreview() {
    AppTheme {
        TextFieldSearch(onValueChange = {}, value = "Hola", onSearch = {})
    }
}
