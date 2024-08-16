package com.bron24.bron24_android.screens.auth

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bron24.bron24_android.R
import com.bron24.bron24_android.domain.entity.auth.enums.OTPStatusCode
import com.bron24.bron24_android.helper.extension.formatWithSpansPhoneNumber
import com.bron24.bron24_android.screens.main.components.ToastManager
import com.bron24.bron24_android.screens.main.components.ToastType
import com.bron24.bron24_android.screens.main.theme.gilroyFontFamily
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun OTPInputScreen(
    authViewModel: AuthViewModel,
    phoneNumber: String,
    onOTPVerified: () -> Unit,
    onBackClick: () -> Unit
) {
    ToastManager { showToast ->
        var otp by remember { mutableStateOf("") }
        val authState by authViewModel.authState.collectAsState()
        val scope = rememberCoroutineScope()
        var resendCounter by remember { mutableIntStateOf(90) } // 1 minute 30 seconds
        val focusRequester = remember { FocusRequester() }
        val keyboardController = LocalSoftwareKeyboardController.current

        LaunchedEffect(Unit) {
            focusRequester.requestFocus()
            keyboardController?.show()
            while (resendCounter > 0) {
                delay(1000)
                resendCounter--
            }
        }

        Scaffold { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(20.dp)
                    .padding(paddingValues)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(26.dp),
                ) {
                    IconButton(
                        onClick = onBackClick,
                        modifier = Modifier
                            .size(26.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_arrow_back),
                            contentDescription = "Back",
                            modifier = Modifier
                                .size(26.dp)
                        )
                    }

                    Text(
                        text = stringResource(id = R.string.otp_title),
                        style = TextStyle(
                            fontFamily = gilroyFontFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 22.sp,
                            color = Color.Black,
                            lineHeight = 24.sp,
                            letterSpacing = (-0.028).em,
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
                        letterSpacing = (-0.028).em
                    ),
                    modifier = Modifier.align(Alignment.Start)
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
                                    scope.launch {
                                        authViewModel.verifyOTP()
                                    }
                                }
                            } else {
                                // Handle the case where otp is empty or invalid
                                authViewModel.updateOTP(0)
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(focusRequester)
                )

                Spacer(modifier = Modifier.height(56.dp))

                if (resendCounter > 0) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(17.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_refresh),
                            contentDescription = stringResource(id = R.string.refresh)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = stringResource(
                                id = R.string.resend_code,
                                resendCounter / 60,
                                String.format("%02d", resendCounter % 60)
                            ),
                            style = TextStyle(
                                fontFamily = gilroyFontFamily,
                                fontWeight = FontWeight.Normal,
                                fontSize = 16.sp,
                                color = Color.Red,
                                lineHeight = 20.sp,
                                letterSpacing = (-0.028).em
                            ),
                        )
                    }
                } else {
                    TextButton(
                        onClick = {
                            authViewModel.verifyOTP()
                            resendCounter = 90
                        },
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    ) {
                        Text(
                            text = stringResource(id = R.string.resend_code_button),
                            style = TextStyle(
                                fontFamily = gilroyFontFamily,
                                fontWeight = FontWeight.Normal,
                                fontSize = 16.sp,
                                color = Color(0xFF32B768),
                                lineHeight = 20.sp,
                                letterSpacing = (-0.028).em
                            ),
                        )
                    }
                }
            }

            LaunchedEffect(authState) {
                when (authState) {
                    is AuthState.Loading -> {
                        showToast("Verifying OTP...", ToastType.INFO)
                    }

                    is AuthState.OTPVerified -> {
                        if ((authState as AuthState.OTPVerified).status == OTPStatusCode.CORRECT_OTP) {
                            showToast("OTP verified successfully", ToastType.INFO)
                            onOTPVerified()
                        } else {
                            showToast("Incorrect OTP. Please try again.", ToastType.ERROR)
                        }
                    }

                    is AuthState.Error -> {
                        showToast(
                            "Error: " + (authState as AuthState.Error).message,
                            ToastType.ERROR
                        )
                    }

                    else -> {
                        // Handle other states if necessary
                    }
                }
            }
        }
    }
}

@Composable
fun OTPTextField(
    otp: String,
    onOtpChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current

    BasicTextField(
        value = otp,
        onValueChange = onOtpChange,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        decorationBox = { innerTextField ->
            Row(
                horizontalArrangement = Arrangement.spacedBy(15.dp, Alignment.CenterHorizontally),
                modifier = modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester)
                    .onGloballyPositioned {
                        focusRequester.requestFocus()
                        keyboardController?.show()
                    },
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
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                                color = Color(0xFF8F92A4),
                                lineHeight = 20.sp,
                                letterSpacing = (-0.028).em
                            ),
                        )
                    }
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun OTPInputScreenPreview() {
    OTPInputScreen(
        authViewModel = hiltViewModel(),
        phoneNumber = "+998 94 018 67 22",
        onOTPVerified = {},
        onBackClick = {}
    )
}