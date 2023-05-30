package com.benhagy.gamefinder3.domain.models

data class ListedGame(
    val id: Int,
    val name: String,
    val backgroundImage: String?,
    val platforms: List<Platform>?,
)
