package com.benhagy.gamefinder3.presentation.home_search_screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.benhagy.gamefinder3.presentation.destinations.GameDetailsScreenDestination
import com.benhagy.gamefinder3.presentation.home_search_screen.viewmodel.HomeSearchScreenEvent
import com.benhagy.gamefinder3.presentation.home_search_screen.viewmodel.HomeSearchScreenViewModel
import com.benhagy.gamefinder3.presentation.ui.theme.montserratFonts
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@RootNavGraph(start = true)
@Destination
fun HomeSearchScreen(
    navigator: DestinationsNavigator,
    viewModel: HomeSearchScreenViewModel = hiltViewModel()
) {
    val state = viewModel.state
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        OutlinedTextField(
            value = state.searchQuery,
            onValueChange = {
                viewModel.onEvent(
                    HomeSearchScreenEvent.OnSearchQueryChanged(it)
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            maxLines = 1,
            singleLine = true,
            placeholder = { Text(text = "Search...", fontFamily = montserratFonts) }
        )
        Spacer(modifier = Modifier.height(8.dp))
        LazyRow {
            items(state.genres.size) { i ->
                val genre = state.genres[i]
                ListedGenreItem(
                    genre = genre,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            viewModel.onEvent(
                                event = HomeSearchScreenEvent.OnGenreButtonClicked(genre.id.toString()))
                        }
                        .padding(4.dp)
                )
                if (i < state.genres.size) {
                    Divider(
                        modifier = Modifier.padding(
                            vertical = 16.dp
                        )
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
        LazyColumn(
            modifier = Modifier.fillMaxHeight()
        ) {
            items(state.games.size) { i ->
                val game = state.games[i]
                ListedGameItem(
                    game = game,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            navigator.navigate(GameDetailsScreenDestination(game.id))
                        }
                        .padding(16.dp)
                )
                if (i < state.games.size) {
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