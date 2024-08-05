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
import androidx.hilt.navigation.compose.hiltViewModel
import com.bron24.bron24_android.R
import com.bron24.bron24_android.screens.howitworks.gilroyFontFamily

@Composable
fun PhoneNumberInputScreen(
    authViewModel: MockAuthViewModel = hiltViewModel(),
    onContinueClick: () -> Unit = {}
) {
    var phoneNumber by remember { mutableStateOf("+998") }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            painter = painterResource(id = R.drawable.logo_bron24),
            contentDescription = "Logo Bron24",
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp, start = 80.dp, end = 80.dp, bottom = 52.dp),
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
            Text("Continue")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomPhoneNumberField(
    value: String,
    onValueChange: (String) -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.Start),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .background(Color(0xF6F6F6))
            .padding(horizontal = 20.dp)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.baseline_person_outline_24),
            contentDescription = "User Icon",
            modifier = Modifier.requiredSize(18.dp),
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(3.dp),
            modifier = Modifier
                .fillMaxWidth()
                .requiredHeight(58.dp)
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
                modifier = Modifier.padding(
                    top = 10.dp,
                    start = 10.dp
                )
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
                    unfocusedIndicatorColor = Color.Transparent
                ),
                modifier = Modifier.fillMaxWidth()
            )
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