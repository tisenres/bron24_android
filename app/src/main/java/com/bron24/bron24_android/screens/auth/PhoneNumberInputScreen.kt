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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.bron24.bron24_android.R
import com.bron24.bron24_android.helper.util.PhoneNumberVisualTransformation
import com.bron24.bron24_android.screens.main.MainViewModel
import com.bron24.bron24_android.screens.main.Screen
import com.bron24.bron24_android.screens.main.theme.gilroyFontFamily

@Composable
fun PhoneNumberInputScreen(
    authViewModel: AuthViewModel = hiltViewModel(),
    navController: NavController,
    mainViewModel: MainViewModel = hiltViewModel()
) {
    val phoneNumber by mainViewModel.phoneNumber.collectAsState()
    val isPhoneNumberValid by authViewModel.isPhoneNumberValid.collectAsState()
    val focusRequester = remember { FocusRequester() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 20.dp)
    ) {
        Spacer(modifier = Modifier.height(24.dp))
        Logo()
        Spacer(modifier = Modifier.height(24.dp))
        CustomPhoneNumberField(
            value = phoneNumber,
            onValueChange = { newValue ->
                if (newValue.startsWith("+998")) {
                    mainViewModel.updatePhoneNumber(newValue)
                }
            },
            focusRequester = focusRequester,
            authViewModel = authViewModel
        )
        Spacer(modifier = Modifier.weight(1f))
        BottomSection(authViewModel, isPhoneNumberValid, navController, phoneNumber)
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
                        onValueChange = { newValue ->
                            val digitsOnly = newValue.filter { it.isDigit() }
                            if (digitsOnly.length <= 9) {
                                val fullNumber = "+998$digitsOnly"
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
                            fontSize = 14.sp,
                            color = Color.Black,
                            lineHeight = 16.8.sp,
                            letterSpacing = (-0.028).em
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
    navController: NavController,
    phoneNumber: String
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
                navController.navigate(
                    Screen.OTPInput.route.replace(
                        "{phoneNumber}",
                        phoneNumber
                    )
                )
            }
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
                letterSpacing = (-0.028).em
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
            letterSpacing = (-0.028).em
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
    PhoneNumberInputScreen(navController = rememberNavController())
}