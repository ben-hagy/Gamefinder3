package com.benhagy.gamefinder3.presentation.game_details_screen.components

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.benhagy.gamefinder3.R
import com.benhagy.gamefinder3.presentation.game_details_screen.viewmodel.GameDetailsScreenEvent
import com.benhagy.gamefinder3.presentation.game_details_screen.viewmodel.GameDetailsScreenViewModel
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.launch

@Composable
@ExperimentalMaterial3Api
fun DetailsTopAppBar(
    viewModel: GameDetailsScreenViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
) {
    val state = viewModel.state

    val coroutineScope = rememberCoroutineScope() // for async tasks
    val context = LocalContext.current // for the toasts, and the website intent

    state.gameDetails?.let { gameDetails ->

        Column(
            modifier = Modifier.padding(4.dp)
        ) {
            TopAppBar(
                title = {},
                modifier = Modifier
                    .background(color = MaterialTheme.colorScheme.background)
                    .fillMaxWidth()
                    .wrapContentHeight(),
                actions = {
                    Row(
                        modifier = Modifier
                            .padding(4.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        IconButton(
                            onClick = {
                                navigator.popBackStack()
                            }) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = stringResource(R.string.go_back_cd),
                                tint = MaterialTheme.colorScheme.onBackground,
                                modifier = Modifier.size(28.dp)
                            )
                        }
                        IconButton(
                            onClick = {
                                coroutineScope.launch {
                                    if (state.isBookmarked) {
                                        viewModel.onEvent(
                                            GameDetailsScreenEvent.RemoveGameFromBookmarks(
                                                id = state.gameDetails.id!!
                                            )
                                        )
                                        Toast.makeText(
                                            context,
                                            "${gameDetails.name} removed from Bookmarks.",
                                            Toast.LENGTH_SHORT
                                        ).show()


                                    } else {
                                        viewModel.onEvent(
                                            GameDetailsScreenEvent.BookmarkGame(game = state.gameDetails)
                                        )
                                        Toast.makeText(
                                            context,
                                            "${gameDetails.name} added to Bookmarks.",
                                            Toast.LENGTH_SHORT
                                        ).show()

                                    }
                                }
                            }, modifier = Modifier.padding(4.dp)
                        ) {
                            Icon(
                                imageVector = if (state.isBookmarked) {
                                    Icons.Default.Bookmark
                                } else {
                                    Icons.Default.BookmarkBorder
                                },
                                contentDescription = stringResource(R.string.add_to_bookmarks_cd),
                                tint = MaterialTheme.colorScheme.onBackground,
                                modifier = Modifier.size(28.dp)
                            )
                        }
                    }

                },
                scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
            )
        }
    }
}