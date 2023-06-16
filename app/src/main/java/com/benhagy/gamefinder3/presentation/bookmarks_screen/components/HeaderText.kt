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
import com.benhagy.gamefinder3.presentation.common_components.ThickDivider
import com.benhagy.gamefinder3.presentation.common_components.ThinSpacer
import com.benhagy.gamefinder3.presentation.common_components.WideSpacer

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
        WideSpacer()
        Text(
            text = fluffText ?: ""
        )
    } else {
        ThinSpacer()
        ThickDivider()
    }
}