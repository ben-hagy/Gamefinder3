package com.benhagy.gamefinder3.data.remote.responses

import com.benhagy.gamefinder3.domain.models.Tag

// response object and mapper for Tags

data class TagResponse(
    val id: Int?,
    val name: String?,
) {
    fun toTag(): Tag {
        return Tag(id = id, name = name)
    }
}