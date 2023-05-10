package com.benhagy.gamefinder3.data.remote.responses

import com.benhagy.gamefinder3.domain.models.Publisher
import com.google.gson.annotations.SerializedName

data class PublisherResponse(
    val id: Int?,
    val name: String,
    @SerializedName("image_background") val backgroundImage: String?,

) {
    fun toPublisher(): Publisher {
        return Publisher(
            id = id,
            name = name,
            backgroundImage = backgroundImage
        )
    }
}
