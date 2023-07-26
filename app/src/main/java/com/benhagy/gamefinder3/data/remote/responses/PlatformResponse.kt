package com.benhagy.gamefinder3.data.remote.responses

// platforms responses are structured differently from other responses for some reason
// requires this intermediary response object

data class PlatformResponse(
    val platform: PlatformResponseObjects
)
