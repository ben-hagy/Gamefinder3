package com.benhagy.gamefinder3.presentation.game_details_screen.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.benhagy.gamefinder3.presentation.game_details_screen.viewmodel.GameDetailsScreenViewModel
import com.benhagy.gamefinder3.presentation.ui.theme.Typography
import com.benhagy.gamefinder3.util.parse

@Composable
fun DescriptionDetailsItem(
    viewModel: GameDetailsScreenViewModel = hiltViewModel()
) {
    val state = viewModel.state

    val scroll = rememberScrollState(0) // for text scrolling

    state.gameDetails?.let { gameDetails ->

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
    }
}