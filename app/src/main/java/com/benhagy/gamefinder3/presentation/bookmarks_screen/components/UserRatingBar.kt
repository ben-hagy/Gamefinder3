package com.benhagy.gamefinder3.presentation.bookmarks_screen.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarOutline
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.smarttoolfactory.ratingbar.RatingBar
import com.smarttoolfactory.ratingbar.model.Shimmer

@Composable
fun UserRatingBar(
    rating: Float?,
    onRatingChange: ((Float) -> Unit)?
) {
    RatingBar(
        rating = rating ?: 0.0f,
        space = 2.5.dp,
        imageVectorEmpty = Icons.Default.StarOutline,
        imageVectorFFilled = Icons.Default.Star,
        shimmer = Shimmer(
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.85f),
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = 2500,
                    easing = LinearEasing
                ),
                repeatMode = RepeatMode.Reverse
            )
        ),
        tintEmpty = MaterialTheme.colorScheme.onBackground.copy(alpha = .5f),
        itemSize = 40.dp,
        onRatingChange = onRatingChange
    )
}