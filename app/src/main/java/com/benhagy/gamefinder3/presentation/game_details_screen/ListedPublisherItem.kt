package com.benhagy.gamefinder3.presentation.game_details_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.benhagy.gamefinder3.domain.models.Publisher
import com.benhagy.gamefinder3.presentation.ui.theme.Typography

@Composable
fun ListedGenreItem(
    publisher: Publisher,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .padding(horizontal = 1.dp, vertical = 1.dp)
            .clip(RoundedCornerShape(4.dp)),
        colors = CardDefaults.cardColors(
            MaterialTheme.colorScheme.secondaryContainer.copy(
                alpha = 0.1f
            )
        )
    ) {
        Column(
            modifier = modifier
                .padding(4.dp)
                .clipToBounds(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = publisher.backgroundImage,
                alignment = Alignment.TopCenter,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(60.dp)
                    .width(60.dp)
                    .clipToBounds()
                    .clip(CircleShape),
                contentDescription = publisher.name,
            )
            Divider(thickness = 4.dp, color = MaterialTheme.colorScheme.onBackground)
            Text(
                text = publisher.name!!,
                style = Typography.labelSmall,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onBackground,
                maxLines = 2,
                overflow = TextOverflow.Clip
            )
        }
    }
}