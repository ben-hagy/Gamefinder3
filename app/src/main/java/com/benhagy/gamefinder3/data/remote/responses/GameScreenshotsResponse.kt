package com.benhagy.gamefinder3.data.remote.responses

// screenshots responses come as a "results" list, which requires this response object

data class GameScreenshotsResponse(
    val results: List<ScreenshotResponse>
)

