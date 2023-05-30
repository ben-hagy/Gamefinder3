package com.benhagy.gamefinder3.presentation.bookmarks_screen.components

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.rounded.CalendarViewMonth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.benhagy.gamefinder3.data.local.entity.BookmarkedGameEntity
import com.benhagy.gamefinder3.presentation.bookmarks_screen.viewmodel.BookmarksScreenEvent
import com.benhagy.gamefinder3.presentation.bookmarks_screen.viewmodel.BookmarksScreenViewModel
import com.benhagy.gamefinder3.presentation.destinations.GameDetailsScreenDestination
import com.benhagy.gamefinder3.presentation.ui.theme.Typography
import com.benhagy.gamefinder3.util.parseReleaseDate
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookmarkedGamesListItem(
    game: BookmarkedGameEntity,
    modifier: Modifier = Modifier,
    viewModel: BookmarksScreenViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
) {
    val context = LocalContext.current
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .clip(
                shape = RoundedCornerShape(8.dp)
            )
            .background(
                MaterialTheme.colorScheme.secondaryContainer
                    .copy(alpha = 0.1f)
            )
            .clickable {
                navigator.navigate(GameDetailsScreenDestination(game.id!!))
            }
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
        ) {

            AsyncImage(
                model = game.backgroundImage ?: "",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(horizontal = 4.dp, vertical = 4.dp)
                    .height(80.dp)
                    .width(80.dp)
                    .clip(shape = RoundedCornerShape(8.dp))
                    .clipToBounds(),
                contentDescription = game.backgroundImage ?: "",
            )

            Column(
                modifier = Modifier
                    .height(100.dp)
                    .weight(3f),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    modifier = Modifier.padding(
                        horizontal = 8.dp,
                        vertical = 2.dp
                    ),
                    text = game.name.toString(),
                    style = Typography.titleMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    modifier = Modifier.padding(
                        horizontal = 8.dp,
                        vertical = 2.dp
                    ),
                    text = game.developer.toString(),
                    style = Typography.titleMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    modifier = Modifier.padding(
                        horizontal = 8.dp,
                        vertical = 2.dp
                    ),
                    text = game.publisher.toString(),
                    style = Typography.titleMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    modifier = Modifier.padding(
                        horizontal = 8.dp,
                        vertical = 2.dp
                    ),
                    text = "Date added: ${game.dateAdded.toString()}",
                    style = Typography.titleMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Icon(
                        modifier = modifier.size(20.dp),
                        imageVector = Icons.Rounded.CalendarViewMonth,
                        contentDescription = "Release Date Icon",
                        tint = MaterialTheme.colorScheme.secondary
                    )
                    Text(
                        text = parseReleaseDate(game.released.toString()),
                        style = Typography.labelSmall
                    )
                }

                TextField(
                    value = game.userNote,
                    onValueChange = {
                        viewModel.onEvent(
                            BookmarksScreenEvent.EditUserNote(it),
                        )
                    },
                    textStyle = Typography.titleMedium
                )

            }
            Column(
                modifier = Modifier
                    .height(100.dp)
                    .weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete favorite item",
                    tint = MaterialTheme.colorScheme.onBackground,
                    modifier = modifier
                        .size(32.dp)
                        .padding(4.dp)
                        .clickable {
                            viewModel.onEvent(
                                BookmarksScreenEvent.RemoveSelectedBookmark(game.id!!)
                            )
                            Toast
                                .makeText(
                                    context,
                                    "${game.name} removed from Favorites.",
                                    Toast.LENGTH_SHORT
                                )
                                .show()
                        }
                )
            }
        }
    }
}


