package com.benhagy.gamefinder3.presentation.bookmarks_screen.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.benhagy.gamefinder3.presentation.ui.theme.Typography

@Composable
fun UserNoteTextField(
    modifier: Modifier,
    text: String?,
    onValueChange: (String) -> Unit,
    keyboardActions: KeyboardActions,
    placeholderText: String?,
    trailingIcon: @Composable (() -> Unit)?
) {
    OutlinedTextField(
        value = text ?: "",
        onValueChange = onValueChange,
        keyboardActions = keyboardActions,
        singleLine = true,
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
//        maxLines = 4,
        textStyle = Typography.labelSmall,
        placeholder = {
            Text(
                text = placeholderText ?: "",
                style = Typography.labelSmall
            )
        },
        trailingIcon = trailingIcon
    )
}