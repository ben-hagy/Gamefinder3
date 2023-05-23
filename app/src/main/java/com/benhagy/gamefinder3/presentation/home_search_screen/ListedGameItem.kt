package com.benhagy.gamefinder3.presentation.home_search_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.benhagy.gamefinder3.domain.models.ListedGame
import com.benhagy.gamefinder3.presentation.ui.theme.Typography

@Composable
fun ListedGameItem(
    game: ListedGame,
    modifier: Modifier = Modifier,
    isRefreshing: Boolean
) {
    val cardWidth = LocalConfiguration.current.screenWidthDp.dp / 1.7f
    Card(
        modifier = modifier
            .padding(horizontal = 4.dp)
            .width(cardWidth),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.inverseSurface.copy(
                alpha = 0.1f
            )
        )
    ) {
        AsyncImage(
            model = game.backgroundImage ?: "",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
                .clip(
                    RoundedCornerShape(8.dp)
                )
                .height(120.dp),
            contentDescription = game.name
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(90.dp),
        ) {
            PlatformsList(
                platforms = game.platforms ?: emptyList(),
                isRefreshing
            )
            Text(
                text = game.name,
                style = Typography.titleMedium,
                modifier = modifier.padding(4.dp),
                textAlign = TextAlign.Center
            )
        }
    }
}