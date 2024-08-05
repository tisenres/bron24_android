package com.bron24.bron24_android.screens.auth

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun OTPInputScreen(
    authViewModel: AuthViewModel = hiltViewModel(),
    onOTPVerified: () -> Unit,
) {
    var otp by remember { mutableStateOf("") }
    val otpVerifyStatus by authViewModel.otpVerifyStatus.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        TextField(
            value = otp,
            onValueChange = {
                otp = it
                authViewModel.updateOTP(it)
            },
            label = { Text("Enter OTP") },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                authViewModel.verifyOTP()
                if (otpVerifyStatus) {
                    onOTPVerified()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Verify")
        }
    }
}