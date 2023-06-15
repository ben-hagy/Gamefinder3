package com.benhagy.gamefinder3.data.remote.responses

// listed games responses come as a "results" list, which requires this response object

data class GamesListResponse(
    val results: List<ListedGameResponse>
)
