package com.benhagy.gamefinder3.presentation.game_details_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.benhagy.gamefinder3.R
import com.benhagy.gamefinder3.presentation.game_details_screen.components.DescriptionDetailsItem
import com.benhagy.gamefinder3.presentation.game_details_screen.components.DetailsScreenshotPager
import com.benhagy.gamefinder3.presentation.game_details_screen.components.DetailsTitleCard
import com.benhagy.gamefinder3.presentation.game_details_screen.components.DetailsTopAppBar
import com.benhagy.gamefinder3.presentation.game_details_screen.components.DeveloperDetailsItem
import com.benhagy.gamefinder3.presentation.game_details_screen.components.EsrbAndTagsWindow
import com.benhagy.gamefinder3.presentation.game_details_screen.components.GenreDetailsItem
import com.benhagy.gamefinder3.presentation.game_details_screen.components.PlatformsDetailsItem
import com.benhagy.gamefinder3.presentation.game_details_screen.components.PublisherDetailsItem
import com.benhagy.gamefinder3.presentation.game_details_screen.components.WeblinkDetailsItem
import com.benhagy.gamefinder3.presentation.game_details_screen.viewmodel.GameDetailsScreenViewModel
import com.benhagy.gamefinder3.presentation.ui.theme.Typography
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Destination
fun GameDetailsScreen(
    id: Int, // used as savedstatehandle receiver for navigation
    navigator: DestinationsNavigator,
    viewModel: GameDetailsScreenViewModel = hiltViewModel()
) {
    val state = viewModel.state

    val overviewScroll = rememberScrollState() // for whole screen scrolling

    state.gameDetails?.let { gameDetails ->

        // ensures the bookmark icon is filled appropriately on screen launch
        LaunchedEffect(gameDetails.id) {
            gameDetails.id?.let { viewModel.isBookmarked(gameId = it) }
        }

        // if there's a network error, show the error
        if (state.error != null) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(4.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = { state.error + (R.string.network_error_home_screen) }.toString(),
                    style = Typography.titleLarge
                )
            }
            // otherwise, show the screen
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(4.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(4.dp)
                ) {
                    // top app bar with back button and bookmarks icon buttons
                    DetailsTopAppBar(navigator = navigator)
                }
                // parent content column
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(4.dp)
                        .verticalScroll(overviewScroll)
                ) {
                    DetailsTitleCard()

                    Spacer(modifier = Modifier.height(4.dp))
                    Divider(
                        thickness = 0.5.dp,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = .5f)
                    )
                    Spacer(modifier = Modifier.height(4.dp))

                    // screenshots pager
                    DetailsScreenshotPager()

                    Divider(thickness = 0.5.dp, color = MaterialTheme.colorScheme.onBackground)
                    Spacer(modifier = Modifier.height(4.dp))

                    // middle window with platforms list, and esrb/tags window

                    PlatformsDetailsItem()
                    EsrbAndTagsWindow()

                    Spacer(modifier = Modifier.width(8.dp))

                    // genres item
                    if (gameDetails.genres.isNotEmpty()) {
                        Text(
                            modifier = Modifier
                                .padding(top = 4.dp, bottom = 4.dp),
                            text = stringResource(R.string.genres_title),
                            style = Typography.titleLarge,
                            color = MaterialTheme.colorScheme.onBackground,
                            textAlign = TextAlign.Center
                        )
                        Column(
                            modifier = Modifier
                                .wrapContentWidth()
                                .wrapContentHeight()
                        ) {
                            gameDetails.genres.forEach { genre ->
                                GenreDetailsItem(
                                    genre = genre,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(4.dp)
                                )
                            }
                        }
                    }
                    // developer item
                    if (gameDetails.developers.isNotEmpty()) {
                        Text(
                            modifier = Modifier
                                .padding(top = 4.dp, bottom = 4.dp),
                            text = stringResource(R.string.developers_title),
                            style = Typography.titleLarge,
                            color = MaterialTheme.colorScheme.onBackground,
                            textAlign = TextAlign.Center
                        )
                        Column(
                            modifier = Modifier
                                .wrapContentWidth()
                                .wrapContentHeight()
                        ) {
                            gameDetails.developers.forEach { developer ->
                                DeveloperDetailsItem(
                                    developer = developer,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(4.dp)
                                )
                            }
                        }
                    }

                    // publisher item
                    if (gameDetails.publishers.isNotEmpty()) {
                        Text(
                            modifier = Modifier
                                .padding(top = 4.dp, bottom = 4.dp),
                            text = stringResource(R.string.publishers_title),
                            style = Typography.titleLarge,
                            color = MaterialTheme.colorScheme.onBackground,
                            textAlign = TextAlign.Center
                        )
                        Column(
                            modifier = Modifier
                                .wrapContentWidth()
                                .wrapContentHeight()
                        ) {
                            gameDetails.publishers.forEach { publisher ->
                                PublisherDetailsItem(
                                    publisher = publisher,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(4.dp)
                                )
                            }
                        }
                    }

                    // description item
                    if (!gameDetails.description.isNullOrEmpty()) {
                        Text(
                            modifier = Modifier
                                .padding(top = 4.dp, bottom = 4.dp),
                            text = stringResource(R.string.description_title),
                            style = Typography.titleLarge,
                            color = MaterialTheme.colorScheme.onBackground,
                            textAlign = TextAlign.Center
                        )
                        Divider(
                            thickness = 0.5.dp,
                            color = MaterialTheme.colorScheme.onBackground.copy(alpha = .5f)
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        DescriptionDetailsItem()
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    // weblink item
                    WeblinkDetailsItem()
                }
            }
        }
    }
}


