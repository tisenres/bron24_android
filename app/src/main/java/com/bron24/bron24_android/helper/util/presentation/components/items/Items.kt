package com.bron24.bron24_android.helper.util.presentation.components.items

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Icon
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.IconButton
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bron24.bron24_android.R
import com.bron24.bron24_android.screens.main.theme.Black
import com.bron24.bron24_android.screens.main.theme.GrayLight
import com.bron24.bron24_android.screens.main.theme.GrayLighter
import com.bron24.bron24_android.screens.main.theme.GrayRegular
import com.bron24.bron24_android.screens.main.theme.GreenLight
import com.bron24.bron24_android.screens.main.theme.GreenNormal
import com.bron24.bron24_android.screens.main.theme.Success
import com.bron24.bron24_android.screens.main.theme.White
import com.bron24.bron24_android.screens.main.theme.bgSuccess
import com.bron24.bron24_android.screens.main.theme.gilroyFontFamily

@Preview(showBackground = true)
@Composable
fun ItemsPreview() {
    val context = LocalContext.current
    Column(modifier = Modifier.fillMaxSize()) {
        ItemSelectedData(date = "", hint = "any data", modifier = Modifier, endIcon = {
            Icon(
                imageVector = Icons.Default.DateRange,
                contentDescription = "icons",

                tint = GrayLight,
            )
        }) {
            Toast.makeText(context, "ishladi", Toast.LENGTH_SHORT).show()
        }


        ItemInfosData(date = "", hint = "sardor", modifier = Modifier, select = false, topIcon = {
            Icon(imageVector = Icons.Default.DateRange, contentDescription = "as")
        }) {

        }
    }
}

@SuppressLint("UnrememberedMutableInteractionSource")
@Composable
fun ItemSelectedData(
    date: String,
    hint: String,
    modifier: Modifier,
    leadingIcon: @Composable() (() -> Unit)? = null,
    endIcon: @Composable (() -> Unit)? = null,

    listener: () -> Unit
) {
    Row(modifier = modifier
        .padding(top = 20.dp)
        .fillMaxWidth()
        .height(38.dp)
        .clip(RoundedCornerShape(16))
        .clickable(
            interactionSource = MutableInteractionSource(), indication = null
        ) { listener.invoke() }
        .border(width = 1.dp, color = GrayLighter, shape = RoundedCornerShape(16))
        .padding(horizontal = 12.dp), verticalAlignment = Alignment.CenterVertically

    ) {
        Text(
            text = if (date.isEmpty()) hint else date,
            fontFamily = gilroyFontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            color = if (date.isEmpty()) GrayLight else Color.DarkGray,
            modifier = Modifier.weight(1f)
        )
        endIcon?.invoke()
    }
}


@Composable
fun CustomAppBar(
    title: String,
    titleColor: Color? = null,
    backgroundColor: Color? = null,
    startIcons: @Composable (() -> Unit)? = null,
    actions: @Composable (() -> Unit)? = null,

    ) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(color = backgroundColor ?: White)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        startIcons?.invoke()
        Text(
            text = title,
            color = titleColor ?: Black,
            fontFamily = gilroyFontFamily,
            fontSize = 18.sp,
            fontWeight = FontWeight.ExtraBold,
            modifier = Modifier.weight(1f)
        )
        actions?.invoke()
    }
}

@Composable
fun ItemEditProfile(
    listener: () -> Unit
) {
    Row(
        modifier = Modifier
            .width(110.dp)
            .height(30.dp)
            .clip(RoundedCornerShape(10))
            .background(color = Success, shape = RoundedCornerShape(10.dp))
            .clickable { listener() },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Default.Edit,
            contentDescription = "icon",
            tint = White,
            modifier = Modifier.size(20.dp)
        )
        Text(
            text = "Edit profile",
            fontFamily = gilroyFontFamily,
            fontSize = 14.sp,
            color = White,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun ItemProfileTask(
    text: String,
    modifier: Modifier? = Modifier,
    startIcons: @Composable (() -> Unit)? = null,
    endIcon: @Composable (() -> Unit)? = null,
) {
    Row(
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .padding(top = 16.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .border(color = GrayLighter, width = 1.dp, shape = RoundedCornerShape(8.dp))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        startIcons?.invoke()
        Text(
            text = text,
            fontFamily = gilroyFontFamily,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = Black,
            modifier = Modifier.padding(horizontal = 12.dp)
        )
    }
}

@SuppressLint("UnrememberedMutableInteractionSource")
@Composable
fun ItemInfosData(
    date: String,
    hint: String,
    select: Boolean,
    modifier: Modifier,
    topIcon: @Composable() (() -> Unit)? = null,
    bottomIcon: @Composable (() -> Unit)? = null,
    listener: () -> Unit
) {
    Column(modifier = modifier
        .padding(top = 20.dp)
        .clip(RoundedCornerShape(16))
        .background(color = if (select) GreenLight else Color.Transparent)
        .clickable(
            interactionSource = MutableInteractionSource(), indication = null
        ) { listener.invoke() }
        .border(
            width = 1.dp,
            color = if (select) GreenLight else GrayLighter,
            shape = RoundedCornerShape(16)
        )
        .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        topIcon?.invoke()
        Text(
            text = if (date.isEmpty()) hint else date,
            fontFamily = gilroyFontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            color = if (date.isEmpty()) GrayLight else Black,
            modifier = Modifier.padding(top = 6.dp)
        )
        bottomIcon?.invoke()

    }
}

@Composable
fun CheckBox(
    text: String, state: Boolean, listener: () -> Unit
) {
    Row(
        modifier = Modifier.padding(top = 8.dp), verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = { listener.invoke() }, modifier = Modifier.size(32.dp)) {
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .border(
                        width = 1.2.dp,
                        color = if (state) Success else GrayLighter,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .clip(RoundedCornerShape(8.dp))
                    .background(color = if (state) Success else White)
            ) {
                if (state) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "icons",
                        tint = White
                    )
                }
            }
        }

        Text(
            text = text,
            fontFamily = gilroyFontFamily,
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal,
            color = GrayRegular,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}

@Composable
fun AppButton(
    text: String, modifier: Modifier? = null, listener: () -> Unit
) {
    Button(
        enabled = true,
        onClick = {
            listener.invoke()
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = Success,
            contentColor = White,
            disabledContainerColor = bgSuccess,
            disabledContentColor = GrayLighter
        ),
        modifier = modifier ?: Modifier
            .padding(vertical = 32.dp)
            .fillMaxWidth()
            .height(50.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Text(
            text = text, fontFamily = gilroyFontFamily, fontSize = 16.sp, color = White
        )
    }
}