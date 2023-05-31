package com.benhagy.gamefinder3.domain.models

import android.os.Build
import androidx.annotation.RequiresApi
import com.benhagy.gamefinder3.data.local.entity.BookmarkedGameEntity
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

data class GameDetails(
    val id: Int?,
    val name: String?,
    val description: String?,
    val metacritic: Int?,
    val released: String?,
    val backgroundImage: String?,
    val website: String?,
    val playtime: Int?,
    val platforms: List<Platform>,
    val developers: List<Developer>,
    val publishers: List<Publisher>,
    val genres: List<Genre>,
    val tags: List<Tag>,
    val esrb: Esrb?,
    val screenshots: List<Screenshot> = emptyList()
) {
    fun toBookmarkedGameEntity(): BookmarkedGameEntity {
        return BookmarkedGameEntity(
            id = id,
            name = name,
            backgroundImage = backgroundImage,
            released = released,
            developer = developers[0].name.toString(),
            publisher = publishers[0].name.toString(),
            dateAdded = DateTimeFormatter.ofPattern("MM dd yyyy", Locale.getDefault()).toString(),
            userNote = "",
            userRating = 3
        )
    }
}

