package com.benhagy.gamefinder3.presentation.game_details_screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.benhagy.gamefinder3.R
import com.benhagy.gamefinder3.presentation.common_components.DefaultDivider
import com.benhagy.gamefinder3.presentation.common_components.DefaultSpacer
import com.benhagy.gamefinder3.presentation.common_components.ThinSpacer
import com.benhagy.gamefinder3.presentation.common_components.WideSpacer
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

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Destination
fun GameDetailsScreen(
    id: Int, // used as savedstatehandle receiver for navigation arguments
    navigator: DestinationsNavigator,
    viewModel: GameDetailsScreenViewModel = hiltViewModel()
) {
    val state = viewModel.state

    val overviewScroll = rememberScrollState() // for parent screen scrolling
    val scaffoldState = rememberScaffoldState() // for scaffold/top app bar/snackbars

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = { DetailsTopAppBar(navigator = navigator, scaffoldState = scaffoldState) },
        backgroundColor = Color.Transparent
    ) {

        state.gameDetails?.let { gameDetails ->

            // ensures the bookmark icon is filled appropriately
            LaunchedEffect(gameDetails.id) {
                gameDetails.id?.let { viewModel.isBookmarked(gameId = it) }
            }

            // if there's a network error, show an error screen
            if (state.error != null) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(4.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = { state.error + (R.string.network_error_message) }.toString(),
                        style = Typography.titleLarge
                    )
                }
                // otherwise, show the screen content
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(4.dp)
                ) {
                    // parent content column
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(4.dp)
                            .verticalScroll(overviewScroll)
                    ) {
                        DetailsTitleCard()

                        DefaultSpacer()
                        DefaultDivider()
                        DefaultSpacer()

                        // screenshots pager
                        DetailsScreenshotPager()

                        DefaultDivider()
                        DefaultSpacer()

                        // middle window with platforms list, and esrb/tags window
                        PlatformsDetailsItem()
                        EsrbAndTagsWindow()

                        WideSpacer()

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
                            DefaultDivider()
                            Spacer(modifier = Modifier.height(2.dp))
                            DescriptionDetailsItem()
                        }

                        ThinSpacer()

                        // weblink item
                        WeblinkDetailsItem()
                    }
                }
            }
        }
    }
}


