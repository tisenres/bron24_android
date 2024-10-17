package com.bron24.bron24_android.screens.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bron24.bron24_android.R
import com.bron24.bron24_android.domain.entity.auth.enums.OTPStatusCode
import com.bron24.bron24_android.helper.extension.formatWithSpansPhoneNumber
import com.bron24.bron24_android.helper.util.presentation.components.toast.ToastManager
import com.bron24.bron24_android.helper.util.presentation.components.toast.ToastType
import com.bron24.bron24_android.screens.main.theme.gilroyFontFamily
import kotlinx.coroutines.delay

@Composable
fun OTPInputScreen(
    authViewModel: AuthViewModel,
    phoneNumber: String,
    onUserLogIn: () -> Unit,
    onUserSignUp: () -> Unit,
    onBackClick: () -> Unit
) {
    var otp by remember { mutableStateOf("") }
    val authState by authViewModel.authState.collectAsState()
    var resendCounter by remember { mutableIntStateOf(90) }
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    var isVerifying by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
        keyboardController?.show()
    }

    // Coroutine scope for handling the counter
    LaunchedEffect(resendCounter) {
        if (resendCounter > 0) {
            while (resendCounter > 0) {
                delay(1000)  // Wait for 1 second
                resendCounter--
            }
        }
    }

    // Ensure keyboard is shown and focus is requested as soon as the composable is launched
//    LaunchedEffect(Unit) {
//        focusRequester.requestFocus()
//        keyboardController?.show()
//    }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
        keyboardController?.show()
    }


    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(vertical = 20.dp, horizontal = 12.dp)
                .padding(paddingValues)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp),
            ) {
                EnhancedBackButton(
                    onClick = onBackClick,
                    modifier = Modifier.align(Alignment.CenterStart)
                )

                Text(
                    text = stringResource(id = R.string.otp_title),
                    style = TextStyle(
                        fontFamily = gilroyFontFamily,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 22.sp,
                        color = Color.Black,
                        lineHeight = 24.sp,
                        textAlign = TextAlign.Center
                    ),
                    modifier = Modifier.align(Alignment.Center),
                )
            }

            Spacer(modifier = Modifier.height(37.dp))

            Text(
                text = stringResource(id = R.string.enter_otp_code) +
                        "\n" +
                        phoneNumber.formatWithSpansPhoneNumber(),
                style = TextStyle(
                    fontFamily = gilroyFontFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp,
                    color = Color.Black,
                    lineHeight = 20.sp,
                ),
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .align(Alignment.Start)
            )

            Spacer(modifier = Modifier.height(40.dp))

            OTPTextField(
                otp = otp,
                onOtpChange = { newOtp ->
                    if (newOtp.length <= 4) {
                        otp = newOtp
                        val otpValue = newOtp.toIntOrNull()
                        if (otpValue != null) {
                            authViewModel.updateOTP(otpValue)
                            if (newOtp.length == 4) {
                                isVerifying = true
                                authViewModel.verifyOTP()
                            }
                        } else {
                            authViewModel.updateOTP(0)
                        }
                    }
                },
                focusRequester = focusRequester,
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester)
            )

            Spacer(modifier = Modifier.height(36.dp)) // Push the resend section to the bottom

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                contentAlignment = Alignment.Center

            ) {
                if (resendCounter > 0) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_refresh),
                            contentDescription = stringResource(id = R.string.refresh)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = stringResource(id = R.string.resend_code) +
                                    " " + resendCounter / 60 + ":" + String.format(
                                "%02d",
                                resendCounter % 60
                            ),
                            style = TextStyle(
                                fontFamily = gilroyFontFamily,
                                fontWeight = FontWeight.Normal,
                                fontSize = 16.sp,
                                color = Color(0xFFB5DAC4),
                                lineHeight = 20.sp,
                            ),
                        )
                    }
                } else {
                    UnderlinedResendButton(
                        onClick = {
                            authViewModel.requestOTP()
                            resendCounter = 90
                        },
                    )

                    if (isVerifying) {
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

            LaunchedEffect(authState) {
                when (authState) {
                    is AuthState.Loading -> {
                        isVerifying = true
                    }
                    is AuthState.OTPVerified -> {
                        isVerifying = false
                        if ((authState as AuthState.OTPVerified).status == OTPStatusCode.CORRECT_OTP) {
                            // Clear focus and hide keyboard
                            focusManager.clearFocus()
                            keyboardController?.hide()

                            ToastManager.showToast("OTP verified successfully", ToastType.SUCCESS)
                            if ((authState as AuthState.OTPVerified).userExists) {
                                onUserLogIn()
                            } else {
                                onUserSignUp()
                            }
                        } else {
                            ToastManager.showToast(
                                "Incorrect OTP. Please try again.",
                                ToastType.ERROR
                            )
                            otp = ""
                            focusRequester.requestFocus()
                            keyboardController?.show()
                        }
                    }
                    is AuthState.Error -> {
                        isVerifying = false
                        ToastManager.showToast(
                            "Error: " + (authState as AuthState.Error).message,
                            ToastType.ERROR
                        )
                    }
                    else -> {
                        isVerifying = false
                    }
                }
            }
        }
    }
}

@Composable
fun UnderlinedResendButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    TextButton(
        onClick = onClick,
        modifier = modifier,
        interactionSource = remember { MutableInteractionSource() },
    ) {
        BasicText(
            text = stringResource(id = R.string.resend_code_button),
            style = TextStyle(
                fontFamily = gilroyFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = Color(0xFF32B768),
                lineHeight = 20.sp,
                textDecoration = TextDecoration.Underline
            )
        )
    }
}

@Composable
fun EnhancedBackButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(44.dp)
            .clip(CircleShape)
            .background(Color.Transparent)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = ripple(),
                onClick = onClick
            )
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_arrow_back),
            contentDescription = "Back",
            modifier = Modifier
                .size(32.dp)
                .align(Alignment.Center)
        )
    }
}

@Composable
fun OTPTextField(
    otp: String,
    onOtpChange: (String) -> Unit,
    focusRequester: FocusRequester,
    modifier: Modifier = Modifier
) {
    BasicTextField(
        value = otp,
        onValueChange = onOtpChange,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.NumberPassword,
            imeAction = ImeAction.Done
        ),
        decorationBox = {
            Row(
                horizontalArrangement = Arrangement.spacedBy(15.dp, Alignment.CenterHorizontally),
                modifier = modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                repeat(4) { index ->
                    val char = when {
                        index >= otp.length -> ""
                        else -> otp[index].toString()
                    }
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(5.dp))
                            .height(53.dp)
                            .aspectRatio(1f)
                            .background(Color(0xFFF6F6F6)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = char,
                            style = TextStyle(
                                fontFamily = gilroyFontFamily,
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 16.sp,
                                color = Color(0xFF8F92A4),
                                lineHeight = 20.sp,
                            ),
                        )
                    }
                }
            }
        },
        modifier = Modifier
            .focusRequester(focusRequester)
            .fillMaxWidth()
    )
}

@Preview(showBackground = true)
@Composable
fun BottomNavigationBarPreview() {
    EnhancedBackButton(onClick = { /*TODO*/ })
}