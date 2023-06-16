package com.benhagy.gamefinder3.presentation.game_details_screen.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.benhagy.gamefinder3.R
import com.benhagy.gamefinder3.presentation.game_details_screen.viewmodel.GameDetailsScreenViewModel
import com.benhagy.gamefinder3.presentation.ui.theme.Typography
import com.benhagy.gamefinder3.util.parsePlatformsList

@Composable
fun PlatformsDetailsItem(
    viewModel: GameDetailsScreenViewModel = hiltViewModel()
) {
    val state = viewModel.state

    state.gameDetails?.let { gameDetails ->

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
                    text = stringResource(R.string.platforms_title),
                    style = Typography.titleMedium
                )
            }
            Column(
                modifier = Modifier
                    .weight(2f)
            ) {
                if (gameDetails.platforms.isEmpty()) {
                    Text(
                        text = stringResource(R.string.null_platforms_response),
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
    }
}