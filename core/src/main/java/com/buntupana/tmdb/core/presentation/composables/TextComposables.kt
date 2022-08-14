package com.buntupana.tmdb.core.presentation.composables

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.*
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalFontFamilyResolver
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.Paragraph
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import com.buntupana.tmdb.core.R
import com.buntupana.tmdb.core.presentation.theme.Dimens
import com.buntupana.tmdb.core.presentation.theme.Primary
import com.buntupana.tmdb.core.presentation.theme.Secondary
import kotlin.math.absoluteValue
import kotlin.math.ceil

@Composable
fun AutoSizeText(
    text: String,
    modifier: Modifier = Modifier,
    acceptableError: Dp = 5.dp,
    maxFontSize: TextUnit = TextUnit.Unspecified,
    color: Color = Color.Unspecified,
    fontStyle: FontStyle? = null,
    fontWeight: FontWeight? = null,
    fontFamily: FontFamily? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    lineHeight: TextUnit = TextUnit.Unspecified,
    maxLines: Int = Int.MAX_VALUE,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    style: TextStyle = LocalTextStyle.current
) {
    BoxWithConstraints(modifier = modifier) {
        var shrunkFontSize = if (maxFontSize.isSpecified) maxFontSize else 100.sp

        val calculateIntrinsics = @Composable {
            val mergedStyle = style.merge(
                TextStyle(
                    color = color,
                    fontSize = shrunkFontSize,
                    fontWeight = fontWeight,
                    textAlign = textAlign,
                    lineHeight = lineHeight,
                    fontFamily = fontFamily,
                    textDecoration = textDecoration,
                    fontStyle = fontStyle,
                    letterSpacing = letterSpacing
                )
            )
            Paragraph(
                text = text,
                style = mergedStyle,
                spanStyles = listOf(),
                placeholders = listOf(),
                maxLines = maxLines,
                ellipsis = false,
                constraints = Constraints(maxWidth = ceil(LocalDensity.current.run { maxWidth.toPx() }).toInt()),
                density = LocalDensity.current,
                fontFamilyResolver = LocalFontFamilyResolver.current
            )
        }

        var intrinsics = calculateIntrinsics()

        val targetWidth = maxWidth - acceptableError / 2f

        with(LocalDensity.current) {
            if (maxFontSize.isUnspecified || targetWidth < intrinsics.minIntrinsicWidth.toDp() || intrinsics.didExceedMaxLines) {
                while ((targetWidth - intrinsics.minIntrinsicWidth.toDp()).toPx().absoluteValue.toDp() > acceptableError / 2f) {
                    shrunkFontSize *= targetWidth.toPx() / intrinsics.minIntrinsicWidth
                    intrinsics = calculateIntrinsics()
                }
                while (intrinsics.didExceedMaxLines || maxHeight < intrinsics.height.toDp()) {
                    shrunkFontSize *= 0.9f
                    intrinsics = calculateIntrinsics()
                }
            }
        }

        if (maxFontSize.isSpecified && shrunkFontSize > maxFontSize)
            shrunkFontSize = maxFontSize

        Text(
            text = text,
            color = color,
            fontSize = shrunkFontSize,
            fontStyle = fontStyle,
            fontWeight = fontWeight,
            fontFamily = fontFamily,
            letterSpacing = letterSpacing,
            textDecoration = textDecoration,
            textAlign = textAlign,
            lineHeight = lineHeight,
            onTextLayout = onTextLayout,
            maxLines = maxLines,
            style = style
        )
    }
}

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
    time: Int
) {

    val hours = time / 60
    val minutes = time - (hours * 60)

    val timeText = if (hours == 0) {
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
    cursorColor: Color = Primary
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
    visibleLines: Int = 12
) {

    var textExpanded by remember {
        mutableStateOf(false)
    }

    Box {

        Text(
            modifier = Modifier.fillMaxSize(),
            text = text,
            fontSize = fontSize,
            fontWeight = fontWeight,
            maxLines = if (textExpanded) 999999999 else visibleLines
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
                        color = Secondary,
                        fontWeight = FontWeight.Bold
                    )
                    Image(
                        modifier = Modifier.size(16.dp),
                        painter = painterResource(id = R.drawable.ic_arrow_right),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(Secondary)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GradientPreview() {
    Box(
        modifier = Modifier.size(400.dp),
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .background(
                    Brush.linearGradient(
                        (0.0f to Color.Transparent),
                        (0.4f to Color.Transparent),
                        (0.9f to Color.Green),
                        tileMode = TileMode.Decal,

                        )
                )
        ) {
            Text(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(16.dp),
                text = "TEST",
                fontSize = 80.sp,
                color = Color.White
            )
        }
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
        visibleLines = 8
    )
}

@Preview(showBackground = true)
@Composable
fun TextFieldSearchPreview() {
    TextFieldSearch(onValueChange = {}, value = "Hola", onSearch = {})
}
