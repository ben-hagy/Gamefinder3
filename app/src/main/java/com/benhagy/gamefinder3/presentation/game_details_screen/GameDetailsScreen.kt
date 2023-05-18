package com.benhagy.gamefinder3.presentation.game_details_screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.benhagy.gamefinder3.R
import com.benhagy.gamefinder3.presentation.game_details_screen.viewmodel.GameDetailsScreenEvent
import com.benhagy.gamefinder3.presentation.game_details_screen.viewmodel.GameDetailsScreenViewModel
import com.benhagy.gamefinder3.presentation.ui.theme.montserratFonts
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.benhagy.gamefinder3.util.parse
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
@Destination
fun GameDetailsScreen(
    id: Int,
    navigator: DestinationsNavigator,
    viewModel: GameDetailsScreenViewModel = hiltViewModel()
) {
    val state = viewModel.state
    val pagerState = rememberPagerState()
    val scroll = rememberScrollState(0)
    val coroutineScope = rememberCoroutineScope()
    state.gameDetails?.let { gameDetails ->
        if (state.error == null) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
            ) {
                Row(
                    modifier = Modifier
                        .background(color = MaterialTheme.colorScheme.background)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
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
                    IconButton(
                        onClick = {
                            coroutineScope.launch {
                                if(state.isFavorite) {
                                    viewModel.onEvent(
                                        GameDetailsScreenEvent.RemoveGameFromFavorites(id = state.gameDetails.id!!)
                                    // snackbar with undo button?
                                    )
                                } else {
                                    viewModel.onEvent(
                                        GameDetailsScreenEvent.SaveGameAsFavorite(game = state.gameDetails)
                                    )
                                }
                            }
                        },
                        modifier = Modifier
                            .padding(4.dp)
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
                Row {
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
                    Column {
                        Text(
                            modifier = Modifier.padding(
                                vertical = 4.dp,
                                horizontal = 4.dp
                            ),
                            text = gameDetails.name.toString(),
                            fontFamily = montserratFonts,
                            fontSize = 28.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                        Row(
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                        ) {
                            Row {
                                Icon(
                                    modifier = Modifier.size(16.dp),
                                    imageVector = Icons.Rounded.Schedule,
                                    contentDescription = "Released",
                                )
                                Text(
                                    modifier = Modifier.padding(
                                        horizontal = 4.dp
                                    ),
                                    text = gameDetails.released ?: "Unknown",
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        color = MaterialTheme.colorScheme.onTertiaryContainer,
                                        fontWeight = FontWeight.Normal
                                    ),
                                )
                            }

                            Spacer(Modifier.width(32.dp))

                        }
                    }

                }
                Spacer(modifier = Modifier.height(2.dp))
                Divider(
                    thickness = 1.dp,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    HorizontalPager(
                        pageCount = gameDetails.screenshots.size,
                        state = pagerState,
                        key = { gameDetails.screenshots[it] }
                    ) { index ->
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
                        fontFamily = montserratFonts,
                        fontWeight = FontWeight.Medium,
                        fontSize = 13.sp,
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

