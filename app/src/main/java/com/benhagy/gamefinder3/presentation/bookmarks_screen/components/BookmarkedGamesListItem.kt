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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.benhagy.gamefinder3.R
import com.benhagy.gamefinder3.data.local.entity.BookmarkedGameEntity
import com.benhagy.gamefinder3.presentation.bookmarks_screen.viewmodel.BookmarksScreenEvent
import com.benhagy.gamefinder3.presentation.bookmarks_screen.viewmodel.BookmarksScreenViewModel
import com.benhagy.gamefinder3.presentation.destinations.GameDetailsScreenDestination
import com.benhagy.gamefinder3.presentation.ui.theme.Typography
import com.benhagy.gamefinder3.util.parseDate
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun BookmarkedGamesListItem(
    game: BookmarkedGameEntity,
    modifier: Modifier = Modifier,
    viewModel: BookmarksScreenViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
) {

    // state value that stores the user note until it's submitted to be saved in the db
    var userNoteInput by remember {
        mutableStateOf(game.userNote)
    }

    // state value to store the user rating until it's submitted to be saved in the db
    var userRating by remember {
        mutableStateOf(game.userRating)
    }

    // keyboard controller instance for handling onDone keyboard actions (submitting user note)
    val keyboardController = LocalSoftwareKeyboardController.current

    Card(
        border = BorderStroke(0.5.dp, MaterialTheme.colorScheme.onBackground),
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(unbounded = true)
            .padding(2.dp)
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
                    .padding(2.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                ) {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(2.dp),
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
                        Spacer(modifier = Modifier.height(2.dp))

                        // date you added
                        Row {
                            Text(
                                text = stringResource(R.string.date_added_display_text),
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
                        Spacer(modifier = Modifier.height(2.dp))
                        Row {
                            UserRatingBar(
                                rating = userRating?.toFloat(),
                                onRatingChange = {
                                    userRating = it.toDouble()
                                    viewModel.onEvent(
                                        BookmarksScreenEvent.EditUserRating(
                                            userRating!!, game.id!!
                                        )
                                    )
                                }
                            )
                        }
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp)
                ) {
                    UserNoteTextField(
                        modifier = Modifier
                            .shadow(elevation = 0.dp, shape = RoundedCornerShape(15)),
                        text = userNoteInput,
                        onValueChange = { userNoteInput = it },
                        keyboardActions = KeyboardActions(
                            onDone = {
                                viewModel.onEvent(
                                    BookmarksScreenEvent.EditUserNote(
                                        userNoteInput, game.id!!
                                    )
                                )
                                keyboardController?.hide()
                            }
                        ),
                        placeholderText = stringResource(id = R.string.enter_your_note_hint),
                        trailingIcon = null
                    )
                }
            }
        }
    }
}