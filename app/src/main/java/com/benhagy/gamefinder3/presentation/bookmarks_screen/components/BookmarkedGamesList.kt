package com.benhagy.gamefinder3.presentation.bookmarks_screen.components

import android.annotation.SuppressLint
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.rememberDismissState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.benhagy.gamefinder3.data.local.entity.BookmarkedGameEntity
import com.benhagy.gamefinder3.presentation.bookmarks_screen.viewmodel.BookmarksScreenEvent
import com.benhagy.gamefinder3.presentation.bookmarks_screen.viewmodel.BookmarksScreenViewModel
import com.benhagy.gamefinder3.util.Constants
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
fun BookmarkedGameList(
    viewModel: BookmarksScreenViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
) {
    val state = viewModel.state
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState
    ) {
        LazyColumn(
            state = listState,
            modifier = Modifier.fillMaxHeight()
        ) {
            itemsIndexed(
                items = state.bookmarkedGames.toMutableList(),
                key = { _: Int, listItem: BookmarkedGameEntity -> listItem.id!! }
            ) { index: Int, item: BookmarkedGameEntity ->
                val game = state.bookmarkedGames[index]
                val dismissState = rememberDismissState(
                    initialValue = DismissValue.Default,
                    confirmStateChange = {
                        if (it == DismissValue.DismissedToStart) {
                            coroutineScope.launch {
                                delay(Constants.DELETE_DELAY_TIME)
                                viewModel.onEvent(
                                    BookmarksScreenEvent.RemoveSelectedBookmark(item.id!!)
                                )
                                scaffoldState.snackbarHostState.showSnackbar(
                                    message = "${game.name} removed from Bookmarks.",
                                    actionLabel = null,
                                    duration = SnackbarDuration.Short
                                )
                            }
                        }
                        true
                    }
                )

                // swipe to dismiss object
                SwipeToDismiss(
                    state = dismissState,
                    modifier = Modifier
                        .padding(vertical = 1.dp)
                        .animateItemPlacement(),
                    directions = setOf(
                        DismissDirection.EndToStart
                    ),
                    dismissThresholds = { direction ->
                        FractionalThreshold(if (direction == DismissDirection.EndToStart) 0.2f else .5f)
                    },
                    background = {
                        val color by animateColorAsState(
                            when (dismissState.targetValue) {
                                DismissValue.Default -> MaterialTheme.colorScheme.background
                                else -> MaterialTheme.colorScheme.error

                            }
                        )

                        val scale by animateFloatAsState(
                            if (dismissState.targetValue == DismissValue.Default) 1f else 1.33f
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(color)
                                .padding(horizontal = 20.dp),
                            contentAlignment = Alignment.CenterEnd
                        ) {
                            Icon(
                                Icons.Default.Delete,
                                contentDescription = null,
                                modifier = Modifier
                                    .scale(scale)
                            )
                        }
                    },

                    // bookmarked list content
                    dismissContent = {
                        BookmarkedGamesListItem(
                            navigator = navigator,
                            game = game,
                            modifier = Modifier
                                .padding(vertical = 2.dp, horizontal = 4.dp)

                        )
                    }
                )
                if (index < state.bookmarkedGames.size) {
                    Divider(
                        color = MaterialTheme.colorScheme.background,
                        modifier = Modifier.padding(
                            horizontal = 16.dp
                        )
                    )
                }
            }
        }
    }
}