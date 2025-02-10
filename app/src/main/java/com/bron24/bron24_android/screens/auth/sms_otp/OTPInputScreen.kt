package com.bron24.bron24_android.screens.auth.sms_otp

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import com.bron24.bron24_android.R
import com.bron24.bron24_android.components.toast.ToastManager
import com.bron24.bron24_android.components.toast.ToastType
import com.bron24.bron24_android.domain.entity.auth.enums.OTPStatusCode
import com.bron24.bron24_android.helper.util.formatPhoneNumber
import com.bron24.bron24_android.screens.main.theme.gilroyFontFamily
import kotlinx.coroutines.delay
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

class OTPInputScreen(val phoneNumber: String) : Screen {
    @Composable
    override fun Content() {
        val viewModel: OTPInputContract.ViewModel = getViewModel<OTPInputScreenVM>()
        val uiState = viewModel.collectAsState()
        val ban = stringResource(id = R.string.banned_user)
        viewModel.collectSideEffect {
            if (it.message.isNotEmpty()) {
                ToastManager.showToast(it.message, type = ToastType.INFO)
            }
            when (it.status) {
                OTPStatusCode.CORRECT_OTP -> {

                }

                OTPStatusCode.INCORRECT_OTP -> {
                    ToastManager.showToast("Tasdiqlash kodi xato kiritildi!", type = ToastType.ERROR)
                }

                OTPStatusCode.NETWORK_ERROR -> {
                    ToastManager.showToast("Internet bilan muammo yuzaga keldi!", type = ToastType.WARNING)
                }

                OTPStatusCode.UNKNOWN_ERROR -> {
                    ToastManager.showToast("Xatolik birozdan kegin qayta urinib ko'ring!", type = ToastType.WARNING)
                }

                OTPStatusCode.BANNED_USER -> {
                    ToastManager.showToast(message = ban, type = ToastType.ERROR)
                }
            }
        }
        OTPInputScreenContent(phoneNumber = phoneNumber, state = uiState, viewModel::onDispatchers)
    }
}

@SuppressLint("DefaultLocale")
@Composable
fun OTPInputScreenContent(
    phoneNumber: String,
    state: State<OTPInputContract.UIState>,
    intent: (OTPInputContract.Intent) -> Unit
) {
    var otp by remember { mutableStateOf("") }
    var resendCounter by remember { mutableIntStateOf(state.value.refreshTime) }
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current
    val isLoading by remember { mutableStateOf(state.value.isLoading) }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
        keyboardController?.show()
    }

    LaunchedEffect(resendCounter) {
        if (resendCounter > 0) {
            while (resendCounter > 0) {
                delay(1000)
                resendCounter--
            }
        }
    }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
        keyboardController?.show()
    }


    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(vertical = 20.dp, horizontal = 12.dp)
                .padding(paddingValues)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp),
            ) {
                EnhancedBackButton(
                    onClick = { intent.invoke(OTPInputContract.Intent.ClickBack) },
                    modifier = Modifier.align(Alignment.CenterStart)
                )

                Text(
                    text = stringResource(id = R.string.otp_title),
                    style = TextStyle(
                        fontFamily = gilroyFontFamily,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 22.sp,
                        color = Color.Black,
                        lineHeight = 24.sp,
                        textAlign = TextAlign.Center
                    ),
                    modifier = Modifier.align(Alignment.Center),
                )
            }

            Spacer(modifier = Modifier.height(37.dp))

            Text(
                text = stringResource(id = R.string.enter_otp_code) +
                        "\n" +
                        phoneNumber.substring(1).formatPhoneNumber(),
                style = TextStyle(
                    fontFamily = gilroyFontFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp,
                    color = Color.Black,
                    lineHeight = 20.sp,
                ),
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .align(Alignment.Start)
            )

            Spacer(modifier = Modifier.height(40.dp))

            OTPTextField(
                otp = state.value.otpCode,
                onOtpChange = { newOtp ->
                    if (newOtp.length <= 4) {
                        otp = newOtp
                        intent.invoke(OTPInputContract.Intent.OTPCode(newOtp))
                        newOtp.toIntOrNull()
                    }
                },
                focusRequester = focusRequester,
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester)
            )
            if (otp.length == 4) {
                intent.invoke(OTPInputContract.Intent.RequestOTP(phoneNumber, otp.toInt()))
                otp = ""
            }

            Spacer(modifier = Modifier.height(36.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                contentAlignment = Alignment.Center

            ) {
                if (resendCounter > 0) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_refresh),
                            contentDescription = stringResource(id = R.string.refresh)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = stringResource(id = R.string.resend_code) +
                                    " " + resendCounter / 60 + ":" + String.format(
                                "%02d",
                                resendCounter % 60
                            ),
                            style = TextStyle(
                                fontFamily = gilroyFontFamily,
                                fontWeight = FontWeight.Normal,
                                fontSize = 16.sp,
                                color = Color(0xFFB5DAC4),
                                lineHeight = 20.sp,
                            ),
                        )
                    }
                } else {
                    UnderlinedResendButton(
                        onClick = {
                            intent.invoke(OTPInputContract.Intent.ClickRestart(phoneNumber))
                            resendCounter = state.value.refreshTime
                        },
                    )

                    if (isLoading) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color.White.copy(alpha = 0.8f)), // White background with some transparency
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(color = MaterialTheme.colorScheme.tertiary) // Progress bar in the center
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun UnderlinedResendButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    TextButton(
        onClick = onClick,
        modifier = modifier,
        interactionSource = remember { MutableInteractionSource() },
    ) {
        BasicText(
            text = stringResource(id = R.string.resend_code_button),
            style = TextStyle(
                fontFamily = gilroyFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.tertiary,
                lineHeight = 20.sp,
                textDecoration = TextDecoration.Underline
            )
        )
    }
}

@Composable
fun EnhancedBackButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    IconButton(
        onClick = onClick,
        modifier = modifier

    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_arrow_back),
            contentDescription = "Back",
            modifier = Modifier
                .size(24.dp)
        )
    }
}

@Composable
fun OTPTextField(
    otp: String,
    onOtpChange: (String) -> Unit,
    focusRequester: FocusRequester,
    modifier: Modifier = Modifier
) {
    BasicTextField(
        value = otp,
        onValueChange = { newInput ->
            if (newInput.length <= 4) {
                // Append only if the new input starts with the current otp
                if (newInput.startsWith(otp)) {
                    onOtpChange(newInput)
                } else if (newInput.length < otp.length) {
                    // Allow deletion (from the end) only
                    onOtpChange(newInput)
                }
            }
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.NumberPassword,
            imeAction = ImeAction.Done
        ),
        decorationBox = {
            Row(
                horizontalArrangement = Arrangement.spacedBy(15.dp, Alignment.CenterHorizontally),
                modifier = modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester),
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
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 16.sp,
                                color = Color(0xFF8F92A4),
                                lineHeight = 20.sp,
                            ),
                        )
                    }
                }
            }
        },
        modifier = Modifier
            .focusRequester(focusRequester)
            .fillMaxWidth()
    )
}

@Preview(showBackground = true)
@Composable
fun BottomNavigationBarPreview() {
    EnhancedBackButton(onClick = { /*TODO*/ })
}