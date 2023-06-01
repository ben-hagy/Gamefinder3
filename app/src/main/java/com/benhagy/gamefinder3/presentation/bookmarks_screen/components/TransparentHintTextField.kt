package com.benhagy.gamefinder3.presentation.bookmarks_screen.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import com.benhagy.gamefinder3.presentation.ui.theme.montserratFonts

@Composable
fun TransparentHintTextField(
    providedText: () -> String,
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit,
    textStyle: TextStyle = TextStyle(),
    singleLine: Boolean = false,
) {
    Card(
        modifier = modifier
    ) {
        OutlinedTextField(
            value = providedText(),
            onValueChange = { onValueChange(it) },
            singleLine = singleLine,
            textStyle = textStyle,
            shape = RoundedCornerShape(15),
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            placeholder = { Text(text = "Enter your note...", fontFamily = montserratFonts) },
            maxLines = 3
        )
    }
}