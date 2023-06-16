package com.benhagy.gamefinder3.presentation.common_components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DefaultSpacer() {
    Spacer(modifier = Modifier.height(4.dp))
}

@Composable
fun WideSpacer() {
    Spacer(modifier = Modifier.height(8.dp))
}

@Composable
fun ThinSpacer() {
    Spacer(modifier = Modifier.height(2.dp))
}

@Composable
fun DefaultDivider() {
    Divider(
        thickness = 0.5.dp,
        color = MaterialTheme.colorScheme.onBackground.copy(alpha = .5f)
    )
}

@Composable
fun ThickDivider() {
    Divider(
        thickness = 1.dp,
        color = MaterialTheme.colorScheme.onBackground
    )
}