package com.buntupana.tmdb.core.presentation.composables

import androidx.annotation.DrawableRes
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.TileMode
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
import com.buntupana.tmdb.core.R
import com.buntupana.tmdb.core.presentation.theme.Dimens
import com.buntupana.tmdb.core.presentation.theme.PrimaryColor
import com.buntupana.tmdb.core.presentation.theme.SecondaryColor

@Composable
fun OutlinedText(
    modifier: Modifier = Modifier,
    text: String = "",
    color: Color = Color.Gray,
    outlineColor: Color = color,
    cornerRound: Dp = 2.dp,
    padding: Dp = 0.dp,
    horizontalPadding: Dp = 4.dp,
    verticalPadding: Dp = 0.dp
) {

    if (text.isNotEmpty()) {
        Text(
            modifier = modifier
                .alpha(0.6f)
                .border(BorderStroke(1.dp, outlineColor), RoundedCornerShape(cornerRound))
                .padding(horizontal = horizontalPadding, vertical = verticalPadding)
                .padding(padding),
            text = text,
            color = color
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
    cursorColor: Color = PrimaryColor
) {
    Box(
        modifier = modifier.padding(
            horizontal = Dimens.padding.medium,
            vertical = Dimens.padding.small
        ),
        contentAlignment = Alignment.CenterStart
    ) {

        val focus = FocusRequester()
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
                .fillMaxSize()
                .animateContentSize(),
            text = text,
            fontSize = fontSize,
            fontWeight = fontWeight,
            maxLines = if (textExpanded) 999999999 else collapsedVisibleLines,
            onTextLayout = {
                if (!textExpanded) {
                    textExpanded = it.lineCount < collapsedVisibleLines && textExpanded == false
                }
            }
        )
        if (textExpanded.not()) {
            Row(
                modifier = Modifier
                    .height(IntrinsicSize.Min)
                    .align(Alignment.BottomEnd),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .background(
                            Brush.horizontalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    MaterialTheme.colorScheme.background
                                ),
                                tileMode = TileMode.Mirror
                            )
                        )
                )
                Row(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.background)
                        .padding(Dimens.padding.small)
                        .clickable {
                            textExpanded = true
                        },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(id = R.string.text_read_more),
                        color = SecondaryColor,
                        fontWeight = FontWeight.Bold
                    )
                    Image(
                        modifier = Modifier.size(16.dp),
                        painter = painterResource(id = R.drawable.ic_arrow_right),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(SecondaryColor)
                    )
                }
            }
        }
    }
}

@Composable
fun TextWithIcon(
    modifier: Modifier = Modifier,
    text: String,
    clickable: (() -> Unit) = {},
    @DrawableRes iconRes: Int
) {

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = text)
        Spacer(modifier = Modifier.width(Dimens.padding.tiny))
        Image(
            modifier = Modifier.size(16.dp),
            painter = painterResource(iconRes),
            contentDescription = null,
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground)
        )
    }

}

@Preview(showBackground = true)
@Composable
fun ExpandableTextPreview() {
    ExpandableText(
        text = """
            1111111111111111111111111111111111
            2222222222222222222222222222222222
            3333333333333333333333333333333333
            4444444444444444444444444444444444
            5555555555555555555555555555555555
            6666666666666666666666666666666666
            7777777777777777777777777777777777
            """.trimIndent(),
        collapsedVisibleLines = 8
    )
}

@Preview(showBackground = true)
@Composable
fun TextFieldSearchPreview() {
    TextFieldSearch(onValueChange = {}, value = "Hola", onSearch = {})
}
