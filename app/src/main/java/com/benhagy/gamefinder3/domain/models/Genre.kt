package com.benhagy.gamefinder3.domain.models

// primary model for Genre objects
// response objects are mapped to this model for use in our screens


data class Genre(
    val id: Int?,
    val name: String?,
    val backgroundImage: String?
)