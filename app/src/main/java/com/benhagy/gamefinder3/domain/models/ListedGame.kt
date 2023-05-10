package com.benhagy.gamefinder3.domain.models

data class ListedGame(
    val id: Int,
    val name: String,
    val released: String?,
    val backgroundImage: String?
)
