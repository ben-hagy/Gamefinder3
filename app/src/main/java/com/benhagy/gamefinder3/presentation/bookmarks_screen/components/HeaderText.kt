package com.benhagy.gamefinder3.presentation.bookmarks_screen.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

// reusable header

@Composable
fun HeaderText(
    modifier: Modifier,
    displayText: String,
    textStyle: TextStyle,
    hasFluffText: Boolean,
    fluffText: String?
) {
    Text(
        text = displayText,
        style = textStyle,
        textAlign = TextAlign.Center,
        modifier = modifier
    )
    if (hasFluffText) {
        Spacer(modifier = modifier.height(8.dp))
        Text(
            text = fluffText ?: ""
        )
    } else {
        Spacer(modifier = Modifier.height(2.dp))
        Divider(
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}