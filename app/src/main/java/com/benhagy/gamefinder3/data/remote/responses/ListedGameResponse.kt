package com.benhagy.gamefinder3.data.remote.responses

import com.benhagy.gamefinder3.domain.models.ListedGame

data class ListedGameResponse(
    val id: Int,
    val name: String,
    val released: String?,
    val backgroundImage: String?,
//    val metacritic: Int?,
//    val platforms: List<PlatformResponse>,
//    val genres: List<GenreResponse>,
//    val tags: List<TagResponse>,
//    val esrbRating: EsrbResponse?,

) {
    fun toListedGame(): ListedGame {
        return ListedGame(
            id, name, released, backgroundImage
        )
    }
}