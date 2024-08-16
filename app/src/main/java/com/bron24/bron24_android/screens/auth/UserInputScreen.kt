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
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bron24.bron24_android.R
import com.bron24.bron24_android.screens.auth.AuthState
import com.bron24.bron24_android.screens.auth.AuthViewModel
import com.bron24.bron24_android.screens.main.theme.gilroyFontFamily

@Composable
fun UserDataInputScreen(
    authViewModel: AuthViewModel,
    onSignUpVerified: () -> Unit,
) {
    val authState by authViewModel.authState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(horizontal = 20.dp)
                .padding(paddingValues)
        ) {
            TopBar()

            Spacer(modifier = Modifier.height(37.dp))

            Text(
                text = stringResource(id = R.string.enter_personal_data),
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
                snackbarHostState = snackbarHostState,
                authViewModel = authViewModel,
                onSignUpVerified = onSignUpVerified
            )
        }
    }

    when (authState) {
        is AuthState.Loading -> {
            // Show loading indicator
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .clip(RoundedCornerShape(10.dp))
            ) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }

        is AuthState.Authenticated -> {
            // Navigate to the next screen when authentication is successful
            onSignUpVerified()
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
fun TopBar() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
    ) {
        Text(
            text = stringResource(id = R.string.register),
            style = TextStyle(
                fontFamily = gilroyFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp,
                color = Color.Black,
                lineHeight = 24.sp,
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
            .height(58.dp)
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
                modifier = Modifier.size(22.dp),
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
            )
        }
    }
}

@Composable
fun ConfirmButtonUser(
    isEnabled: Boolean,
    onClick: () -> Unit,
    snackbarHostState: SnackbarHostState,
    authViewModel: AuthViewModel,
    onSignUpVerified: () -> Unit,
    modifier: Modifier = Modifier
) {
    val authState by authViewModel.authState.collectAsState()

    Button(
        onClick = {
            onClick()
        },
        colors = ButtonDefaults.buttonColors(
            contentColor = Color.White,
            containerColor = if (isEnabled) Color(0xFF32B768) else Color(0xFFE4E4E4)
        ),
        enabled = isEnabled,
        shape = RoundedCornerShape(10.dp),
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 20.dp)
            .height(47.dp)
    ) {
        Text(
            text = stringResource(id = R.string.continue_text),
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            fontFamily = gilroyFontFamily,
            color = if (isEnabled) Color.White else Color.Gray,
            lineHeight = 32.sp,
            letterSpacing = (-0.028).em
        )
    }

    LaunchedEffect(authState) {
        when (authState) {
            is AuthState.Authenticated -> {
                onSignUpVerified()
            }

            is AuthState.Error -> {
                snackbarHostState.showSnackbar(
                    message = "Error: " + (authState as AuthState.Error).message,
                    actionLabel = "OK"
                )
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