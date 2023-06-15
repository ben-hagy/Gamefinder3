package com.benhagy.gamefinder3.data.remote.responses

import com.benhagy.gamefinder3.domain.models.Genre
import com.google.gson.annotations.SerializedName

// response object and mapper for Genres
// called by repo functions when the api call occurs

data class GenreResponse(
    val id: Int?,
    val name: String?,
    @SerializedName("image_background") val backgroundImage: String?
) {
    fun toGenre(): Genre {
        return Genre(
            id = id,
            name = name,
            backgroundImage = backgroundImage
        )
    }
}