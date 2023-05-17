package com.benhagy.gamefinder3.presentation.favorites_screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.benhagy.gamefinder3.presentation.destinations.GameDetailsScreenDestination
import com.benhagy.gamefinder3.presentation.favorites_screen.viewmodel.FavoritesScreenViewModel
import com.benhagy.gamefinder3.presentation.ui.theme.montserratFonts
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
    val coroutineScope = rememberCoroutineScope()
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = "Favorite Games",
            fontFamily = montserratFonts,
            fontSize = 24.sp,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(2.dp))
        Divider(
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.height(3.dp))
        LazyColumn(
            state = listState,
            modifier = Modifier.fillMaxHeight()
        ) {
            items(state.value.favoriteGames.size) { i ->
                val game = state.value.favoriteGames[i]
                FavoriteGameListItem(
                    game = game,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            navigator.navigate(GameDetailsScreenDestination(game.id!!))
                        }
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