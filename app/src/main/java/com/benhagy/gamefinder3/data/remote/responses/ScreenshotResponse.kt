package com.benhagy.gamefinder3.data.remote.responses

import com.benhagy.gamefinder3.domain.models.Screenshot

data class ScreenshotResponse(
    val id: Int,
    val image: String,
) {
    fun toScreenshot(): Screenshot {
        return Screenshot(id, image)
    }
}
