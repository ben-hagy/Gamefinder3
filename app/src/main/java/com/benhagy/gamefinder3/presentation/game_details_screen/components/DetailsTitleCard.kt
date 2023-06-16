package com.benhagy.gamefinder3.presentation.game_details_screen.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CalendarMonth
import androidx.compose.material.icons.rounded.Schedule
import androidx.compose.material.icons.rounded.StarRate
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.benhagy.gamefinder3.R
import com.benhagy.gamefinder3.presentation.common_components.ThinSpacer
import com.benhagy.gamefinder3.presentation.game_details_screen.viewmodel.GameDetailsScreenViewModel
import com.benhagy.gamefinder3.presentation.ui.theme.Typography
import com.benhagy.gamefinder3.util.parseReleaseDate

@Composable
fun DetailsTitleCard(
    viewModel: GameDetailsScreenViewModel = hiltViewModel()
) {
    val state = viewModel.state

    state.gameDetails?.let { gameDetails ->

        // circular header image
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
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.secondary
                        )
                        Text(
                            modifier = Modifier.padding(horizontal = 4.dp),
                            text = stringResource(R.string.released_header) +
                                    parseReleaseDate(gameDetails.released.toString()),
                            style = Typography.labelSmall
                        )
                    }
                }
                ThinSpacer()

                // playtime
                Row(
                    modifier = Modifier.padding(horizontal = 6.dp),
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Icon(
                        modifier = Modifier.size(20.dp),
                        imageVector = Icons.Rounded.Schedule,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.secondary
                    )
                    Text(
                        modifier = Modifier.padding(horizontal = 4.dp),
                        text = stringResource(R.string.average_playtime_header) +
                                "${gameDetails.playtime ?: 0}h",
                        style = Typography.labelSmall
                    )
                }
                ThinSpacer()

                // metascore
                Row(
                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Icon(
                        modifier = Modifier.size(20.dp),
                        imageVector = Icons.Rounded.StarRate,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.secondary
                    )
                    Text(
                        modifier = Modifier.padding(horizontal = 4.dp),
                        text = stringResource(R.string.metascore_header) +
                                "${gameDetails.metacritic ?: "N/A"}",
                        style = Typography.labelSmall
                    )
                }
            }
        }
    }
}