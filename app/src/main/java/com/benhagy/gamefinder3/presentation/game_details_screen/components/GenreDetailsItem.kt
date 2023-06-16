package com.benhagy.gamefinder3.presentation.game_details_screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
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
import coil.compose.AsyncImage
import com.benhagy.gamefinder3.domain.models.Genre
import com.benhagy.gamefinder3.presentation.common_components.DefaultSpacer
import com.benhagy.gamefinder3.presentation.ui.theme.Typography

@Composable
fun GenreDetailsItem(
    genre: Genre,
    modifier: Modifier // is used when object is called on GameDetailsScreen
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(2.dp)
            .background(
                MaterialTheme.colorScheme.background
            )
    ) {
        Row(
            modifier = Modifier
                .wrapContentHeight(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = genre.backgroundImage ?: "",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(horizontal = 4.dp, vertical = 4.dp)
                    .height(60.dp)
                    .width(60.dp)
                    .clipToBounds()
                    .clip(RoundedCornerShape(10)),
                contentDescription = genre.backgroundImage ?: "",
            )
            DefaultSpacer()
            Row(
                modifier = Modifier
                    .height(60.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.padding(
                        horizontal = 8.dp,
                        vertical = 2.dp
                    ),
                    text = genre.name.toString(),
                    style = Typography.titleSmall,
                    maxLines = 2,
                    overflow = TextOverflow.Clip,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }
    }
}


