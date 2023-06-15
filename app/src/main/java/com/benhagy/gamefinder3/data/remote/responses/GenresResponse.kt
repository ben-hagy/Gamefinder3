package com.benhagy.gamefinder3.data.remote.responses

// genre responses come in a "results" list, which requires this response object

data class GenresResponse(
    val results: List<GenreResponse>
)
