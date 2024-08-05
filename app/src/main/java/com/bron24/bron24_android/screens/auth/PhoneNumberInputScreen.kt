package com.bron24.bron24_android.screens.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.bron24.bron24_android.R
import com.bron24.bron24_android.screens.main.theme.gilroyFontFamily

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun PhoneNumberInputScreen(
    authViewModel: MockAuthViewModel = MockAuthViewModel(),
    onContinueClick: () -> Unit = {}
) {
    var phoneNumber by remember { mutableStateOf("+998") }
    val focusRequester = remember { FocusRequester() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 20.dp)
            .imePadding()
    ) {
        Spacer(modifier = Modifier.height(24.dp))
        Logo()
        Spacer(modifier = Modifier.height(24.dp))
        CustomPhoneNumberField(
            value = phoneNumber,
            onValueChange = { newValue ->
                if (newValue.startsWith("+998")) {
                    phoneNumber = newValue
                    authViewModel.updatePhoneNumber(newValue)
                }
            },
            focusRequester = focusRequester
        )
        Spacer(modifier = Modifier.weight(1f))
        BottomSection(authViewModel, onContinueClick)
    }
}

@Composable
fun Logo() {
    Image(
        painter = painterResource(id = R.drawable.logo_bron24),
        contentDescription = "Logo Bron24",
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 60.dp),
        contentScale = ContentScale.FillWidth
    )
}

@Composable
fun CustomPhoneNumberField(
    value: String,
    onValueChange: (String) -> Unit,
    focusRequester: FocusRequester
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
        keyboardController?.show()
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(Color(0xFFF6F6F6))
            .padding(10.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_person_outline_24),
                contentDescription = "User Icon",
                modifier = Modifier.size(24.dp),
                tint = Color(0xFFB8BDCA)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.phone_number),
                    style = TextStyle(
                        fontFamily = gilroyFontFamily,
                        fontWeight = FontWeight.Normal,
                        fontSize = 14.sp,
                        color = Color(0xFFB8BDCA),
                        lineHeight = 16.8.sp,
                        letterSpacing = (-0.028).em
                    ),
                    modifier = Modifier.align(Alignment.Start)
                )
                Spacer(modifier = Modifier.height(3.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "+998",
                        style = TextStyle(
                            fontFamily = gilroyFontFamily,
                            fontWeight = FontWeight.Normal,
                            fontSize = 14.sp,
                            color = Color(0xFFB8BDCA),
                            lineHeight = 16.8.sp,
                            letterSpacing = (-0.028).em
                        )
                    )
                    Spacer(modifier = Modifier.width(3.dp))
                    BasicTextField(
                        value = value.removePrefix("+998"),
                        onValueChange = { newValue -> onValueChange("+998$newValue") },
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Number
                        ),
                        singleLine = true,
                        textStyle = TextStyle(
                            fontFamily = gilroyFontFamily,
                            fontWeight = FontWeight.Normal,
                            fontSize = 14.sp,
                            color = Color.Black,
                            lineHeight = 16.8.sp,
                            letterSpacing = (-0.028).em
                        ),
                        decorationBox = { innerTextField ->
                            Box(modifier = Modifier.fillMaxWidth()) {
                                innerTextField()
                            }
                        },
                        modifier = Modifier
                            .focusRequester(focusRequester)
                            .fillMaxWidth()
                    )
                }
            }
        }
    }
}

@Composable
fun BottomSection(
    authViewModel: MockAuthViewModel,
    onContinueClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(bottom = 16.dp)
    ) {
        TermsAndConditionsText()
        Spacer(modifier = Modifier.height(12.dp))
        ConfirmButton(
            isEnabled = true,
            onClick = {
                authViewModel.requestOTP()
                onContinueClick()
            }
        )
    }
}

@Composable
fun TermsAndConditionsText() {
    val uriHandler = LocalUriHandler.current
    val annotatedText = buildAnnotatedString {
        append("By clicking on the Continue button you accept our ")

        pushStringAnnotation(tag = "URL", annotation = "https://example.com/terms")
        withStyle(
            style = SpanStyle(
                color = Color(0xFF32B768),
                textDecoration = TextDecoration.Underline,
                fontFamily = gilroyFontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                letterSpacing = (-0.028).em
            )
        ) {
            append("Terms & Conditions")
        }
        pop()
    }

    ClickableText(
        text = annotatedText,
        style = TextStyle(
            fontFamily = gilroyFontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            color = Color.Black,
            lineHeight = 16.8.sp,
            letterSpacing = (-0.028).em
        ),
        onClick = { offset ->
            annotatedText.getStringAnnotations(tag = "URL", start = offset, end = offset)
                .firstOrNull()?.let { annotation ->
                    uriHandler.openUri(annotation.item)
                }
        },
        modifier = Modifier.padding(vertical = 16.dp)
    )
}

@Composable
fun ConfirmButton(
    isEnabled: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            contentColor = Color.White,
            containerColor = if (isEnabled) Color(0xFF32B768) else Color(0xFFE4E4E4)
        ),
        enabled = isEnabled,
        shape = RoundedCornerShape(10.dp),
        modifier = modifier
            .fillMaxWidth()
            .height(47.dp)
    ) {
        Text(
            text = stringResource(id = R.string.continue_text),
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            fontFamily = gilroyFontFamily,
            color = if (isEnabled) Color.White else Color.Gray,
            lineHeight = 32.sp,
            letterSpacing = (-0.028).em
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PhoneNumberInputScreenPreview() {
    PhoneNumberInputScreen(
        authViewModel = MockAuthViewModel(),
        onContinueClick = {}
    )
}