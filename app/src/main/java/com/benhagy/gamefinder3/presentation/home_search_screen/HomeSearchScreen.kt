package com.benhagy.gamefinder3.presentation.home_search_screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.benhagy.gamefinder3.R
import com.benhagy.gamefinder3.presentation.destinations.GameDetailsScreenDestination
import com.benhagy.gamefinder3.presentation.home_search_screen.components.ListedGameItem
import com.benhagy.gamefinder3.presentation.home_search_screen.components.ListedGenreItem
import com.benhagy.gamefinder3.presentation.home_search_screen.viewmodel.HomeSearchScreenEvent
import com.benhagy.gamefinder3.presentation.home_search_screen.viewmodel.HomeSearchScreenViewModel
import com.benhagy.gamefinder3.presentation.ui.theme.Typography
import com.benhagy.gamefinder3.presentation.ui.theme.montserratFonts
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.launch

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

    if (state.error != null) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = state.error.toString(),
                style = Typography.titleLarge
            )
        }
    } else {
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
                        contentDescription = stringResource(R.string.delete_search_text_cd),
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
                placeholder = { Text(text = stringResource(R.string.search_hint), fontFamily = montserratFonts) }
            )
            Spacer(modifier = Modifier.height(8.dp))
            LazyRow(
                state = rememberLazyListState()
            ) {
                items(state.genres.size) { i ->
                    val genre = state.genres[i]
                    ListedGenreItem(
                        genre = genre,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                viewModel.onEvent(HomeSearchScreenEvent.OnSearchQueryChanged(""))
                                viewModel.onEvent(
                                    event = HomeSearchScreenEvent.OnGenreButtonClicked(genre.id.toString())
                                )
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

            LazyVerticalGrid(
                modifier = Modifier
                    .fillMaxHeight(),
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
}