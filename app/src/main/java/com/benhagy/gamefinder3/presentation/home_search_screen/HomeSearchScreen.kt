package com.benhagy.gamefinder3.presentation.home_search_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.benhagy.gamefinder3.R
import com.benhagy.gamefinder3.domain.models.ListedGame
import com.benhagy.gamefinder3.presentation.common_components.ThinSpacer
import com.benhagy.gamefinder3.presentation.common_components.WideSpacer
import com.benhagy.gamefinder3.presentation.destinations.GameDetailsScreenDestination
import com.benhagy.gamefinder3.presentation.home_search_screen.components.ListedGameItem
import com.benhagy.gamefinder3.presentation.home_search_screen.components.ListedGenreItem
import com.benhagy.gamefinder3.presentation.home_search_screen.viewmodel.HomeSearchScreenEvent
import com.benhagy.gamefinder3.presentation.home_search_screen.viewmodel.HomeSearchScreenViewModel
import com.benhagy.gamefinder3.presentation.ui.theme.Typography
import com.benhagy.gamefinder3.presentation.ui.theme.montserratFonts
import com.benhagy.gamefinder3.util.Constants.SEARCH_DELAY_TIME
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalComposeUiApi::class)
@Composable
@RootNavGraph(start = true)
@Destination
fun HomeSearchScreen(
    navigator: DestinationsNavigator,
    viewModel: HomeSearchScreenViewModel = hiltViewModel()
) {
    val state = viewModel.state
    // listState used to scroll list back to position 0 whenever search queries change
    val listState = rememberLazyGridState()
    // coroutineScope used to handle delays and scroll position changes
    val coroutineScope = rememberCoroutineScope()

    // values to handle focus and keyboard actions for the search field
    val keyboardController = LocalSoftwareKeyboardController.current
    val localFocusManager = LocalFocusManager.current

    // reference used on this screen for our paged items
    val games: LazyPagingItems<ListedGame> = state.games.collectAsLazyPagingItems()

    // network error will show a screen informing the user to check their network and restart app
    if (state.error != null) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = state.error.toString(), style = Typography.titleLarge
            )
            ThinSpacer()
            Text(
                text = stringResource(R.string.network_error_message),
                style = Typography.labelMedium
            )
        }
        // no error will instead show the screen normally
    } else {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // search field
            OutlinedTextField(
                value = state.searchQuery,
                onValueChange = {
                    viewModel.onEvent(
                        HomeSearchScreenEvent.OnSearchQueryChanged(it)
                    )
                    coroutineScope.launch {
                        delay(SEARCH_DELAY_TIME)
                        listState.scrollToItem(0)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(
                    onDone = {
                        localFocusManager.clearFocus()
                        keyboardController?.hide()
                    }),
                trailingIcon = {
                    // clear button on the search box
                    Icon(Icons.Filled.Clear,
                        contentDescription = stringResource(R.string.delete_search_text_cd),
                        modifier = Modifier.clickable {
                            viewModel.onEvent(
                                HomeSearchScreenEvent.OnSearchQueryChanged("")
                            )
                            coroutineScope.launch {
                                delay(SEARCH_DELAY_TIME)
                                listState.scrollToItem(0)
                            }
                        }
                    )
                },
                maxLines = 1,
                singleLine = true,
                placeholder = {
                    Text(
                        text = stringResource(R.string.search_hint),
                        fontFamily = montserratFonts
                    )
                }

            )
            WideSpacer()

            // genre buttons row
            LazyRow(
                state = rememberLazyListState(),
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.background.copy(alpha = 0.85f))
            ) {
                items(state.genres.size) { i ->
                    val genre = state.genres[i]
                    ListedGenreItem(genre = genre, modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            viewModel.onEvent(
                                event = HomeSearchScreenEvent.OnGenreButtonClicked(genre.id.toString())
                            )
                            coroutineScope.launch {
                                delay(SEARCH_DELAY_TIME)
                                listState.scrollToItem(0)
                            }
                        }
                        .padding(2.dp))
                    if (i < state.genres.size) {
                        Divider(
                            modifier = Modifier.padding(
                                vertical = 16.dp
                            )
                        )
                    }
                }
            }
            // show loading box when list is loading
            when (games.loadState.refresh) {
                is LoadState.Loading -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
                // otherwise show paginated list results
                else -> {
                    LazyVerticalGrid(
                        modifier = Modifier.fillMaxHeight(),
                        columns = GridCells.Fixed(2),
                        state = listState
                    ) {
                        items(
                            count = games.itemCount,
                            key = games.itemKey { it.id }) { index ->
                            val game = games[index]
                            if (game != null) {
                                ListedGameItem(game = game,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            navigator.navigate(GameDetailsScreenDestination(game.id))
                                        }
                                        .padding(4.dp))
                            }
                        }
                    }
                }
            }
        }
    }
}