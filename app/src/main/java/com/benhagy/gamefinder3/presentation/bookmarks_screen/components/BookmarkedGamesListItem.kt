package com.benhagy.gamefinder3.presentation.bookmarks_screen.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.benhagy.gamefinder3.data.local.entity.BookmarkedGameEntity
import com.benhagy.gamefinder3.presentation.bookmarks_screen.viewmodel.BookmarksScreenEvent
import com.benhagy.gamefinder3.presentation.bookmarks_screen.viewmodel.BookmarksScreenViewModel
import com.benhagy.gamefinder3.presentation.destinations.GameDetailsScreenDestination
import com.benhagy.gamefinder3.presentation.ui.theme.Typography
import com.benhagy.gamefinder3.util.parseDate
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
fun BookmarkedGamesListItem(
    game: BookmarkedGameEntity,
    modifier: Modifier = Modifier,
    viewModel: BookmarksScreenViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
) {
    Card(
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.onBackground),
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(unbounded = true)
            .padding(4.dp)
            .background(
                MaterialTheme.colorScheme.background
                    .copy(alpha = 0.1f)
            )
            .clickable {
                navigator.navigate(GameDetailsScreenDestination(game.id!!))
            }
    ) {
        Column {
            Column(
                modifier = Modifier
                    .wrapContentHeight(unbounded = true)
                    .padding(4.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                ) {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(4.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        //image
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
                    }
                    Spacer(modifier = Modifier.width(4.dp))
                    Column(
                        modifier = Modifier
                            .weight(3f)
                            .padding(vertical = 2.dp),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        // title
                        Text(
                            text = game.name.toString(),
                            style = Typography.labelLarge,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                            color = MaterialTheme.colorScheme.onBackground
                        )

                        // date you added
                        Row {
                            Text(
                                text = "Date added: ",
                                style = Typography.titleSmall,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                            Text(
                                modifier = Modifier.padding(
                                    horizontal = 8.dp,
                                    vertical = 2.dp
                                ),
                                text = parseDate(game.dateAdded),
                                style = Typography.labelSmall,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            // user note
                            TransparentHintTextField(
                                text = game.userNote,
                                onValueChange = { note ->
                                    viewModel.onEvent(
                                        BookmarksScreenEvent.EditUserNote(
                                            note = note,
                                            id = game.id!!
                                        )
                                    )
                                }
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Row {

                            RatingBar(
                                modifier = Modifier
                                    .padding(vertical = 2.dp, horizontal = 4.dp),
                                rating = game.userRating?.toDouble() ?: 0.0,
                                stars = 5,
                                starsColor = MaterialTheme.colorScheme.onBackground
                            )
                        }

                    }
                }
            }
        }
    }
}

// developer texts
//Row {
//    Text(
//        text = "Developer: ",
//        style = Typography.titleSmall,
//        color = MaterialTheme.colorScheme.onBackground
//    )
//    Text(
//        modifier = Modifier.padding(
//            horizontal = 8.dp,
//            vertical = 2.dp
//        ),
//        text = game.developer.toString(),
//        style = Typography.labelSmall,
//        color = MaterialTheme.colorScheme.onBackground
//    )
//}
//Row {
//    // publisher texts
//    Text(
//        text = "Publisher: ",
//        style = Typography.titleSmall,
//        color = MaterialTheme.colorScheme.onBackground
//    )
//    Text(
//        modifier = Modifier.padding(
//            horizontal = 8.dp,
//            vertical = 2.dp
//        ),
//        text = game.publisher.toString(),
//        style = Typography.labelSmall,
//        color = MaterialTheme.colorScheme.onBackground
//    )
//}


