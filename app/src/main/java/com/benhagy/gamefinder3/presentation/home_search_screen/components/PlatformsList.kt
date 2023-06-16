package com.benhagy.gamefinder3.presentation.home_search_screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.benhagy.gamefinder3.domain.models.Platform
import com.benhagy.gamefinder3.presentation.ui.theme.Typography
import com.benhagy.gamefinder3.presentation.ui.theme.montserratFonts

@Composable
fun PlatformsList(
    platforms: List<Platform>,
) {
    val listState = rememberLazyListState()

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp, vertical = 6.dp),
        state = listState
    ) {
        items(platforms.size) { i ->
            val platform = platforms[i].name.toString()
            Card(
                shape = RoundedCornerShape(15),
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .background(
                        MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.7f)
                    )
            ) {
                Text(
                    modifier = Modifier
                        .padding(horizontal = 2.dp, vertical = 2.dp),
                    text = platform,
                    fontFamily = montserratFonts,
                    style = Typography.displaySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
    }

}