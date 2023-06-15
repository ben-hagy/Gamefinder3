package com.benhagy.gamefinder3.data.remote.responses

import com.benhagy.gamefinder3.domain.models.Screenshot

// response object and mapper for Screenshots
// called by repo function, but joined into game details when inflated to the model

data class ScreenshotResponse(
    val id: Int,
    val image: String,
) {
    fun toScreenshot(): Screenshot {
        return Screenshot(id, image)
    }
}
