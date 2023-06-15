package com.benhagy.gamefinder3.domain.models

// primary model for Listed Game objects
// response objects are mapped to this model for use in our screens


data class ListedGame(
    val id: Int,
    val name: String,
    val backgroundImage: String?,
    val platforms: List<Platform>?,
)
