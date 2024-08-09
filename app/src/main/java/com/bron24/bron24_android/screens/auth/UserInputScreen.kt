package com.bron24.bron24_android.screens.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.bron24.bron24_android.R
import com.bron24.bron24_android.helper.extension.formatWithSpansPhoneNumber
import com.bron24.bron24_android.screens.main.Screen
import com.bron24.bron24_android.screens.main.theme.gilroyFontFamily

@Composable
fun UserDataInputScreen(
    authViewModel: AuthViewModel,
    navController: NavController,
    onBackClick: () -> Unit
) {
    val authState by authViewModel.authState.collectAsState()
    val phoneNumber by authViewModel.phoneNumber.collectAsState()
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(20.dp)
    ) {
        TopBar(onBackClick = onBackClick)

        Spacer(modifier = Modifier.height(37.dp))

        Text(
            text = stringResource(id = R.string.enter_personal_data) +
                    "\n" +
                    phoneNumber.formatWithSpansPhoneNumber(),
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

        Spacer(modifier = Modifier.height(11.dp))

        UserDataField(
            fieldName = firstName,
            placeholder = "First Name",
            icon = R.drawable.ic_person,
            onValueChange = { newValue -> firstName = newValue },
        )

        Spacer(modifier = Modifier.height(20.dp))

        UserDataField(
            fieldName = lastName,
            placeholder = "Last Name",
            icon = R.drawable.ic_person,
            onValueChange = { newValue -> lastName = newValue },
        )

        Spacer(modifier = Modifier.height(20.dp))

        ConfirmButton(
            isEnabled = firstName.isNotEmpty() && lastName.isNotEmpty(),
            onClick = {
                authViewModel.authenticateUser(
                    firstName = firstName,
                    lastName = lastName
                )
            }
        )
    }

    when (authState) {
        is AuthState.Loading -> {
            // Show loading indicator
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
        is AuthState.Authenticated -> {
            // Navigate to the next screen when authentication is successful
            navController.navigate(Screen.LocationPermission.route)
        }
        is AuthState.Error -> {
            // Show error message if there was an error
            Box(modifier = Modifier.fillMaxSize()) {
                Text(
                    text = (authState as AuthState.Error).message,
                    color = Color.Red,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
        else -> {
            // Handle other states if necessary
        }
    }
}

@Composable
fun TopBar(onBackClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(24.dp)
    ) {
        IconButton(
            onClick = onBackClick,
            modifier = Modifier.size(16.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_arrow_back),
                contentDescription = "Back",
                modifier = Modifier.size(16.dp)
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = stringResource(id = R.string.register),
            style = TextStyle(
                fontFamily = gilroyFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = Color.Black,
                lineHeight = 22.05.sp,
                letterSpacing = (-0.028).em,
                textAlign = TextAlign.Center
            ),
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
fun UserDataField(
    fieldName: String,
    placeholder: String,
    icon: Int,
    onValueChange: (String) -> Unit,
) {
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
                painter = painterResource(id = icon),
                contentDescription = "User Icon",
                modifier = Modifier.size(24.dp),
                tint = Color(0xFFB8BDCA)
            )

            Spacer(modifier = Modifier.width(10.dp))

            BasicTextField(
                value = fieldName,
                onValueChange = onValueChange,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
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
                decorationBox = { innerTextField ->
                    if (fieldName.isEmpty()) {
                        Text(
                            text = placeholder,
                            style = TextStyle(
                                fontFamily = gilroyFontFamily,
                                fontWeight = FontWeight.Normal,
                                fontSize = 14.sp,
                                color = Color(0xFFB8BDCA),
                                lineHeight = 16.8.sp,
                                letterSpacing = (-0.028).em
                            )
                        )
                    }
                    innerTextField()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp)
            )
        }
    }
}

@Preview
@Composable
private fun UserDataInputScreenPreview() {
    // Add preview content here
}
