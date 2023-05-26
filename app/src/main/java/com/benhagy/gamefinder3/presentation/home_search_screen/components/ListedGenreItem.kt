package com.benhagy.gamefinder3.presentation.home_search_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontVariation.width
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.benhagy.gamefinder3.domain.models.Genre
import com.benhagy.gamefinder3.presentation.ui.theme.Typography

@Composable
fun ListedGenreItem(
    genre: Genre,
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
        if (genre.name!!.length < 12) {
            Column(
                modifier = Modifier
                    .padding(4.dp)
                    .wrapContentHeight()
                    .wrapContentWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            )
            {
                AsyncImage(
                    model = genre.backgroundImage,
                    alignment = Alignment.TopCenter,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .height(60.dp)
                        .width(60.dp)
                        .clipToBounds()
                        .clip(CircleShape),
                    contentDescription = genre.name,
                )
                Divider(thickness = 4.dp, color = MaterialTheme.colorScheme.background)
                Text(
                    text = genre.name,
                    style = Typography.labelSmall,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onBackground,
                    maxLines = 3,
                    overflow = TextOverflow.Clip
                )
            }
        } else {
            Column(
                modifier = Modifier
                    .padding(4.dp)
                    .wrapContentHeight()
                    .width(86.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            )
            {
                AsyncImage(
                    model = genre.backgroundImage,
                    alignment = Alignment.TopCenter,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .height(60.dp)
                        .width(60.dp)
                        .clipToBounds()
                        .clip(CircleShape),
                    contentDescription = genre.name,
                )
                Divider(thickness = 4.dp, color = MaterialTheme.colorScheme.background)
                Text(
                    text = genre.name,
                    style = Typography.labelSmall,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onBackground,
                    maxLines = 3,
                    overflow = TextOverflow.Clip
                )
            }
        }
    }
}
