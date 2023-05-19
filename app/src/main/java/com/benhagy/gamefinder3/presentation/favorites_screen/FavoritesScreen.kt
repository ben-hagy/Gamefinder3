package com.benhagy.gamefinder3.presentation.favorites_screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.benhagy.gamefinder3.presentation.destinations.GameDetailsScreenDestination
import com.benhagy.gamefinder3.presentation.favorites_screen.viewmodel.FavoritesScreenViewModel
import com.benhagy.gamefinder3.presentation.ui.theme.Typography
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun FavoritesScreen(
    navigator: DestinationsNavigator,
    viewModel: FavoritesScreenViewModel = hiltViewModel()
) {
    val state = viewModel.state
    val listState = rememberLazyListState()

    if (state.value.favoriteGames.isEmpty()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Favorite Games",
                style = Typography.titleLarge,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "No favorites added yet!"
            )
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Favorite Games",
                style = Typography.titleLarge,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(2.dp))
            Divider(
                thickness = 1.dp,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(5.dp))
            LazyColumn(
                state = listState,
                modifier = Modifier.fillMaxHeight()
            ) {
                items(state.value.favoriteGames.size) { i ->
                    val game = state.value.favoriteGames[i]
                    FavoriteGameListItem(
                        navigator = navigator,
                        game = game,
                        modifier = Modifier
                            .padding(4.dp)
                    )
                    if (i < state.value.favoriteGames.size) {
                        Divider(
                            modifier = Modifier.padding(
                                horizontal = 16.dp
                            )
                        )
                    }
                }
            }
        }
    }
}