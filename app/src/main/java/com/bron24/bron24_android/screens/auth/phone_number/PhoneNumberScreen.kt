package com.bron24.bron24_android.screens.auth.phone_number

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bron24.bron24_android.R
import com.bron24.bron24_android.domain.entity.auth.enums.PhoneNumberResponseStatusCode
import com.bron24.bron24_android.helper.extension.PhoneNumberVisualTransformation
import com.bron24.bron24_android.components.toast.ToastManager
import com.bron24.bron24_android.components.toast.ToastType
import com.bron24.bron24_android.screens.auth.AuthState
import com.bron24.bron24_android.screens.auth.AuthViewModel
import com.bron24.bron24_android.screens.main.theme.gilroyFontFamily



@Composable
fun PhoneNumberInputScreen(
    authViewModel: AuthViewModel,
    onNavigateToOTPScreen: (String) -> Unit
) {
    val authState by authViewModel.authState.collectAsState()
    val phoneNumber by authViewModel.phoneNumber.collectAsState()
    val isPhoneNumberValid by authViewModel.isPhoneNumberValid.collectAsState()
    val focusRequester = remember { FocusRequester() }
    var isLoading by remember { mutableStateOf(false) }

    LaunchedEffect(authState) {
        when (authState) {
            is AuthState.Loading -> {
                isLoading = true
            }

            is AuthState.OTPRequested -> {
                isLoading = false
                val status = (authState as AuthState.OTPRequested).status
                when (status) {
                    PhoneNumberResponseStatusCode.SUCCESS -> {
                        onNavigateToOTPScreen(phoneNumber.slice(1 until phoneNumber.length))
                    }

                    PhoneNumberResponseStatusCode.MANY_REQUESTS -> {
                        onNavigateToOTPScreen(phoneNumber.slice(1 until phoneNumber.length))
                    }

                    PhoneNumberResponseStatusCode.INCORRECT_PHONE_NUMBER -> {
                        ToastManager.showToast(
                            "Incorrect phone number. Please try again.",
                            ToastType.ERROR
                        )
                    }

                    else -> {
                        // TODO: remove when fix otpreq 404 bug
                        onNavigateToOTPScreen(phoneNumber.slice(1 until phoneNumber.length))

                        ToastManager.showToast(
                            "Failed to request OTP. Please try again later.",
                            ToastType.ERROR
                        )
                    }
                }
            }

            is AuthState.Error -> {
                isLoading = false
                ToastManager.showToast(
                    "Error: " + (authState as AuthState.Error).message,
                    ToastType.ERROR
                )

            }

            else -> {
                isLoading = false
                // Handle other states if necessary
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .height(74.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(horizontal = 20.dp)
        ) {
            Spacer(modifier = Modifier.height(36.dp))
            Logo()
            Spacer(modifier = Modifier.height(36.dp))
            CustomPhoneNumberField(
                value = phoneNumber,
                onValueChange = { newValue ->
                    if (newValue.startsWith("+998")) {
                        authViewModel.updatePhoneNumber(newValue)
                    }
                },
                focusRequester = focusRequester,
                authViewModel = authViewModel
            )
            Spacer(modifier = Modifier.weight(1f))
            BottomSection(
                authViewModel,
                isPhoneNumberValid,
            )
        }

        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White.copy(alpha = 0.8f)), // White background with some transparency
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = Color(0xFF32B768)) // Progress bar in the center
            }
        }
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
    focusRequester: FocusRequester,
    authViewModel: AuthViewModel
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    var textFieldValue by remember { mutableStateOf(TextFieldValue(text = value.removePrefix("+998"))) }

    LaunchedEffect(value) {
        textFieldValue = textFieldValue.copy(
            text = value.removePrefix("+998"),
            selection = TextRange(value.length)  // Ensure the cursor is at the end
        )
    }

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
                modifier = Modifier.size(30.dp),
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
                        fontSize = 16.sp,
                        color = Color(0xFFB8BDCA),
                        lineHeight = 20.sp,
//                        letterSpacing = (-0.028).em
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
                            fontSize = 16.sp,
                            color = Color(0xFFB8BDCA),
                            lineHeight = 20.sp,
//                            letterSpacing = (-0.028).em
                        )
                    )
                    Spacer(modifier = Modifier.width(3.dp))
                    BasicTextField(
                        value = textFieldValue,
                        onValueChange = { newValue ->
                            val digitsOnly = newValue.text.filter { it.isDigit() }
                            if (digitsOnly.length <= 9) {
                                val fullNumber = "+998$digitsOnly"
                                textFieldValue = newValue.copy(
                                    text = digitsOnly,
                                    selection = TextRange(digitsOnly.length)
                                )
                                onValueChange(fullNumber)
                                authViewModel.updatePhoneNumber(fullNumber)
                            }
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.NumberPassword,
                            imeAction = ImeAction.Done
                        ),
                        singleLine = true,
                        textStyle = TextStyle(
                            fontFamily = gilroyFontFamily,
                            fontWeight = FontWeight.Normal,
                            fontSize = 16.sp,
                            color = Color.Black,
                            lineHeight = 20.sp,
                        ),
                        visualTransformation = PhoneNumberVisualTransformation(),
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
    authViewModel: AuthViewModel,
    isPhoneNumberValid: Boolean,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .imePadding()
            .padding(bottom = 16.dp)
    ) {
        TermsAndConditionsText()
        ConfirmButton(
            isEnabled = isPhoneNumberValid,
            onClick = {
                authViewModel.requestOTP()
            },
        )
    }
}

@Composable
fun ConfirmButton(
    isEnabled: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val scale by animateFloatAsState(if (isPressed) 0.95f else 1f, label = "")

    Button(
        onClick = {
            onClick()
        },
        colors = ButtonDefaults.buttonColors(
            contentColor = Color.White,
            containerColor = if (isEnabled) Color(0xFF32B768) else Color(0xFFE4E4E4)
        ),
        enabled = isEnabled,
        shape = RoundedCornerShape(10.dp),
        interactionSource = interactionSource,
        modifier = modifier
            .fillMaxWidth()
            .height(52.dp)
            .scale(scale)
    ) {
        Text(
            text = stringResource(id = R.string.continue_text),
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            fontFamily = gilroyFontFamily,
            color = if (isEnabled) Color.White else Color.Gray,
            lineHeight = 32.sp,
        )
    }
}


@Composable
fun TermsAndConditionsText() {
    val uriHandler = LocalUriHandler.current
    val annotatedText = buildAnnotatedString {
        append(stringResource(id = R.string.terms_and_conditions) + " ")

        pushStringAnnotation(tag = "URL", annotation = "https://bron24.com/terms")
        withStyle(
            style = SpanStyle(
                color = Color(0xFF32B768),
                textDecoration = TextDecoration.Underline,
                fontFamily = gilroyFontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
//                letterSpacing = (-0.028).em
            )
        ) {
            append(stringResource(id = R.string.terms_and_conditions_text))
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
        ),
        onClick = { offset ->
            annotatedText.getStringAnnotations(tag = "URL", start = offset, end = offset)
                .firstOrNull()?.let { annotation ->
                    uriHandler.openUri(annotation.item)
                }
        },
        modifier = Modifier.fillMaxWidth()
    )
}

@Preview(showBackground = true)
@Composable
private fun PhoneNumberInputScreenPreview() {
}