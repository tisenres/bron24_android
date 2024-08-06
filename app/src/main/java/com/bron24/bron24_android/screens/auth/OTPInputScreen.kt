package com.bron24.bron24_android.screens.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
            IconButton(
                onClick = onBackClick,
                modifier = Modifier.size(24.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_arrow_back),
                    contentDescription = "Back",
                    modifier = Modifier.size(22.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp)) // Optional: Adjust spacing between icon and text

            Text(
                text = stringResource(id = R.string.otp_title),
                style = TextStyle(
                    fontFamily = gilroyFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = Color.Black,
                    lineHeight = 22.05.sp,
                    letterSpacing = (-0.028).em,
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically)
            )
        }

        Spacer(modifier = Modifier.height(37.dp))

        Text(
            text = stringResource(id = R.string.enter_otp_code) + "\n+998 " + phoneNumber,
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
            },
            modifier = Modifier.fillMaxWidth() // This ensures the OTPTextField takes full width
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
    onOtpChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    BasicTextField(
        value = otp,
        onValueChange = onOtpChange,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        decorationBox = {
            Row(
                horizontalArrangement = Arrangement.spacedBy(15.dp, Alignment.CenterHorizontally),
                modifier = modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
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
                            .background(Color(0xFFF6F6F6))
                            .clip(
                                RoundedCornerShape(
                                    corner = CornerSize(5.dp)
                                )
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = char,
                            style = TextStyle(
                                fontFamily = gilroyFontFamily,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                color = Color(0xFF8F92A4),
                                lineHeight = 17.15.sp,
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
        phoneNumber = "94 018 67 22",
        onOTPVerified = {},
        onBackClick = {}
    )
}