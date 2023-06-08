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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
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

    // instances to handle custom keyboard and focus interactions for the user note
    val keyboardController = LocalSoftwareKeyboardController.current
    val localFocusManager = LocalFocusManager.current

    Card(
        border = BorderStroke(0.5.dp, MaterialTheme.colorScheme.onBackground),
        shape = RoundedCornerShape(10),
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(unbounded = true)
            .padding(2.dp)
            .background(
                MaterialTheme.colorScheme.background
                    .copy(alpha = 0.1f)
            )
                // full card can be clicked to go to details screen for the clicked game
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
                        //game thumbnail image
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
                        // bookmarked game title
                        Text(
                            text = game.name.toString(),
                            style = Typography.labelLarge,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Spacer(modifier = Modifier.height(2.dp))

                        // date the game was added
                        // this is also how the list is sorted for display (newer dates go on list bottom)
                        Row {
                            Text(
                                text = stringResource(R.string.date_added_display_text),
                                style = Typography.titleSmall,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                            Text(
                                modifier = Modifier.padding(
                                    horizontal = 4.dp,
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
                                rating = userRating,
                                onRatingChange = {
                                    userRating = it
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
            }
        }

        // user note field w/delete button. saves note when focus changes or imeAction.onDone is triggered
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(4.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            OutlinedTextField(
                value = userNoteInput,
                onValueChange = {
                    userNoteInput = it
                },
                modifier = modifier
                    .shadow(elevation = 0.dp, shape = RoundedCornerShape(30))
                    .fillMaxWidth()
                    .padding(bottom = 4.dp)
                    .onFocusChanged {
                        viewModel.onEvent(
                            BookmarksScreenEvent.SaveUserNote(
                                userNoteInput, game.id!!
                            )
                        )
                    },
                textStyle = Typography.labelSmall,
                placeholder = {
                    Text(
                        text = stringResource(R.string.enter_your_note_hint)
                    )
                },
                trailingIcon = {
                    IconButton(onClick = {
                        userNoteInput = ""
                        viewModel.onEvent(
                            BookmarksScreenEvent.ClearUserNote(
                                userNoteInput, game.id!!
                            )
                        )
                    }) {
                        Icon(
                            Icons.Default.Clear,
                            contentDescription = stringResource(R.string.clear_note_cd)
                        )
                    }
                },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(
                    onDone = {
                        viewModel.onEvent(
                            BookmarksScreenEvent.SaveUserNote(
                                userNoteInput,
                                game.id!!
                            )
                        )
                        localFocusManager.clearFocus()
                        keyboardController?.hide()
                    }
                ),
                maxLines = 4
            )
        }
    }
}

