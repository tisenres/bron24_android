package com.bron24.bron24_android.components.items

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.PathSegment
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.bron24.bron24_android.R
import com.bron24.bron24_android.helper.util.NetworkConnection
import com.bron24.bron24_android.screens.main.theme.Black
import com.bron24.bron24_android.screens.main.theme.GrayLight
import com.bron24.bron24_android.screens.main.theme.GrayLighter
import com.bron24.bron24_android.screens.main.theme.GrayRegular
import com.bron24.bron24_android.screens.main.theme.GreenLight
import com.bron24.bron24_android.screens.main.theme.Success
import com.bron24.bron24_android.screens.main.theme.White
import com.bron24.bron24_android.screens.main.theme.bgSuccess
import com.bron24.bron24_android.screens.main.theme.gilroyFontFamily
import com.bron24.bron24_android.screens.main.theme.interFontFamily

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
        ItemLanguage(text = "Uzbek", selected = true) {}
        ItemInputText(
            modifier = Modifier.padding(top = 12.dp),
            text = "sardor",
            hint = "",
            //visualTransformation = null,
            onValueChange = {},
            isSelect = true,
            listener = {}
        )
    }
}

@Composable
fun ErrorNetWork(networkConnection: NetworkConnection) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Transparent),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(0.6f)
                .fillMaxHeight(0.3f)
                .clip(RoundedCornerShape(12.dp))
                .background(color = White, shape = RoundedCornerShape(12.dp))
                .border(width = 1.5.dp, color = Color.Red, shape = RoundedCornerShape(12.dp)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Internetni tekshiring!",
                fontFamily = gilroyFontFamily,
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Red
            )
        }
    }
}

@SuppressLint("UnrememberedMutableInteractionSource")
@Composable
fun ItemInputText(
    modifier: Modifier,
    hint: String,
    text: String,
    error: Boolean = false,
    minHeight: Dp? = null,
    isSelect: Boolean = false,
    listener: (Boolean) -> Unit,
    value: ((String) -> Unit)? = null,
    onValueChange: (String) -> Unit,
    //
    visualTransformation: VisualTransformation = VisualTransformation.None,
    leadingIcon: @Composable() (() -> Unit)? = null,
    endIcon: @Composable() (() -> Unit)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
) {
    val select = remember {
        mutableStateOf(false)
    }
    BasicTextField(
        value = text,
        onValueChange = onValueChange,
        modifier = modifier
            .defaultMinSize(minHeight = minHeight ?: 54.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(color = White, shape = RoundedCornerShape(10.dp))
            .onFocusChanged { focusState ->
                select.value = focusState.isFocused
                listener.invoke(focusState.isFocused)
            }
            .border(
                1.2.dp,
                if (select.value && !error) Success else if (error) Color.Red else GrayLighter,
                RoundedCornerShape(10.dp)
            ),
        textStyle = TextStyle(
            fontSize = 16.sp,
            fontFamily = gilroyFontFamily,
            fontWeight = FontWeight.SemiBold
        ),
        cursorBrush = SolidColor(Success),
        keyboardOptions = keyboardOptions,
        //keyboardActions = keyboardActions?: KeyboardActions.Default,
        singleLine = true,
        maxLines = 1,
        visualTransformation = visualTransformation,

        decorationBox = { innerTextField ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .width(minHeight ?: 54.dp),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    leadingIcon?.invoke()
                    Box(contentAlignment = Alignment.CenterStart) {
                        if (text.isEmpty()) {
                            androidx.compose.material3.Text(
                                text = hint,
                                style = MaterialTheme.typography.bodyMedium,
                                color = Success,
                                modifier = Modifier.focusable(false),
                                fontSize = 16.sp,
                                fontFamily = gilroyFontFamily
                            )
                        }
                        innerTextField()
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    endIcon?.invoke()
                }
            }
        }
    )
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
            text = date.ifEmpty { hint },
            fontFamily = gilroyFontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            color = if (date.isEmpty()) GrayLight else Color.DarkGray,
            modifier = Modifier.weight(1f)
        )
        endIcon?.invoke()
    }
}


@SuppressLint("UnrememberedMutableInteractionSource")
@Composable
fun ItemLanguage(
    text: String,
    selected: Boolean,
    listener: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(54.dp)
            .clip(RoundedCornerShape(8.dp))
            .clickable(
                interactionSource = MutableInteractionSource(),
                indication = null
            ) { listener.invoke() }
            .border(width = 1.2.dp, color = GrayLighter, shape = RoundedCornerShape(8.dp))
            .padding(horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text,
            fontFamily = gilroyFontFamily,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.weight(1f)
        )
        if (selected) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = "icon",
                tint = Success,
                modifier = Modifier.size(24.dp)
            )
        }
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
            fontSize = 20.sp,
            fontWeight = FontWeight.ExtraBold,
            modifier = Modifier
                .padding(start = if (startIcons != null) 8.dp else 0.dp)
                .weight(1f)
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
            .clip(RoundedCornerShape(10.dp))
            .background(color = Success, shape = RoundedCornerShape(8.dp))
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
    borderColor: Color? = null,
    index: Int = 0,
    paddingHor: Dp? = null,
    startIcons: @Composable (() -> Unit)? = null,
    endIcon: @Composable (() -> Unit)? = null,
    listener: (Int) -> Unit
) {
    Row(modifier = Modifier
        .padding(horizontal = paddingHor ?: 20.dp)
        .padding(top = 16.dp)
        .fillMaxWidth()
        .clip(RoundedCornerShape(8.dp))
        .clickable { listener.invoke(index) }
        .border(
            color = borderColor ?: GrayLighter, width = 1.dp, shape = RoundedCornerShape(8.dp)
        )
        .padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
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
    topIcon: @Composable (() -> Unit)? = null,
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
    text: String, modifier: Modifier, listener: () -> Unit
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
        modifier = modifier
            .padding(vertical = 24.dp)
            .fillMaxWidth()
            .height(50.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Text(
            text = text, fontFamily = gilroyFontFamily, fontSize = 16.sp, color = White
        )
    }
}

@Composable
fun SearchView(
    modifier: Modifier = Modifier,
) {
//    val firstName by viewModel.firstName.collectAsState()

    Column(
        modifier = modifier
            .fillMaxWidth()
            .height(150.dp)
            .background(
                color = Color(0xFF32B768),
                shape = RoundedCornerShape(bottomEnd = 25.dp, bottomStart = 25.dp)
            )
            .padding(start = 20.dp, end = 20.dp, top = 20.dp, bottom = 10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        ProfileRow( "John")
        SearchRow(
            onSearchClick = {

            },
            onFilterClick = {

            },
        )
    }
}

@Composable
fun ProfileRow(firstName: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = rememberAsyncImagePainter(model = R.drawable.ball_pic), // Optimized with Coil
                contentDescription = "profile_image",
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(15.dp))
            Column {
                val helloText = stringResource(id = R.string.hello) + ", $firstName!"
                androidx.compose.material3.Text(
                    text = helloText,
                    style = TextStyle(
                        fontFamily = interFontFamily,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 18.sp,
                        color = Color.White,
                        lineHeight = 22.sp,
//                        letterSpacing = (-0.028).em
                    )
                )
            }
        }
    }
}

@Composable
fun SearchRow(
    onSearchClick: () -> Unit,
    onFilterClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp, bottom = 10.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier
                .weight(1f)
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(5.dp)
                )
                .height(40.dp)
                .clip(RoundedCornerShape(5.dp)) // Ensure rounded corners
                .clickable { onSearchClick.invoke() }
                .padding(horizontal = 10.dp, vertical = 10.dp)
        ) {
            Image(
                painter = rememberAsyncImagePainter(model = R.drawable.ic_search_green),
                contentDescription = "search_icon",
                modifier = Modifier.size(15.dp),
            )
            Spacer(modifier = Modifier.width(9.dp))
            val searchHint = stringResource(id = R.string.search_stadium)
            androidx.compose.material3.Text(
                text = searchHint,
                style = TextStyle(
                    fontFamily = gilroyFontFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp,
                    color = Color(0xFF9C9E9C),
                    lineHeight = 16.sp
                )
            )
        }
        Spacer(modifier = Modifier.width(19.dp))
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(5.dp))
                .background(color = Color.White)
                .clickable {
                    onFilterClick()
                },
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_filter),
                contentDescription = "filter_icon",
                colorFilter = ColorFilter.tint(Color(0xFF32B768)),
                modifier = Modifier.size(18.dp)
            )
        }
    }
}