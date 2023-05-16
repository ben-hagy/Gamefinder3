package com.benhagy.gamefinder3.presentation.favorites_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.benhagy.gamefinder3.presentation.favorites_screen.viewmodel.FavoritesScreenViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun FavoritesScreen(
    navigator: DestinationsNavigator,
    viewModel: FavoritesScreenViewModel = hiltViewModel()
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = "This is the favorites screen!"
        )
    }
}