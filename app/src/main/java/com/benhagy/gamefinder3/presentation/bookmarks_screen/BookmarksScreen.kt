package com.benhagy.gamefinder3.presentation.bookmarks_screen

import android.widget.Toast
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissValue
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.benhagy.gamefinder3.data.local.entity.BookmarkedGameEntity
import com.benhagy.gamefinder3.presentation.bookmarks_screen.components.BookmarkedGamesListItem
import com.benhagy.gamefinder3.presentation.bookmarks_screen.viewmodel.BookmarksScreenEvent
import com.benhagy.gamefinder3.presentation.bookmarks_screen.viewmodel.BookmarksScreenViewModel
import com.benhagy.gamefinder3.presentation.ui.theme.Typography
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun BookmarksScreen(
    navigator: DestinationsNavigator,
    viewModel: BookmarksScreenViewModel = hiltViewModel()
) {
    val state = viewModel.state
    val listState = rememberLazyListState()
    val context = LocalContext.current

    if (state.value.bookmarkedGames.isEmpty()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Bookmarked Games",
                style = Typography.titleLarge,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "No bookmarked games added yet!"
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
                text = "Bookmarked Games",
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
                itemsIndexed(
                    items = state.value.bookmarkedGames.toMutableList(),
                    key = { index: Int, listItem: BookmarkedGameEntity -> listItem.id!!}
                ) { index: Int, item: BookmarkedGameEntity ->
                    val game = state.value.bookmarkedGames[index]
                    val dismissState = rememberDismissState(
                        initialValue = DismissValue.Default,
                        confirmValueChange = {
                            if(it != DismissValue.DismissedToEnd) {
                                viewModel.onEvent(
                                    BookmarksScreenEvent.RemoveSelectedBookmark(item.id!!)
                                )
                                Toast.makeText(
                                    context,
                                    "${game.name} removed from Bookmarks.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            true
                        }
                    )

                SwipeToDismiss(
                    state = dismissState,
                    directions = setOf(
                        DismissDirection.EndToStart
                    ),
                    background = {
                        val color by animateColorAsState(
                            when (dismissState.targetValue) {
                                DismissValue.Default -> MaterialTheme.colorScheme.background
                                else -> Color.Red

                            }
                        )
                        val alignment = Alignment.CenterEnd
                        val icon = Icons.Default.Delete

                        val scale by animateFloatAsState(
                            if (dismissState.targetValue == DismissValue.Default) 0.75f else 1f
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(color)
                                .padding(horizontal = 20.dp),
                            contentAlignment = alignment
                        ) {
                            Icon(
                                icon,
                                contentDescription = "Delete icon",
                                modifier = Modifier
                                    .scale(scale)
                            )
                        }
                    },
                    dismissContent = {
                        BookmarkedGamesListItem(
                            navigator = navigator,
                            game = game,
                            modifier = Modifier
                                .padding(4.dp)
                        )
                    })
                if (index < state.value.bookmarkedGames.size) {
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