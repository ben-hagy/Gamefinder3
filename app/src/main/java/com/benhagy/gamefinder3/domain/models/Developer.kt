package com.benhagy.gamefinder3.domain.models

// primary model for Developer objects
// response objects are mapped to this model for use in our screens

data class Developer(
    val id: Int?,
    val name: String?,
    val backgroundImage: String?
)
