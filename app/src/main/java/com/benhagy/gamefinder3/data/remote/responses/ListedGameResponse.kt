package com.benhagy.gamefinder3.data.remote.responses

import com.benhagy.gamefinder3.domain.models.ListedGame
import com.google.gson.annotations.SerializedName

data class ListedGameResponse(
    val id: Int,
    val name: String,
    val released: String?,
    @SerializedName("background_image")
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