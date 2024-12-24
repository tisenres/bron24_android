package com.bron24.bron24_android.screens.settings.edit_profile

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathSegment
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import com.bron24.bron24_android.R
import com.bron24.bron24_android.helper.util.MaskVisualTransformation
import com.bron24.bron24_android.helper.util.formatPhoneNumber
import com.bron24.bron24_android.components.items.AppButton
import com.bron24.bron24_android.components.items.CustomAppBar
import com.bron24.bron24_android.components.items.ItemInputText
import com.bron24.bron24_android.screens.main.theme.Black
import com.bron24.bron24_android.screens.main.theme.Black61
import com.bron24.bron24_android.screens.main.theme.GrayLighter
import com.bron24.bron24_android.screens.main.theme.White
import com.bron24.bron24_android.screens.main.theme.gilroyFontFamily

class EditProfileScreen : Screen {
    @Composable
    override fun Content() {
        EditProfileScreenContent()
    }
}

@SuppressLint("UnrememberedMutableInteractionSource")
@Composable
fun EditProfileScreenContent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = White)
    ) {
        CustomAppBar(
            title = "Edit profile",
            startIcons = {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "icons")
            }
        ) {

        }
        Box(
            modifier = Modifier
                .fillMaxHeight(0.25f)
                .fillMaxWidth(), contentAlignment = Alignment.Center
        ) {
            Box(modifier = Modifier.size(110.dp)) {
                Box(
                    modifier = Modifier
                        .size(110.dp)
                        .border(
                            brush = Brush.linearGradient(
                                start = Offset(0f, 0f),
                                colors = listOf(White, Color(0xff2D6544)),
                                end = Offset(0f, Float.POSITIVE_INFINITY)
                            ),
                            shape = CircleShape,
                            width = 5.dp
                        )
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.joxon_pic),
                        contentDescription = "img",
                        modifier = Modifier
                            .size(110.dp)
                            .clip(shape = CircleShape)
                            .align(Alignment.Center),
                        contentScale = ContentScale.Crop
                    )
                }
                IconButton(
                    onClick = { },
                    modifier = Modifier
                        .clip(shape = CircleShape)
                        .background(color = Color(0xff28613f))
                        .align(Alignment.BottomEnd)
                        .size(36.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_camera),
                        contentDescription = "icon",
                        tint = White,
                        modifier = Modifier.size(22.dp)
                    )
                }
            }
        }

        Text(
            text = "Personal",
            modifier = Modifier.padding(horizontal = 20.dp),
            fontFamily = gilroyFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            color = Black
        )
        Text(
            text = "First name",
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .padding(top = 24.dp),
            fontFamily = gilroyFontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            color = Black61
        )
        ItemInputText(
            modifier = Modifier
                .padding(top = 12.dp)
                .padding(horizontal = 20.dp),
            hint = "",
            text = "Sardorbek",
            listener = {},
            onValueChange = {}
        )
        Text(
            text = "Last name",
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .padding(top = 20.dp),
            fontFamily = gilroyFontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            color = Black61
        )
        ItemInputText(
            modifier = Modifier
                .padding(top = 12.dp)
                .padding(horizontal = 20.dp),
            hint = "",
            text = "Abdurakhmonov",
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            listener = {},
            onValueChange = {}
        )
        Text(
            text = "Phone number",
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .padding(top = 20.dp),
            fontFamily = gilroyFontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            color = Black61
        )
        ItemInputText(
            modifier = Modifier
                .padding(top = 12.dp)
                .padding(horizontal = 20.dp),
            hint = "",
            text = "883410807",
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            listener = {},
            onValueChange = {},
            visualTransformation = MaskVisualTransformation("+998 ## ### ## ##")
        )
        Button(
            onClick = {},

            modifier = Modifier
                .padding(vertical = 32.dp)
                .clip(RoundedCornerShape(8.dp))
                .border(width = 2.dp, color = Color.Red, shape = RoundedCornerShape(8.dp))
                .align(Alignment.CenterHorizontally),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
        ) {
            Text(
                text = "Delete account",
                fontSize = 14.sp,
                fontFamily = gilroyFontFamily,
                fontWeight = FontWeight.Bold,
                color = Color.Red,
                modifier = Modifier.padding(start = 8.dp)
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        AppButton(text = "Save Changes", modifier = Modifier.padding(horizontal = 20.dp)) {

        }
    }
}

@Preview(showBackground = true)
@Composable
private fun EditProfileScreenPreview() {
    EditProfileScreenContent()
}