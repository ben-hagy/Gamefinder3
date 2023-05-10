package com.benhagy.gamefinder3.data.remote.responses

import com.benhagy.gamefinder3.domain.models.ShortScreenshot


data class ShortScreenshotResponse(
    val id: Int?,
    val image: String?
) {
    fun toShortScreenshot(): ShortScreenshot {
        return ShortScreenshot(id = id, image = image)
    }
}