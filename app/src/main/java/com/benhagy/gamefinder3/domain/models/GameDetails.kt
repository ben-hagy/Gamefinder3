package com.benhagy.gamefinder3.domain.models

import android.os.Build
import androidx.annotation.RequiresApi
import com.benhagy.gamefinder3.data.local.entity.BookmarkedGameEntity
import java.time.LocalDate
import java.util.Date

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
    @RequiresApi(Build.VERSION_CODES.O)
    fun toBookmarkedGameEntity(): BookmarkedGameEntity {
        return BookmarkedGameEntity(
            id = id,
            name = name,
            backgroundImage = backgroundImage,
            released = released,
            developer = developers[0].toString(),
            publisher = publishers[0].toString(),
            dateAdded = Date().time.toString(),
            userNote = "",
            userRating = null
        )
    }
}

