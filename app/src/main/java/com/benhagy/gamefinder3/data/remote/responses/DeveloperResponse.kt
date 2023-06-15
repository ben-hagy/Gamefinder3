package com.benhagy.gamefinder3.data.remote.responses

import com.benhagy.gamefinder3.domain.models.Developer
import com.google.gson.annotations.SerializedName

// response object and mapper for Developers

data class DeveloperResponse(
    val id: Int?,
    val name: String,
    @SerializedName("image_background") val backgroundImage: String?,

) {
    fun toDeveloper(): Developer {
        return Developer(
            id = id,
            name = name,
            backgroundImage = backgroundImage
        )
    }
}
