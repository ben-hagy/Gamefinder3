package com.benhagy.gamefinder3.domain.models

import com.benhagy.gamefinder3.data.local.entity.BookmarkedGameEntity

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
            userNote = "",
            userRating = null
        )
    }
}

