package com.benhagy.gamefinder3.data.remote.responses

import com.google.gson.annotations.SerializedName

// platforms are structured differently from other responses for some reason; requires this
// intermediary response object

data class PlatformResponse(
    val platform: PlatformResponseObjects,
    @SerializedName("released_at")
    val releasedAt: String?
)
