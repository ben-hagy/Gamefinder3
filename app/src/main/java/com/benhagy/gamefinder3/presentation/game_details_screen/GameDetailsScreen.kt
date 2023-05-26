package com.benhagy.gamefinder3.presentation.game_details_screen

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CalendarMonth
import androidx.compose.material.icons.rounded.Public
import androidx.compose.material.icons.rounded.Schedule
import androidx.compose.material.icons.rounded.StarRate
import androidx.compose.material.icons.rounded.Web
import androidx.compose.material.icons.rounded.WebAsset
import androidx.compose.material.icons.rounded.Webhook
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.benhagy.gamefinder3.R
import com.benhagy.gamefinder3.presentation.game_details_screen.viewmodel.GameDetailsScreenEvent
import com.benhagy.gamefinder3.presentation.game_details_screen.viewmodel.GameDetailsScreenViewModel
import com.benhagy.gamefinder3.presentation.home_search_screen.ListedGenreItem
import com.benhagy.gamefinder3.presentation.ui.theme.Typography
import com.benhagy.gamefinder3.util.parse
import com.benhagy.gamefinder3.util.parseEsrbAsLogo
import com.benhagy.gamefinder3.util.parseEsrbFluffText
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
    id: Int,
    navigator: DestinationsNavigator,
    viewModel: GameDetailsScreenViewModel = hiltViewModel()
) {
    // variables for states and local context
    val state = viewModel.state
    val pagerState = rememberPagerState() // for image scroller
    val scroll = rememberScrollState(0) // for text scroller
    val overviewScroll = rememberScrollState()
    val coroutineScope = rememberCoroutineScope() // for async tasks
    val context = LocalContext.current // for the toasts, and the website intent

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
                Column(
                    modifier = Modifier
                        .padding(4.dp)
                ) {
                    // top app bar with back button and favorites icon buttons
                    TopAppBar(
                        title = {},
                        modifier = Modifier
                            .background(color = MaterialTheme.colorScheme.background)
                            .fillMaxWidth(),
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
                                        painter = painterResource(id = R.drawable.ic_back_arrow),
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
                                modifier = Modifier.padding(horizontal = 6.dp),
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
                                    .padding(4.dp)
                                    .clip(
                                        RoundedCornerShape(8.dp)
                                    )
                                    .height(240.dp),
                                contentDescription = null
                            )
                        }
                    }
                    // ratings and tags window
                    Row(
                        modifier = Modifier
                            .padding(4.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            modifier = Modifier
                                .weight(1f)
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
                            color = MaterialTheme.colorScheme.secondary
                        )
                        LazyRow(
                            modifier = Modifier
                        ) {
                            items(gameDetails.genres.size) { i ->
                                val genre = gameDetails.genres[i]
                                ListedGenreItem(
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
                            color = MaterialTheme.colorScheme.secondary
                        )
                        LazyRow(
                            modifier = Modifier
                        ) {
                            items(gameDetails.developers.size) { i ->
                                val developer = gameDetails.developers[i]
                                ListedGenreItem(
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
                            color = MaterialTheme.colorScheme.secondary
                        )
                        LazyRow(
                            modifier = Modifier
                        ) {
                            items(gameDetails.publishers.size) { i ->
                                val publisher = gameDetails.publishers[i]
                                ListedGenreItem(
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
                            .padding(top = 2.dp, bottom = 4.dp),
                        text = "Description:",
                        style = Typography.titleLarge,
                        color = MaterialTheme.colorScheme.secondary
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
                                style = Typography.titleMedium,
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


