package com.benhagy.gamefinder3.presentation.bookmarks_screen.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.benhagy.gamefinder3.presentation.ui.theme.montserratFonts

@Composable
fun TransparentHintTextField(
    text: String,
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit,
    textStyle: TextStyle = TextStyle(),
    singleLine: Boolean = false,
) {
    Box(
        modifier = modifier
            .shadow(
                elevation = 0.2.dp,
                shape = RoundedCornerShape(30)
            )
    ) {
        TextField(
            value = text,
            onValueChange = onValueChange,
            singleLine = singleLine,
            textStyle = textStyle,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            placeholder = { Text(text = "Enter your note...", fontFamily = montserratFonts) },
            maxLines = 1
        )
    }
}