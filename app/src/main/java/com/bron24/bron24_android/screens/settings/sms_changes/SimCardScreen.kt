package com.bron24.bron24_android.screens.settings.sms_changes

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import com.bron24.bron24_android.R
import com.bron24.bron24_android.components.items.AppButton
import com.bron24.bron24_android.components.items.CustomAppBar
import com.bron24.bron24_android.screens.main.theme.White
import com.bron24.bron24_android.screens.main.theme.gilroyFontFamily

class SimCardScreen : Screen {
    @Composable
    override fun Content() {

    }
}

@Composable
fun SimCardScreenContent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = White)
    ) {
        CustomAppBar(
            title = "Phone Number",
            startIcons = {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "icons")
            }
        ){

        }
        Image(
            painter = painterResource(id = R.drawable.sim_card),
            contentDescription = "img",
            modifier = Modifier
                .padding(top = 40.dp)
                .height(150.dp)
                .width(180.dp)
                .align(Alignment.CenterHorizontally)
        )
        Text(
            text = "Do you want to change your phone number?",
            modifier = Modifier
                .padding(horizontal = 40.dp)
                .align(Alignment.CenterHorizontally),
            fontSize = 24.sp,
            fontFamily = gilroyFontFamily,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        Text(
            text = "+998 90 900 90 90 - your current number\nAll your data will be preserved",
            modifier = Modifier
                .padding(horizontal = 40.dp, vertical = 8.dp)
                .align(Alignment.CenterHorizontally),
            fontSize = 14.sp,
            fontFamily = gilroyFontFamily,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center,
            lineHeight = TextUnit(value = 1.8f, type = TextUnitType.Em)
        )
        Spacer(modifier = Modifier.weight(1f))
        AppButton(text = "Change phone number", modifier = Modifier.padding(horizontal = 20.dp)) {
            
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SimCardScreenPreview() {
    SimCardScreenContent()
}