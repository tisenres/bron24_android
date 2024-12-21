package com.bron24.bron24_android.screens.auth

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bron24.bron24_android.R
import com.bron24.bron24_android.components.toast.ToastManager
import com.bron24.bron24_android.components.toast.ToastType
import com.bron24.bron24_android.screens.main.theme.gilroyFontFamily
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.foundation.gestures.detectTapGestures

@Composable
fun UserDataInputScreen(
    authViewModel: AuthViewModel,
    onSignUpVerified: () -> Unit,
) {

    val authState by authViewModel.authState.collectAsState()
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 20.dp)
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = {
                        keyboardController?.hide()
                        focusManager.clearFocus()
                    }
                )
            }
    ) {
        TopBar()

        Spacer(modifier = Modifier.height(30.dp))

        Text(
            text = stringResource(id = R.string.enter_personal_data),
            style = TextStyle(
                fontFamily = gilroyFontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                color = Color.Black,
                lineHeight = 20.sp,
            ),
            modifier = Modifier.align(Alignment.Start)
        )

        Spacer(modifier = Modifier.height(16.dp))

        UserDataField(
            fieldName = firstName,
            placeholder = stringResource(id = R.string.first_name),
            icon = R.drawable.ic_empty_user,
            onValueChange = { newValue -> firstName = newValue },
        )

        Spacer(modifier = Modifier.height(16.dp))

        UserDataField(
            fieldName = lastName,
            placeholder = stringResource(id = R.string.last_name),
            icon = R.drawable.ic_empty_user,
            onValueChange = { newValue -> lastName = newValue },
        )

        Spacer(modifier = Modifier.weight(1f))

        ConfirmButtonUser(
            isEnabled = firstName.isNotEmpty() && lastName.isNotEmpty(),
            onClick = {
                authViewModel.authenticateUser(
                    firstName = firstName,
                    lastName = lastName
                )
            },
            authViewModel = authViewModel,
            onSignUpVerified = onSignUpVerified
        )
    }

    when (authState) {
        is AuthState.Loading -> {
            isLoading = true
            // Show loading indicator
        }

        is AuthState.Authenticated -> {
            isLoading = false
            // Navigate to the next screen when authentication is successful
            onSignUpVerified()
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
        }
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

@Composable
fun TopBar() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(30.dp)
    ) {
        Text(
            text = stringResource(id = R.string.register),
            style = TextStyle(
                fontFamily = gilroyFontFamily,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 22.sp,
                color = Color.Black,
                lineHeight = 24.sp,
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
            .height(60.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(Color(0xFFF6F6F6))
            .padding(10.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = "User Icon",
                modifier = Modifier.size(26.dp),
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
                    fontSize = 16.sp,
                    color = Color.Black,
                    lineHeight = 20.sp,
                ),
                decorationBox = { innerTextField ->
                    if (fieldName.isEmpty()) {
                        Text(
                            text = placeholder,
                            style = TextStyle(
                                fontFamily = gilroyFontFamily,
                                fontWeight = FontWeight.Normal,
                                fontSize = 16.sp,
                                color = Color(0xFFB8BDCA),
                                lineHeight = 20.sp,
                            ),
                            modifier = Modifier.align(Alignment.CenterVertically)
                        )
                    }
                    innerTextField()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterVertically),

                )
        }
    }
}

@Composable
fun ConfirmButtonUser(
    isEnabled: Boolean,
    onClick: () -> Unit,
    authViewModel: AuthViewModel,
    onSignUpVerified: () -> Unit,
    modifier: Modifier = Modifier
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    val authState by authViewModel.authState.collectAsState()
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val scale by animateFloatAsState(if (isPressed) 0.95f else 1f, label = "")

    Button(
        onClick = {
            keyboardController?.hide()
            focusManager.clearFocus()
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
            .padding(bottom = 20.dp)
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

    LaunchedEffect(authState) {
        when (authState) {
            is AuthState.Authenticated -> {
                onSignUpVerified()
            }
            is AuthState.Error -> {
                // Handle error state if necessary
            }
            else -> {
                // Handle other states if necessary
            }
        }
    }
}

@Preview
@Composable
private fun UserDataInputScreenPreview() {
    UserDataInputScreen(authViewModel = hiltViewModel()) {

    }
}