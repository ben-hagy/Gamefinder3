package com.benhagy.gamefinder3.presentation.game_details_screen

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.rounded.CalendarMonth
import androidx.compose.material.icons.rounded.Public
import androidx.compose.material.icons.rounded.Schedule
import androidx.compose.material.icons.rounded.StarRate
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.benhagy.gamefinder3.presentation.game_details_screen.components.DeveloperDetailsItem
import com.benhagy.gamefinder3.presentation.game_details_screen.components.GenreDetailsItem
import com.benhagy.gamefinder3.presentation.game_details_screen.components.PublisherDetailsItem
import com.benhagy.gamefinder3.presentation.game_details_screen.viewmodel.GameDetailsScreenEvent
import com.benhagy.gamefinder3.presentation.game_details_screen.viewmodel.GameDetailsScreenViewModel
import com.benhagy.gamefinder3.presentation.ui.theme.Typography
import com.benhagy.gamefinder3.presentation.ui.theme.montserratFonts
import com.benhagy.gamefinder3.util.parse
import com.benhagy.gamefinder3.util.parseEsrbAsLogo
import com.benhagy.gamefinder3.util.parseEsrbFluffText
import com.benhagy.gamefinder3.util.parsePlatformsList
import com.benhagy.gamefinder3.util.parseReleaseDate
import com.benhagy.gamefinder3.util.parseTagsList
import com.benhagy.gamefinder3.util.parseWebsiteAsHyperlink
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
@Destination
fun GameDetailsScreen(
    id: Int, // used as savedstatehandle receiver for navigation
    navigator: DestinationsNavigator,
    viewModel: GameDetailsScreenViewModel = hiltViewModel()
) {
    val state = viewModel.state
    val pagerState = rememberPagerState() // for image scroller
    val scroll = rememberScrollState(0) // for text scroller
    val overviewScroll = rememberScrollState() // for whole screen scrolling
    val coroutineScope = rememberCoroutineScope() // for async tasks
    val context = LocalContext.current // for the toasts, and the website intent

    state.gameDetails?.let { gameDetails ->

        // ensures the favorite icon is filled/unfilled appropriately
        LaunchedEffect(gameDetails.id) {
            gameDetails.id?.let { viewModel.isFavorite(gameId = it) }
        }

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
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(4.dp)
                ) {
                    // top app bar with back button and favorites icon buttons
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
                                        contentDescription = "Go back",
                                        tint = MaterialTheme.colorScheme.onBackground,
                                        modifier = Modifier.size(28.dp)
                                    )
                                }
                                IconButton(
                                    onClick = {
                                        coroutineScope.launch {
                                            if (state.isFavorite) {
                                                viewModel.onEvent(
                                                    GameDetailsScreenEvent.RemoveGameFromFavorites(
                                                        id = state.gameDetails.id!!
                                                    )
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
                                        imageVector = if (state.isFavorite) {
                                            Icons.Default.Bookmark
                                        } else {
                                            Icons.Default.BookmarkBorder
                                        },
                                        contentDescription = "Add to favorites",
                                        tint = MaterialTheme.colorScheme.onBackground,
                                        modifier = Modifier.size(28.dp)
                                    )
                                }
                            }

                        },
                        scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
                    )
                }
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(4.dp)
                        .verticalScroll(overviewScroll)
                ) {
                    // second row with game icon, game name, release date, average playtime, & metascore
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
                            ) {
                                // release date
                                Row(
                                    modifier = Modifier.padding(
                                        horizontal = 6.dp, vertical = 2.dp
                                    ),
                                    verticalAlignment = Alignment.Bottom,
                                    horizontalArrangement = Arrangement.Start
                                ) {
                                    Icon(
                                        modifier = Modifier.size(20.dp),
                                        imageVector = Icons.Rounded.CalendarMonth,
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
                            Spacer(Modifier.height(2.dp))
                            // playtime
                            Row(
                                modifier = Modifier.padding(horizontal = 6.dp),
                                verticalAlignment = Alignment.Bottom,
                                horizontalArrangement = Arrangement.Start
                            ) {
                                Icon(
                                    modifier = Modifier.size(20.dp),
                                    imageVector = Icons.Rounded.Schedule,
                                    contentDescription = "Played Time Icon",
                                    tint = MaterialTheme.colorScheme.secondary
                                )
                                Text(
                                    modifier = Modifier.padding(horizontal = 4.dp),
                                    text = "${gameDetails.playtime ?: 0} hours (average)",
                                    style = Typography.labelSmall
                                )
                            }
                            // metascore
                            Spacer(Modifier.height(2.dp))
                            Row(
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                verticalAlignment = Alignment.Bottom,
                                horizontalArrangement = Arrangement.Start
                            ) {
                                Icon(
                                    modifier = Modifier.size(20.dp),
                                    imageVector = Icons.Rounded.StarRate,
                                    contentDescription = "Metacritic Score Icon",
                                    tint = MaterialTheme.colorScheme.secondary
                                )
                                Text(
                                    modifier = Modifier.padding(horizontal = 4.dp),
                                    text = "Metacritic Score: ${gameDetails.metacritic ?: "N/A"}",
                                    style = Typography.labelSmall
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Divider(
                        thickness = 1.dp, color = MaterialTheme.colorScheme.onBackground
                    )
                    // screenshots pager
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
                                    .padding(bottom = 8.dp)
                                    .clip(
                                        RoundedCornerShape(8.dp)
                                    )
                                    .height(232.dp),
                                contentDescription = null
                            )
                        }
                    }
                    Box(
                        modifier = Modifier
                            .offset(y = -(24).dp)
                            .fillMaxWidth(0.5f)
                            .clip(RoundedCornerShape(100))
                            .background(Color.Transparent)
                            .padding(8.dp)
                            .align(Alignment.CenterHorizontally)
                    ) {
                        IconButton(
                            onClick = {
                                coroutineScope.launch {
                                    pagerState.animateScrollToPage(
                                        pagerState.currentPage - 1
                                    )
                                }
                            },
                            modifier = Modifier.align(Alignment.CenterStart)
                        ) {
                            Icon(
                                imageVector = Icons.Default.KeyboardArrowLeft,
                                contentDescription = "Go back",
                                modifier = Modifier.size(60.dp),
                                tint = MaterialTheme.colorScheme.onBackground
                            )
                        }
                        IconButton(
                            onClick = {
                                coroutineScope.launch {
                                    pagerState.animateScrollToPage(
                                        pagerState.currentPage + 1
                                    )
                                }
                            },
                            modifier = Modifier.align(Alignment.CenterEnd)
                        ) {
                            Icon(
                                imageVector = Icons.Default.KeyboardArrowRight,
                                contentDescription = "Go forward",
                                modifier = Modifier.size(60.dp),
                                tint = MaterialTheme.colorScheme.onBackground
                            )
                        }
                    }
                    Row(
                        modifier = Modifier
                            .padding(4.dp)
                            .fillMaxWidth()
                    ) {
                        // platforms text list
                        Column(
                            modifier = Modifier
                                .weight(1f)
                        ) {
                            Text(
                                text = "Platforms: ",
                                style = Typography.titleMedium
                            )
                        }
                        Column(
                            modifier = Modifier
                                .weight(2f)
                        ) {
                            if (gameDetails.platforms.isEmpty()) {
                                Text(
                                    text = "No platforms listed for this game.",
                                    style = Typography.labelSmall,
                                )
                            } else {
                                Text(
                                    text = parsePlatformsList(gameDetails.platforms),
                                    style = Typography.labelSmall,
                                    maxLines = 10,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        }
                    }
                    // esrb, platforms, and tags window
                    Row(
                        modifier = Modifier
                            .padding(vertical = 6.dp, horizontal = 4.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            modifier = Modifier
                                .weight(1f),
                            verticalArrangement = Arrangement.Center
                        ) {
                            // rating image and description
                            Column(
                                modifier = Modifier.wrapContentSize(),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.Start
                            ) {
                                Image(
                                    painter = painterResource(
                                        id = parseEsrbAsLogo(gameDetails.esrb?.name ?: "Unknown")
                                    ),
                                    contentDescription = "ESRB Logo",
                                    contentScale = ContentScale.Inside,
                                    alignment = Alignment.Center,
                                    modifier = Modifier
                                        .height(120.dp)
                                        .width(80.dp)
                                )
                                Text(
                                    text = parseEsrbFluffText(gameDetails.esrb?.name ?: "Unknown"),
                                    style = Typography.labelSmall,
                                    textAlign = TextAlign.Start,
                                    maxLines = 3
                                )
                            }
                        }
                        // tags
                        Column(
                            modifier = Modifier
                                .weight(2f)
                        ) {
                            Text(
                                text = "Tags:",
                                style = Typography.titleMedium
                            )
                            if (gameDetails.tags.isEmpty()) {
                                Text(
                                    text = "No tags found for this game.",
                                    style = Typography.labelSmall,
                                )
                            } else {
                                Text(
                                    text = parseTagsList(gameDetails.tags),
                                    style = Typography.labelSmall,
                                    maxLines = 10,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(8.dp))

                    }
                    if (gameDetails.genres.isNotEmpty()) {
                        Text(
                            modifier = Modifier
                                .padding(top = 2.dp, bottom = 4.dp),
                            text = "Genres:",
                            style = Typography.titleLarge,
                            color = MaterialTheme.colorScheme.secondary,
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
                    if (gameDetails.developers.isNotEmpty()) {
                        Text(
                            modifier = Modifier
                                .padding(top = 2.dp, bottom = 4.dp),
                            text = "Developers:",
                            style = Typography.titleLarge,
                            color = MaterialTheme.colorScheme.secondary,
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
                    if (gameDetails.publishers.isNotEmpty()) {
                        Text(
                            modifier = Modifier
                                .padding(top = 2.dp, bottom = 4.dp),
                            text = "Publishers:",
                            style = Typography.titleLarge,
                            color = MaterialTheme.colorScheme.secondary,
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
                    Text(
                        modifier = Modifier
                            .padding(top = 4.dp, bottom = 4.dp),
                        text = "Description:",
                        style = Typography.titleLarge,
                        color = MaterialTheme.colorScheme.secondary,
                        textAlign = TextAlign.Center
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Text(
                            text = parse(gameDetails.description.toString()),
                            style = Typography.bodyMedium,
                            textAlign = TextAlign.Justify,
                            modifier = Modifier
                                .height(350.dp)
                                .verticalScroll(scroll)
                                .padding(4.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentSize()
                            .padding(4.dp)

                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Public,
                            contentDescription = "Website",
                            tint = MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier.size(20.dp)
                        )
                        if (!gameDetails.website.isNullOrEmpty()) {
                            ClickableText(
                                modifier = Modifier
                                    .padding(horizontal = 4.dp),
                                text = parseWebsiteAsHyperlink(gameDetails.website),
                                style = TextStyle(
                                    fontFamily = montserratFonts,
                                    color = MaterialTheme.colorScheme.onBackground,
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 16.sp,
                                    lineHeight = 18.sp,
                                    letterSpacing = 0.2.sp
                                ),
                                softWrap = true,
                                onClick = {
                                    context.startActivity(
                                        Intent(
                                            Intent.ACTION_VIEW,
                                            Uri.parse(gameDetails.website)
                                        )
                                    )
                                }
                            )
                        } else {
                            Text(
                                text = "No website provided",
                                style = Typography.titleMedium,
                                modifier = Modifier.padding(horizontal = 4.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}


