package com.bron24.bron24_android.screens.auth.register

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import com.bron24.bron24_android.R
import com.bron24.bron24_android.screens.main.theme.gilroyFontFamily
import org.orbitmvi.orbit.compose.collectAsState

const val MAX_INPUT_CHARS = 20

data class RegisterScreen(val phoneNumber: String) : Screen {
    @Composable
    override fun Content() {
        val viewModel: RegisterScreenContract.ViewModel = getViewModel<RegisterScreenVM>()
        val uiState = viewModel.collectAsState()
        RegisterScreenContent(phoneNumber, state = uiState, intent = viewModel::onDispatchers)
    }
}

@Composable
fun RegisterScreenContent(
    phoneNumber: String,
    state: State<RegisterScreenContract.UIState>,
    intent: (RegisterScreenContract.Intent) -> Unit
) {
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
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
                intent.invoke(
                    RegisterScreenContract.Intent.ClickRegister(
                        phoneNumber,
                        firstName,
                        lastName
                    )
                )
            },
        )
    }
    if (state.value.isLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White.copy(alpha = 0.8f)),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = Color(0xFF32B768))
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
                onValueChange = { if (it.length <= MAX_INPUT_CHARS) onValueChange(it.trim())},
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
                    .align(Alignment.CenterVertically)
                )
        }
    }
}

@Composable
fun ConfirmButtonUser(
    isEnabled: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
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

}