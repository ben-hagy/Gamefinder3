package com.benhagy.gamefinder3.presentation.home_search_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.benhagy.gamefinder3.domain.models.ListedGame
import com.benhagy.gamefinder3.presentation.ui.theme.montserratFonts

@Composable
fun ListedGameItem(
    game: ListedGame,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.onBackground.copy(
                alpha = 0.1f)
        )
    ) {
        Text(
            text = game.name,
            fontFamily = montserratFonts,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onBackground,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
            maxLines = 2
        )
        Spacer(modifier = Modifier.size(4.dp))
        AsyncImage(
            modifier = Modifier
                .wrapContentSize(),
            model = game.backgroundImage,
            alignment = Alignment.Center,
            contentDescription = null,
            contentScale = ContentScale.Inside
        )
        Spacer(modifier = Modifier.size(4.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {

        }
    }
}



//Row(
//modifier = modifier,
//verticalAlignment = Alignment.CenterVertically
//) {
//    Column(
//        modifier = Modifier.weight(1f)
//    ) {
//        Row(
//            modifier = Modifier.fillMaxWidth()
//        ) {
//            Text(
//                text = game.name,
//                fontFamily = montserratFonts,
//                fontWeight = FontWeight.SemiBold,
//                fontSize = 16.sp,
//                color = MaterialTheme.colorScheme.onBackground,
//                overflow = TextOverflow.Ellipsis,
//                maxLines = 1,
//                modifier = Modifier.weight(1f)
//            )
//            Spacer(modifier = Modifier.width(4.dp))
//            Text(
//                text = game.released.toString(),
//                fontFamily = montserratFonts,
//                fontWeight = FontWeight.Normal,
//                color = MaterialTheme.colorScheme.onBackground
//            )
//        }
//        Spacer(modifier = Modifier.height(8.dp))
//        Text(
//            text = "(${game.id})",
//            fontFamily = montserratFonts,
//            fontStyle = FontStyle.Italic,
//            color = MaterialTheme.colorScheme.onBackground
//        )
//    }
//}