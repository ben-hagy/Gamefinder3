package com.benhagy.gamefinder3.presentation.game_details_screen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.benhagy.gamefinder3.R
import com.benhagy.gamefinder3.presentation.game_details_screen.viewmodel.GameDetailsScreenViewModel
import com.benhagy.gamefinder3.presentation.ui.theme.Typography
import com.benhagy.gamefinder3.util.parseEsrbAsLogo
import com.benhagy.gamefinder3.util.parseEsrbFluffText
import com.benhagy.gamefinder3.util.parseTagsList

@Composable
fun EsrbAndTagsWindow(
    viewModel: GameDetailsScreenViewModel = hiltViewModel()
) {
    val state = viewModel.state

    state.gameDetails?.let { gameDetails ->

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
                            id = parseEsrbAsLogo(
                                gameDetails.esrb?.name ?: stringResource(
                                    R.string.unknown_text
                                )
                            )
                        ),
                        contentDescription = stringResource(R.string.esrb_logo_cd),
                        contentScale = ContentScale.Fit,
                        alignment = Alignment.Center,
                        modifier = Modifier
                            .height(120.dp)
                            .width(80.dp)
                            .padding(bottom = 2.dp)
                    )
                    Text(
                        text = parseEsrbFluffText(
                            gameDetails.esrb?.name ?: stringResource(
                                R.string.unknown_text
                            )
                        ),
                        style = Typography.labelSmall,
                        textAlign = TextAlign.Start,
                        maxLines = 3
                    )
                }
            }
            Column(
                modifier = Modifier
                    .weight(2f)
            ) {
                Text(
                    text = stringResource(R.string.tags_title),
                    style = Typography.titleMedium
                )
                if (gameDetails.tags.isEmpty()) {
                    Text(
                        text = stringResource(R.string.null_tags_response),
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
        }
    }
}