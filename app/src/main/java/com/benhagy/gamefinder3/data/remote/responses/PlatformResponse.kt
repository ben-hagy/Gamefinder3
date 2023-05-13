package com.benhagy.gamefinder3.data.remote.responses

import com.google.gson.annotations.SerializedName

data class PlatformResponse(
    val platform: PlatformResponseObjects,
    @SerializedName("released_at")
    val releasedAt: String?
)
