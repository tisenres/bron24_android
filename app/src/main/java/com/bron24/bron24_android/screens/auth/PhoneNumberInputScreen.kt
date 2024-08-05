package com.bron24.bron24_android.screens.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.bron24.bron24_android.R
import com.bron24.bron24_android.screens.howitworks.gilroyFontFamily

@Composable
fun PhoneNumberInputScreen(
    authViewModel: MockAuthViewModel = MockAuthViewModel(),
    onContinueClick: () -> Unit = {}
) {
    var phoneNumber by remember { mutableStateOf("+998") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            painter = painterResource(id = R.drawable.logo_bron24),
            contentDescription = "Logo Bron24",
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp, start = 50.dp, end = 50.dp, bottom = 52.dp),
            contentScale = ContentScale.FillWidth
        )

        CustomPhoneNumberField(
            value = phoneNumber,
            onValueChange = { newValue ->
                if (newValue.startsWith("+998")) {
                    phoneNumber = newValue
                    authViewModel.updatePhoneNumber(newValue)
                }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                authViewModel.requestOTP()
                onContinueClick()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(id = R.string.continue_text),
                style = TextStyle(
                    fontFamily = gilroyFontFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp,
                    color = Color.White,
                    lineHeight = 16.8.sp,
                    letterSpacing = (-0.028).em
                ),
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomPhoneNumberField(
    value: String,
    onValueChange: (String) -> Unit
) {
    Box(
        contentAlignment = Alignment.CenterStart,
        modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .background(Color(0xFFF6F6F6))
            .padding(horizontal = 16.dp, vertical = 12.dp)
            .fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_person_outline_24),
                contentDescription = "User Icon",
                modifier = Modifier.size(24.dp),
                tint = Color(0xFFB8BDCA),
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column(
                verticalArrangement = Arrangement.spacedBy(3.dp),
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp)
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
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                TextField(
                    value = value,
                    onValueChange = onValueChange,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number
                    ),
                    singleLine = true,
                    textStyle = TextStyle(fontSize = 14.sp),
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedTextColor = Color(0xFF1F2B37)
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
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