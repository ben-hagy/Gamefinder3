package com.benhagy.gamefinder3.presentation.game_details_screen

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Schedule
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.benhagy.gamefinder3.R
import com.benhagy.gamefinder3.presentation.game_details_screen.viewmodel.GameDetailsScreenEvent
import com.benhagy.gamefinder3.presentation.game_details_screen.viewmodel.GameDetailsScreenViewModel
import com.benhagy.gamefinder3.presentation.ui.theme.Typography
import com.benhagy.gamefinder3.util.parse
import com.benhagy.gamefinder3.util.parseReleaseDate
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
@Destination
fun GameDetailsScreen(
    id: Int,
    navigator: DestinationsNavigator,
    viewModel: GameDetailsScreenViewModel = hiltViewModel()
) {
    // variables for states and local context
    val state = viewModel.state
    val pagerState = rememberPagerState() // for image scroller
    val scroll = rememberScrollState(0) // for text scroller
    val coroutineScope = rememberCoroutineScope() // for async tasks
    val context = LocalContext.current // for the toasts

    state.gameDetails?.let { gameDetails ->

        LaunchedEffect(gameDetails.id) {
            gameDetails.id?.let { viewModel.isFavorite(gameId = it) }
        }

        if (state.error == null) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
            ) {
                // first row with back button and favorites icon buttons
                Row(
                    modifier = Modifier
                        .background(color = MaterialTheme.colorScheme.background)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    //back button
                    IconButton(
                        onClick = {
                            navigator.popBackStack()
                        },
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_back_arrow),
                            contentDescription = "Go back",
                            tint = MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier.size(28.dp)
                        )
                    }
                    // favorite buttons
                    // onClick handles changing the icon via isFavorite state update in viewmodel,
                    // performing the addition/removal, and showing toast
                    IconButton(
                        onClick = {
                            coroutineScope.launch {
                                if (state.isFavorite) {
                                    viewModel.onEvent(
                                        GameDetailsScreenEvent.RemoveGameFromFavorites(id = state.gameDetails.id!!)
                                    )
                                    Toast.makeText(
                                        context,
                                        "${gameDetails.name} removed from Favorites.",
                                        Toast.LENGTH_SHORT
                                    ).show()


                                } else {
                                    viewModel.onEvent(
                                        GameDetailsScreenEvent.SaveGameAsFavorite(game = state.gameDetails)
                                    )
                                    Toast.makeText(
                                        context,
                                        "${gameDetails.name} added to Favorites.",
                                        Toast.LENGTH_SHORT
                                    ).show()

                                }
                            }
                        }, modifier = Modifier.padding(4.dp)
                    ) {
                        Icon(
                            painter = if (state.isFavorite) {
                                painterResource(id = R.drawable.ic_favorite_filled)
                            } else {
                                painterResource(id = R.drawable.ic_favorite_unfilled)
                            },
                            contentDescription = "Add to favorites",
                            tint = MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier.size(28.dp)
                        )

                    }
                }
                // second row with game icon, name, release date
                Row {
                    // game icon
                    AsyncImage(
                        model = gameDetails.backgroundImage ?: "",
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        alignment = Alignment.TopCenter,
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                            .size(80.dp)
                            .clipToBounds()
                            .border(
                                width = 2.dp,
                                color = MaterialTheme.colorScheme.secondary,
                                shape = CircleShape
                            )
                            .clip(CircleShape)
                    )
                    Column(
                        modifier = Modifier
                            .padding(8.dp)
                    ) {
                        // game name
                        Text(
                            modifier = Modifier.padding(
                                vertical = 4.dp, horizontal = 8.dp
                            ),
                            text = gameDetails.name.toString(),
                            style = Typography.titleLarge
                        )
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                        ){
                            // release date
                            Row(
                                modifier = Modifier.padding(horizontal = 6.dp),
                                verticalAlignment = Alignment.Bottom,
                                horizontalArrangement = Arrangement.Start
                            ) {
                                Icon(
                                    modifier = Modifier.size(20.dp),
                                    imageVector = Icons.Rounded.Schedule,
                                    contentDescription = "Release Date Icon",
                                    tint = MaterialTheme.colorScheme.secondary
                                )
                                Text(
                                    modifier = Modifier.padding(horizontal = 4.dp),
                                    text = parseReleaseDate(gameDetails.released.toString()),
                                    style = Typography.labelSmall
                                )
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                Divider(
                    thickness = 1.dp, color = MaterialTheme.colorScheme.onBackground
                )
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    HorizontalPager(pageCount = gameDetails.screenshots.size,
                        state = pagerState,
                        key = { gameDetails.screenshots[it] }) { index ->
                        AsyncImage(
                            model = gameDetails.screenshots[index].image,
                            contentScale = ContentScale.Fit,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(4.dp)
                                .clip(
                                    RoundedCornerShape(8.dp)
                                )
                                .height(240.dp),
                            contentDescription = null
                        )
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = parse(gameDetails.description.toString()),
                        style = Typography.bodyMedium,
                        textAlign = TextAlign.Start,
                        modifier = Modifier
                            .height(400.dp)
                            .verticalScroll(scroll)
                            .padding(4.dp)
                    )
                }
            }
        }
    }
}


