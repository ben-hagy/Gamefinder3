package com.benhagy.gamefinder3.data.remote.responses

import com.benhagy.gamefinder3.domain.models.ListedGame
import com.google.gson.annotations.SerializedName

// response object and mapper for listed game objects
// called by repo functions when the api call occurs

data class ListedGameResponse(
    val id: Int,
    val name: String,
    @SerializedName("background_image")
    val backgroundImage: String?,
    val platforms: List<PlatformResponse>?,

) {
    fun toListedGame(): ListedGame {
        return ListedGame(
            id = id,
            name = name,
            backgroundImage = backgroundImage,
            platforms = platforms?.map { it.platform.toPlatform() },
        )
    }
}