package com.benhagy.gamefinder3.presentation.favorites_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.benhagy.gamefinder3.data.local.entity.FavoriteGameEntity

@Composable
fun FavoriteGameListItem(
    game: FavoriteGameEntity,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
//        Column(
//            modifier = modifier
//                .clipToBounds()
//                .padding(4.dp),
//            verticalArrangement = Arrangement.Center,
//            horizontalAlignment = Alignment.CenterHorizontally
//
//        ) {
//            AsyncImage(
//                model = game.backgroundImage,
//                contentDescription = null,
//                modifier = modifier
//                    .height(60.dp)
//                    .width(60.dp)
//                    .clipToBounds()
//                    .clip(shape = RoundedCornerShape(50)),
//                contentScale = ContentScale.Crop,
//                alignment = Alignment.TopStart
//            )
//        }
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = game.name.toString(),
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onBackground,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = game.released.toString(),
            fontWeight = FontWeight.Light,
            color = MaterialTheme.colorScheme.onBackground
        )
        Icon(
            imageVector = Icons.Default.Delete,
            contentDescription = "Delete favorite item",
            modifier = modifier
                .padding(4.dp)
        )
    }
    Spacer(modifier = Modifier.height(8.dp))
}

