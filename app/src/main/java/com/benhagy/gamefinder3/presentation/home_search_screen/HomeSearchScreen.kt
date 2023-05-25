package com.benhagy.gamefinder3.presentation.home_search_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColor
import androidx.hilt.navigation.compose.hiltViewModel
import com.benhagy.gamefinder3.presentation.destinations.GameDetailsScreenDestination
import com.benhagy.gamefinder3.presentation.home_search_screen.viewmodel.HomeSearchScreenEvent
import com.benhagy.gamefinder3.presentation.home_search_screen.viewmodel.HomeSearchScreenViewModel
import com.benhagy.gamefinder3.presentation.ui.theme.montserratFonts
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@RootNavGraph(start = true)
@Destination
fun HomeSearchScreen(
    navigator: DestinationsNavigator,
    viewModel: HomeSearchScreenViewModel = hiltViewModel()
) {
    val state = viewModel.state
    val listState = rememberLazyGridState()
    val coroutineScope = rememberCoroutineScope()
    var selectedItem by remember { mutableStateOf(-1) }

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
            trailingIcon = {
                Icon(
                    Icons.Filled.Clear,
                    contentDescription = null,
                    modifier = Modifier
                        .clickable {
                            viewModel.onEvent(
                                HomeSearchScreenEvent.OnSearchClearClicked
                            )
                        }
                )
            },
            maxLines = 1,
            singleLine = true,
            placeholder = { Text(text = "Search...", fontFamily = montserratFonts) }
        )
        Spacer(modifier = Modifier.height(8.dp))
        LazyRow(
            state = rememberLazyListState()
//            modifier = Modifier
//                .background(Brush.verticalGradient(listOf(Color.Transparent, PurpleGrey40)))
        ) {
            items(state.genres.size) { i ->
                val genre = state.genres[i]
                ListedGenreItem(
                    genre = genre,
                    modifier = Modifier
                        .fillMaxWidth()
                        // code below handles holding highlight color for selected items...
                            // however, the selected item doesn't retain when you change screens
                            // may need new state variable (isSelected isn't holding true)
                        .selectable(
                            onClick = {
                                viewModel.onEvent(HomeSearchScreenEvent.OnSearchQueryChanged(""))
                                viewModel.onEvent(
                                    event = HomeSearchScreenEvent.OnGenreButtonClicked(genre.id.toString())
                                )
                                selectedItem = i
                            },
                            selected = selectedItem == i,
                            )
                        .background(
                            if (selectedItem == i && state.isSelected) {
                                MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.1f)
                            } else {
                                MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.1f)
                            }
                        )
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
//        Spacer(modifier = Modifier.height(4.dp))
        LazyVerticalGrid(
            modifier = Modifier
                .fillMaxHeight(),
//                .background(Brush.verticalGradient(listOf(PurpleGrey40, Color.Transparent))),
            columns = GridCells.Fixed(2),
            state = listState
        ) {
            if (state.isRefreshing) {
                coroutineScope.launch { listState.animateScrollToItem(0) }
            }
            items(state.games.size) { i ->
                val game = state.games[i]
                ListedGameItem(
                    game = game,
                    isRefreshing = state.isRefreshing,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            navigator.navigate(GameDetailsScreenDestination(game.id))
                        }
                        .padding(4.dp),
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
        Spacer(modifier = Modifier.height(30.dp))
    }
}