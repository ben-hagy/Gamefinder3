package com.benhagy.gamefinder3.data.remote.responses

import com.benhagy.gamefinder3.domain.models.Platform

// response object and mapper for Platforms

data class PlatformResponseObjects(
    val id: Int?,
    val name: String?,
) {
    fun toPlatform(): Platform {
        return Platform(id = id, name = name)
    }
}