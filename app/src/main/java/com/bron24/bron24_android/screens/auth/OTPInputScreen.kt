package com.bron24.bron24_android.screens.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import com.bron24.bron24_android.screens.main.theme.gilroyFontFamily
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun OTPInputScreen(
    authViewModel: MockAuthViewModel = hiltViewModel(),
    phoneNumber: String,
    onOTPVerified: () -> Unit,
    onBackClick: () -> Unit
) {
    var otp by remember { mutableStateOf("") }
    val otpVerifyStatus by authViewModel.otpVerifyStatus.collectAsState()
    val scope = rememberCoroutineScope()
    var resendCounter by remember { mutableStateOf(90) }

    LaunchedEffect(Unit) {
        while (resendCounter > 0) {
            delay(1000)
            resendCounter--
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(24.dp),
            verticalAlignment = Alignment.CenterVertically // Center align children vertically
        ) {
            IconButton(onClick = onBackClick) {
                Image(
                    painter = painterResource(id = R.drawable.ic_arrow_back),
                    contentDescription = "Back",
                )
            }

            Spacer(modifier = Modifier.weight(1f)) // Spacer to push the text to the center

            Text(
                text = stringResource(id = R.string.otp_title),
                style = TextStyle(
                    fontFamily = gilroyFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = Color.Black,
                    lineHeight = 22.05.sp,
                    letterSpacing = (-0.028).em
                ),
                modifier = Modifier.align(Alignment.CenterVertically)
            )

            Spacer(modifier = Modifier.weight(1f))
        }

        Spacer(modifier = Modifier.height(37.dp))

        Text(
            text = stringResource(id = R.string.enter_otp_code),
            style = TextStyle(
                fontFamily = gilroyFontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                color = Color.Black,
                lineHeight = 16.8.sp,
                letterSpacing = (-0.028).em
            ),
            modifier = Modifier.align(Alignment.Start)
        )

        Text(
            text = phoneNumber,
            style = TextStyle(fontSize = 16.sp),
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(40.dp))

        OTPTextField(
            otp = otp,
            onOtpChange = { newOtp ->
                if (newOtp.length <= 4) {
                    otp = newOtp
                    authViewModel.updateOTP(newOtp)
                    if (newOtp.length == 4) {
                        scope.launch {
                            authViewModel.verifyOTP()
                            if (otpVerifyStatus) {
                                onOTPVerified()
                            }
                        }
                    }
                }
            }
        )

        Spacer(modifier = Modifier.height(56.dp))

        if (resendCounter > 0) {
            Text(
                text = "Resend code again after ${resendCounter / 60}:${
                    String.format(
                        "%02d",
                        resendCounter % 60
                    )
                }",
                color = Color.Gray,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        } else {
            TextButton(
                onClick = {
                    // TODO Implement resend code logic here
                    resendCounter = 90
                },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text("Resend code", color = Color.Green)
            }
        }
    }
}

@Composable
fun OTPTextField(
    otp: String,
    onOtpChange: (String) -> Unit
) {
    BasicTextField(
        value = otp,
        onValueChange = onOtpChange,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        decorationBox = {
            Row(
                horizontalArrangement = Arrangement.spacedBy(15.dp),
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                repeat(4) { index ->
                    val char = when {
                        index >= otp.length -> ""
                        else -> otp[index].toString()
                    }
                    Box(
                        modifier = Modifier
                            .height(53.dp)
                            .aspectRatio(1f)
                            .background(Color.LightGray.copy(alpha = 0.3f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = char,
                            style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold)
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
        phoneNumber = "94 018 67 22",
        onOTPVerified = {},
        onBackClick = {}
    )
}