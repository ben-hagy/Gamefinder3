package com.benhagy.gamefinder3.presentation.bookmarks_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.benhagy.gamefinder3.R
import com.benhagy.gamefinder3.presentation.bookmarks_screen.components.BookmarkedGameList
import com.benhagy.gamefinder3.presentation.bookmarks_screen.components.HeaderText
import com.benhagy.gamefinder3.presentation.bookmarks_screen.viewmodel.BookmarksScreenViewModel
import com.benhagy.gamefinder3.presentation.common_components.WideSpacer
import com.benhagy.gamefinder3.presentation.ui.theme.Typography
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun BookmarksScreen(
    navigator: DestinationsNavigator,
    viewModel: BookmarksScreenViewModel = hiltViewModel()
) {
    val state = viewModel.state

    // parent column for the screen
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // header changes if the list is empty
        if (state.bookmarkedGames.isEmpty()) {
            HeaderText(
                modifier = Modifier,
                displayText = stringResource(id = R.string.bookmarked_games_title),
                textStyle = Typography.titleLarge,
                hasFluffText = true,
                fluffText = stringResource(id = R.string.no_bookmarks_fluff_text)
            )
        } else {
            HeaderText(
                modifier = Modifier
                    .padding(vertical = 4.dp),
                displayText = stringResource(id = R.string.bookmarked_games_title),
                textStyle = Typography.titleLarge,
                hasFluffText = false,
                fluffText = null
            )
            WideSpacer()

            //bookmarked games list
            BookmarkedGameList(navigator = navigator)
        }
    }
}